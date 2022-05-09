package gniazda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GuideTerminal {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 4999);
        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        PrintWriter pr = new PrintWriter(s.getOutputStream());
        do {
            System.out.print("""
            
            1.AddGuide
            2.RemoveGuide    
            3.GetTourParticipants
            What you want to do?: """);
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Podaj nazwe wycieczki ktorej chcesz byc przewodnikiem: ");
                    String tripName = scanner.next();
                    System.out.print("Podaj imie: ");
                    String name = scanner.next();

                    pr.println(choice+5);
                    pr.println(tripName);
                    pr.println(name);
                    pr.flush();
                }
                case 2 -> {
                    System.out.print ("Podaj nazwe wycieczki z ktorej chcesz zrezygnowac z bycia przewodnikiem ");
                    String name2 = scanner.next();
                    System.out.print("Podaj swoje imie: ");
                    String name = scanner.next();

                    pr.println(choice+5);
                    pr.println(name2);
                    pr.println(name);
                    pr.flush();
                }
                case 3 -> {
                    System.out.print ("Podaj nazwe wycieczki ktorej liste uczestnikow chcesz zobaczyc ");
                    String name2 = scanner.next();

                    pr.println(choice+5);
                    pr.println(name2);
                    pr.flush();

                    System.out.println("\nNAZWA WYCIECZKI | UCZESTNICY ");
                        String trip = bf.readLine();
                        System.out.println(trip);

                }
                default -> System.out.println("Zle");
            }

            pr.flush();
        } while (true);

    }
}
