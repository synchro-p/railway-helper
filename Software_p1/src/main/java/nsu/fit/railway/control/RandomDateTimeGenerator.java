package nsu.fit.railway.control;

import java.time.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateTimeGenerator {
    public static LocalDateTime after(LocalDateTime start) {
        return LocalDateTime.of(start.toLocalDate(), between(start.toLocalTime(), LocalTime.MAX));
    }

    private static LocalTime between(LocalTime startTime, LocalTime endTime) {
        int startSeconds = startTime.toSecondOfDay();
        int endSeconds = endTime.toSecondOfDay();
        int randomTime = ThreadLocalRandom
                .current()
                .nextInt(startSeconds, endSeconds);

        return LocalTime.ofSecondOfDay(randomTime);
    }
}
