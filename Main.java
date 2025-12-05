package minicompilateurjs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        String codeSource = "";
        try {
            codeSource = new String(Files.readAllBytes(Paths.get("test.txt")));
            System.out.println("Fichier test.txt lu avec succes.\n");
        } catch (IOException e) {
            System.err.println("ERREUR : Impossible de lire le fichier test.txt");
            System.err.println("Veuillez creer test.txt a cote du programme.");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            return;
        }

        System.out.println("--- CODE SOURCE ---");
        System.out.println(codeSource);
        System.out.println("\n-------------------");

        minicompilateurjs.Scanner scanner = new minicompilateurjs.Scanner();
        List<Token> tokens = scanner.analyser(codeSource);

        System.out.println("--- ANALYSE LEXICALE ---");
        for (Token t : tokens) {
            System.out.println(t);
        }
        System.out.println("\n-------------------");

        System.out.println("--- ANALYSE SYNTAXIQUE ---");
        Parser parser = new Parser(tokens);
        parser.Z();

        System.out.println("\nAppuyez sur Entree pour quitter...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
}