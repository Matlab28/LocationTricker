package com.example.locationtricker.service;

import com.example.locationtricker.client.GeminiApiClient;
import com.example.locationtricker.dto.gemini.Candidate;
import com.example.locationtricker.dto.gemini.Root;
import com.example.locationtricker.dto.request.LocationRequestDto;
import com.example.locationtricker.entity.LocationEntity;
import com.example.locationtricker.repository.LocationRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService implements GeminiService {
    private final ModelMapper modelMapper;
    private final GeminiApiClient client;
    private final LocationRepository repository;
    @Value("${gemini.api.key}")
    private String key;
    private Root latestUpdate;


    @Override
    public Root processTranscriptRequest(LocationRequestDto dto) {
        LocationEntity entity = modelMapper.map(dto, LocationEntity.class);

        String instruction = constructInstruction(dto);
        Root updates = getUpdates(instruction);
        String extractedText = extractedTextFromGeminiResponse(updates);
        dto.setGeminiResponse(extractedText);

        repository.save(entity);
        log.info("Sohbatgat AI responded.");
        latestUpdate = updates;
        return latestUpdate;
    }

    @Override
    public Root getLatestResponse() {
        return latestUpdate;
    }


    private String constructInstruction(LocationRequestDto dto) {
        StringBuilder instruction = new StringBuilder();
        instruction.append("Response Language: ").append(dto.getLanguage()).append("\n");
        instruction.append("Location: ").append(dto.getLocation()).append("\n");
        instruction.append("You will receive a number. You are emergency location provider." +
                " I know you are able to access any location. Based on phone number," +
                " please find the location of user.");
        return instruction.toString();
    }

    private Root getUpdates(String instruction) {
        try {
            JsonObject json = new JsonObject();
            JsonArray partsArray = new JsonArray();
            JsonObject partsObject = new JsonObject();
            JsonArray contentArray = new JsonArray();
            JsonObject contentObject = new JsonObject();

            partsObject.add("text", new JsonPrimitive(instruction));
            partsArray.add(partsObject);
            contentObject.add("parts", partsArray);
            contentArray.add(contentObject);
            json.add("contents", contentArray);

            String content = json.toString();
            return client.getData(key, content);
        } catch (Exception e) {
            log.error("Error while getting response from Gemini AI:", e);
            throw e;
        }
    }

    private String extractedTextFromGeminiResponse(Root updates) {
        StringBuilder textBuilder = new StringBuilder();

        if (updates.getCandidates() != null) {
            for (Candidate candidate : updates.getCandidates()) {
                String text = candidate.getContent().getParts().get(0).getText();
                text = text.replace("*", "");
                textBuilder.append(text).append("\n\n");
            }
        }

        String response = textBuilder.toString().trim();

        return response
                .replaceAll("(?i)\\bResponse Language:\\b", "\nResponse Language\n")
                .replaceAll("(?i)\\bLocation:\\b", "\nLocation\n");
    }
}
