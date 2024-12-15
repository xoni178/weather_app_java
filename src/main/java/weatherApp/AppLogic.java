package weatherApp;

import java.io.*;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Models.WeatherData;

public class AppLogic {
    public static String locationName;

    //fetch data for location
    public static Map<String, Double> getWeatherData(String location) {
        Map<String, Double> weatherData = new HashMap<String, Double>();
        ArrayList<Double> latLon = getPlaceLatLon(location);

        if(latLon == null) return null;

        WeatherData locationData = getLocationData((double) latLon.get(0), (double) latLon.get(1));


        weatherData.put("wind_speed",  locationData.getTemperature_2m());
        weatherData.put("temperature",  locationData.getWind_speed_10m());
        weatherData.put("humidity", locationData.getRelative_humidity_2m().doubleValue());
        weatherData.put("weather_code", locationData.getWeather_code().doubleValue());

        return weatherData;
    }

    public static WeatherData getLocationData(double lat, double lon) {

        String url = "https://api.open-meteo.com/v1/forecast?latitude="+lat +"&longitude="+ lon + "&current=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m";

        try {
            //call API
            HttpURLConnection connection = fetchApiData(url);

            if(connection.getResponseCode() != 200) {
                System.out.println("Couldn't connect to api... " + connection.getResponseCode());
                return null;
            }else {
                StringBuilder dataJSON = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while((line = reader.readLine()) != null) {
                    dataJSON.append(line);
                }

                reader.close();
                connection.disconnect();


                Map<String, Object> locationData = new HashMap<String, Object>();
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> data = objectMapper.readValue(dataJSON.toString(), new TypeReference<Map<String, Object>>() {});
                WeatherData weatherData = objectMapper.convertValue(data.get("current"), WeatherData.class);

                return weatherData;
            }
        }catch(Exception e) {
            System.err.println("Error reading data from API: " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Double> getPlaceLatLon(String name) {
        String search = name.replaceAll(" ", "_");

        String url = "http://api.openweathermap.org/geo/1.0/direct?q="+ search +"&limit=1&appid=d1e3399215041d4ae59261fb1fb1c74f";


        try {
            HttpURLConnection connection = fetchApiData(url);


            if(connection.getResponseCode() != 200) {

                AppError.setErrorMesage("Couldn't connect... " + connection.getResponseCode());
            }else {
                StringBuilder geoDataJSON = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while((line = reader.readLine()) != null) {
                    geoDataJSON.append(line);
                }

                reader.close();
                connection.disconnect();

                if(!geoDataJSON.toString().equals("[]")) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Map<String, Object>> data = objectMapper.readValue(geoDataJSON.toString(), new TypeReference<List<Map<String, Object>>>() {});
                    Map<String, Object> geoData = data.get(0);

                    ArrayList<Double> latLon = new ArrayList<Double>();

                    latLon.add((double)geoData.get("lat"));
                    latLon.add((double)geoData.get("lon"));

                    locationName = (String) geoData.get("name");

                    return latLon;
                }

                AppError.setErrorMesage("Location entered: " + name + " is not found.");
            }
            return null;

        }catch(Exception e){
            System.out.print("second expedition" + e);

        }
        return null;

    }
    private static HttpURLConnection fetchApiData(String urlStr) {

        try {

            //create connection
            URL url = URI.create(urlStr).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //set method to GET
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Content-type", "application/json");


            //connect to api
            connection.connect();

            return connection;

        }catch(IOException e) {
            System.out.print(e);
        }
        return null;
    }
}
