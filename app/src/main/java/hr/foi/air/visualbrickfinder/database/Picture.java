package hr.foi.air.visualbrickfinder.database;

public class Picture {

    private String image;
    private String date;
    private int id;

    public Picture(String image, String date, int id) {
        this.image=image;
        this.date=date;
        this.id=id;
    }

    public String getImage() {
        return image;
    }

    public String getDate() { return date; }

    public int getId() {return id;}


}
