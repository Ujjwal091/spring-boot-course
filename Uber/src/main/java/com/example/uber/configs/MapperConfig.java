package com.example.uber.configs;

import com.example.uber.dto.PointDto;
import com.example.uber.utils.GeometryUtils;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.typeMap(PointDto.class, Point.class).setConverter(mappingContext -> {
            PointDto pointDto = mappingContext.getSource();
            return GeometryUtils.createPoint(pointDto);
        });

        modelMapper.typeMap(Point.class, PointDto.class).setConverter(mappingContext -> {
            Point pointDto = mappingContext.getSource();
            double[] coordinates = {pointDto.getX(), pointDto.getY()};

            return new PointDto(coordinates);
        });

        return modelMapper;
    }
}
