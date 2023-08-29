package ru.chemakin.Client;


import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import ru.chemakin.Client.dto.MeasurementsDTO;
import ru.chemakin.Client.dto.SensorDTO;

import java.util.HashMap;
import java.util.Map;

public class Client
{
    public static void main( String[] args )
    {
        SensorDTO sensorDTO = new SensorDTO("ttt");

        addMeasurement(15.4, false, sensorDTO, new RestTemplate());



    }

    public static void registerSensor(String nameSensorDTO){

        String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", nameSensorDTO);

        makeRequestWithJSONData(jsonData, url);
    }

    public static void addMeasurement(double value, boolean raining, String nameSensorDTO){
        String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();

        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", nameSensorDTO));


        makeRequestWithJSONData(jsonData, url);
    }


    public static void makeRequestWithJSONData(Map<String, Object> jsonData, String url){
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, jsonData, HttpStatus.class);
    }
}
