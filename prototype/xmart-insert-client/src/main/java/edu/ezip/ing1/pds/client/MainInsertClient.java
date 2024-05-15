package edu.ezip.ing1.pds.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.*;
import java.io.OutputStream;
import java.net.Socket;
import java.io.ByteArrayOutputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import client.frontend.Player;


public class MainInsertClient {

    private static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String studentsToBeInserted = "player-to-be-inserted.yaml";
    private final static String networkConfigFile = "network.yaml";
    private static String threadName = "inserter-client";
    private static  String requestOrder = "INSERT_PLAYER";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    //public static final String ipServeur = "172.31.252.86";
    private static Socket socket;
    static JsonNodeFactory factory = JsonNodeFactory.instance;
    public static void main(String[] args) throws IOException, InterruptedException, SQLException, ClassNotFoundException {

        final Players guys = ConfigLoader.loadConfig(Players.class, studentsToBeInserted);
        logger.trace("Students loaded : {}", guys.toString());
        //networkConfig.setIpaddress(ipBDD);
        //networkConfig.setTcpport(port);
        
        int birthdate = 0;
        
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

    public static int sendRequest(Data j,String requestOrder) {
        LoggingLabel = "I n s e r t e r - C l i e n t";
        logger = LoggerFactory.getLogger(LoggingLabel);
        //requestOrder = requestOrder1;
        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Students loaded : {}", j.toString());
        //networkConfig.setIpaddress(ipBDD);
        //networkConfig.setTcpport(port);
        //System.out.println(networkConfig.getIpaddress() + " " + networkConfig.getTcpport());
        int birthdate = 0;
        try {
            

            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(j);
            logger.trace("Student with its JSON face : {}", jsonifiedGuy);
            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(requestOrder);
            request.setRequestContent(jsonifiedGuy);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            final InsertPlayersClientRequestRequest clientRequest = new InsertPlayersClientRequestRequest (
                                                                        networkConfig,
                                                                        birthdate++, request, j, requestBytes);
            clientRequests.push(clientRequest);
            Thread insert = clientRequest.getThread();
            insert.join();
            return Integer.parseInt(clientRequest.getResponse());
        } catch (Exception e) {
            System.err.println(e);
        }
        return 0;
/*         try  {
            socket = new Socket(networkConfig.getIpaddress(), networkConfig.getTcpport());
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(jsonRequest.getBytes());
            outputStream.flush(); 
            String str = getReponseServeur(socket);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(str);
            JsonNode responseBody = jsonResponse.get("response_body");
            clientRequest.setResponse(responseBody.asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

/*         while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            //clientRequest.join();
            final Player guy = (Player)clientRequest2.getInfo();

            logger.debug("Thread {} complete : {} {} {} --> {}",
                                    clientRequest2.getThreadName(),
                                    guy.getNom(), guy.getPrenom(), guy.getNumero(),
                                    clientRequest2.getResponse());
    } */
}
    public static int updateRequest(Data j,HashMap<String,Object> thingsToChange,String requestOrder) {
      try {
        

        LoggingLabel = "U P D A T E R - C l i e n t";
        logger = LoggerFactory.getLogger(LoggingLabel);
        //requestOrder = "UPDATE_PLAYER";
        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Students loaded : {}", j.toString());
        //networkConfig.setIpaddress(ipBDD);
        //networkConfig.setTcpport(port);
        int birthdate = 0;
            final ObjectMapper objectMapper = new ObjectMapper();  
            ObjectNode rootNode = factory.objectNode();
            for (String attribut : thingsToChange.keySet()){
                rootNode.put(attribut, thingsToChange.get(attribut).toString());

            }
            rootNode.put("id", j.getId());
            final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            logger.trace("Student with its JSON face : {}", jsonifiedGuy);
            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(requestOrder);
            request.setRequestContent(jsonifiedGuy);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            final InsertPlayersClientRequestRequest clientRequest = new InsertPlayersClientRequestRequest (
                                                                        networkConfig,
                                                                        birthdate++, request, j, requestBytes);
            clientRequests.push(clientRequest);
            Thread insert = clientRequest.getThread();
            while (insert.isAlive()) {
                //Waiting the thread to die
            }
            return Integer.parseInt(clientRequest.getResponse());
        } catch (Exception e) {
            System.err.println(e);
        }
        return 0;

           // String jsonRequest = objectMapper.writeValueAsString(request);
        
/*         try  {
            socket = new Socket(networkConfig.getIpaddress(), networkConfig.getTcpport());
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(jsonRequest.getBytes());
            outputStream.flush(); 
            String str = getReponseServeur(socket);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(str);
            JsonNode responseBody = jsonResponse.get("response_body");
            clientRequest.setResponse(responseBody.asText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            //clientRequest.join();
            final Player guy = (Player)clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                                    clientRequest2.getThreadName(),
                                    guy.getNom(), guy.getPrenom(), guy.getNumero(),
                                    clientRequest2.getResponse());
    } */
    
}
    public static int deleteRequest(Data j,String requestOrder) {
        try {

        LoggingLabel = "D E L E T E - C l i e n t";
        logger = LoggerFactory.getLogger(LoggingLabel);
        //requestOrder = "DELETE_PLAYER";
        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Students loaded : {}", j.toString());
        //networkConfig.setIpaddress(ipBDD);
        //networkConfig.setTcpport(port);
        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();  
        ObjectNode rootNode = factory.objectNode();
        rootNode.put("id", j.getId());
        final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            logger.trace("Student with its JSON face : {}", jsonifiedGuy);
            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(requestOrder);
            request.setRequestContent(jsonifiedGuy);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            final InsertPlayersClientRequestRequest clientRequest = new InsertPlayersClientRequestRequest (
                                                                        networkConfig,
                                                                        birthdate++, request, j, requestBytes);
            clientRequests.push(clientRequest);
            Thread insert = clientRequest.getThread();
            while (insert.isAlive()) {
                //Waiting the thread to die
            }
            return Integer.parseInt(clientRequest.getResponse());
        } catch (Exception e) {
            System.err.println(e);
        }
        return 0;
    }

}

