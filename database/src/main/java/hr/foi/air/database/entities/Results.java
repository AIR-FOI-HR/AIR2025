package hr.foi.air.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"idPicture", "idProduct"})
public class Results {
    @ForeignKey(
            entity = Picture.class, parentColumns = "id", childColumns = "idPicture"
    )
    int idPicture;
    @ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "idProduct")
    int idProduct;

    public int getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(int idPicture) {
        this.idPicture = idPicture;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}
