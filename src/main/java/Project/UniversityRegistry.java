package Project;

public class UniversityRegistry {

    private University university;
    private int numOfUniversities;

    public UniversityRegistry() {
        this.university = university;
    }

    public University getUniversity() {
        return university;
    }

    @Override
    public String toString() {
        return "UniversityRegistry{" +
                "university=" + university +
                ", numOfUniversities=" + numOfUniversities +
                '}';
    }
}
