package com.example.locationtricker.entity;

import com.example.locationtricker.constant.Language;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sohbatgah")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Language language;

    @Column(length = 1000)
    private String question;
}
