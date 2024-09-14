package com.example.ddinter.controllers;


import com.example.ddinter.models.Interaction;
import com.example.ddinter.services.DrugInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drugs")
public class DrugInteractionController {

    @Autowired
    private DrugInteractionService drugInteractionService;

    @PostMapping("/interactions")
    public ResponseEntity<List<Interaction>> getDrugInteractions(@RequestBody List<String> drugList) {
        return ResponseEntity.ok(drugInteractionService.findInteractions(drugList));
    }

    @GetMapping("/distinct")
    public ResponseEntity<List<String>> getDistinctDrugNames() {
        return ResponseEntity.ok(drugInteractionService.getDistinctDrugNames());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello");
    }
}
