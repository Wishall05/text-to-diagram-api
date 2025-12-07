package com.example.diagram.controller;

import com.example.diagram.model.DiagramResponse;
import com.example.diagram.model.InputRequest;
import com.example.diagram.service.DiagramService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diagram")
public class DiagramController {

    private final DiagramService service;

    public DiagramController(DiagramService service) {
        this.service = service;
    }

    @PostMapping(value = "/generate",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DiagramResponse generate(@RequestBody InputRequest request) {
        return service.generateDiagram(request.getText());
    }
}
