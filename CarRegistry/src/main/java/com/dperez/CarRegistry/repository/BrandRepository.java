package com.dperez.CarRegistry.repository;

import com.dperez.CarRegistry.repository.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {

    // Busca la marca del coche por nombre ignorando mayúsculas o minúsculas
    @Query("SELECT b FROM BrandEntity b WHERE LOWER(b.name) = LOWER(:name)")
    Optional<BrandEntity> findByNameIgnoreCase(@Param("name") String name);

    //Optional<BrandEntity> findByName(String name);
}

