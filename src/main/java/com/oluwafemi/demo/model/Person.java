package com.oluwafemi.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;
public class Person {

    private final UUID id;
    @NotBlank
    private String name;

    //@JsonProperty("name") ==> We use this, so when we send a JSON payload (e.g. {"name" : "James Bond" })to this server,
    // SpringBoot knows how to construct a Person object from the payload. Would still work without it - I believe
    public Person(@JsonProperty("id") UUID id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}


// For @NotNull or @NotBlank annotations to work, @Valid must be used in the controller (for the object/entity coming from the request)
// Also, the following has to be added to the pom.xml dependencies :
//      <dependency>
//      <groupId>org.springframework.boot</groupId>
//      <artifactId>spring-boot-starter-validation</artifactId>
//      </dependency>
