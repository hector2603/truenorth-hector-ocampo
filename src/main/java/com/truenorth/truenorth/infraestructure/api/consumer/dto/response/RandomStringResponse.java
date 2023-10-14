package com.truenorth.truenorth.infraestructure.api.consumer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomStringResponse {
    private String jsonrpc;
    private ResultResponse result;
    private Integer id;
}
