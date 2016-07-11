package com.codeaim.urlcheck.utility;

import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.util.PGInterval;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class DurationsTest
{
    @Test
    public void convertDuration()
    {
        Duration duration = Duration.ofDays(5).plusHours(2).plusMinutes(53).plusSeconds(32);
        PGInterval interval = Durations.convert(duration);
        Duration roundTrippedDuration = Durations.convert(interval);

        Assert.assertEquals(duration, roundTrippedDuration);
    }

    @Test
    public void convertDurationOversized()
    {
        Duration duration = Duration.ofDays(34).plusHours(32).plusMinutes(153).plusSeconds(532);
        PGInterval interval = Durations.convert(duration);
        Duration roundTrippedDuration = Durations.convert(interval);

        Assert.assertEquals(duration, roundTrippedDuration);
    }

    @Test
    public void convertDurationYear()
    {
        Duration duration = Duration.ofDays(5345).plusHours(32).plusMinutes(153).plusSeconds(532);
        PGInterval interval = Durations.convert(duration);
        Duration roundTrippedDuration = Durations.convert(interval);

        Assert.assertEquals(duration, roundTrippedDuration);
    }
}
