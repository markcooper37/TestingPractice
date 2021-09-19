package com.filestotest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.anyInt;
import static org.assertj.core.api.Assertions.*;

class PersonServiceTest {

    @Mock
    private PersonDAO personDAO;
    private PersonService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new PersonService(personDAO);
    }

    @Test
    void itCanSavePerson() {

        Mockito.when(personDAO.savePerson(Mockito.any(Person.class))).thenReturn(1);

        Person person  = new Person(5, "Bob", 26);

        underTest.savePerson(person);

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        Mockito.verify(personDAO).savePerson(personArgumentCaptor.capture());

        Person person1 = personArgumentCaptor.getValue();
        assertThat(person1.getId()).isEqualTo(5);
        assertThat(person1.getName()).isEqualTo("Bob");
        assertThat(person1.getAge()).isEqualTo(26);

    }

    @Test
    void itCanDeletePerson() {

        Person person  = new Person(5, "Bob", 26);
        List<Person> people = List.of(person);

        Mockito.when(personDAO.getPeople()).thenReturn(people);

        Mockito.when(personDAO.deletePerson(anyInt())).thenReturn(1);

        int result = underTest.deletePerson(5);

        assertThat(result).isEqualTo(1);

    }

    @Test
    void canGetPeopleFromDB() {

        Person person1  = new Person(5, "Bob", 26);
        Person person2 = new Person(7, "Susan", 39);
        List<Person> people = List.of(person1, person2);

        Mockito.when(personDAO.getPeople()).thenReturn(people);

        List<Person> allPeople = underTest.getPeople();

        assertThat(allPeople).isEqualTo(people);

    }

    @Test
    void canGetPersonById() {

        Person person1  = new Person(5, "Bob", 26);
        Person person2 = new Person(7, "Susan", 39);
        List<Person> people = List.of(person1, person2);

        Mockito.when(personDAO.getPeople()).thenReturn(people);

        Person person = (underTest.getPersonById(5)).get();

        assertThat(person.getId()).isEqualTo(5);
        assertThat(person.getName()).isEqualTo("Bob");
        assertThat(person.getAge()).isEqualTo(26);

    }

    @Test
    void willNotSavePersonWithNullID() {

        Person person = new Person(null, "Bob", 26);

        assertThatThrownBy(() -> {
            underTest.savePerson(person);
        }).hasMessage("Person cannot have empty fields");

        Mockito.verifyNoInteractions(personDAO);

    }

    @Test
    void willNotSavePersonWithNoName() {

        Person person = new Person(5, "", 26);

        assertThatThrownBy(() -> {
            underTest.savePerson(person);
        }).hasMessage("Person cannot have empty fields");

        Mockito.verifyNoInteractions(personDAO);

    }

    @Test
    void willNotSavePersonWithNullAge() {

        Person person = new Person(5, "Bob", null);

        assertThatThrownBy(() -> {
            underTest.savePerson(person);
        }).hasMessage("Person cannot have empty fields");

        Mockito.verifyNoInteractions(personDAO);

    }

    @Test
    void willNotSavePersonIfPersonIsAlreadySaved() {

        Person person = new Person(5, "Bob", 26);
        List<Person> people = List.of(person);

        Mockito.when(personDAO.getPeople()).thenReturn(people);

        assertThatThrownBy(() -> {
            underTest.savePerson(person);
        }).hasMessage("person with id 5 already exists");

    }


    @Test
    void willNotDeletePersonIfIDDoesNotExist() {

        List<Person> people = new ArrayList<>();

        Mockito.when(personDAO.getPeople()).thenReturn(people);

        assertThatThrownBy(() -> {
            underTest.deletePerson(5);
        }).hasMessage("person with id 5 not found");

    }

}