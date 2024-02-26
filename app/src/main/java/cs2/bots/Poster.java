package cs2.bots;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class Poster {

    public static void updatePrices(MyItemsResponse myListings, List<ToSell> saleRate, Map<String,ListedItems> lowestPrices, String authToken) {
        System.out.println(myListings.getMyItems());
        for(ListedItems onSale : myListings.getMyItems()) {
            String itemName = onSale.getName();
            String salesID = onSale.getSalesID();
            double listPrice = onSale.getPrice()*1000;
            //if(lowestPrices.get(itemName) != null) {System.out.println(tmp.getPriceAsInt());}
            if(lowestPrices.get(itemName) != null && lowestPrices.get(itemName).getPrice()-1 != listPrice) {
                int tmp = (int) lowestPrices.get(itemName).getPrice()-1;
                setPrices(salesID, authToken, (int) tmp);
                System.out.println(itemName + " price updated to " + tmp + "USD");
            }
        }
    }

    public static void setPrices(String itemID, String authToken, int price) {
        String reqeust = "set-price?key=" + authToken + "&item_id=" + itemID + "&price=" + price + "&cur=USD";
        post(reqeust);
    }

    public static void post(String requestURL) {
        // Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Define the URL you want to send the POST request to
        String url = "https://market.csgo.com/api/v2/" + requestURL;

        // Build the request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        // Send the request asynchronously and handle the response
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::statusCode)
            .thenAccept(statusCode -> {
                System.out.println("Response status code: " + statusCode);
            })
            .join(); // Wait for the response to complete
    }
    
}
