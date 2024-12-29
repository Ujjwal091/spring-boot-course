package com.example.uber.entities;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;

    @OneToOne(optional = false)
    private User user;

    private Boolean isAvailable;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point currentLocation;
}
