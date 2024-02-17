import java.util.Comparator;

public class JoueursCompare implements Comparator<InfosJoueurs> {

    String attribut;
    boolean croissant;
    JoueursCompare(String attribut,boolean croissant){
        this.attribut = attribut;
        this.croissant = croissant;
    }

    @Override
    public int compare(InfosJoueurs j1, InfosJoueurs j2) {
        int res =-1;
        switch (attribut) {
            case "Age":
                res = Integer.compare(j1.age,j2.age);
                break;
            case "Nom":
                res = j1.nom.compareTo(j2.nom);
                break;
            case "Prenom":
                res = j1.prenom.compareTo(j2.prenom);
                break;
            case "Salaire":
                res = Integer.compare(j1.salaire,j2.salaire);
                break;
            case "Contrat":
                res = j1.contrat.compareTo(j2.contrat);
                break;
            case "Taille":
                res = Integer.compare(j1.taille, j2.taille);
                break;
            case "Poids":
                res = Integer.compare(j1.poids, j2.poids);
                break;
            case "Numero":
                res = Integer.compare(j1.numero, j2.numero);
                break;
            case "Poste":
                res = Integer.compare(posteToInt(j1), posteToInt(j2));
                break;
            default:
                break;
        }
        if (croissant) return res;
        else return res*(-1);
    }


    private int posteToInt(InfosJoueurs j){
        String poste = j.postion;
        if (poste.startsWith("G")) return 1;
        if (poste.startsWith("D")) return 2;
        if (poste.startsWith("M")) return 3;
        if (poste.startsWith("A")) return 4;
        return 5; //Le cas du BU --> buteur le poste le plus sur le terrain 
    }   
    
}
