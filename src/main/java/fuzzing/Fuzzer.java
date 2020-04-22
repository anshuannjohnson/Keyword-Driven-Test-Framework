package fuzzing;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Fuzzer {
    private int min;
    private int max;
    private char start;
    private char end;

    public Fuzzer(int min, int max, char start, char end) {
        this.min = min;
        this.max = max;
        this.start = start;
        this.end = end;
    }

    public String fuzz() {
        Random random = new Random();
        int targetStringLength = ThreadLocalRandom.current().nextInt(min, max + 1);
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = start + (int)
                (random.nextFloat() * (end - start + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

    String deleteRandomChar(String string) {
        int index = ThreadLocalRandom.current().nextInt(0, string.length());
        return string.substring(0, index) + string.substring(index + 1);
    }

    String insertRandomChar(String string) {
        Random random = new Random();
        int index = ThreadLocalRandom.current().nextInt(0, string.length());
        int randomLimitedInt = start + (int)
            (random.nextFloat() * (end - start + 1));

        return string.substring(0, index) + (char)randomLimitedInt + string.substring(index);
    }

    String flipRandomChar(String string) {
        Random random = new Random();
        int index = ThreadLocalRandom.current().nextInt(0, string.length());
        int randomLimitedInt = start + (int)
            (random.nextFloat() * (end - start + 1));

        return string.substring(0, index) + (char)randomLimitedInt + string.substring(index + 1);
    }

    public String mutate(String string) {
        int count = 0;
        while (++count < 10) {
            int index = ThreadLocalRandom.current().nextInt(0, 3);
            if (index == 0) string = deleteRandomChar(string);
            else if (index == 1) string = insertRandomChar(string);
            else if (index == 2) string = flipRandomChar(string);
        }

        return string;
    }

}
