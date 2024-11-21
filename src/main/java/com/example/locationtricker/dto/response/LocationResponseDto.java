package com.example.locationtricker.dto.response;

import com.example.locationtricker.constant.Language;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocationResponseDto {
    private Long id;
    private Language language;
    private String location;
    private String geminiResponse;
}
