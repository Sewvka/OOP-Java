package PrimeTest;

import Prime.IsPrime;
import Prime.StreamPrimeCheck;
import Prime.ThreadPrimeCheck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsPrimeTest {

    private final long[] testArray = {6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053};
    private long[] enlargedTestArray = new long[100];

    public void arrayIncreasing() {
        for (int i = 0; i < enlargedTestArray.length; i++) {
            enlargedTestArray[i] = testArray[i % testArray.length];
        }
    }

    private final long[] primesUpTo1000 = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37,
            41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89,
            97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151,
            157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223,
            227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
            283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359,
            367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433,
            439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503,
            509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593,
            599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659,
            661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743,
            751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827,
            829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911,
            919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997};


    @Test
    public void simpleTest() {
        arrayIncreasing();

        assertFalse(IsPrime.check(enlargedTestArray));
    }

    @Test
    public void threadTest2() throws Exception {
        arrayIncreasing();

        assertFalse(ThreadPrimeCheck.threadRun(enlargedTestArray, 2));

    }

    @Test
    public void threadTest4() throws Exception {
        arrayIncreasing();

        assertFalse(ThreadPrimeCheck.threadRun(enlargedTestArray, 4));

    }

    @Test
    public void threadTest8() throws Exception {
        arrayIncreasing();

        assertFalse(ThreadPrimeCheck.threadRun(enlargedTestArray, 8));

    }

    @Test
    public void threadTest16() throws Exception {
        arrayIncreasing();

        assertFalse(ThreadPrimeCheck.threadRun(enlargedTestArray, 16));

    }


    @Test
    public void streamTest() {
        arrayIncreasing();
        int index = 0;
        Long[] testArrayLong = new Long[enlargedTestArray.length];
        for (final Long value : enlargedTestArray) {
            testArrayLong[index++] = value;
        }

        assertFalse(StreamPrimeCheck.streamRun(testArrayLong));
    }

    @Test
    public void threadTest10 () throws Exception {
        assertFalse(ThreadPrimeCheck.threadRun(primesUpTo1000, 10));
        int count = 10;
        long totalTime = 0;
        for (int i = 0; i < count; i++){
            long start = System.currentTimeMillis();
            ThreadPrimeCheck.threadRun(primesUpTo1000, 10);
            long end = System.currentTimeMillis();
            totalTime += end-start;
        }
        long avgTime = totalTime/count;
        System.out.println("avg time Thread test 10: " + avgTime + " ms");
    }
}