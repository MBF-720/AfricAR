package pfe.africar.classes;

public class GradeInfo {
    private String subjectName;
    private double averageGrade;

    public GradeInfo(String subjectName, double averageGrade) {
        this.subjectName = subjectName;
        this.averageGrade = averageGrade;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public double getAverageGrade() {
        return averageGrade;
    }
}
