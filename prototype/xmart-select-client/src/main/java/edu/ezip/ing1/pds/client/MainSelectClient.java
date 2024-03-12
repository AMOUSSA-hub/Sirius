package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import client.frontend.InfosJoueurs;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.UUID;
import java.io.OutputStream;
import java.net.Socket;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileWriter;
import java.sql.Date;

public class MainSelectClient {

    private final static String LoggingLabel = "S e l e c t - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String studentsToBeInserted = "students-to-be-inserted.yaml";
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";
    private static final String requestOrder = "SELECT_ALL_STUDENTS";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    public static final String ipServeur = "172.31.252.86";
    private static Socket socket;

    public static void main(String[] args) throws IOException, InterruptedException, SQLException, Exception {

        
    }

    public static void selectAllPlayers(List<InfosJoueurs> list) throws Exception{
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        networkConfig.setIpaddress("172.31.253.218");
        networkConfig.setTcpport(5432);
        logger.debug("Load Network config file : {}", networkConfig.toString());
    
        int birthdate = 0;
       
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);  
        request.setRequestOrder(requestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllStudentsClientRequest clientRequest = new SelectAllStudentsClientRequest(
                                                                    networkConfig,
                                                                    birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);
        String jsonRequest = objectMapper.writeValueAsString(request);
        
      
        try {
            socket = new Socket(ipServeur, 45065);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(jsonRequest.getBytes());
            outputStream.flush(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        
        while (!clientRequests.isEmpty()) {

            final ClientRequest joinedClientRequest = clientRequests.pop();
            //joinedClientRequest.join();

            String res = getReponseServeur(socket);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.readTree(res);
            JsonNode studentsNode = responseNode.get("response_body").get("students");
            String fileName = "Select.txt";
        try {
            int nbJoueurs = studentsNode.size();
            System.out.println("Nombre de joueurs selectionnés : " + nbJoueurs + "\n");
            FileWriter fileWriter = new FileWriter(fileName, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            //writeString(bufferedWriter,String.valueOf(nbJoueurs));
            for (JsonNode studentNode : studentsNode) {
                String nom = studentNode.get("nom").asText();
                String prenom = studentNode.get("prenom").asText();
                int numero = studentNode.get("numero").asInt();
                Long date = (studentNode.get("naissance").asLong());
                Date date2 = new Date(date);
                //String date3 = date2.toString();
                String nationalite = studentNode.get("nationalite").asText();
                String poste = studentNode.get("poste").asText();
                String pied = studentNode.get("pied").asText();
                int taille = studentNode.get("taille").asInt();
                int poids = studentNode.get("poids").asInt();
                list.add(new InfosJoueurs(prenom, nom, date2, nationalite, Date.valueOf(LocalDate.now()), 0, poste, taille, numero, poids, pied));
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la création ou de l'écriture dans le fichier : " + e.getMessage());
        }     
    
        


            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
        }
    }

    public static String getReponseServeur(Socket socket) {
        try {
            if (socket.isConnected() && !socket.isClosed()) {
                InputStream inputStream = socket.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                String response = outputStream.toString("UTF-8");
                outputStream.close();
                inputStream.close();
                return response;
            } else {
                System.err.println("Le client n'est pas joignable");            
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }
    
    private static void writeString(BufferedWriter b,String attribut) throws Exception{
        b.write(attribut);
        b.newLine();
    }

    private static void writeInt(BufferedWriter b,int attribut) throws Exception{
        b.write(attribut);
        b.newLine();
    }

}
