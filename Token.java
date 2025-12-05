package minicompilateurjs;

public class Token {
    public String type;   // Ex: MOT_CLE, NOMBRE, IDENTIFIANT, OPERATEUR
    public String valeur; // La chaine réelle lue (ex: "var", "12", "age")
    public int ligne;     // Le numéro de ligne (pour afficher les erreurs)

    public Token(String type, String valeur, int ligne) {
        this.type = type;
        this.valeur = valeur;
        this.ligne = ligne;
    }

    @Override
    public String toString() {
        // Format d'affichage : Ligne X : <TYPE, valeur>
        return "Ligne " + ligne + " : <" + type + ", " + valeur + ">";
    }
}