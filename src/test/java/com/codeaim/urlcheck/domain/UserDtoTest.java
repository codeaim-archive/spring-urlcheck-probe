package com.codeaim.urlcheck.domain;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class UserDtoTest
{
    @Test
    public void build()
    {
        UserDto userDto = UserDto.builder()
                .id(1)
                .username("username")
                .email("email@example.com")
                .resetToken("resetToken")
                .accessToken("accessToken")
                .password("password")
                .emailVerified(true)
                .modified(Instant.now())
                .version(1)
                .build();

        Assert.assertEquals(1, userDto.getId());
        Assert.assertEquals("username", userDto.getUsername());
        Assert.assertEquals("email@example.com", userDto.getEmail());
        Assert.assertEquals("resetToken", userDto.getResetToken());
        Assert.assertEquals("accessToken", userDto.getAccessToken());
        Assert.assertEquals("password", userDto.getPassword());
        Assert.assertEquals(true, userDto.isEmailVerified());
        Assert.assertNotNull(userDto.getCreated());
        Assert.assertNotNull(userDto.getModified());
        Assert.assertEquals(1, userDto.getVersion());
    }

    @Test
    public void buildFrom()
    {
        UserDto userDto = UserDto.buildFrom(UserDto.builder()
                .id(1)
                .username("username")
                .email("email@example.com")
                .resetToken("resetToken")
                .accessToken("accessToken")
                .password("password")
                .emailVerified(true)
                .created(Instant.now())
                .modified(Instant.now())
                .version(1)
                .build())
                .build();

        Assert.assertEquals(1, userDto.getId());
        Assert.assertEquals("username", userDto.getUsername());
        Assert.assertEquals("email@example.com", userDto.getEmail());
        Assert.assertEquals("resetToken", userDto.getResetToken());
        Assert.assertEquals("accessToken", userDto.getAccessToken());
        Assert.assertEquals("password", userDto.getPassword());
        Assert.assertEquals(true, userDto.isEmailVerified());
        Assert.assertNotNull(userDto.getCreated());
        Assert.assertNotNull(userDto.getModified());
        Assert.assertEquals(1, userDto.getVersion());
    }
}
