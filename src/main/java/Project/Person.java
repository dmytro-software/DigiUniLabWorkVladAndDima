package Project;

public abstract class Person {
    private final int idPerson;
    private String pib;
    private final String birthDate;
    private String email;
    private long phoneNumber;


    public Person(int idPerson, String pib, String birthDate, String email, long phoneNumber) {
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

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ID: " + idPerson +
                "\n | ПІБ: " + pib +
                "\n | Дата народж: " + birthDate +
                "\n | Email: " + email +
                "\n | Тел: " + phoneNumber;
    }
}

