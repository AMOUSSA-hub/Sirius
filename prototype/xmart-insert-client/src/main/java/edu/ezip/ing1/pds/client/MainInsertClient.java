package edu.ezip.ing1.pds.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ezip.ing1.pds.business.dto.Player;
import edu.ezip.ing1.pds.business.dto.Student;
import edu.ezip.ing1.pds.business.dto.Students;
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
//import client.frontend.Player;


public class MainInsertClient {

    private static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String studentsToBeInserted = "player-to-be-inserted.yaml";
    private final static String networkConfigFile = "network.yaml";
    private static String threadName = "inserter-client";
    private static  String requestOrder = "INSERT_PLAYER";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private static final String ipBDD = "172.31.253.218";
    private static final String bdd = "test";
    private static final String user = "toto";
    private static final String password = "toto";
    private static final int port = 5432;
    public static final String ipServeur = "172.31.252.86";
    private static Socket socket;
    public static void main(String[] args) throws IOException, InterruptedException, SQLException, ClassNotFoundException {

        final Students guys = ConfigLoader.loadConfig(Students.class, studentsToBeInserted);
        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Students loaded : {}", guys.toString());
        networkConfig.setIpaddress(ipBDD);
        networkConfig.setTcpport(port);
        
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

    public static void sendPlayer(Player j) throws Exception {
        LoggingLabel = "I n s e r t e r - C l i e n t";
        logger = LoggerFactory.getLogger(LoggingLabel);
        requestOrder = "INSERT_PLAYER";
        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Students loaded : {}", j.toString());
        networkConfig.setIpaddress(ipBDD);
        networkConfig.setTcpport(port);
        int birthdate = 0;
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

            final InsertStudentsClientRequest clientRequest = new InsertStudentsClientRequest (
                                                                        networkConfig,
                                                                        birthdate++, request, j, requestBytes);
            clientRequests.push(clientRequest);

            String jsonRequest = objectMapper.writeValueAsString(request);
        
        try  {
            socket = new Socket(ipServeur, 45065);
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
    }
}
    public static void updatePlayer(Player j) throws Exception {
        LoggingLabel = "U P D A T E R - C l i e n t";
        logger = LoggerFactory.getLogger(LoggingLabel);
        requestOrder = "UPDATE_PLAYER";
        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Students loaded : {}", j.toString());
        networkConfig.setIpaddress(ipBDD);
        networkConfig.setTcpport(port);
        int birthdate = 0;
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

            final InsertStudentsClientRequest clientRequest = new InsertStudentsClientRequest (
                                                                        networkConfig,
                                                                        birthdate++, request, j, requestBytes);
            clientRequests.push(clientRequest);

            String jsonRequest = objectMapper.writeValueAsString(request);
        
        try  {
            socket = new Socket(ipServeur, 45065);
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
    }
    
}
}

