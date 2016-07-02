package com.codeaim.urlcheck.repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeaim.urlcheck.Application;
import com.codeaim.urlcheck.domain.RoleDto;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@TestPropertySource(locations="classpath:test.properties")
@SpringBootTest
public class RoleRepositoryJdbcTest
{
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void save()
    {
        RoleDto firstRoleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto secondRoleDto = RoleDto.builder()
                .name("name2")
                .build();

        Collection<RoleDto> savedRoleDtos = roleRepository.save(Arrays.asList(firstRoleDto, secondRoleDto));

        savedRoleDtos
                .stream()
                .forEach(savedRoleDto -> roleRepository.delete(savedRoleDto));

        Assert.assertEquals(2, savedRoleDtos.size());
    }

    @Test
    public void findOne()
    {
        RoleDto roleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto savedRoleDto = roleRepository.save(roleDto);

        Optional<RoleDto> foundRoleDto = roleRepository.findOne(savedRoleDto.getId());

        roleRepository.delete(savedRoleDto);

        Assert.assertTrue(foundRoleDto.isPresent());
    }

    @Test
    public void exists()
    {
        RoleDto roleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto savedRoleDto = roleRepository.save(roleDto);

        boolean exists = roleRepository.exists(savedRoleDto.getId());

        roleRepository.delete(savedRoleDto);

        Assert.assertTrue(exists);
    }

    @Test
    public void count()
    {
        Long roleCount = roleRepository.count();

        Assert.assertNotNull(roleCount);
    }

    @Test
    public void findAll()
    {
        Collection<RoleDto> roles = roleRepository.findAll();

        Assert.assertNotNull(roles);
    }

    @Test
    public void findAllByIds()
    {
        RoleDto firstRoleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto secondRoleDto = RoleDto.builder()
                .name("name2")
                .build();

        RoleDto savedFirstRoleDto = roleRepository.save(firstRoleDto);
        RoleDto savedSecondRoleDto = roleRepository.save(secondRoleDto);
        Collection<RoleDto> foundRoleDtos = roleRepository.findAll(Arrays.asList(savedFirstRoleDto.getId(), savedSecondRoleDto.getId()));

        roleRepository.delete(savedFirstRoleDto);
        roleRepository.delete(savedSecondRoleDto);

        Assert.assertEquals(2, foundRoleDtos.size());
    }

    @Test
    public void insert()
    {
        RoleDto roleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto savedRoleDto = roleRepository.save(roleDto);

        roleRepository.delete(savedRoleDto);

        Assert.assertNotNull(savedRoleDto);
    }
}
