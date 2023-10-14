package com.truenorth.truenorth.infraestructure.api.consumer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateRandomStringRequest {
    private String jsonrpc = "2.0";
    private String method = "generateStrings";
    private ParamsRequest params;
    private Integer id = 6035;
}
