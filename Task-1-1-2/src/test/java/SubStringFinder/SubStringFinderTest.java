package SubStringFinder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubStringFinderTest {

    private static Stream<Arguments> provideTestsForFindSubStringTest() {
        return Stream.of(
                Arguments.of(
                        "./src/test/resources/1.in",
                        "No one alerted you",
                        List.of(471)
                ),
                Arguments.of(
                        "./src/test/resources/2.in",
                        "I'd love to turn you on",
                        List.of(485, 989)
                ),
                Arguments.of(
                        "./src/test/resources/3.in",
                        "is",
                        List.of(6)
                ),
                Arguments.of(
                        "./src/test/resources/3.in",
                        "asdfgh",
                        new ArrayList<Integer>()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestsForFindSubStringTest")
    public void findSubStringTest(String filePath, String subString, List<Integer> expResult) {
        List<Integer> resultList = Substring.findSubString(filePath, subString);
        assertEquals(expResult, resultList);
    }
}

