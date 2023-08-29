package ru.chemakin.Client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MeasurementsResponse {
    List<MeasurementsDTO> measurementsDTOS;
}
