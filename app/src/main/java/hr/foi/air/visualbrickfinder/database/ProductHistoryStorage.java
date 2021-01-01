package hr.foi.air.visualbrickfinder.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hr.foi.air.visualbrickfinder.HistoryFragment;

public class ProductHistoryStorage {

    private HistoryFragment caller;

    public void getPictures(HistoryFragment caller) {
        this.caller=caller;
        //loadData(); //Implement when database is finished, returns all photographs
        setMockBrickData();
    }

    private void setMockBrickData() {
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(
                "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MXx8fGVufDB8fHw%3D&w=1000&q=80",
                date,
                1));
        pictures.add(new Picture(
                "https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                date,
                2));
        pictures.add(new Picture(
                "https://images.ctfassets.net/79nimht05j33/5XrYmDVAa2z7zGJZNVZPX1/91e8c3419148292d88a36ca34b2abdb5/JamesHardieSiding-33.jpg?w=1500&q=70",
                date,
                3));

        returnPictures(pictures);
    }

    private void returnPictures(List<Picture> pictures) {
        caller.receivePictures(pictures);
    }
}
