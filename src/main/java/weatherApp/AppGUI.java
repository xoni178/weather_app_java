package weatherApp;

import java.awt.*;
import java.awt.event.*;

import java.util.Map;

import javax.swing.*;

public class AppGUI extends JFrame {

    public AppGUI(){
        //add the windows title
        this.setTitle("Weather App Java");

        this.getContentPane().setBackground(new Color(40, 40, 40));

        //set the windows size
        this.setSize(550, 750);

        //set GUI close to close whole program
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Centre window
        this.setLocationRelativeTo(null);

        //manually position GUI elements
        this.setLayout(null);

        //remove the feature to resize the window
        this.setResizable(false);



        addGUIElements();
    }

    private void addGUIElements() {
        //create search text field
        JTextField searchBar = new JTextField();

        //set the size of text field
        searchBar.setBounds(10, 10, 450, 30);
        searchBar.setBackground(new Color(18, 18, 18));
        searchBar.setForeground(Color.WHITE);
        searchBar.setBorder(null);

        //display text field
        this.add(searchBar);



        //search button
        JButton searchBttn = new JButton();

        ImageIcon searchIcon = getIconImage("search.png");


        searchBttn.setIcon(resizeIcon(15, 15, searchIcon));

        //when hovered change cursor to hand
        searchBttn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //search button size
        searchBttn.setBounds(470, 10, 50 ,30);

        //search button background color
        searchBttn.setBackground(new Color(34, 34, 34));
        searchBttn.setFocusable(false);
        //display button
        this.add(searchBttn);


        //Weather condition image
        ImageIcon weatherIcon = getIconImage("clear.png");

        JLabel weatherConditionImage = new JLabel(resizeIcon(200, 200, weatherIcon));

        weatherConditionImage.setBounds(0, 125, 500, 250);

        this.add(weatherConditionImage);

        //City name
        JLabel cityName = new JLabel("Enter a city...");
        cityName.setBounds(0, 70, 500, 60);
        cityName.setFont(new Font("dialog", Font.BOLD, 50));
        cityName.setForeground(Color.WHITE);
        cityName.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(cityName);

        //Temp number
        JLabel tempNumber = new JLabel("");
        tempNumber.setBounds(0, 375, 500, 60);
        tempNumber.setFont(new Font("dialog", Font.BOLD, 50));
        tempNumber.setForeground(Color.WHITE);
        tempNumber.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(tempNumber);

        //Weather condition description
        JLabel weatherConditionDesc = new JLabel("");
        weatherConditionDesc.setBounds(0, 440, 500, 40);
        weatherConditionDesc.setFont(new Font("dialog", Font.PLAIN, 20));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        weatherConditionDesc.setForeground(Color.WHITE);

        this.add(weatherConditionDesc);

        //Error displaying
        JLabel errorDisplayer = new JLabel("");
        errorDisplayer.setBounds(0, 490, 500, 40);
        errorDisplayer.setFont(new Font("dialog", Font.ITALIC, 20));
        errorDisplayer.setHorizontalAlignment(SwingConstants.CENTER);
        errorDisplayer.setForeground(Color.RED);

        this.add(errorDisplayer);

        //humidity image
        ImageIcon humidityIcon = getIconImage("humidity.png");
        JLabel humidityImage = new JLabel(resizeIcon(35, 55, humidityIcon));

        humidityImage.setBounds(15, 600, 65, 55);

        this.add(humidityImage);

        //humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b></html>");
        humidityText.setBounds(80, 600, 85, 55);
        humidityText.setFont(new Font("dialog", Font.PLAIN, 16));
        humidityText.setForeground(Color.WHITE);

        this.add(humidityText);

        //windspeed image
        ImageIcon windspeedIcon = getIconImage("wind.png");
        JLabel windspeedImage = new JLabel(resizeIcon(55, 45, windspeedIcon));

        windspeedImage.setBounds(320, 600, 65, 55);

        this.add(windspeedImage);

        //windspeed text
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b></html>");
        windspeedText.setBounds(400, 600, 85, 55);
        windspeedText.setFont(new Font("dialog", Font.PLAIN, 16));
        windspeedText.setForeground(Color.WHITE);

        this.add(windspeedText);

        searchBttn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, Double> weatherData = AppLogic.getWeatherData(searchBar.getText());

                searchBar.setText("");

                if(weatherData != null) {
                    ImageIcon weatherIcon = getIconImage(generateImagePath(weatherData.get("weather_code")));

                    cityName.setText(AppLogic.locationName);
                    weatherConditionImage.setIcon(resizeIcon(200, 200, weatherIcon));
                    tempNumber.setText(String.valueOf(weatherData.get("temperature") + "°C"));
                    humidityText.setText("<html><b>Humidity </b>" + String.valueOf(weatherData.get("humidity") + "%</html>"));
                    windspeedText.setText("<html><b>Windspeed </b>" + String.valueOf(weatherData.get("wind_speed") + "km/h</html>"));
                    weatherConditionDesc.setText(generateDescription(weatherData.get("weather_code")));


                    errorDisplayer.setText("");
                }


                if(AppError.getErrorMesage() != null) {
                    errorDisplayer.setText(AppError.getErrorMesage());
                    AppError.setErrorMesage(null);
                }

            }
        });
    }



    private String generateImagePath(double wcode) {
        String path;

        switch((int) wcode) {
            case 2, 3:
                path = "cloudy.png";
                break;
            case 51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82, 85, 86, 95, 96, 99:
                path = "rain.png";
                break;
            case 71, 73, 75, 77:
                path = "snow.png";
                break;
            default:
                path = "clear.png";
                break;
        }

        return path;
    }


    private String generateDescription(double wcode) {
        String desc = "";

        switch((int) wcode) {
            case 0:
                desc = "Clear sky";
                break;
            case 1:
                desc = "Mainly clear";
                break;
            case 2:
                desc = "Partly cloudy";
                break;
            case 3:
                desc = "Overcast";
                break;
            case 45:
                desc = "Fog";
                break;
            case 48:
                desc = "Depositing rime fog";
                break;
            case 51:
                desc = "Light drizzle";
                break;
            case 53:
                desc = "Moderate drizzle";
                break;
            case 55:
                desc = "Dense intensity drizzle";
                break;
            case 56:
                desc = "Light freezing drizzle";
                break;
            case 57:
                desc = "Dense intensity freezing drizzle";
                break;
            case 61:
                desc = "Slight rain";
                break;
            case 63:
                desc = "Moderate rain";
                break;
            case 65:
                desc = "Heavy intensity rain";
                break;
            case 66:
                desc = "Light frezzing rain";
                break;
            case 67:
                desc = "Heavy intensity rain";
                break;
            case 71:
                desc = "Slight snowfall";
                break;
            case 73:
                desc = "Moderate snowfall";
                break;
            case 75:
                desc = "Heavy snowfall";
                break;
            case 77:
                desc = "Snow grains";
                break;
            case 80:
                desc = "Slight rain showers";
                break;
            case 81:
                desc = "Moderate rain showers";
                break;
            case 82:
                desc = "Violent rain showers";
                break;
            case 85:
                desc = "Slight snow showers";
                break;
            case 86:
                desc = "Heavy snow showers";
                break;
            case 95:
                desc = "Slight or moderate thunderstorm";
                break;
            case 96:
                desc = "Thunderstorm with slight hail";
                break;
            case 99:
                desc = "Thunderstorm with heavy hail";
                break;
            default:
                desc = "Unknown";
                break;
        }

        return desc;
    }

    private ImageIcon getIconImage(String imagePath) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(imagePath);
        return new ImageIcon(imgURL);
    }


    private ImageIcon resizeIcon(int width, int height, ImageIcon icon) {
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(width,  height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);  // transform it back and return
    }
}
