package hr.foi.air.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import hr.foi.air.database.converters.DateConverter;

@Entity(tableName = "pictures")
@TypeConverters(DateConverter.class)
public class Picture {
    @PrimaryKey(autoGenerate = true)
    int id;
    @NotNull
    String imageUri = "nekaAdresa";
    Date pictureDate;

    public Picture() {
    }

    public Picture(Picture p) {
        this.id = p.id;
        this.imageUri = p.imageUri;
        this.pictureDate = p.pictureDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(@NotNull String imageUri) {
        this.imageUri = imageUri;
    }

    public Date getPictureDate() {
        return pictureDate;
    }

    public void setPictureDate(Date pictureDate) {
        this.pictureDate = pictureDate;
    }
}
