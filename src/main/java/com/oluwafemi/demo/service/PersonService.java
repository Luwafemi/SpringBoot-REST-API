package com.oluwafemi.demo.service;

import com.oluwafemi.demo.dao.PersonDao;
import com.oluwafemi.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
//@Service ==> This is how we tell SpringBoot that this Service class should be instantiated as a Bean, so we can inject it in other classes (I guess)
public class PersonService {

    private final PersonDao personDao;

    @Autowired
    // @Autowired ==> This is how we auto-wire a PersonDao object into this class
    // @Qualifier("postgres)  ==> This, together with @Repository("postgres") in the DataAccessObject class (PostgresPersonDataAccessService.java,
    // in this case) is how we tell SpringBoot which particular implementation to use as PersonDao here - Dependency injection
    public PersonService(@Qualifier("fakeDao") PersonDao personDao) {

        this.personDao = personDao;
    }

    public int addPerson(Person person) {

        return personDao.insertPerson(person);
    }

    public List<Person> getAllPeople() {
        return personDao.getAllPeople();
    }

    public Optional<Person> getPersonByID(UUID id) {
        return personDao.getPersonById(id);
    }

    public int deletePerson(UUID id) {
        return personDao.deletePersonById(id);
    }

    public int updatePerson(UUID id, Person newPerson) {
        return personDao.updatePersonById(id, newPerson);
    }
}
