package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Field;

@JsonRootName(value = "stats")
public class Stat extends Data {
    private short buts;
    private short passesdecisives;
    private short cartonsjaunes;
    private short cartonsrouges;
    private short notedumatch;
    private short minutesjouees;
    private int id_joueurs;
    private int id_matchs;
    
    public Stat() {}

    public Stat(short buts, short passesdecisives, short cartonsjaunes, short cartonsrouges, short notedumatch, short minutesjouees, int id_joueurs, int id_matchs) {
        this.id_joueurs = id_joueurs;
        this.id_matchs = id_matchs;
        this.buts = buts;
        this.passesdecisives = passesdecisives;
        this.cartonsjaunes = cartonsjaunes;
        this.cartonsrouges = cartonsrouges;
        this.notedumatch = notedumatch;
        this.minutesjouees = minutesjouees;
        
    }

    public Stat(ResultSet resultSet) throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet,"id_joueurs", "id_matchs","buts", "passesdecisives", "cartonsjaunes", "cartonsrouges", "notedumatch", "minutesjouees");
    }

    public final Stat build(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(7, id_joueurs);
        preparedStatement.setInt(8, id_matchs);
        preparedStatement.setShort(1, buts);
        preparedStatement.setShort(2, passesdecisives);
        preparedStatement.setShort(3, cartonsjaunes);
        preparedStatement.setShort(4, cartonsrouges);
        preparedStatement.setShort(5, notedumatch);
        preparedStatement.setShort(6, minutesjouees);
        return this;
    }

    @JsonProperty("buts")
    public void setButs(short buts) {
        this.buts = buts;
    }

    @JsonProperty("passesdecisives")
    public void setPassesDecisives(short passesdecisives) {
        this.passesdecisives = passesdecisives;
    }

    @JsonProperty("cartonsjaunes")
    public void setCartonsJaunes(short cartonsjaunes) {
        this.cartonsjaunes = cartonsjaunes;
    }

    @JsonProperty("cartonsrouges")
    public void setCartonsRouges(short cartonsrouges) {
        this.cartonsrouges = cartonsrouges;
    }

    @JsonProperty("notedumatch")
    public void setNoteDuMatch(short notedumatch) {
        this.notedumatch = notedumatch;
    }

    @JsonProperty("minutesjouees")
    public void setMinutesJouees(short minutesjouees) {
        this.minutesjouees = minutesjouees;
    }

    @JsonProperty("id_joueurs")
    public void setIdJoueurs(int id_joueurs) {
        this.id_joueurs = id_joueurs;
    }

    @JsonProperty("id_matchs")
    public void setIdMatchs(int id_matchs) {
        this.id_matchs = id_matchs;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    public short getButs() {
        return buts;
    }

    public short getPassesDecisives() {
        return passesdecisives;
    }

    public short getCartonsJaunes() {
        return cartonsjaunes;
    }

    public short getCartonsRouges() {
        return cartonsrouges;
    }

    public short getNoteDuMatch() {
        return notedumatch;
    }

    public short getMinutesJouees() {
        return minutesjouees;
    }

    public int getIdJoueurs() {
        return id_joueurs;
    }

    public int getIdMatchs() {
        return id_matchs;
    }

    
    public int getId() {
        return id;
    }

    private void setFieldsFromResultSet(final ResultSet resultSet, final String... fieldNames) throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType().equals(short.class)) {
                field.set(this, resultSet.getShort(fieldName));
            } else if (field.getType().equals(int.class)) {
                field.set(this, resultSet.getInt(fieldName));
            }
        }
    }

    @Override
    public String toString() {
        return "Stat{" +
                "  id_joueurs=" + id_joueurs +
                ", id_matchs=" + id_matchs +
                ", buts=" + buts +
                ", passesdecisives=" + passesdecisives +
                ", cartonsjaunes=" + cartonsjaunes +
                ", cartonsrouges=" + cartonsrouges +
                ", notedumatch=" + notedumatch +
                ", minutesjouees=" + minutesjouees +
                
                '}';
    }
}
