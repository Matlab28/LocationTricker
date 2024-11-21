package com.example.locationtricker.controller;

import com.example.locationtricker.dto.request.LocationRequestDto;
import com.example.locationtricker.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63342")
public class LocationController {
    private final LocationService service;


//    @PostMapping("/processQuestion")
//    public Root processSohbatgahQuestion(@RequestBody SohbatgahRequestDto dto) {
//        return service.processTranscriptRequest(dto);
//    }

    @PostMapping("/findLocation")
    public ResponseEntity<?> processSohbatgahQuestion(@RequestBody LocationRequestDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.processTranscriptRequest(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Something went wrong while processing location request...");
        }
    }

    @GetMapping("/latestResponse")
    public ResponseEntity<?> getLatestResponse() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getLatestResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Something went wrong while responding...");
        }
    }
}
