package ru.chemakin.Client;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.chemakin.Client.dto.MeasurementsDTO;
import ru.chemakin.Client.dto.MeasurementsResponse;
import ru.chemakin.Client.dto.SensorDTO;

import java.util.HashMap;
import java.util.Map;

public class Client
{
    public static void main( String[] args )
    {
//        registerSensor("client_sensor");
//        for (int i = 0; i < 500; i++) {
//            addMeasurement(Math.random()*50, false, "client_sensor");
//        }

        makeGetRequest();
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

    // TODO fix error
    public static void makeGetRequest(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements";
        ResponseEntity<MeasurementsResponse> entity = restTemplate.getForEntity(url, MeasurementsResponse.class);

        MeasurementsResponse measurementsResponse = entity.getBody();

        for (MeasurementsDTO m:
             measurementsResponse.getMeasurementsDTOS()) {
            System.out.println(m.getValue() + " - " + m.isRaining() + " - " + m.getSensorDTO().getName());
        }
    }
}
