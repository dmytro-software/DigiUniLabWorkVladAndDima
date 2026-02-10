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
        String result = "УНІВЕРСИТЕТ\n";

        for(int i = 0 ;i<numOfUniversities;i++){
            result = result + (i + 1) + ". " + university + "\n";
            result = result + "--------------------------------------------------\n";
        }
        return result;
    }
}
