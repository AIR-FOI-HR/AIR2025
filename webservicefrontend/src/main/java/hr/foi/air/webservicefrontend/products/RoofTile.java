package hr.foi.air.webservicefrontend.products;

import com.google.gson.annotations.SerializedName;

public class RoofTile extends Brick {

    //@SerializedName(value="dimensions")
    private String dimensions;
    private String websiteImageUrl;

    public RoofTile(String name, String brand, String description, String image, String dimensions, Integer id) {
        super(name, brand, description, image, id);
        this.dimensions = dimensions;
    }

    @Override
    public String toString() {
        return "RoofTile{" +
                "dimensions='" + dimensions + '\'' +
                '}';
    }


    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getWebsiteImageUrl() {
        return websiteImageUrl;
    }

    public void setWebsiteImageUrl(String image) { this.websiteImageUrl = image; }
}
