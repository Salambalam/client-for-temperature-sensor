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
        // Register a new sensor with the name "client_sensor"
        registerSensor("client_sensor");

        // Generate random measurements and send them to the server
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            addMeasurement(random.nextDouble() * 50,
                    random.nextBoolean(),
                    "client_sensor");
        }

        // Retrieve data from the server
        makeGetRequest();
    }

    /**
     * Registers a new sensor on the server.
     * @param nameSensorDTO The name of the sensor to register.
     */
    public static void registerSensor(String nameSensorDTO) {
        String url = "http://localhost:8080/sensors/registration";

        // Create JSON data for sensor registration
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", nameSensorDTO);

        // Send a POST request for sensor registration
        makeRequestWithJSONData(jsonData, url);
    }

    /**
     * Adds a measurement to the server.
     * @param value The measurement value.
     * @param raining A flag indicating rain (true if raining, false if not).
     * @param nameSensorDTO The name of the sensor for which the measurement is made.
     */
    public static void addMeasurement(double value, boolean raining, String nameSensorDTO) {
        String url = "http://localhost:8080/measurements/add";

        // Create JSON data for adding a measurement
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", nameSensorDTO));

        // Send a POST request for adding a measurement
        makeRequestWithJSONData(jsonData, url);
    }

    /**
     * Sends a POST request with JSON data to the specified URL.
     * @param jsonData JSON data to send.
     * @param url URL to send the request to.
     */
    public static void makeRequestWithJSONData(Map<String, Object> jsonData, String url) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(jsonData, httpHeaders);

        // Send a POST request with JSON data
        restTemplate.postForObject(url, entity, HttpStatus.class);
    }

    /**
     * Retrieves data from the server.
     */
    public static void makeGetRequest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/measurements";

        // Send a GET request to retrieve data from the server
        ResponseEntity<MeasurementsResponse> entity = restTemplate.getForEntity(url, MeasurementsResponse.class);

        MeasurementsResponse measurementsResponse = entity.getBody();

        // Print the received measurements to the console
        for (MeasurementsDTO m : measurementsResponse.getMeasurements()) {
            System.out.println(m);
        }
    }
}
