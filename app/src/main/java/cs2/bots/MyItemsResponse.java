package cs2.bots;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MyItemsResponse {
    
    @SerializedName("items")
    private List<ListedItems> myItems;
    
    public MyItemsResponse(List<ListedItems> myItems) {
        this.myItems = myItems;
    }

    public List<ListedItems> getMyItems(){
        return myItems;
    }
    
    
}
