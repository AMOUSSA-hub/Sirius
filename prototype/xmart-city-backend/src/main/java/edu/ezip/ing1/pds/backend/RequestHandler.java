package edu.ezip.ing1.pds.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Player;
import edu.ezip.ing1.pds.business.dto.Players;
import edu.ezip.ing1.pds.business.dto.Student;
import edu.ezip.ing1.pds.business.dto.Students;
import edu.ezip.ing1.pds.business.server.XMartCityService;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.sql.Date;
import com.fasterxml.jackson.databind.JsonNode;


public class RequestHandler implements Runnable {
    private final Socket socket;
    private final Connection connection;
    private final Thread self;
    private static final String threadNamePrfx = "core-request-handler";
    private final InputStream instream;
    private final OutputStream outstream;
    // private final Connection connection;
    private final static String LoggingLabel = "C o re - B a c k e n d - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private int requestCount = 0;

    private final XMartCityService xmartCityService = XMartCityService.getInstance();
    private final CoreBackendServer father;

    private static final int maxTimeLapToGetAClientPayloadInMs = 5000;
    private static final int timeStepMs = 300;
    private final BlockingDeque<Integer> waitArtifact = new LinkedBlockingDeque<Integer>(1);

    public Players players;

    protected RequestHandler(final Socket socket,
                             final Connection connection,
                             final int myBirthDate,
                             final CoreBackendServer father) throws IOException {
        this.socket = socket;
        this.connection = connection;
        this.father = father;
        final StringBuffer threadName = new StringBuffer();
        threadName.append(threadNamePrfx).append("★").append(String.format("%04d",myBirthDate));
        self = new Thread(this, threadName.toString());
        instream = socket.getInputStream();
        outstream = socket.getOutputStream();
        self.start();
    }



    @Override
    public void run() {
        try {

            int timeout = maxTimeLapToGetAClientPayloadInMs;
            while (0 == instream.available() && 0 < timeout) {
                waitArtifact.pollFirst(timeStepMs, TimeUnit.MILLISECONDS);
                timeout-=timeStepMs;
            }
            if (0>timeout) return;

            final byte [] inputData = new byte[instream.available()];
            instream.read(inputData);
            final Request request = getRequest(inputData);
            execSql(request);
            final Response response = xmartCityService.dispatch(request, connection);
            final byte [] outoutData = getResponse(response);
            //LoggingUtils.logDataMultiLine(logger, Level.DEBUG, outoutData);
            outstream.write(outoutData);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            father.completeRequestHandler(this);
        }
    }

    private final Request getRequest(byte [] data) throws IOException {
        logger.debug("data received {} bytes", data.length);
        //LoggingUtils.logDataMultiLine(logger, Level.DEBUG, data);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        final Request request = mapper.readValue(data, Request.class);
        logger.debug(request.toString());
        return request;
    }

    private final byte [] getResponse(final Response response) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        //mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(response);
    }

    public final Connection getConnection() {
        return connection;
    }

    public final Socket getSocket() {
        return socket;
    }

    private void execSql(Request request) {
        String requestOrder = request.getRequestOrder();
        String requestBody;
        String sql = XMartCityService.getQuery(requestOrder);
        players = new Players();
        if (requestOrder.equals("SELECT_ALL_STUDENTS")) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String nom = (resultSet.getString(1));
                    String prenom = (resultSet.getString(2));                
                    int numero = (resultSet.getInt(3));
                    Date date = resultSet.getDate(4);
                    String poste = resultSet.getString(5);
                    String nation = resultSet.getString(6);
                    String pied = resultSet.getString(7);
                    int taille = resultSet.getInt(8);
                    int poids = resultSet.getInt(9);
                    players.add(new Player(prenom, nom, date, nation, Date.valueOf(LocalDate.now()), 0, poste, taille,numero,poids,pied));
                }
                ObjectMapper objectMapper = new ObjectMapper();
                String data = objectMapper.writeValueAsString(players);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                LoggingUtils.logDataMultiLine(logger,Level.DEBUG,getResponse(response));
                sendResponse(response, socket);
            } catch (Exception e) {
                System.err.println(e);
                
            }
        }
         if (requestOrder.equals("INSERT_STUDENT")) {
            try {
                requestBody = request.getRequestBody();
                ObjectMapper objectMapper = new ObjectMapper();

                JsonNode jsonNode = objectMapper.readTree(requestBody);

                String studentName = jsonNode.get("student_name").asText();
                String studentFirstname = jsonNode.get("student_1stname").asText();
                String studentGroup = jsonNode.get("student_group").asText();

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, studentName);
                preparedStatement.setString(2, studentFirstname);
                preparedStatement.setString(3, studentGroup);
                int resultat = preparedStatement.executeUpdate();
                objectMapper = new ObjectMapper();
                String data = objectMapper.writeValueAsString(resultat);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                sendResponse(response, socket);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        if (requestOrder.equals("INSERT_PLAYER")){
            try {
            requestBody = request.getRequestBody();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                //System.out.println(jsonNode.toString());
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                String nom = jsonNode.get("nom").asText();
                String prenom = jsonNode.get("prenom").asText();
                int numero = jsonNode.get("numero").asInt();
                long dateText = jsonNode.get("naissance").asLong();
                String poste = jsonNode.get("position").asText();
                String pied = jsonNode.get("pied").asText();
                int taille = jsonNode.get("taille").asInt();
                int poids = jsonNode.get("poids").asInt();
                String nation = jsonNode.get("nationalite").asText();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date sqlDate = new java.sql.Date(dateText);
                //System.out.println(sqlDate);
                preparedStatement.setString(1,nom);
                preparedStatement.setString(2, prenom);
                preparedStatement.setInt(3, numero);
                preparedStatement.setDate(4, sqlDate);
                preparedStatement.setString(5, nation);
                preparedStatement.setString(6, poste);
                preparedStatement.setString(7, pied);
                preparedStatement.setInt(8, taille);
                preparedStatement.setInt(9, poids);

                int resultat = preparedStatement.executeUpdate();
                objectMapper = new ObjectMapper();
                String data = objectMapper.writeValueAsString(resultat);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                sendResponse(response, socket);
            }catch(Exception e){
                System.err.println(e);

            }
        } 

    }

    public void sendResponse(Response response, Socket clientSocket) {
        try {
            if (clientSocket.isConnected() && !clientSocket.isClosed()) {
                OutputStream outputStream = clientSocket.getOutputStream();
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(response);
                outputStream.write(jsonResponse.getBytes());
                outputStream.flush(); 
            } else {
                System.err.println("Le client n'est pas joignable");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    


}
