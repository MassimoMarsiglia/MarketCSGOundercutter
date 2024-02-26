package cs2.bots;

import com.google.gson.annotations.SerializedName;

public class ListedItems {

    @SerializedName("price")
    private String price;

    @SerializedName("id")
    private String listingID;

    @SerializedName("item_id")
    private String salesID;

    @SerializedName("market_hash_name")
    private String name;

    public String getName(){
        return name;
    }

    public double getPrice(){
        return Double.parseDouble(price);
    }

    public int getPriceAsInt() {
        return Integer.valueOf(price);
    }

    public String getListingID() {
        return listingID;
    }

    public String getSalesID() {
        return salesID;
    }

    @Override
    public String toString() {
        return "price: " + price + " listingID: " + listingID + " salesID: " + salesID + " name: " + name;
    }
    
}
