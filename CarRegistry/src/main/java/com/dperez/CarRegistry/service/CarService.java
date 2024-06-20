package com.dperez.CarRegistry.service;

import com.dperez.CarRegistry.service.model.Car;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.LinkedList;

public interface CarService {

    Car addCar(Car car) throws DataIntegrityViolationException;
    Car getCarById(Integer id);
    Car updateCarById(Integer id, Car car);
    void deleteCarById(Integer id);
    LinkedList<Car> getAllCars();
}
