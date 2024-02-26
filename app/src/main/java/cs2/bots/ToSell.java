package cs2.bots;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ToSell {

    String name;
    double minPrice;
    double maxPrice;
    String encodedText = "";

    public ToSell(String hashname, double min, double max) {
        this.name = hashname;
        this.minPrice = min;
        this.maxPrice = max;
    }

    public String getName() {
        return name;
    }

    public int getMinPriceAsInt() {
        double tmp = minPrice;
        return (int)tmp;
    }

    public int getMaxPriceAsInt() {
        double tmp = maxPrice;
        return (int)tmp;
    }

    public String getEncodedName(){
        try {
            encodedText = URLEncoder.encode(name, "UTF-8")
                        .replaceAll("\\+", "%20")
                        .replaceAll("\\%21", "!")
                        .replaceAll("\\%27", "'")
                        .replaceAll("\\%28", "(")
                        .replaceAll("\\%29", ")")
                        .replaceAll("\\%7E", "~");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        return encodedText;
    }
    
    @Override
    public String toString() {
        return "name: " + name + " minPrice: " + getMinPriceAsInt() + " maxPrice: " + getMaxPriceAsInt();
    }
}
