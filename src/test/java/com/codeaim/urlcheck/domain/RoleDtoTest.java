package com.codeaim.urlcheck.domain;

import java.time.Duration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class RoleDtoTest
{
    @Test
    public void build()
    {
        RoleDto roleDto = RoleDto.builder()
                .id(1)
                .name("name")
                .checkLimit(10)
                .resultEventLimit(50)
                .resultRetentionDuration(Duration.ZERO)
                .build();

        Assert.assertEquals(1, roleDto.getId());
        Assert.assertEquals("name", roleDto.getName());
        Assert.assertEquals(10, roleDto.getCheckLimit());
        Assert.assertEquals(50, roleDto.getResultEventLimit());
        Assert.assertEquals(Duration.ZERO, roleDto.getResultRetentionDuration());
    }
}
