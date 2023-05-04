package edu.javacourse.city.dao;

import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PersonCheckDaoTest {

    @Test
    public void checkPerson() throws PersonCheckException {
        PersonRequest person = new PersonRequest();

        person.setSurName("Васильев");
        person.setGivenName("Павел");
        person.setPatronymic("Николаевич");
        person.setDateOfBirth(LocalDate.of(1995,3, 18));
        person.setStreetCode(1);
        person.setBuilding("10");
        person.setExtension("2");
        person.setApartment("121");

        PersonCheckDao personCheckDao = new PersonCheckDao();
        PersonResponse response = personCheckDao.checkPerson(person);
        Assert.assertTrue(response.isRegistered());
        Assert.assertFalse(response.isTemporal());
    }

    @Test
    public void checkPerson2() throws PersonCheckException {
        PersonRequest person = new PersonRequest();

        person.setSurName("Васильева");
        person.setGivenName("Ирина");
        person.setPatronymic("Николаевна");
        person.setDateOfBirth(LocalDate.of(1997,8, 21));
        person.setStreetCode(1);
        person.setBuilding("271");
        person.setApartment("4");

        PersonCheckDao personCheckDao = new PersonCheckDao();
        PersonResponse response = personCheckDao.checkPerson(person);
        Assert.assertTrue(response.isRegistered());
        Assert.assertFalse(response.isTemporal());
    }
}