package weatherApp;

import javax.swing.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class App {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
//                System.out.println("Root of classpath: " + getClass().getClassLoader().getResource("clear.png"));
                new AppGUI().setVisible(true);

//                Map<String, Double> weatherData = AppLogic.getWeatherData("tokyo");


            }

        });
    }
}
