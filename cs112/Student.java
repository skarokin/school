/**
 * Used for examples in DS
 */
public class Student {

    /**
     * INSTANCE VARIABLES
     */
    private String name;
    private int graduationYear;

    /**
     * Constructor
     * @param name is name of the student
     * @param graduationYear is graduation year of the student
     */
    public Student (String name, int graduationYear) {
        this.name = name;
        this.graduationYear = graduationYear;
    }
    public String toString() {
        return String.format("%s (%d)", name, graduationYear);
    }



    
}
