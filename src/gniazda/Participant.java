package gniazda;

public class Participant {
    private String name;
    private String surname;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Participant(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
