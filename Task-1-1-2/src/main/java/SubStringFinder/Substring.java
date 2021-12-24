package SubStringFinder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Substring{

    /**
     * Prefix-function for Knuth-Morris-Pratt algorithm
     * @param buffer buffer to calculate prefix-function
     * @return array of calculated prefix-functions for every position
     */
    private static int[] prefixFunction(String buffer) {
        int[] result = new int[buffer.length()];
        result[0] = 0;
        for (int i = 1; i < buffer.length(); i++) {
            int k = result[i - 1];
            while ((k > 0) && (buffer.charAt(i) != buffer.charAt(k))) {
                k = result[k - 1];
            }
            if (buffer.charAt(i) == buffer.charAt(k)) {
                k++;
            }
            result[i] = k;
        }

        return result;
    }

    /**
     * Function for finding all indexes of first symbol of substring in the buffer.
     * Using Knuth-Morris-Pratt algorithm
     * @param buffer buffer to search for substring
     * @param subString string to find in the buffer
     * @param shift position of the first symbol of buffer in relation to the file
     * @return list of indexes of first symbol of found substring in the file
     */
    private static List<Integer> findSubStringInBuffer(String buffer, String subString, int shift) {
        List<Integer> resultList = new ArrayList<>();
        int[] prefixFuncResult = prefixFunction(subString + "#" + buffer);
        for (int i = 0; i < buffer.length(); i++) {
            if (prefixFuncResult[subString.length() + i + 1] == subString.length()) {
                resultList.add(i - subString.length() + 1 + shift);
            }
        }

        return resultList;
    }

    /**
     * Find all substring in file
     * @param filePath filepath of source file
     * @param subString string to find in file
     * @return list of indexes of first symbol of found substrings in the file
     */
    public static List<Integer> findSubString(String filePath, String subString) {
        List<Integer> resultList = new ArrayList<>();
        try (
                FileReader fileReader = new FileReader(filePath);
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            int bufferSize = 100 * subString.length();
            char[] charBuffer = new char[bufferSize];
            int curBufferSize = bufferedReader.read(charBuffer, 0, bufferSize);
            int bufferCount = 0;
            String shift = "";

            if (curBufferSize < subString.length()) {
                return resultList;
            } else if (curBufferSize != bufferSize) {
                String buffer = String.valueOf(charBuffer);
                List<Integer> subStringInBuffer = findSubStringInBuffer(buffer, subString, 0);
                resultList.addAll(subStringInBuffer);
                return resultList;
            }
            while (charBuffer.length == curBufferSize) {
                if (bufferCount != 0) curBufferSize = bufferedReader.read(charBuffer, 0, bufferSize);
                String buffer = shift + String.valueOf(charBuffer);
                List<Integer> subStringInBuffer = findSubStringInBuffer(
                        buffer,
                        subString,
                        bufferCount == 0 ? 0 : bufferSize - subString.length() + (bufferCount - 1) * bufferSize);
                resultList.addAll(subStringInBuffer);
                shift = buffer.substring(
                        shift.length() + curBufferSize - subString.length(),
                        shift.length() + curBufferSize);
                bufferCount++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

}
