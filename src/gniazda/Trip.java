package gniazda;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    private String name;
    private String description;
    private String date;
    private int spaces;
    private int freeSpaces;

    public List<Participant> getParticipants() {
        return participants;
    }

    List<Participant> participants = new ArrayList<>();


    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    String guide = null;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getSpaces() {
        return spaces;
    }

    public int getFreeSpaces() {
        return freeSpaces;
    }

    public void setFreeSpaces(int freeSpaces) {
        this.freeSpaces = freeSpaces;
    }

    public Trip(String name, String description, String date, int spaces, int freeSpaces) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.spaces = spaces;
        this.freeSpaces = freeSpaces;
    }
}
