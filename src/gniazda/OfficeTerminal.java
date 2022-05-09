package gniazda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class OfficeTerminal {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 4999);
        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        PrintWriter pr = new PrintWriter(s.getOutputStream());
do {
    System.out.print("""
            
            1.AddTourOffer
            2.RemoveTourOffer
            3.GetTourOffers
            What you want to do?: """);
    Scanner scanner = new Scanner(System.in);
    int choice = scanner.nextInt();

    switch (choice) {
        case 1 -> {
            System.out.print("Nazwa wycieczki: ");
            String name = scanner.next();
            System.out.print("Opis wycieczki: ");
            String description = scanner.next();
            System.out.print("Termin: ");
            String date = scanner.next();
            System.out.print("Liczba miejsc: ");
            String amount = scanner.next();
            pr.println(choice);
            pr.println(name);
            pr.println(description);
            pr.println(date);
            pr.println(amount);
            pr.flush();
        }
        case 2 -> {
            System.out.print ("Podaj nazwe wycieczki ktora chcesz usunac ");
            String name2 = scanner.next();
            pr.println(choice);
            pr.println(name2);
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
        default -> System.out.println("Źle");
    }

    pr.flush();

} while (true);

    }
}






