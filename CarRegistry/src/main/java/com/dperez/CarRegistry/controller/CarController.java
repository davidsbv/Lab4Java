package com.dperez.CarRegistry.controller;

import com.dperez.CarRegistry.controller.dtos.CarDTO;
import com.dperez.CarRegistry.controller.mapper.CarDTOMapper;
import com.dperez.CarRegistry.service.CarService;
import com.dperez.CarRegistry.service.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    private static final Logger log = LoggerFactory.getLogger(CarController.class);
    @Autowired
    private CarService carService;

    @PostMapping("add-car")
    public ResponseEntity<?> addCar(@RequestBody CarDTO carDTO){

        try {
            // Se convierte carDTO a Car y se utiliza en la llmada al método addCar.
            // Cuando se guarda se devuelve en newCarDTO  y se muestra la respuesta
            Car car = CarDTOMapper.INSTANCE.carDTOToCar(carDTO);
            Car newCar = carService.addCar(car);
            CarDTO newCarDTO = CarDTOMapper.INSTANCE.carToCarDTO(newCar);
            log.info("New Car added");
            return ResponseEntity.ok(newCarDTO);

        } catch (IllegalArgumentException e) {
            // Error por Id ya existente.
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (Exception e){
            log.error("Error while adding new car");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("get-car/{id}")
    public ResponseEntity<?> getCarById(@PathVariable Integer id){

        // Se busca la id solicitada. Si existe se devuelve la información del coche. Si no devuelve mensaje de error.
        Car car = carService.getCarById(id);
        if (car != null){
            log.info("Car info loaded");
            CarDTO carDTO = CarDTOMapper.INSTANCE.carToCarDTO(car);
            return ResponseEntity.ok(carDTO);
        }
        else {
            log.error("Id does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");
        }
    }

    @PutMapping("update-car/{id}")
        public ResponseEntity<?> updateCarById(@PathVariable Integer id, @RequestBody CarDTO carDto){

        try {
            // carDTO a Car y llamada al método updateCarById
            Car car = CarDTOMapper.INSTANCE.carDTOToCar(carDto);
            Car carToUpdate = carService.updateCarById(id, car);
            CarDTO carUpdated = CarDTOMapper.INSTANCE.carToCarDTO(carToUpdate);
            return ResponseEntity.ok(carUpdated);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (Exception e){
            log.error("Error while updating car");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
