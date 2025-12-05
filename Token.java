package minicompilateurjs;

public class Token {
    public String type;   
    public String valeur; 
    public int ligne;     
    
    public Token(String type, String valeur, int ligne) {
        this.type = type;
        this.valeur = valeur;
        this.ligne = ligne;
    }

    @Override
    public String toString() {
        
        return "Ligne " + ligne + " : <" + type + ", " + valeur + ">";
    }

}
