package prime;

public class IsPrime {

    public static boolean check(long[] num) {

        for (long k : num) {
            long cur = k;
            for (long i = 2; i <= cur / 2; i++) {
                cur = cur % i;
                if (cur == 0) {
                    return true;
                }
            }
        }

        return false;
    }

}
