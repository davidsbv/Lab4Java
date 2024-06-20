package com.dperez.CarRegistry.controller.mapper;

import com.dperez.CarRegistry.controller.dtos.CarDTO;
import com.dperez.CarRegistry.service.model.Car;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-19T20:27:55+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
public class CarDTOMapperImpl implements CarDTOMapper {

    @Override
    public CarDTO carToCarDTO(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDTO carDTO = new CarDTO();

        carDTO.setId( car.getId() );
        carDTO.setBrand( car.getBrand() );
        carDTO.setModel( car.getModel() );
        carDTO.setMileage( car.getMileage() );
        carDTO.setPrice( car.getPrice() );
        carDTO.setYear( car.getYear() );
        carDTO.setDescription( car.getDescription() );
        carDTO.setColour( car.getColour() );
        carDTO.setFuelType( car.getFuelType() );
        carDTO.setNumDoors( car.getNumDoors() );

        return carDTO;
    }

    @Override
    public Car carDTOToCarEntity(CarDTO carDTO) {
        if ( carDTO == null ) {
            return null;
        }

        Car car = new Car();

        car.setId( carDTO.getId() );
        car.setBrand( carDTO.getBrand() );
        car.setModel( carDTO.getModel() );
        car.setMileage( carDTO.getMileage() );
        car.setPrice( carDTO.getPrice() );
        car.setYear( carDTO.getYear() );
        car.setDescription( carDTO.getDescription() );
        car.setColour( carDTO.getColour() );
        car.setFuelType( carDTO.getFuelType() );
        car.setNumDoors( carDTO.getNumDoors() );

        return car;
    }
}
