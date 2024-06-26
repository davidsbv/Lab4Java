package com.dperez.CarRegistry.service.impl;

import com.dperez.CarRegistry.repository.BrandRepository;
import com.dperez.CarRegistry.repository.CarRepository;
import com.dperez.CarRegistry.repository.entity.BrandEntity;
import com.dperez.CarRegistry.repository.entity.CarEntity;
import com.dperez.CarRegistry.repository.mapper.CarEntityMapper;
import com.dperez.CarRegistry.service.CarService;
import com.dperez.CarRegistry.service.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@Slf4j
@Service
public class CarServiceImpl implements CarService {

  //  private static final Logger log = LoggerFactory.getLogger(CarServiceImpl.class);
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

        // Búsqueda de car por id
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);

        // Si se encuentra devuelve el objeto car. En caso contrario devuelve null.
        return carEntityOptional.map(CarEntityMapper.INSTANCE::carEntityToCar).orElse(null);
    }

    @Override
    public Car updateCarById(Integer id, Car car) throws IllegalArgumentException {

        // Verifica si la Marca del objeto Car existe
        Optional<BrandEntity> brandEntityOptional = brandRepository.findByNameIgnoreCase(car.getBrand());

        if (brandEntityOptional.isEmpty()){
            throw new IllegalArgumentException("Brand with name " + car.getBrand() + " does not exist.");
        }

        // Verifica si la Id existe. Lanza excepción en caso negativo. En caso afirmativo actualiza los datos
        if(id != null && !carRepository.existsById(id)){
           throw new IllegalArgumentException("Id " + id + " does not exist.");
        }
        else {
            // Se obtiene la BrandEntity existente y se asocia a la carEntity a actualizar
            BrandEntity brandEntity = brandEntityOptional.get();
            log.info("Marca " + brandEntity.toString() + " encontrada");
            CarEntity carEntity = CarEntityMapper.INSTANCE.carToCarEntity(car);
            // Seteo de la id y la marca
            carEntity.setId(id);
            carEntity.setBrand(brandEntity);

            // Actualiza los datos y devuelve el objeto actualizado.
            CarEntity updatedCarEntity = carRepository.save(carEntity);
            return CarEntityMapper.INSTANCE.carEntityToCar(updatedCarEntity);
        }

    }

    @Override
    public void deleteCarById(Integer id) throws IllegalArgumentException {

        if(id != null && carRepository.existsById(id)){
            carRepository.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("Car not found with Id: " + id);
        }
    }

    @Override
    public LinkedList<Car> getAllCars() {
        return null;
    }
}
