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
    private String websiteImageUrl;
    @SerializedName(value="primaId")
    private int id;
    private String localUrl = "";


    public Brick(String name, String brand, String description, String image,Integer id) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.websiteImageUrl = image;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Brick{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", url='" + websiteImageUrl + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
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

    public String getWebsiteImageUrl() {
        return websiteImageUrl;
    }

    public void setWebsiteImageUrl(String image) {
        this.websiteImageUrl = image;
    }

    public String getLocalImageUrl() { return localUrl; }

    public void setLocalImageUrl(String image) { this.localUrl = image; }
/*
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

 */
}
