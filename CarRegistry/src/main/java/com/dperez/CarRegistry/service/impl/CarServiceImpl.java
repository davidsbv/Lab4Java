package com.dperez.CarRegistry.service.impl;

import com.dperez.CarRegistry.repository.BrandRepository;
import com.dperez.CarRegistry.repository.CarRepository;
import com.dperez.CarRegistry.repository.entity.BrandEntity;
import com.dperez.CarRegistry.repository.entity.CarEntity;
import com.dperez.CarRegistry.repository.mapper.CarEntityMapper;
import com.dperez.CarRegistry.service.CarService;
import com.dperez.CarRegistry.service.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private static final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandRepository brandRepository;


    @Override
    public Car addCar(Car car) throws IllegalArgumentException {

        // Verifica si la Id ya existe. Lanza una excepción en caso afirmativo.
        if(car.getId() != null && carRepository.existsById(car.getId())){

            throw new IllegalArgumentException("The Id already" + car.getId() +" exists");
        }

        // Verificar si la marca existe
        Optional<BrandEntity> brandEntityOptional = brandRepository.findByNameIgnoreCase(car.getBrand());

        if(!brandEntityOptional.isPresent()){
            throw new IllegalArgumentException("Brand " + car.getBrand() + " does not exist");
        }

        // Obtener la BrandEntity existente
        BrandEntity brandEntity = brandEntityOptional.get();

        // Asociar la BrandEntitiy existente a la CarEntity
        CarEntity carEntity = CarEntityMapper.INSTANCE.carToCarEntity(car);
        carEntity.setBrand((brandEntity)); // Se asocia la BrandEntity existente

        // Se guarda la CarEntity en la base de datos
        CarEntity savedCarEntity = carRepository.save(carEntity);

        // Se devuelve el coche guardado como modelo de dominio
        return CarEntityMapper.INSTANCE.carEntityToCar(savedCarEntity);
    }

    @Override
    public Car getCarById(Integer id) {
        return null;
    }

    @Override
    public Car updateCar(Integer id, Car car) {
        return null;
    }

    @Override
    public void deleteCar(Integer id) {

    }

    @Override
    public LinkedList<Car> getAllCars() {
        return null;
    }
}
