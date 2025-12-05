package minicompilateurjs;

import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int i;
    private boolean erreur;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.i = 0;
        this.erreur = false;
    }

    public void Z() {
        S();
        if (i == tokens.size() && erreur == false) {
            System.out.println("Programme Syntaxiquement CORRECT !");
        } else {
            System.out.println("Programme Syntaxiquement INCORRECT (ou fin inattendue).");
        }
    }

    public void S() {
        if (i < tokens.size() && !tokens.get(i).valeur.equals("}")) {
            String val = tokens.get(i).valeur;
            String type = tokens.get(i).type;

            if (val.equals("var") || val.equals("let") || val.equals("const")) {
                DeclVar();
                S();
            } else if (val.equals("function")) {
                DeclFonction();
                S();
            } else if (val.equals("RAYEL")) {
                InstructionForEach();
                S();
            } else if (type.equals("IDENTIFIANT")) {
                Affectation();
                S();
            } else if (val.equals("if") || val.equals("else") || val.equals("while") || 
                     val.equals("for") || val.equals("switch") || val.equals("do")) {
                IgnorerBloc();
                S();
            } else if (val.equals("return") || val.equals("break") || val.equals("continue")) {
                IgnorerSimple();
                S();
            } else {
                System.err.println("Erreur : Instruction inconnue '" + val + "' à la ligne " + tokens.get(i).ligne);
                erreur = true;
                i++;
                S();
            }
        }
    }

    public void DeclVar() {
        i++;
        if (i < tokens.size() && tokens.get(i).type.equals("IDENTIFIANT")) {
            i++;
            if (i < tokens.size() && tokens.get(i).valeur.equals("=")) {
                i++;
                
                while(i < tokens.size() && !tokens.get(i).valeur.equals(";")) {
                    i++;
                }
            }
            if (i < tokens.size() && tokens.get(i).valeur.equals(";")) {
                i++;
            } else {
                System.err.println("Erreur DeclVar : Manque ;");
                erreur = true;
            }
        } else {
            System.err.println("Erreur DeclVar : Manque Nom");
            erreur = true;
        }
    }

    public void InstructionForEach() {
        i++;
        if (check(".")) {
            if (check("forEach")) {
                if (check("(")) {
                    if (checkType("IDENTIFIANT")) {
                        if (check("=>")) {
                            if (check("{")) {
                                S(); 
                                if (check("}")) {
                                    if (check(")")) {
                                        if (!check(";")) {
                                            System.err.println("Erreur ForEach : Manque ; final");
                                            erreur = true;
                                        }
                                    } else {
                                        System.err.println("Erreur ForEach : Manque )");
                                        erreur = true;
                                    }
                                } else {
                                    System.err.println("Erreur ForEach : Manque }");
                                    erreur = true;
                                }
                            } else {
                                System.err.println("Erreur ForEach : Manque {");
                                erreur = true;
                            }
                        } else {
                            System.err.println("Erreur ForEach : Manque =>");
                            erreur = true;
                        }
                    } else {
                        System.err.println("Erreur ForEach : Manque variable");
                        erreur = true;
                    }
                } else {
                    System.err.println("Erreur ForEach : Manque (");
                    erreur = true;
                }
            } else {
                System.err.println("Erreur : Attendu forEach");
                erreur = true;
            }
        } else {
            System.err.println("Erreur : Attendu .");
            erreur = true;
        }
    }

    public void Affectation() {
        i++;
        if (i < tokens.size() && tokens.get(i).valeur.equals("=")) {
            i++;
            
            while(i < tokens.size() && !tokens.get(i).valeur.equals(";")) {
                i++;
            }
            if (!check(";")) {
                System.err.println("Erreur Affectation : Manque ;");
                erreur = true;
            }
        }
    }

    public void DeclFonction() {
        i++;
        i++;
        i++;
        i++;
        if (check("{")) {
            S();
            if (!check("}")) {
                System.err.println("Erreur Fonction : Manque }");
                erreur = true;
            }
        } else {
            System.err.println("Erreur Fonction : Manque {");
            erreur = true;
        }
    }

    public void IgnorerBloc() {
        i++;
        while(i < tokens.size() && !tokens.get(i).valeur.equals("{")) {
            i++;
        }
        if(i < tokens.size()) i++;
        S();
        if(i < tokens.size() && tokens.get(i).valeur.equals("}")) i++;
    }

    public void IgnorerSimple() {
        while(i < tokens.size() && !tokens.get(i).valeur.equals(";")) {
            i++;
        }
        if(i < tokens.size()) i++;
    }

    private boolean check(String val) {
        if (i < tokens.size() && tokens.get(i).valeur.equals(val)) {
            i++; return true;
        }
        return false;
    }
    
    private boolean checkType(String type) {
        if (i < tokens.size() && tokens.get(i).type.equals(type)) {
            i++; return true;
        }
        return false;
    }
}