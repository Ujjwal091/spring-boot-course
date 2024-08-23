package com.example.uber.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Rider {
    @Id
    private Long id;
    private Double rating;

    @OneToOne(optional = false)
    private User user;
}
