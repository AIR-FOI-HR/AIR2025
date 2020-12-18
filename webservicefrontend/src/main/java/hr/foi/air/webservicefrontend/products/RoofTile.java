package hr.foi.air.webservicefrontend.products;

import com.google.gson.annotations.SerializedName;

public class RoofTile extends Brick {

    //@SerializedName(value="dimensions")
    private String dimensions;

    public RoofTile(String name, String brand, String description, String image, String dimensions) {
        super(name, brand, description, image);
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
}
