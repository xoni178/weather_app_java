package Models;


public class WeatherData {

    private String time;
    private int interval;
    private Double temperature;
    private Double humidity;
    private Double weather_code;
    private Double wind_speed;

    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }

    public void setInterval(int interval){
        this.interval = interval;
    }
    public int getInterval(){
        return this.interval;
    }

    public void setTemperature_2m(Double temperature){
        this.temperature = temperature;
    }
    public Double getTemperature_2m(){
        return this.temperature;
    }
    public void setRelative_humidity_2m(Double humidity){
        this.humidity = humidity;
    }
    public Double getRelative_humidity_2m(){
        return this.humidity;
    }
    public void setWeather_code(Double weatherCode){
        this.weather_code = weatherCode;
    }
    public Double getWeather_code(){
        return this.weather_code;
    }
    public void setWind_speed_10m(Double windSpeed){
        this.wind_speed = windSpeed;
    }
    public Double getWind_speed_10m(){
        return this.wind_speed;
    }
}
