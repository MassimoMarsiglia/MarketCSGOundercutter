package cs2.bots;

import com.google.gson.annotations.SerializedName;

public class MyItem {

    @SerializedName("market_hash_name")
    private String name;

    @SerializedName("id")
    private String id;
    
    public MyItem(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString(){
        return "name: " + name + " id: " + id;
    }
}
