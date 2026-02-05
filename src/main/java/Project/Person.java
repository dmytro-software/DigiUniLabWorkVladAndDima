package Project;

public abstract class Person {
    private final int idPerson;
    private String pib;
    private final String birthDate;
    private String email;
    private int phoneNumber;


    public Person(int idPerson, String pib, String birthDate, String email, int phoneNumber ) {
        this.idPerson = idPerson;
        this.pib = pib;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public String getPib() {
        return pib;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return  "idPerson=" + idPerson +
                ", pib='" + pib + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber;
    }
}

