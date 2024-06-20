package com.dperez.CarRegistry.controller;

import com.dperez.CarRegistry.controller.dtos.CarDTO;
import com.dperez.CarRegistry.controller.mapper.CarDTOMapper;
import com.dperez.CarRegistry.service.CarService;
import com.dperez.CarRegistry.service.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            Car car = CarDTOMapper.INSTANCE.carDTOToCarEntity(carDTO);
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
}
