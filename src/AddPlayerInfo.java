public class AddPlayerInfo {
  private String nom, sexe, age, poste, taille;

  public AddPlayerInfo(){}
  public AddPlayerInfo(String nom, String sexe, String age, String poste, String taille){
    this.nom = nom;
    this.sexe = sexe;
    this.age = age;
    this.poste = poste;
    this.taille = taille;
    System.out.println(taille);
  }

  public String toString(){
    String str;
    if(this.nom != null && this.sexe != null && this.taille != null && this.age != null && this.poste != null){
      str = "Description de l'objet InfoZDialog";
      str += "Nom : " + this.nom + "\n";
      str += "Sexe : " + this.sexe + "\n";
      str += "Age : " + this.age + "\n";
      str += "Poste : " + this.poste + "\n";
      str += "Taille : " + this.taille + "\n";
    }
    else{
      str = "Aucune information !";
    }
    return str;
  }
}
