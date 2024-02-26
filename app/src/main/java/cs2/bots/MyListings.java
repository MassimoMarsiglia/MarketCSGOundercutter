package cs2.bots;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MyListings {

    @SerializedName("items")
    private List<MyItem> listings;

    public MyListings(List<MyItem> listings) {
        this.listings = listings;
    }
    
    public List<MyItem> getMyListings() {
        return listings;
    }
}