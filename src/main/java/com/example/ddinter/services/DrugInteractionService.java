package com.example.ddinter.services;

import com.example.ddinter.impl.InteractionRepositoryCustomImpl;
import com.example.ddinter.models.Interaction;
import com.example.ddinter.repositories.InteractionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class DrugInteractionService {

    private static final Logger logger = LoggerFactory.getLogger(DrugInteractionService.class);

    @Autowired
    private InteractionRepositoryCustomImpl interactionRepositoryCustom;

    @Autowired
    private InteractionRepository interactionRepository;

    /**
     * Find drug interactions based on the drug list provided in the request.
     *
     * @param drugList List of drugs with their names.
     * @return A map containing the drug interactions.
     */
    public List<Interaction> findInteractions(List<String> drugList) {
        logger.info("Received request to find interactions for drug list: {}", drugList);

        List<Interaction> interactionsList = new ArrayList<>();

        for (int i=0; i<drugList.size(); i++) {
            for (int j=0; j<i; j++) {
                String drug1 = drugList.get(i);
                String drug2 = drugList.get(j);

                if (!drug1.equals(drug2)) {
                    logger.debug("Querying interactions between Drug1: {} and Drug2: {}", drug1, drug2);

                    // Use the repository to find the interaction
                    List<Interaction> interaction = interactionRepository.findByDrug1AndDrug2(drug1, drug2);

                    if (!interaction.isEmpty()) {
                        logger.debug("Found interaction: {}", interaction.get(0));
                        interactionsList.add(interaction.get(0));
                    } else {
                        logger.debug("No interaction found between {} and {}", drug1, drug2);
                    }
                }
            }
        }

        // Sorting interactions based on levels
        Map<String, Integer> levelToInt = Map.of(
                "Major", 0,
                "Moderate", 1,
                "Minor", 2,
                "Unknown", 3
        );

        interactionsList.sort(Comparator.comparingInt(
                interaction -> levelToInt.getOrDefault(interaction.getLevel(), 3)
        ));

        logger.info("Returning sorted interactions: {}", interactionsList);
        return interactionsList;
    }

    /**
     * Fetch all distinct drug names from Drug_A and Drug_B, and combine them into a unique list.
     *
     * @return A list of distinct drug names.
     */
    public List<String> getDistinctDrugNames() {
        logger.info("Received request to fetch distinct drug names");

        // Fetch distinct Drug_A and Drug_B values using repository methods
        List<String> distinctDrugList = interactionRepositoryCustom.getAllUniqueDrugs();
        logger.info("Returning distinct drug names: {}", distinctDrugList);
        return distinctDrugList;
    }
}
