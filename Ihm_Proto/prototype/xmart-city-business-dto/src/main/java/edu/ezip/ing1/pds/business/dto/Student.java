package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "student")
public class Student {
    private  String nom,nationalite;
    private  String prenom;
    private  Date naissance;
    private int numero;
    private String poste;
    private String pied;
    private int taille;
    private int poids;

    public Student() {
    }
    public final Student build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "name", "firstname","group");
        return this;
    }
/*     public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, name, firstname,group);
    } */
/*     public Student(String name, String firstname, String group,String test) {
        this.name = name;
        this.firstname = firstname;
        this.group = group;
        this.test = test;
    } */

    public Student(String nom, String prenom, Date naissance, int numero, String poste, String pied, int taille, int poids,String nationalite) {
        this.nom = nom;
        this.prenom = prenom;
        this.naissance = naissance;
        this.numero = numero;
        this.poste = poste;
        this.pied = pied;
        this.taille = taille;
        this.poids = poids;
        this.nationalite = nationalite;
    }

/*     public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getGroup() {
        return group;
    } */

/*     @JsonProperty("student_name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("student_1stname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @JsonProperty("student_group")
    public void setGroup(String group) {
        this.group = group;
    }
    @JsonProperty("student_test") 
    public void setTest(String test){
        this.test = test;
    } */


    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public Date getNaissance() {
        return naissance;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public String getPoste() {
        return poste;
    }
    
    public String getPied() {
        return pied;
    }
    
    public int getTaille() {
        return taille;
    }
    
    public int getPoids() {
        return poids;
    }
    
    public String getNationalite(){
        return nationalite;
    }

    @JsonProperty("nom")
    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonProperty("prenom")
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @JsonProperty("naissance")
    public void setNaissance(Date naissance) {
        this.naissance = naissance;
    }

    @JsonProperty("numero")
    public void setNumero(int numero) {
        this.numero = numero;
    }

    @JsonProperty("poste")
    public void setPoste(String poste) {
        this.poste = poste;
    }

    @JsonProperty("pied")
    public void setPied(String pied) {
        this.pied = pied;
    }

    @JsonProperty("taille")
    public void setTaille(int taille) {
        this.taille = taille;
    }

    @JsonProperty("poids")
    public void setPoids(int poids) {
        this.poids = poids;
    }

    @JsonProperty("nationalite")
    public void setNationalite(String nationalite){
        this.nationalite = nationalite;
    }


    private void setFieldsFromResulset(final ResultSet resultSet, final String ... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
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
        return "Player{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}
