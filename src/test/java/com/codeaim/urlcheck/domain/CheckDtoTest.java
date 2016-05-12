package com.codeaim.urlcheck.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckDtoTest
{
    @Test
    public void build() {
        CheckDto checkDto = CheckDto.builder()
                .id(1)
                .userId(1)
                .latestResultId(1)
                .name("name")
                .url("http://www.example.com")
                .probe("probe")
                .status(Status.UNKNOWN)
                .state(State.WAITING)
                .interval(1)
                .confirming(true)
                .version(1)
                .build();

        Assert.assertEquals(1, checkDto.getId());
        Assert.assertEquals(1, checkDto.getUserId());
        Assert.assertEquals(1, checkDto.getLatestResultId());
        Assert.assertEquals("name", checkDto.getName());
        Assert.assertEquals("http://www.example.com", checkDto.getUrl());
        Assert.assertEquals("probe", checkDto.getProbe());
        Assert.assertEquals(Status.UNKNOWN, checkDto.getStatus());
        Assert.assertEquals(State.WAITING, checkDto.getState());
        Assert.assertNotNull(checkDto.getCreated());
        Assert.assertNotNull(checkDto.getModified());
        Assert.assertNotNull(checkDto.getRefresh());
        Assert.assertNotNull(checkDto.getLocked());
        Assert.assertEquals(1, checkDto.getInterval());
        Assert.assertEquals(true, checkDto.isConfirming());
        Assert.assertEquals(1, checkDto.getVersion());
    }

    @Test
    public void buildFrom() {
        CheckDto checkDto = CheckDto.buildFrom(CheckDto.builder()
                .id(1)
                .userId(1)
                .latestResultId(1)
                .name("name")
                .url("http://www.example.com")
                .probe("probe")
                .status(Status.UNKNOWN)
                .state(State.WAITING)
                .created(Instant.now())
                .modified(Instant.now())
                .refresh(Instant.now())
                .locked(Instant.now())
                .interval(1)
                .confirming(true)
                .version(1)
                .build())
                .build();

        Assert.assertEquals(1, checkDto.getId());
        Assert.assertEquals(1, checkDto.getUserId());
        Assert.assertEquals(1, checkDto.getLatestResultId());
        Assert.assertEquals("name", checkDto.getName());
        Assert.assertEquals("http://www.example.com", checkDto.getUrl());
        Assert.assertEquals("probe", checkDto.getProbe());
        Assert.assertEquals(Status.UNKNOWN, checkDto.getStatus());
        Assert.assertEquals(State.WAITING, checkDto.getState());
        Assert.assertNotNull(checkDto.getCreated());
        Assert.assertNotNull(checkDto.getModified());
        Assert.assertNotNull(checkDto.getRefresh());
        Assert.assertNotNull(checkDto.getLocked());
        Assert.assertEquals(1, checkDto.getInterval());
        Assert.assertEquals(true, checkDto.isConfirming());
        Assert.assertEquals(1, checkDto.getVersion());
    }
}
