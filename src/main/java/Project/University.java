package Project;

public class University {

    private String universityName;
    private String universityShortName;
    private String city;
    private String universityAddress;

    public University(String universityName, String universityShortName, String city, String universityAddress) {
        this.universityName = universityName;
        this.universityShortName = universityShortName;
        this.city = city;
        this.universityAddress = universityAddress;
    }

    public String getUniversityName() {
        return universityName;
    }

    public String getUniversityShortName() {
        return universityShortName;
    }

    public String getCity() {
        return city;
    }

    public String getUniversityAddress() {
        return universityAddress;
    }
}
