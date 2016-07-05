package com.codeaim.urlcheck.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
@SpringBootTest
public class ResultDtoTest
{
    @Test
    public void build()
    {
        ResultDto resultDto = ResultDto.builder()
                .id(1)
                .checkId(1)
                .previousResultId(OptionalLong.of(1))
                .status(Status.UNKNOWN)
                .probe("probe")
                .statusCode(200)
                .responseTime(1000)
                .changed(true)
                .confirmation(true)
                .created(Instant.now())
                .modified(Instant.now())
                .version(1)
                .build();

        Assert.assertEquals(1, resultDto.getId());
        Assert.assertEquals(1, resultDto.getCheckId());
        Assert.assertEquals(1, resultDto.getPreviousResultId().getAsLong());
        Assert.assertEquals(Status.UNKNOWN, resultDto.getStatus());
        Assert.assertEquals("probe", resultDto.getProbe());
        Assert.assertEquals(200, resultDto.getStatusCode());
        Assert.assertEquals(1000, resultDto.getResponseTime());
        Assert.assertEquals(true, resultDto.isChanged());
        Assert.assertEquals(true, resultDto.isConfirmation());
        Assert.assertNotNull(resultDto.getCreated());
        Assert.assertNotNull(resultDto.getModified());
        Assert.assertEquals(1, resultDto.getVersion());
    }

    @Test
    public void buildFrom()
    {
        ResultDto resultDto = ResultDto.buildFrom(ResultDto.builder()
                .id(1)
                .checkId(1)
                .previousResultId(OptionalLong.of(1))
                .status(Status.UNKNOWN)
                .probe("probe")
                .statusCode(200)
                .responseTime(1000)
                .changed(true)
                .confirmation(true)
                .created(Instant.now())
                .modified(Instant.now())
                .version(1)
                .build())
                .build();

        Assert.assertEquals(1, resultDto.getId());
        Assert.assertEquals(1, resultDto.getCheckId());
        Assert.assertEquals(1, resultDto.getPreviousResultId().getAsLong());
        Assert.assertEquals(Status.UNKNOWN, resultDto.getStatus());
        Assert.assertEquals("probe", resultDto.getProbe());
        Assert.assertEquals(200, resultDto.getStatusCode());
        Assert.assertEquals(1000, resultDto.getResponseTime());
        Assert.assertEquals(true, resultDto.isChanged());
        Assert.assertEquals(true, resultDto.isConfirmation());
        Assert.assertNotNull(resultDto.getCreated());
        Assert.assertNotNull(resultDto.getModified());
        Assert.assertEquals(1, resultDto.getVersion());
    }
}
