package com.example.uber.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
public class Driver {
    @Id
    private Long id;
    private Double rating;

    @OneToOne(optional = false)
    private User user;

    private Boolean isAvailable;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point currentLocation;
}
