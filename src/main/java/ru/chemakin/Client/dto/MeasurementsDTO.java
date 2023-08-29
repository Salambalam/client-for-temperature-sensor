package ru.chemakin.Client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementsDTO {
    private double value;
    private boolean raining;
    private SensorDTO sensorDTO;
}
