package minicompilateurjs;

import java.util.ArrayList;
import java.util.List;

public class Scanner {

    static final String[] TAB_MOTS_CLES = {
        "var", "let", "const", "if", "else", "while", "for", "function", "return", "class", "forEach"
    };

    static final String[] TAB_SPECIAL = { "RAYEL" }; 

    static final String[] TAB_OPERATEURS = {
        "+", "-", "*", "/", "=", "==", "=>", "<", ">", "<=", ">=", "++", "--"
    };

    static int[][] MAT_ID = {
        { 1, -1, -1 }, 
        { 1,  1, -1 }  
    };

    static int[][] MAT_NUM = {
        { 1, -1 }, 
        { 1, -1 }  
    };

    static int[][] MAT_SEP = {
        { 1, -1 }, 
        {-1, -1 }  
    };

    static int indexID(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '$' || c == '_') return 0;
        if (c >= '0' && c <= '9') return 1;
        return 2; 
    }

    static int indexNum(char c) {
        if (c >= '0' && c <= '9') return 0;
        return 1; 
    }

    static int indexSep(char c) {
        if (c == ';' || c == ',' || c == '.' || c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']') return 0;
        return 1; 
    }

    public List<Token> analyser(String source) {
        List<Token> tokens = new ArrayList<>();
        source = source + "#"; 
        
        int i = 0;
        int ligne = 1;

        while (source.charAt(i) != '#') {
            char tc = source.charAt(i);

            if (tc == ' ' || tc == '\t' || tc == '\r') { i++; continue; }
            if (tc == '\n') { ligne++; i++; continue; }

            
            if (tc == '/' && source.charAt(i+1) == '/') {
                while(source.charAt(i) != '\n' && source.charAt(i) != '#') {
                    i++;
                }
                continue;
            }

            if (indexID(tc) == 0) { 
                int ec = 0; 
                int start = i;
                while (tc != '#' && MAT_ID[ec][indexID(tc)] != -1) {
                    ec = MAT_ID[ec][indexID(tc)];
                    i++;
                    tc = source.charAt(i);
                }
                String lexeme = "";
                for(int k=start; k<i; k++) lexeme += source.charAt(k);

                if (estDansTableau(lexeme, TAB_SPECIAL)) {
                    tokens.add(new Token("MOT_CLE_PERSO", lexeme, ligne));
                } else if (estDansTableau(lexeme, TAB_MOTS_CLES)) {
                    tokens.add(new Token("MOT_CLE", lexeme, ligne));
                } else {
                    tokens.add(new Token("IDENTIFIANT", lexeme, ligne));
                }
            }
            
            else if (indexNum(tc) == 0) {
                int ec = 0;
                int start = i;
                while (tc != '#' && MAT_NUM[ec][indexNum(tc)] != -1) {
                    ec = MAT_NUM[ec][indexNum(tc)];
                    i++;
                    tc = source.charAt(i);
                }
                String lexeme = "";
                for(int k=start; k<i; k++) lexeme += source.charAt(k);
                tokens.add(new Token("NOMBRE", lexeme, ligne));
            }

            else if (indexSep(tc) == 0) {
                int ec = 0;
                int start = i;
                while (tc != '#' && MAT_SEP[ec][indexSep(tc)] != -1) {
                    ec = MAT_SEP[ec][indexSep(tc)];
                    i++;
                    tc = source.charAt(i);
                }
                String lexeme = "";
                for(int k=start; k<i; k++) lexeme += source.charAt(k);
                tokens.add(new Token("SEPARATEUR", lexeme, ligne));
            }
            
            else {
                String opSimple = "" + tc;
                String opDouble = "" + tc + source.charAt(i+1); 

                if (estDansTableau(opDouble, TAB_OPERATEURS)) {
                    tokens.add(new Token("OPERATEUR", opDouble, ligne));
                    i += 2;
                }
                else if (estDansTableau(opSimple, TAB_OPERATEURS)) {
                    tokens.add(new Token("OPERATEUR", opSimple, ligne));
                    i++;
                }
                else if (tc == '"') {
                     i++;
                     while(source.charAt(i) != '"' && source.charAt(i) != '#') i++;
                     if(source.charAt(i) == '"') i++;
                     tokens.add(new Token("CHAINE", "...", ligne));
                } 
                else {
                    
                    i++; 
                }
            }
        }
        return tokens;
    }

    private boolean estDansTableau(String val, String[] tab) {
        int k = 0;
        while (k < tab.length) {
            if (tab[k].equals(val)) return true;
            k++;
        }
        return false;
    }
}