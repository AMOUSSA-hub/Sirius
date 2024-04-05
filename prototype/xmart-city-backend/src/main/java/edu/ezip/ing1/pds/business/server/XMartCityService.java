package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Player;
import edu.ezip.ing1.pds.business.dto.Players;
import edu.ezip.ing1.pds.business.dto.TeamEvent;
import edu.ezip.ing1.pds.business.dto.TeamEvents;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL_PLAYERS("SELECT joueurs.nom,joueurs.prenom,joueurs.numero,joueurs.datenaissance,joueurs.nationalite,joueurs.poste,joueurs.pied,joueurs.taille,joueurs.poids,joueurs.id_joueurs,joueurs_id_joueurs_seq.last_value,joueurs.photo,contrat_.datefin,contrat_.salaire from joueurs_id_joueurs_seq,joueurs JOIN contrat_ ON contrat_.id_Joueurs = joueurs.id_joueurs;"),
        INSERT_STUDENT("INSERT into \"ezip-ing1\".players (\"name\", \"firstname\", \"group\") values (?, ?, ?)"),
        INSERT_PLAYER("Insert into joueurs(nom,prenom,numero,datenaissance,nationalite,poste,pied,taille,poids,photo) values(?,?,?,?,?,?,?,?,?,?) RETURNING id_joueurs"),
        INSERT_PLAYER_CONTRAT("INSERT INTO contrat_(datefin,salaire,id_joueurs) values(?,?,?)"),
        INSERT_STATS("INSERT INTO a_joue_le_match(id_joueurs,id_matchs,buts,passesdecisives,cartonsjaunes,cartonsrouges,notedumatch,minutesjouees) values(?,?,?,?,?,?,?,?)"),
        INSERT_EVENTS("INSERT INTO evenement(date_debut,date_fin,type,label) values(?,?,?,?)"),
        UPDATE_PLAYER("Update joueurs set = ? where id_joueurs = ? "),
        DELETE_PLAYER("Delete from joueurs where id_joueurs = ? "),
        SELECT_ALL_EVENTS("SELECT * from evenement"),
        SELECT_ALL_STATS("SELECT * from a_joue_le_match"),
        DELETE_EVENT("Delete from evenement where id_evenement = ? ");
        private String query;
        
        private Queries(final String query) {
            this.query = query;
        }
    }

    public static XMartCityService inst = null;
    public static final XMartCityService getInstance()  {
        if(inst == null) {
            inst = new XMartCityService();
        }
        return inst;
    }

    private XMartCityService() {

    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException,SQLException,JsonProcessingException {
        Response response =new Response();

        if(request.getRequestOrder().equals("SELECT_ALL_EVENTS")){
            TeamEvents events = new TeamEvents(); 
            PreparedStatement preparedStatement = connection.prepareStatement(XMartCityService.getQuery(request.getRequestOrder()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                int id = (resultSet.getInt("id_evenement"));
                Date date_debut = resultSet.getDate("date_debut");           
                Date date_fin = resultSet.getDate("date_fin");    
                String type = resultSet.getString("type");
                String label = resultSet.getString("label");
                events.add(new TeamEvent(id, label, type,date_debut, date_fin));
                System.out.println(events.toString());
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String data = objectMapper.writeValueAsString(events);
            response.setRequestId(request.getRequestId());
            response.setResponseBody(data);
            System.out.println(response.getResponseBody());

            
        }


        return response;
    }

    public static String getQuery(String queries) {
        if (queries.equals("SELECT_ALL_PLAYERS")) {
            return Queries.SELECT_ALL_PLAYERS.query;
        }
        if (queries.equals("INSERT_STUDENT")) {
            return Queries.INSERT_STUDENT.query;
        }
        if (queries.equals("INSERT_PLAYER")) {
            return Queries.INSERT_PLAYER.query;
        }
        if (queries.equals("INSERT_STATS")) {
            return Queries.INSERT_STATS.query;
        }
        if (queries.equals("INSERT_EVENTS")) {
            return Queries.INSERT_EVENTS.query;
        }
        if (queries.equals("UPDATE_PLAYER")) {
            return Queries.UPDATE_PLAYER.query;
        }

        if (queries.equals("SELECT_ALL_EVENTS")) {
            return Queries.SELECT_ALL_EVENTS.query;
        }
        if (queries.equals("SELECT_ALL_STATS")) {
            return Queries.SELECT_ALL_STATS.query;
        }

        if (queries.equals("INSERT_PLAYER_CONTRAT")) {
            return Queries.INSERT_PLAYER_CONTRAT.query;
        }

        if (queries.equals("DELETE_PLAYER")){
            return Queries.DELETE_PLAYER.query;
        }

        if (queries.equals("DELETE_EVENT")){
            return Queries.DELETE_EVENT.query;
        }
        return null;
    }

    public static void putColumnNameForUpdate(String name){
        Queries.UPDATE_PLAYER.query = "Update joueurs set " + name + " = ? where id_joueurs = ? ";
    }
}
