package com.codeaim.urlcheck.repository;

import com.codeaim.urlcheck.Application;
import com.codeaim.urlcheck.domain.UserDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@TestPropertySource(locations="classpath:test.properties")
@SpringBootTest
public class UserRepositoryJdbcTest
{
    @Autowired
    private UserRepository userRepository;

    @Test
    public void save()
    {
        UserDto firstUserDto = UserDto.builder()
                .name("name")
                .email("email@example.com")
                .resetToken("resetToken")
                .accessToken("accessToken")
                .password("password")
                .emailVerified(true)
                .build();

        UserDto secondUserDto = UserDto.builder()
                .name("name2")
                .email("email2@example.com")
                .resetToken("resetToken2")
                .accessToken("accessToken2")
                .password("password2")
                .emailVerified(true)
                .build();

        Collection<UserDto> savedUserDtos = userRepository.save(Arrays.asList(firstUserDto, secondUserDto));

        savedUserDtos
                .stream()
                .forEach(savedUserDto -> userRepository.delete(savedUserDto));

        Assert.assertEquals(2, savedUserDtos.size());
    }

    @Test
    public void findOne()
    {
        UserDto userDto = UserDto.builder()
                .name("name")
                .email("email@example.com")
                .resetToken("resetToken")
                .accessToken("accessToken")
                .password("password")
                .emailVerified(true)
                .build();

        UserDto savedUserDto = userRepository.save(userDto);

        Optional<UserDto> foundUserDto = userRepository.findOne(savedUserDto.getId());

        userRepository.delete(savedUserDto);

        Assert.assertTrue(foundUserDto.isPresent());
    }

    @Test
    public void exists()
    {
        UserDto userDto = UserDto.builder()
                .name("name")
                .email("email@example.com")
                .resetToken("resetToken")
                .accessToken("accessToken")
                .password("password")
                .emailVerified(true)
                .build();

        UserDto savedUserDto = userRepository.save(userDto);

        boolean exists = userRepository.exists(savedUserDto.getId());

        userRepository.delete(savedUserDto);

        Assert.assertTrue(exists);
    }

    @Test
    public void count()
    {
        Long userCount = userRepository.count();

        Assert.assertNotNull(userCount);
    }

    @Test
    public void findAll()
    {
        Collection<UserDto> users = userRepository.findAll();

        Assert.assertNotNull(users);
    }

    @Test
    public void findAllByIds()
    {
        UserDto firstUserDto = UserDto.builder()
                .name("name")
                .email("email@example.com")
                .resetToken("resetToken")
                .accessToken("accessToken")
                .password("password")
                .emailVerified(true)
                .build();

        UserDto secondUserDto = UserDto.builder()
                .name("name2")
                .email("email2@example.com")
                .resetToken("resetToken2")
                .accessToken("accessToken2")
                .password("password2")
                .emailVerified(true)
                .build();

        UserDto savedFirstUserDto = userRepository.save(firstUserDto);
        UserDto savedSecondUserDto = userRepository.save(secondUserDto);
        Collection<UserDto> foundUserDtos = userRepository.findAll(Arrays.asList(savedFirstUserDto.getId(), savedSecondUserDto.getId()));

        userRepository.delete(savedFirstUserDto);
        userRepository.delete(savedSecondUserDto);

        Assert.assertEquals(2, foundUserDtos.size());
    }

    @Test
    public void insert()
    {
        UserDto userDto = UserDto.builder()
                .name("name")
                .email("email@example.com")
                .resetToken("resetToken")
                .accessToken("accessToken")
                .password("password")
                .emailVerified(true)
                .build();

        UserDto savedUserDto = userRepository.save(userDto);

        userRepository.delete(savedUserDto);

        Assert.assertNotNull(savedUserDto);
    }

    @Test
    public void update()
    {
        UserDto userDto = UserDto.builder()
                .name("name")
                .email("email@example.com")
                .resetToken("resetToken")
                .accessToken("accessToken")
                .password("password")
                .emailVerified(true)
                .build();

        UserDto savedUserDto = userRepository.save(userDto);

        UserDto updatedUserDto = UserDto.buildFrom(savedUserDto)
                .name("updated")
                .build();

        UserDto savedUpdatedUserDto = userRepository.save(updatedUserDto);

        userRepository.delete(savedUpdatedUserDto);

        Assert.assertEquals("updated", savedUpdatedUserDto.getName());
    }
}
