package edu.ezip.ing1.pds.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Player;
import edu.ezip.ing1.pds.business.dto.Players;
import edu.ezip.ing1.pds.business.dto.TeamEvent;
import edu.ezip.ing1.pds.business.dto.TeamEvents;
import edu.ezip.ing1.pds.business.dto.Stats;
import edu.ezip.ing1.pds.business.dto.Stat;
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
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.sql.Date;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestHandler implements Runnable {
    private final Socket socket;
    private final Connection connection;
    private final Thread self;
    private static final String threadNamePrfx = "core-request-handler";
    private final InputStream instream;
    private final OutputStream outstream;
    private final static String LoggingLabel = "Core-Backend-Server";
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

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            father.completeRequestHandler(this);
        }
    }

    private final Request getRequest(byte [] data) throws IOException {
        logger.debug("data received {} bytes", data.length);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        final Request request = mapper.readValue(data, Request.class);
        logger.debug(request.toString());
        return request;
    }

    private final byte [] getResponse(final Response response) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
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
        ObjectMapper objectMapper = new ObjectMapper();
        players = new Players();
        if (requestOrder.equals("SELECT_ALL_PLAYERS")) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String nom = resultSet.getString(1);
                    String prenom = resultSet.getString(2);
                    int numero = resultSet.getInt(3);
                    Date date = resultSet.getDate(4);
                    String nation = resultSet.getString(5);
                    String poste = resultSet.getString(6);
                    String pied = resultSet.getString(7);
                    int taille = resultSet.getInt(8);
                    int poids = resultSet.getInt(9);
                    int id = resultSet.getInt(10);
                    int last_value = resultSet.getInt(11);
                    byte[] photo = resultSet.getBytes(12);
                    Date dateContrat = resultSet.getDate(13);
                    int salaire = resultSet.getInt(14);
                    players.add(new Player(nom, prenom, date, numero, poste, pied, taille, poids,nation,id,last_value,photo,dateContrat,salaire));
                }
                String data = objectMapper.writeValueAsString(players);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                LoggingUtils.logDataMultiLine(logger, Level.DEBUG, getResponse(response));
                sendResponse(response);
            } catch (Exception e) {
                System.err.println(e);
            }
        } else if (requestOrder.equals("SELECT_ALL_EVENTS")) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                TeamEvents events = new TeamEvents();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_evenement");
                    Date date_debut = resultSet.getDate("date_debut");
                    Date date_fin = resultSet.getDate("date_fin");
                    String type = resultSet.getString("type");
                    String label = resultSet.getString("label");
                    events.add(new TeamEvent(id, label, type, date_debut, date_fin));
                }
                String data = objectMapper.writeValueAsString(events);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                LoggingUtils.logDataMultiLine(logger, Level.DEBUG, getResponse(response));
                sendResponse(response);
            } catch (Exception e) {
                System.err.println(e);
            }
        } else if (requestOrder.equals("SELECT_ALL_STATS")) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                Stats stats = new Stats();
                while (resultSet.next()) {
                    short buts = resultSet.getShort("buts");
                    short passesdecisives = resultSet.getShort("passesdecisives");
                    short cartonsjaunes = resultSet.getShort("cartonsjaunes");
                    short cartonsrouges = resultSet.getShort("cartonsrouges");
                    short notedumatch = resultSet.getShort("notedumatch");
                    short minutesjouees = resultSet.getShort("minutesjouees");
                    int id_joueurs = resultSet.getInt("id_joueurs");
                    int id_matchs = resultSet.getInt("id_matchs");

                    System.out.println("Buts: "+buts);
                    stats.add(new Stat(buts, passesdecisives, cartonsjaunes,cartonsrouges,notedumatch,minutesjouees, id_joueurs, id_matchs));
                }
                String data = objectMapper.writeValueAsString(stats);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                LoggingUtils.logDataMultiLine(logger, Level.DEBUG, getResponse(response));
                sendResponse(response);
            } catch (Exception e) {
                System.err.println(e);
            }
        } else if (requestOrder.equals("INSERT_STUDENT")) {
            try {
                requestBody = request.getRequestBody();
                JsonNode jsonNode = objectMapper.readTree(requestBody);

                String studentName = jsonNode.get("student_name").asText();
                String studentFirstname = jsonNode.get("student_1stname").asText();
                String studentGroup = jsonNode.get("student_group").asText();

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, studentName);
                preparedStatement.setString(2, studentFirstname);
                preparedStatement.setString(3, studentGroup);
                int resultat = preparedStatement.executeUpdate();
                String data = objectMapper.writeValueAsString(resultat);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                sendResponse(response);
            } catch (Exception e) {
                System.err.println(e);
            }
        } else if (requestOrder.equals("INSERT_PLAYER")) {
            try {
                connection.setAutoCommit(false);
                requestBody = request.getRequestBody();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
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
                JsonNode photoNode = jsonNode.get("photo");
                byte[] photo = objectMapper.convertValue(photoNode, byte[].class);
                java.sql.Date sqlDate = new java.sql.Date(dateText);
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, prenom);
                preparedStatement.setInt(3, numero);
                preparedStatement.setDate(4, sqlDate);
                preparedStatement.setString(5, nation);
                preparedStatement.setString(6, poste);
                preparedStatement.setString(7, pied);
                preparedStatement.setInt(8, taille);
                preparedStatement.setInt(9, poids);
                preparedStatement.setBytes(10, photo);
                ResultSet res = preparedStatement.executeQuery();
                int id_Joueurs = -1;
                if (res.next()) {
                    id_Joueurs = res.getInt(1);
                }
                sql = XMartCityService.getQuery("INSERT_PLAYER_CONTRAT");
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql);
                Date dateFin = new Date(jsonNode.get("contrat").asLong());
                int salaire = jsonNode.get("salaire").asInt();
                preparedStatement2.setDate(1, dateFin);
                preparedStatement2.setInt(2, salaire);
                preparedStatement2.setInt(3, id_Joueurs);
                int resultat = preparedStatement2.executeUpdate();
                connection.commit();
                //connection.setAutoCommit(true);
                String data = objectMapper.writeValueAsString(resultat);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                sendResponse(response);
            } catch (Exception e) {
                System.err.println(e);
            }
            
        } 
        else if (requestOrder.equals("INSERT_STATS")) {
            try {
                requestBody = request.getRequestBody();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                int id_joueurs = jsonNode.get("id_joueurs").asInt();
                int id_matchs = jsonNode.get("id_matchs").asInt();
                short buts = (short)jsonNode.get("buts").asInt();
                short passesdecisives = (short)jsonNode.get("passesdecisives").asInt();
                short cartonsjaunes = (short)jsonNode.get("cartonsjaunes").asInt();
                short cartonsrouges = (short)jsonNode.get("cartonsrouges").asInt();
                short notedumatch = (short)jsonNode.get("notedumatch").asInt();
                short minutesjouees = (short)jsonNode.get("minutesjouees").asInt();
                preparedStatement.setInt(1, id_joueurs);
                preparedStatement.setInt(2, id_matchs);
                preparedStatement.setShort(3, buts);
                preparedStatement.setShort(4, passesdecisives);
                preparedStatement.setShort(5, cartonsjaunes);
                preparedStatement.setShort(6, cartonsrouges);
                preparedStatement.setShort(7, notedumatch);
                preparedStatement.setShort(8, minutesjouees);
                int resultat = preparedStatement.executeUpdate();
                String data = objectMapper.writeValueAsString(resultat);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                sendResponse(response);
                }catch (Exception e) {
                    System.err.println(e);
                }
        }
        else if (requestOrder.equals("INSERT_EVENTS")) {
            try {
                requestBody = request.getRequestBody();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                Date date_debut = new Date(jsonNode.get("date_debut").asLong());
                Date date_fin = new Date(jsonNode.get("date_fin").asLong());             
                String type = jsonNode.get("type").asText();
                String label = jsonNode.get("label").asText();
                preparedStatement.setDate(1, date_debut);
                preparedStatement.setDate(2, date_fin);
                preparedStatement.setString(3, type);
                preparedStatement.setString(4, label);
                int resultat = preparedStatement.executeUpdate();
                String data = objectMapper.writeValueAsString(resultat);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                sendResponse(response);
                }catch (Exception e) {
                    System.err.println(e);
                }
                
        }else if (requestOrder.equals("UPDATE_PLAYER")) {
            try {
                requestBody = request.getRequestBody();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                String attribut = jsonNode.fieldNames().next();
                XMartCityService.putColumnNameForUpdate(attribut);
                sql = XMartCityService.getQuery("UPDATE_PLAYER");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println(preparedStatement.toString());
                String valueString = "";
                int valueInt = 0;
                Long valueLong = null;
                byte[] valueByte = null;
                if (attribut.equals("nom") || attribut.equals("prenom") || attribut.equals("poste") || attribut.equals("pied") || attribut.equals("nationalite")) {
                    valueString = jsonNode.get(attribut).asText();
                    preparedStatement.setString(1, valueString);
                } else if (attribut.equals("taille") || attribut.equals("poids") || attribut.equals("numero")) {
                    valueInt = jsonNode.get(attribut).asInt();
                    preparedStatement.setInt(1, valueInt);
                } else if (attribut.equals("naissance")) {
                    valueLong = jsonNode.get(attribut).asLong();
                    java.sql.Date sqlDate = new java.sql.Date(valueLong);
                    preparedStatement.setDate(1, sqlDate);
                } else if (attribut.equals("photo")){
                    String base64Photo = jsonNode.get("photo").asText();
                    byte[] photoBytes = Base64.getMimeDecoder().decode(base64Photo);
                    preparedStatement.setBytes(1,photoBytes);
                    //preparedStatement.setBytes(1,photoBytes);
                    System.out.println(preparedStatement.toString());
                }
                int id = jsonNode.get("id").asInt();
                preparedStatement.setInt(2, id);
                int resultat = preparedStatement.executeUpdate();
                String data = objectMapper.writeValueAsString(resultat);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                sendResponse(response);
            } catch (Exception e) {
                System.err.println(e);
            }
        } else if (requestOrder.equals("DELETE_PLAYER")) {
            try {
                requestBody = request.getRequestBody();
                JsonNode jsonNode = objectMapper.readTree(requestBody);
                int id = jsonNode.get("id").asInt();
                PreparedStatement preparedStatement = null;

/*                 connection.setAutoCommit(false);
                PreparedStatement preparedStatement = null;
            // Supprimer les enregistrements dans la table contrat_ avec une clé étrangère correspondant à l'id_joueurs
            String deleteContratQuery = "DELETE FROM contrat_ WHERE id_joueurs = ?";
            preparedStatement = connection.prepareStatement(deleteContratQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            // Supprimer les enregistrements dans la table a_joue_le_match avec une clé étrangère correspondant à l'id_joueurs
            String deleteAJoueLeMatchQuery = "DELETE FROM a_joue_le_match WHERE id_joueurs = ?";
            preparedStatement = connection.prepareStatement(deleteAJoueLeMatchQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate(); */

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

                int resultat = preparedStatement.executeUpdate();
                String data = objectMapper.writeValueAsString(resultat);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                sendResponse(response);
            } catch (Exception e) {
                System.err.println(e);
            }

        }


        else if (requestOrder.equals("DELETE_EVENT")) {
            try {
                requestBody = request.getRequestBody();
            
                 JsonNode jsonNode = objectMapper.readTree(requestBody);
                 int id = jsonNode.get("id").asInt();

                 System.out.println("blalbal");

             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setInt(1, id);
             System.out.println( preparedStatement.toString());

                 int resultat = preparedStatement.executeUpdate();
                String data = objectMapper.writeValueAsString(resultat);
                Response response = new Response();
                response.setRequestId(request.getRequestId());
                response.setResponseBody(data);
                sendResponse(response);
            } catch (Exception e) {
                System.err.println(e);
            }

        }
    }

    public void sendResponse(Response response) {
        try {
            if (socket.isConnected() && !socket.isClosed()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(response);
                outstream.write(jsonResponse.getBytes());
                outstream.flush();
            } else {
                System.err.println("Le client n'est pas joignable");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

