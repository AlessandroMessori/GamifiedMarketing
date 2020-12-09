package fakeEntities;

public class Position {
    private final String uname;
    private final int points;


    public Position(String uname, int points) {
        this.uname = uname;
        this.points = points;
    }

    public String getUname() {
        return uname;
    }

    public int getPoints() {
        return points;
    }
}
