package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Date;
import java.util.HashMap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Field;

@JsonRootName(value = "event")
public class TeamEvent extends Data {
    private String label,type;
    Date date_debut, date_fin;


    public enum Type {
        TRAINING("TRAINING"),
        GAME("GAME"),
        FRIENDY_GAME("FRIENDLY_GAME");
        public String type;
        
        private Type(final String type) {
            this.type= type;
        }

    }

    




    public static String[] getAllTypeEvent(){

        Type[] types = Type.values();

        // Création d'un tableau de chaînes de caractères
        String[] typeStrings = new String[types.length];

        // Remplissage du tableau avec les chaînes correspondantes
        for (int i = 0; i < types.length; i++) {
            typeStrings[i] = types[i].type;
        }

        return typeStrings;

    }


    public TeamEvent(){}
    
    public TeamEvent(int id,String label,String type, Date date_debut,Date date_fin ){
        this.id  = id;
        this.label = label;
        this.type = type;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }



    public final TeamEvent build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "id", "label","type","date_debut","date_fin");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement,id+"", label,type,date_debut.toString(), date_fin.toString());
    }






    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("date_debut")
    public void setDateDebut(Date date_debut) {
        this.date_debut = date_debut;
    }

    @JsonProperty("date_fin")
    public void setDateFin(Date date_fin) {
        this.date_fin = date_fin;
    }


    public int getId() {
        return id;
    }
    
    public String getLabel() {
        return label;
    }
    
    public String getType() {
        return type;
    }
    
    public Date getDateDebut() {
        return date_debut;
    }
    
    public Date getDateFin() {
        return date_fin;
    }



        private void setFieldsFromResulset(final ResultSet resultSet, final String ... fieldNames )throws NoSuchFieldException, SQLException, IllegalAccessException {
            for(final String fieldName : fieldNames ) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
    }
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final String ... fieldNames )
                throws NoSuchFieldException, SQLException, IllegalAccessException {
            int ix = 0;
            for(final String fieldName : fieldNames ) {
                preparedStatement.setString(++ix, fieldName);
            }
            return preparedStatement;
        }



        @Override
        public String toString() {
            return "Event{" +
                    "id='" + id + '\'' +
                    ", label='" + label + '\'' +
                    ", type='" + type + '\'' +
                    ", date_debut='" + date_debut + '\'' +
                    ", date_fin='" + date_fin + '\'' +
                    '}';
        }


        public HashMap<String,Object> getDifferencies (TeamEvent te){
            HashMap<String,Object> differencies = new HashMap<>();

            if(!this.getLabel().equals(te.getLabel())){
                differencies.put("label",te.getLabel());
            }

            if(!this.getType().equals(te.getType())){
                differencies.put("type",te.getType());
            }

            if(!this.getDateDebut().equals(te.getDateDebut())){
                differencies.put("date_debut",te.getDateDebut());
            }

            if(!this.getDateFin().equals(te.getDateFin())){
                differencies.put("date_fin",te.getDateFin());
            }

            
    return differencies;

        }


   
}
