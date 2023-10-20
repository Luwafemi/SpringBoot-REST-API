package com.oluwafemi.demo.dao;

import com.oluwafemi.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
//@Repository ==> This is how we tell SpringBoot that this class should be instantiated as a Bean, so we can inject it in other classes
//("fakeDao") ==> This, together with @Qualifier("fakeDao") in the actual Service class (PersonService.java in this case) is
// how we tell SpringBoot what particular implementation of PerSonDao to use in PersonService  - Dependency injection
public class FakePersonDataAccessService implements PersonDao {

    private static final List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> getAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = getPersonById(id);
        if (personMaybe.isEmpty()) return 0;
        DB.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return getPersonById(id)
                .map(p -> {
                        p.setName(person.getName()) ;
                        return 1;
                }).orElse(0);
    }
}
