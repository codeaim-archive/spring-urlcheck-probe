package com.codeaim.urlcheck.repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeaim.urlcheck.Application;
import com.codeaim.urlcheck.domain.RoleDto;
import com.codeaim.urlcheck.domain.UserDto;
import com.codeaim.urlcheck.domain.UserRoleDto;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class UserRoleRepositoryJdbcTest
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    public void save()
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

        RoleDto firstRoleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto secondRoleDto = RoleDto.builder()
                .name("name2")
                .build();

        RoleDto savedFirstRoleDto = roleRepository.save(firstRoleDto);
        RoleDto savedSecondRoleDto = roleRepository.save(secondRoleDto);

        UserRoleDto firstUserRoleDto = UserRoleDto.builder()
                .userId(savedUserDto.getId())
                .roleId(savedFirstRoleDto.getId())
                .build();

        UserRoleDto secondUserRoleDto = UserRoleDto.builder()
                .userId(savedUserDto.getId())
                .roleId(savedSecondRoleDto.getId())
                .build();

        Collection<UserRoleDto> savedUserRoleDtos = userRoleRepository.save(Arrays.asList(firstUserRoleDto, secondUserRoleDto));

        userRepository.delete(savedUserDto);
        roleRepository.delete(savedFirstRoleDto);
        roleRepository.delete(savedSecondRoleDto);

        savedUserRoleDtos
                .stream()
                .forEach(savedUserRoleDto -> userRoleRepository.delete(savedUserRoleDto));

        Assert.assertEquals(2, savedUserRoleDtos.size());
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

        RoleDto roleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto savedRoleDto = roleRepository.save(roleDto);

        UserRoleDto userRoleDto = UserRoleDto.builder()
                .userId(savedUserDto.getId())
                .roleId(savedRoleDto.getId())
                .build();

        UserRoleDto savedUserRoleDto = userRoleRepository.save(userRoleDto);

        Optional<UserRoleDto> foundUserRoleDto = userRoleRepository.findOne(savedUserRoleDto.getId());

        userRepository.delete(savedUserDto);
        roleRepository.delete(savedRoleDto);
        userRoleRepository.delete(userRoleDto);

        Assert.assertTrue(foundUserRoleDto.isPresent());
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

        RoleDto roleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto savedRoleDto = roleRepository.save(roleDto);

        UserRoleDto userRoleDto = UserRoleDto.builder()
                .userId(savedUserDto.getId())
                .roleId(savedRoleDto.getId())
                .build();

        UserRoleDto savedUserRoleDto = userRoleRepository.save(userRoleDto);

        boolean exists = userRoleRepository.exists(savedUserRoleDto.getId());

        userRepository.delete(savedUserDto);
        roleRepository.delete(savedRoleDto);
        userRoleRepository.delete(savedUserRoleDto);

        Assert.assertTrue(exists);
    }

    @Test
    public void count()
    {
        Long roleCount = userRoleRepository.count();

        Assert.assertNotNull(roleCount);
    }

    @Test
    public void findAll()
    {
        Collection<UserRoleDto> roles = userRoleRepository.findAll();

        Assert.assertNotNull(roles);
    }

    @Test
    public void findAllByIds()
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

        RoleDto firstRoleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto secondRoleDto = RoleDto.builder()
                .name("name2")
                .build();

        RoleDto savedFirstRoleDto = roleRepository.save(firstRoleDto);
        RoleDto savedSecondRoleDto = roleRepository.save(secondRoleDto);

        UserRoleDto firstUserRoleDto = UserRoleDto.builder()
                .userId(savedUserDto.getId())
                .roleId(savedFirstRoleDto.getId())
                .build();

        UserRoleDto secondUserRoleDto = UserRoleDto.builder()
                .userId(savedUserDto.getId())
                .roleId(savedSecondRoleDto.getId())
                .build();

        UserRoleDto savedFirstUserRoleDto = userRoleRepository.save(firstUserRoleDto);
        UserRoleDto savedSecondUserRoleDto = userRoleRepository.save(secondUserRoleDto);
        Collection<UserRoleDto> foundUserRoleDtos = userRoleRepository.findAll(Arrays.asList(savedFirstUserRoleDto.getId(), savedSecondUserRoleDto.getId()));

        userRepository.delete(savedUserDto);
        roleRepository.delete(savedFirstRoleDto);
        roleRepository.delete(savedSecondRoleDto);
        userRoleRepository.delete(savedFirstUserRoleDto);
        userRoleRepository.delete(savedSecondUserRoleDto);

        Assert.assertEquals(2, foundUserRoleDtos.size());
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

        RoleDto roleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto savedRoleDto = roleRepository.save(roleDto);

        UserRoleDto userRoleDto = UserRoleDto.builder()
                .userId(savedUserDto.getId())
                .roleId(savedRoleDto.getId())
                .build();

        UserRoleDto savedUserRoleDto = userRoleRepository.save(userRoleDto);

        userRepository.delete(savedUserDto);
        roleRepository.delete(savedRoleDto);
        userRoleRepository.delete(savedUserRoleDto);

        Assert.assertNotNull(savedUserRoleDto);
    }

    @Test
    public void deleteAll()
    {
        userRoleRepository.deleteAll();
        Collection<UserRoleDto> userRoleDtos = userRoleRepository.findAll();

        Assert.assertEquals(0, userRoleDtos.size());
    }
}
