package com.oluwafemi.demo.api;

import com.oluwafemi.demo.model.Person;
import com.oluwafemi.demo.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
   // @Autowired ==> This is how we auto-wire a PersonService object into this class
    public PersonController(PersonService personService) {

        this.personService = personService;
    }

    @PostMapping
    //@RequestBody ==> we are taking the body from the request and creating a Person object with it
    //@Valid is used to enforce data integrity/constraints ( together with @NotBlank, @NotNull  etc.) - Check Person.java for full explanation
    public void addPerson(@Valid @RequestBody Person person) {
        personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPeople() {

        return personService.getAllPeople();
    }

   //(path = "{id}") ==> we use this to extract whatever comes after "api/v1/person/" and store it as pathVariable 'id'
   // then we use '@PathVariable("id") UUID id' to pass it as an argument. I think the equivalent in Node.js is req.param
   // api/v1/person/:id
    @GetMapping(path = "{id}")
    public Person getPersonByID(@PathVariable("id") UUID id) {
        return personService.getPersonByID(id)
                .orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deletePersonByID(@PathVariable("id") UUID id) {
        personService.deletePerson(id);
    }

    @PutMapping(path = "{id}")
    public void updatePerson(@PathVariable("id") UUID id, @Valid @RequestBody Person newPerson) {
        personService.updatePerson(id, newPerson);
    }
}
