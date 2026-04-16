package Project.Models;

import Project.Annotation.DisplayName;
import Project.Annotation.Identifier;
import Project.Annotation.ValidContact;

import java.time.LocalDate;

public abstract class Person {
    @Identifier(label = "Person Id")
    private final int idPerson;

    @DisplayName(value = "Last Name, First Name, Patronymic")
    private String pib;

    private final LocalDate birthDate;

    @ValidContact(format = "Email")
    private String email;

    @ValidContact(format = "Phone")
    private int phoneNumber;


    public Person(int idPerson, String pib, LocalDate birthDate, String email, int phoneNumber) {
        this.idPerson = idPerson;
        this.pib = pib;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Person() {
        this.idPerson = 0;
        this.birthDate = LocalDate.now();
    }

    public int getIdPerson() {
        return idPerson;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ID: " + idPerson +
                " | PIB: " + pib +
                " | Birth date: " + birthDate +
                " | Email: " + email +
                " | Phone number: " + phoneNumber;
    }
}

