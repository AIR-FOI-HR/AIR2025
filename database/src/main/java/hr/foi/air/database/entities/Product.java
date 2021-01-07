package hr.foi.air.database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "products")
public class Product {
    @PrimaryKey(autoGenerate = false)
    int id;
    @NotNull
    String productName = "pocetnoIme";



    String brand;
    String dimensions;
    String description;
    @NotNull
    int flagFavorite = 22;
    @NotNull
    String productImage = "pocetnoImeSlike";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getProductName() {
        return productName;
    }

    public void setProductName(@NotNull String productName) {
        this.productName = productName;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public int getFlagFavorite() {
        return flagFavorite;
    }

    public void setFlagFavorite(@NotNull int flagFavorite) {
        this.flagFavorite = flagFavorite;
    }

    @NotNull
    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(@NotNull String productImage) {
        this.productImage = productImage;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
