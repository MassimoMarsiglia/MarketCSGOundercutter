package cs2.bots;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

public class Requester {

    public static Map<String,List<ListedItems>> getListings(String authToken, List<ToSell> forSale){
        String requestURL;

        requestURL = "search-list-items-by-hash-name-all?key=" + authToken + getListingsHashNameEncoded(forSale);

        RequesterResponse response = getRequest(requestURL);

        Map<String,List<ListedItems>> listedItems = new HashMap<>();

        if(response.getStatusCode() == 200) {
            String responseBody = response.getResponseBody();

            Gson gson = new Gson();
            Listing listing = gson.fromJson(responseBody, Listing.class);
            listedItems = listing.getListedItems();
        }

        else if(response.getStatusCode() != 200) {
            System.out.println(response.getStatusCode());
        }
        return listedItems;
    }

    public static Map<String,ListedItems> getLowestListPrices(Map<String,List<ListedItems>> listings, List<ToSell> forSale, List<ListedItems> myListings, double fee){
        HashMap<String, ListedItems> lowestPrices = new HashMap<>();
        String hashname;
        ListedItems lowestPrice = new ListedItems();
        for(ToSell onSale : forSale) {
            hashname = onSale.getName().trim();
            List<ListedItems> listingsOfItem = listings.get(hashname);
            for(ListedItems myItem : myListings) {
                int counter = 0;
                if(myItem.getName().trim().equals(hashname)){
                    for(ListedItems saleListing : listingsOfItem) {
                        double listPrice;
                        listPrice = saleListing.getPrice();
                        if(counter > 0 && onSale.getMinPriceAsInt()/fee <= (int) listPrice && onSale.getMaxPriceAsInt()/fee >= listPrice && listPrice < lowestPrice.getPrice() && !saleListing.getListingID().trim().equals(myItem.getSalesID().trim())){
                            lowestPrice = saleListing;
                        }
                        else if(counter == 0 && onSale.getMinPriceAsInt()/fee <= listPrice && onSale.getMaxPriceAsInt()/fee >= listPrice && !saleListing.getListingID().trim().equals(myItem.getSalesID().trim())) {
                            counter++;
                            lowestPrice = saleListing;
                            System.out.println(lowestPrice);
                        }
                        else if(lowestPrice == null){
                            ListedItems tmp = new ListedItems();
                            tmp.setPrice(String.valueOf(onSale.getMaxPriceAsInt()/fee));
                            lowestPrice = tmp; // need to find a solution to fix bug when there are no listings it returns the previous items
                        }
                    }
                    lowestPrices.put(hashname, lowestPrice);
                }
            }
        }
        return lowestPrices;
    }

    private static String getListingsHashNameEncoded(List<ToSell> forSale) {
        StringBuilder urlBit = new StringBuilder();
        for (ToSell sell : forSale) {
            urlBit.append("&list_hash_name[]=").append(sell.getEncodedName());
        }
        //System.out.println(urlBit.toString());
        return urlBit.toString();
    }

    public static List<MyItem> getMyItems(String authToken){
        String requestURL;

        requestURL = "my-inventory?key=" + authToken;

        List<MyItem> listedItems = new ArrayList<>();

        RequesterResponse response = getRequest(requestURL);

        if(response.getStatusCode() == 200) {

            String responseBody = response.getResponseBody();

            Gson gson = new Gson();
            MyListings listing = gson.fromJson(responseBody, MyListings.class);
            listedItems = listing.getMyListings();
        }
        else if(response.getStatusCode() != 200) {
            System.out.println(response.getStatusCode());
        }

        return listedItems;
    }

    public static List<ListedItems> getMyListings(String authToken) {
        String requestURL = "items?key=" + authToken;

        List<ListedItems> myListings = new ArrayList<>();;

        RequesterResponse response = getRequest(requestURL);

        if(response.getStatusCode() == 200) {
            String responseBody = response.getResponseBody();

            Gson gson = new Gson();
            MyItemsResponse myListing = gson.fromJson(responseBody, MyItemsResponse.class);
            myListings = myListing.getMyItems();
        }
        else if(response.getStatusCode() != 200) {
            System.out.println(response.getStatusCode());
        }
        return myListings;
    }

    public static RequesterResponse getRequest(String requestURL){
        // Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Define the URI of the API endpoint
        String uri = "https://market.csgo.com/api/v2/" + requestURL;

        // Create a HttpRequest object for the GET request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET() // This specifies that it's a GET request
                .build();

        try {
            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response status code
            int statusCode = response.statusCode();
            //System.out.println("Response Status Code: " + statusCode);

            // Print the response body
            String responseBody = response.body();
            //System.out.println("Response Body: " + responseBody);

            // Print the response headers
            HttpHeaders headers = response.headers();
            //System.out.println("Response Headers: " + headers);

            RequesterResponse requesterResponse = new RequesterResponse(responseBody, statusCode);

            return requesterResponse;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
