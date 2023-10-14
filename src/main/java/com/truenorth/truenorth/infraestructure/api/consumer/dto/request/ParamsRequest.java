package com.truenorth.truenorth.infraestructure.api.consumer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamsRequest {

    private String apiKey;
    private Integer n;
    private Integer length;
    private String characters;
    private Boolean replacement;
    private String pregeneratedRandomization;

}
