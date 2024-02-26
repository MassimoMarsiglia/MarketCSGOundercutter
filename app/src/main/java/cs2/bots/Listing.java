package cs2.bots;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

public class Listing {

    @SerializedName("data")
    private Map<String,List<ListedItems>> listings;

    public Listing(Map<String,List<ListedItems>> listings) {
        this.listings = listings;
    }
    
    public Map<String,List<ListedItems>> getListedItems() {
        return listings;
    }
}
