package com.truenorth.truenorth.infraestructure.api.consumer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomResponse {
    private List<DataResponse> data;
    private String completionTime;
}
