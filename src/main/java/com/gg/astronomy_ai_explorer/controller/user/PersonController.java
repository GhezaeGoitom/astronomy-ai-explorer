package com.gg.astronomy_ai_explorer.controller.user;

import com.gg.astronomy_ai_explorer.dto.user.MyPersonDto;
import com.gg.astronomy_ai_explorer.dto.user.PersonDto;
import com.gg.astronomy_ai_explorer.dto.user.PersonUpdateDto;
import com.gg.astronomy_ai_explorer.service.user.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllPersons() {

        return ResponseEntity.ok(
                personService.getAllPersons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPersonById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                personService.getPersonById(id));
    }

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(
            @Valid @RequestBody PersonDto personDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personService.createPerson(personDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(
            @PathVariable Long id,
            @Valid @RequestBody PersonUpdateDto personDto) {

        return ResponseEntity.ok(
                personService.updatePerson(id, personDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(
            @PathVariable Long id) {

        personService.deletePerson(id);

        return ResponseEntity.noContent().build();
    }

    //    / my profile
    @PutMapping("/me")
    public ResponseEntity<PersonDto> updateMyProfile(
            @Valid @RequestBody MyPersonDto request
    ) {
        return ResponseEntity.ok(
                personService.updateMyProfile(request)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<PersonDto> getMyProfile() {
        return ResponseEntity.ok(
                personService.getMyProfile()
        );
    }
}