package ru.chemakin.Client;


import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.chemakin.Client.dto.MeasurementsDTO;
import ru.chemakin.Client.dto.MeasurementsResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {
    public static void main(String[] args) {
        registerSensor("client_sensor");

        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            addMeasurement(random.nextDouble() * 50,
                    random.nextBoolean(),
                    "client_sensor");
        }

        makeGetRequest();
    }

    public static void registerSensor(String nameSensorDTO) {

        String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", nameSensorDTO);

        makeRequestWithJSONData(jsonData, url);
    }

    public static void addMeasurement(double value, boolean raining, String nameSensorDTO) {
        String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();

        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", nameSensorDTO));


        makeRequestWithJSONData(jsonData, url);
    }


    public static void makeRequestWithJSONData(Map<String, Object> jsonData, String url) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(jsonData, httpHeaders);
        restTemplate.postForObject(url, entity, HttpStatus.class);
    }

    public static void makeGetRequest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements";
        ResponseEntity<MeasurementsResponse> entity = restTemplate.getForEntity(url, MeasurementsResponse.class);

        MeasurementsResponse measurementsResponse = entity.getBody();

        for (MeasurementsDTO m :
                measurementsResponse.getMeasurements()) {
            System.out.println(m);
        }
    }
}
