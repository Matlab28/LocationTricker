package com.example.locationtricker.service;

import com.example.locationtricker.dto.gemini.Root;
import com.example.locationtricker.dto.request.LocationRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface GeminiService {
    Root processTranscriptRequest(LocationRequestDto dto);

    Root getLatestResponse();
}
