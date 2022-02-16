package Prime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamPrimeCheck {

    public static boolean streamRun(Long[] array) {

        List<Long> list = Arrays.asList(array);

        Optional<Long> n = list
                .parallelStream()
                .filter(PrimeValidation::isNotPrime)
                .findFirst();

        return n.isPresent();

    }

}