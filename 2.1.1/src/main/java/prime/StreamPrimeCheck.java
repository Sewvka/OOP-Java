package prime;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamPrimeCheck {

    public static boolean streamRun(Long[] array) {

        List<Long> list = Arrays.asList(array);

        return list.parallelStream().anyMatch(PrimeValidation::isNotPrime);

    }

}