package cs2.bots;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class App {
    public static void main(String[] args) throws IOException {

        String authToken = "";
        String skinDirectory = "";
        List<ToSell> forSale;
        double fee = 0;
        long updateFrequency = 10000;

        try {
            File jarFile = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            String jarDirPath = jarFile.getParent();
            String configFilePath = jarDirPath + File.separator + "config.properties";
            System.out.println("Config file path: " + configFilePath);

            //configFilePath = "C:\\Users\\marsi\\Desktop\\cs2 bots\\app\\src\\main\\resources\\config.properties";
            
            // Now you can load the properties from the config file
            Properties pros = new Properties();
            try (FileInputStream fis = new FileInputStream(configFilePath)) {
                pros.load(fis);
                // Use properties...
                authToken = pros.getProperty("authToken");
                skinDirectory = pros.getProperty("skinDirectory");
                fee = Double.parseDouble(pros.getProperty("fee"));
                updateFrequency = Long.parseLong(pros.getProperty("updateFrequency"));
            } catch (IOException e) {
                System.err.println("Error loading config.properties: " + e.getMessage());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        forSale = parseItemsFromFile(skinDirectory);
        System.out.println(forSale);
        Map<String, List<ListedItems>> listings = new HashMap<>();
        //listings = Requester.getListings(authToken,forSale);
        MyItemsResponse myListings = new MyItemsResponse(Requester.getMyListings(authToken));
        //System.out.println(myListings.getMyItems());

        Map<String, ListedItems> lowestPrices = new HashMap<>();
        //lowestPrices = Requester.getLowestListPrices(listings, forSale, myListings.getMyItems(), fee);
        System.out.println(lowestPrices);
        //Poster.updatePrices(myListings, forSale, lowestPrices, authToken);
        //System.out.println(Requester.getMyItems(authToken));

        while(true){
            try {
                // Add a delay of 1 second (1000 milliseconds) before calling Poster.updatePrices()
                Thread.sleep(updateFrequency);
            } catch (InterruptedException e) {
                // Handle interrupted exception if needed
                e.printStackTrace();
            }
        
            // Call Poster.updatePrices() here
            listings = Requester.getListings(authToken, forSale);
            myListings = new MyItemsResponse(Requester.getMyListings(authToken));
            lowestPrices = Requester.getLowestListPrices(listings, forSale, myListings.getMyItems(), fee);
            Poster.updatePrices(myListings, forSale, lowestPrices, authToken);
        }
    }

    public static List<ToSell> parseItemsFromFile(String skinDirectory) throws IOException {
        List<ToSell> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(skinDirectory))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    String name = parts[0];
                    int priceMin = Integer.parseInt(parts[1]);
                    int priceMax = Integer.parseInt(parts[2]);
                    ToSell item = new ToSell(name, priceMin, priceMax);
                    items.add(item);
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }
        }
        return items;
    }
}
