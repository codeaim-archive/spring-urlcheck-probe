package com.codeaim.urlcheck.utility;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.postgresql.util.PGInterval;

public class Durations
{
//    public static PGInterval convert(Duration duration)
//    {
//        long secondsDuration = duration.getSeconds();
//
//        long days = TimeUnit.SECONDS.toDays(secondsDuration);
//        secondsDuration -= TimeUnit.DAYS.toSeconds(days);
//        long hours = TimeUnit.SECONDS.toHours(secondsDuration);
//        secondsDuration -= TimeUnit.HOURS.toSeconds(hours);
//        long minutes = TimeUnit.SECONDS.toMinutes(secondsDuration);
//        secondsDuration -= TimeUnit.MINUTES.toSeconds(minutes);
//        long seconds = TimeUnit.SECONDS.toSeconds(secondsDuration);
//
//        PGInterval interval = null;
//        try
//        {
//            interval = new PGInterval(String.format(
//                    "%d days %d hours %d mins %d secs",
//                    days,
//                    hours,
//                    minutes,
//                    seconds));
//        } catch (SQLException e)
//        {
//        }
//
//        return interval;
//    }

//    public static Duration convert(PGInterval interval)
//    {
//        return Duration.of(interval.getDays(), ChronoUnit.DAYS)
//                .plusHours(interval.getHours())
//                .plusMinutes(interval.getMinutes())
//                .plusSeconds((long) interval.getSeconds());
//    }

    public static PGInterval convert(Duration duration)
    {
        PGInterval interval = new PGInterval();
        interval.setSeconds(duration.get(ChronoUnit.SECONDS));
        return interval;
    }

    public static Duration convert(PGInterval interval)
    {
        return Duration.of(interval.getDays(), ChronoUnit.DAYS)
                .plusHours(interval.getHours())
                .plusMinutes(interval.getMinutes())
                .plusSeconds((long) interval.getSeconds());
    }
}
