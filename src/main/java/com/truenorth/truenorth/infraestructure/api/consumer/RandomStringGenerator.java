package com.truenorth.truenorth.infraestructure.api.consumer;

import com.truenorth.truenorth.infraestructure.api.consumer.dto.request.GenerateRandomStringRequest;
import com.truenorth.truenorth.infraestructure.api.consumer.dto.request.ParamsRequest;
import com.truenorth.truenorth.infraestructure.api.consumer.dto.response.RandomStringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.truenorth.truenorth.application.constants.ConstantsMessages.CHARACTERS;

@Component
public class RandomStringGenerator {
    @Value("${app.random.apiURL}")
    private String API_URL;
    @Value("${app.random.apiKey}")
    private String apiKey;
    @Autowired
    RestTemplate restTemplate;

    public String generateRandomString(Integer length){
        GenerateRandomStringRequest generateRandomStringRequest = new GenerateRandomStringRequest();
        generateRandomStringRequest.setParams(new ParamsRequest(apiKey, 1, length, CHARACTERS, true, null));
        HttpEntity<GenerateRandomStringRequest> request = new HttpEntity<>(generateRandomStringRequest);
        ResponseEntity<RandomStringResponse> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, RandomStringResponse.class);
        return Objects.requireNonNull(response.getBody()).getResult().getRandom().getData().get(0).getRandom();
    }


}
