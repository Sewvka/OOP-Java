package GradeBook;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Contains a student's grades.
 */
public class Gradebook {
    private HashMap<String, Integer>[] grades;

    /**
     * Initializes the gradebook.
     */
    public Gradebook() {
        grades = (HashMap<String, Integer>[]) new HashMap[8];
        for (int i = 0; i < 8; i++) grades[i] = new HashMap<String, Integer>();
    }

    /**
     * Adds a new subject and its grade.
     *
     * @param subject - title of subject to be added.
     * @param sem     - number of semester to add grade to.
     * @param grade   - the grade itself.
     */
    public void addGrade(String subject, int sem, Integer grade) {
        if (sem > 8 || sem < 1) throw new IllegalArgumentException("Semester value has to be between 1 and 8!");

        grades[sem - 1].putIfAbsent(subject, grade);
    }

    /**
     * Calculates the average grade over all semesters and grades.
     *
     * @return - returns the average grade as float value.
     */

    public float averageGrade() {
//        float avg = 0;
//        int gradecount = 0;

//        for (int i = 0; i < 8; i++) {
//            for (Map.Entry<String, Integer> entry : grades[i].entrySet()) {
//                avg += entry.getValue();
//                gradecount++;
//            }
//        }

        List<Collection<Integer>> marks = new ArrayList<>();
        IntStream.range(0, 8).forEach( idx -> marks.add(grades[idx].values()));
        List<Integer> floatMarks = marks.stream().collect(ArrayList::new, List::addAll, List::addAll);
        return (float) floatMarks.stream().mapToDouble(d -> d).average().orElse(0.0);
    }

    /**
     * Checks if an increased stipend can be given this semester.
     *
     * @param sem - current semester.
     * @return - returns true if an increased stipend is possible.
     */
    public boolean increasedStipend(int sem) {
        if (sem > 8 || sem < 1) throw new IllegalArgumentException("Semester value has to be between 1 and 8!");

//        for (Map.Entry<String, Integer> entry : grades[sem - 1].entrySet()) {
//            if (entry.getValue() != 5) {
//                return false;
//            }
//        }

        return grades[sem-1].values().stream().allMatch(mark -> mark.equals(5));
    }

    /**
     * Checks if a red diploma can be give.
     * Conditions for a red diploma:
     * 1) No '3' grades.
     * 2) 75% of grades in diploma are '5'.
     *
     * @return - returns true if a red diploma is possible.
     */
    public boolean redDiploma() {
        Map<String, Integer> diploma = new HashMap<String, Integer>();
//        int gradecount = 0;

//        for (int i = 0; i < 8; i++) {
//            for (Map.Entry<String, Integer> entry : grades[i].entrySet()) {
//                if (entry.getValue() == 3) {
//                    return false;
//                }
//
//                if (entry.getValue() == 5) {
//                    diploma.putIfAbsent(entry.getKey(), entry.getValue());
//                }
//
//                gradecount++;
//            }
//        }
        List<Integer> marks = new ArrayList<>();
        IntStream.range(0, 8).forEach(idx -> {
            grades[idx].entrySet().forEach(entry -> {
                if (entry.getValue() == 5) diploma.putIfAbsent(entry.getKey(), entry.getValue()); marks.add(entry.getValue());
            });
        });
        if (marks.stream().anyMatch(mark -> mark == 3)) return false;

        float ratioOf5 = (float) diploma.size() / marks.size();

        return ratioOf5 >= 0.75;


    }
}