package com.example.locationtricker.dto.request;

import com.example.locationtricker.constant.Language;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocationRequestDto {
    private Language language;
    private String location;
    private String geminiResponse;
}
