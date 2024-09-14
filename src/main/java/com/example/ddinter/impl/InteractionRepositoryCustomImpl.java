package com.example.ddinter.impl;

import com.example.ddinter.repositories.InteractionRepositoryCustom;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InteractionRepositoryCustomImpl implements InteractionRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<String> getAllUniqueDrugs() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group()
                        .addToSet("Drug1").as("allDrugs1")
                        .addToSet("Drug2").as("allDrugs2"),
                Aggregation.project()
                        .and("allDrugs1").concatArrays("allDrugs2")
                        .as("uniqueDrugs")
        );

        AggregationResults<UniqueDrugsResult> results = mongoTemplate.aggregate(aggregation, "interactions", UniqueDrugsResult.class);
        return results.getMappedResults().isEmpty() ? List.of() : results.getMappedResults().get(0).getUniqueDrugs();
    }

    @Data
    public static class UniqueDrugsResult {
        private List<String> uniqueDrugs;
    }
}
