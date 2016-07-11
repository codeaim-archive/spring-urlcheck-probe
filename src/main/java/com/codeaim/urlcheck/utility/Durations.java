package com.codeaim.urlcheck.utility;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.postgresql.util.PGInterval;

public class Durations
{
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
