package com.codeaim.urlcheck.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
@SpringBootTest
public class RoleDtoTest
{
    @Test
    public void build() {
        RoleDto roleDto = RoleDto.builder()
                .id(1)
                .name("name")
                .build();

        Assert.assertEquals(1, roleDto.getId());
        Assert.assertEquals("name", roleDto.getName());
    }
}
