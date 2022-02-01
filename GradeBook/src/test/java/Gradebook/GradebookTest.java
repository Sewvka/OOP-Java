package Gradebook;

import GradeBook.Gradebook;
import com.sun.jdi.connect.Connector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GradebookTest {

    private Gradebook testBook() {
        Gradebook book = new Gradebook();

        book.addGrade("Introduction to algebra and math analysis", 1, 3);
        book.addGrade("Introduction to discrete maths and mathematical logic", 1, 4);
        book.addGrade("History", 1, 4);
        book.addGrade("Declarative programming", 1, 4);
        book.addGrade("Basics of speech culture", 1, 4);
        book.addGrade("Imperative programming", 1, 5);
        book.addGrade("Introduction to algebra and math analysis", 2, 4);
        book.addGrade("Imperative programming", 2, 5);
        book.addGrade("Digital platforms", 1, 5);
        book.addGrade("Introduction to discrete maths and mathematical logic", 2, 5);
        book.addGrade("English", 2, 4);
        book.addGrade("Declarative programming", 2, 4);

        return book;
    }

    @Test
    public void testGradebookAvg() {
        Gradebook book = testBook();

        assertEquals(4.25, book.averageGrade());
    }

    @Test
    public void testGradebookIncreasedStipend() {
        Gradebook badBook = testBook();
        Gradebook goodBook = new Gradebook();
        goodBook.addGrade("SomeSubject", 1, 5);
        goodBook.addGrade("SomeOtherSubject", 2, 4);

        assertTrue(goodBook.increasedStipend(1));
        assertFalse(badBook.increasedStipend(1));
        assertFalse(goodBook.increasedStipend(2));
    }

    @Test
    public void testGradebookRedDiploma() {
        Gradebook badBook = testBook();
        Gradebook goodBook = new Gradebook();
        goodBook.addGrade("Somesubject",1, 5);
        goodBook.addGrade("Somesubject1",1, 5);
        goodBook.addGrade("Somesubject2", 1,5);
        goodBook.addGrade("Somesubject3",1, 4);

        assertTrue(goodBook.redDiploma());
        assertFalse(badBook.redDiploma());
    }

}
