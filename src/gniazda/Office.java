package gniazda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Office extends Thread implements Runnable {
    private ServerSocket ss;
    private Thread t = null;

    synchronized public List<Trip> getTrips() {
        return trips;
    }

    List<Trip> trips = Collections.synchronizedList(new ArrayList<>()) ;



    private volatile boolean serverRunning = true; //koniec wątku == false

    private String serverTID; // id wątku

    public Office(String serverTID, ServerSocket ss) {
        this.serverTID = serverTID;
        this.ss = ss;
        System.out.println("Server " + serverTID + " started");
        System.out.println("listening at port: " + ss.getLocalPort());
        System.out.println("bind address: " + ss.getInetAddress());
        start();
        start();
    }
    public void start() {
        t = new Thread(() -> {


    while (serverRunning) {
        try {
            Socket conn = ss.accept();
            System.out.println("Connection established by " + (serverTID));
            serviceRequests(conn);

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    try {
        ss.close();                                //zamkniecie gniazda serwera
    } catch (Exception exc) {
    }
});
        t.start();
    }

    private void serviceRequests(Socket connection) throws IOException {
        InputStreamReader in = new InputStreamReader(connection.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        PrintWriter pr = new PrintWriter(connection.getOutputStream());
        try {
            bf = new BufferedReader(                   // utworzenie strumieni
                    new InputStreamReader(
                            connection.getInputStream()));
            pr = new PrintWriter(
                    connection.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        do {
            int choice = Integer.parseInt(bf.readLine());
            switch (choice) {
                case 1 -> {
                   // System.out.println("1");
                    String name = bf.readLine();
                    String description = bf.readLine();
                    String date = bf.readLine();
                    int spaces = Integer.parseInt(bf.readLine());
                    Trip trip = new Trip(name, description, date, spaces, spaces);
                    getTrips().add(trip);
                }
                case 2 -> {
                  //  System.out.println("2");
                    String name2 = bf.readLine();
                    for(int i=0; i<= getTrips().size(); i++) {
                        if(getTrips().get(i).getName().equals(name2))
                        {
                            getTrips().remove(i);}

                    }
                }
                case 3 -> {
                 //   System.out.println("3");
                   // System.out.println(getTrips().size());
                    pr.println(getTrips().size());
                   // pr.flush();
                    for(int i=0; i<= getTrips().size()-1; i++) {
                        pr.println(getTrips().get(i).getName()+" " + getTrips().get(i).getDescription()+" " + getTrips().get(i).getDate()+" " + getTrips().get(i).getSpaces()+" " + getTrips().get(i).getFreeSpaces());
                       // pr.flush();
                    }
                }
                case 4 -> {
                   // System.out.println("4");
                    String tripName = bf.readLine();
                    String name = bf.readLine();
                    String surname = bf.readLine();
                    //System.out.println(getTrips().size());
                    //System.out.println(getTrips().get(0).getName());
                    for(int i=0; i<getTrips().size(); i++) {
                        if(getTrips().get(i).getName().equals(tripName)) {
                            if(getTrips().get(i).getFreeSpaces()>0)
                                getTrips().get(i).participants.add(new Participant(name,surname));
                            getTrips().get(i).setFreeSpaces(getTrips().get(i).getFreeSpaces()-1);
                        //    System.out.println( getTrips().get(i).participants.get(0).getName());
                        }
                    }

                }
                case 5 -> {
                    //System.out.println("5");
                    String tripName = bf.readLine();
                    String surname = bf.readLine();

                    for(int i=0; i< getTrips().size(); i++) {
                        if(getTrips().get(i).getName().equals(tripName)) {
                            for(int j = 0; j< getTrips().get(i).participants.size(); j++) {
                                if (getTrips().get(i).participants.get(j).getSurname().equals(surname))
                                    getTrips().get(i).participants.remove(j);
                                getTrips().get(i).setFreeSpaces(getTrips().get(i).getFreeSpaces()+1);

                            }
                        }
                    }
                }
                case 6 -> {
                  //  System.out.println("4");
                    String tripName = bf.readLine();
                    String name = bf.readLine();

                    if (getTrips().size() != 0) {
                        for (int i = 0; i < getTrips().size(); i++) {
                            if (getTrips().get(i).getName().equals(tripName)) {

                                if (getTrips().get(i).getGuide() == null)
                                {getTrips().get(i).setGuide(name);}
                                else System.out.println("Wycieczka ma już przewodnika :" + getTrips().get(i).getGuide());
                            }
                        }
                    } else System.out.println("Nie ma takiej wycieczki");
                }
                case 7 -> {

                    String tripName = bf.readLine();
                    String name = bf.readLine();

                    for(int i=0; i< getTrips().size(); i++) {
                        if(getTrips().get(i).getName().equals(tripName)) {
                            if(getTrips().get(i).getGuide()!=null) {
                                getTrips().get(i).setGuide(null);
                            }
                        }
                    }
                }
                case 8 -> {
                    String tripName = bf.readLine();

                    for (int i = 0; i < getTrips().size(); i++) {
                        String temp = getTrips().get(i).getName()+" ";
                        if (getTrips().get(i).getName().equals(tripName)) {
                            for (int j = 0; j < getTrips().get(i).getParticipants().size(); j++) {
                                temp += getTrips().get(i).getParticipants().get(j).getName() + " " + getTrips().get(i).getParticipants().get(j).getSurname()+" ";
                            }
                            pr.println(temp);
                        }
                    }
                }
                default -> System.out.println("Źle");
            }
            pr.flush();
        }while (true);



    }

    public static void main(String[] args) throws InterruptedException {

        final int SERVERS_NUM = 4;   // liczba serwerów
        ServerSocket ss = null;
        try {

            String host = "localhost";
            int port = 4999;
            InetSocketAddress isa = new InetSocketAddress(host, port);
            ss = new ServerSocket();
            ss.bind(isa);
        } catch (Exception exc) {
            exc.printStackTrace();
            System.exit(1);
        }

        for (int i = 1; i <= SERVERS_NUM; i++) {
            sleep(500);
            new Office("serv thread " + i, ss).start();
        }
    }

}


