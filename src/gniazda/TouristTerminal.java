package gniazda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TouristTerminal {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 4999);
        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        PrintWriter pr = new PrintWriter(s.getOutputStream());
        do {
            System.out.print("""
            
            1.RegisterForTour
            2.UnregisterFromTour       
            3.GetTourOffers
            What you want to do?: """);
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Nazwe wycieczki w ktorej chcesz uczestniczyc: ");
                    String tripName = scanner.next();
                    System.out.print("Podaj imie: ");
                    String name = scanner.next();
                    System.out.print("Nazwisko: ");
                    String surname = scanner.next();

                    pr.println(choice+3);
                    pr.println(tripName);
                    pr.println(name);
                    pr.println(surname);
                    pr.flush();
                }
                case 2 -> {
                    System.out.print ("Podaj nazwe wycieczki z ktorej chcesz zrezygnowac ");
                    String name2 = scanner.next();
                    System.out.print("Podaj swoje nazwisko: ");
                    String name = scanner.next();

                    pr.println(choice+3);
                    pr.println(name2);
                    pr.println(name);
                    pr.flush();
                }
                case 3 -> {

                    pr.println(choice);
                    pr.flush();
                    int quantity = Integer.parseInt(bf.readLine());
                    System.out.println("\nNAZWA | OPIS | DATA | MIEJSCA | WOLNE MIEJSCA ");
                    for (int i = 0; i <= quantity-1; i++) {
                        String trip = bf.readLine();
                        System.out.println(trip);
                    }
                }
                default -> System.out.println("Zle");
            }

            pr.flush();
        } while (true);

    }
}

