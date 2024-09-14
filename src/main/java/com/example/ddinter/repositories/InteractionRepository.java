package com.example.ddinter.repositories;


import com.example.ddinter.models.Interaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends MongoRepository<Interaction, String> {

    // Find interactions between two drugs
    @Query("{ 'Drug1': ?0, 'Drug2': ?1 }")
    List<Interaction> findByDrug1AndDrug2(String drug1, String drug2);

}
