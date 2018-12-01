package ru.undeground.location;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class LocationAPI {

    public static void main(String[] args) {
        for (Location loc : getLocations("Hermitage")) {
            System.out.println(loc);
        }
    }

    public static List<Location> getLocations(String location) {
        List<Location> list = new ArrayList<Location>();

        try {
            String lol = "https://places.cit.api.here.com/places/v1/autosuggest?at=52.531,13.3848&q=PLACE_TO_REPLACE&app_id=EQQCQ6nkLFRQKWxOTL1Z&app_code=5v3wF97gfzJ1_gxdysC-XA";
            String request = lol.replace("PLACE_TO_REPLACE", location);

            String response = sendRequest(request);
            System.out.println(response);
            JSONObject obj = parseLocations(response);

            JSONArray arr = obj.getJSONArray("results");
            for (int i = 0; i < arr.length() && i < 5; i++) {
                String title = arr.getJSONObject(i).getString("title");
                String href = arr.getJSONObject(i).getString("href");
                list.add(new Location(title, getLocationView(href)));
            }

        } catch (Exception error) {
            System.out.println(error.toString());
        }

        return list;
    }

    private static String getLocationView(String locationHref) {
        try {
            String response = sendRequest(locationHref);
            JSONObject obj = parseViewResultsByAPI(response);
            System.out.println(obj);
            return obj.getString("view");
        } catch (Exception error) {
            return error.toString();
        }
    }

    private static String sendRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception error) {
            return error.toString();
        }
    }

    private static JSONObject parseViewResultsByAPI(String response) {
        return parseApiResults(response, ".*(\\{\"name.*\\}).*");
    }

    private static JSONObject parseLocations(String response) {
        return parseApiResults(response, ".*(\\{\"results.*\\}).*");
    }

    private static JSONObject parseApiResults(String response, String pattern) {
        Document html = Jsoup.parse(response);
        Element div = html.getElementById("main-response");
        Element script = div.nextElementSibling();
        String JsonResponse = script.data();
        String FuckingJson = JsonResponse.replaceAll(pattern, "$1");
        return new JSONObject(FuckingJson);
    }
}
