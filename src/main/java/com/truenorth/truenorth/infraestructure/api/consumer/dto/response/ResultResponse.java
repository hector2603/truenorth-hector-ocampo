package com.truenorth.truenorth.infraestructure.api.consumer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse {
    public RandomResponse random;
    public Integer bitsUsed;
    public Integer bitsLeft;
    public Integer requestsLeft;
    public Integer advisoryDelay;

}
