package com.dw.ventas.controllers;


import com.dw.ventas.entities.Persona;
import com.dw.ventas.models.PersonaRequest;
import com.dw.ventas.models.PersonaUpdateRequest;
import com.dw.ventas.services.PersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping()
    public ResponseEntity<Persona> createPersona(@Valid @RequestBody PersonaRequest request) {
        Persona nuevaPersona = personaService.createPersona(request);
        return new ResponseEntity<>(nuevaPersona, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Integer id) {
        final Optional<Persona> persona = personaService.findPersonaById(id);
        return persona.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<Persona>> getAllPersonas() {
        final List<Persona> personas = personaService.findAllPersonas();
        return ResponseEntity.ok(personas);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Persona>> getPersonasByAnyCriteria(@RequestParam String term) {
        final List<Persona> personas = personaService.findPersonasByAnyCriteria(term);
        return ResponseEntity.ok(personas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Integer id, @Valid @RequestBody PersonaUpdateRequest request) {
        Persona personaActualizada = personaService.updatePersona(id, request);
        return ResponseEntity.ok(personaActualizada);
    }
}