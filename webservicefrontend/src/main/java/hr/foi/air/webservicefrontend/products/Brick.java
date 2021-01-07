package hr.foi.air.webservicefrontend.products;

import com.google.gson.annotations.SerializedName;

public class Brick {

    /*
    Change serialized name to the field names in json response
     */

    @SerializedName(value="productName")
    private String name;
    @SerializedName(value="productBrand")
    private String brand;
    @SerializedName(value="teaser")
    private String description;
    @SerializedName(value="assets")
    private String image;
    //private String url;

    public Brick(String name, String brand, String description, String image) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.image = image;
        //this.url = url;
    }

    @Override
    public String toString() {
        return "Brick{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", url='" + image + '\'' +
                '}';
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
/*
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

 */
}
