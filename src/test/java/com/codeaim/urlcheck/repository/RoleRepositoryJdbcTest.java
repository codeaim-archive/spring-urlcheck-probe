package com.codeaim.urlcheck.repository;

import com.codeaim.urlcheck.Application;
import com.codeaim.urlcheck.domain.RoleDto;
import com.codeaim.urlcheck.repository.jdbc.RoleRepositoryJdbc;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
public class RoleRepositoryJdbcTest
{
    private RoleRepository roleRepository;

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("db/migration"));

    @Before
    public void setup() {
        roleRepository = new RoleRepositoryJdbc(new JdbcTemplate(db.getTestDatabase()), new NamedParameterJdbcTemplate(db.getTestDatabase()));
        roleRepository.deleteAll();
    }

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

        roleRepository.delete(firstRoleDto);
        roleRepository.delete(secondRoleDto);

        Assert.assertEquals(2, savedRoleDtos.size());
    }

    @Test
    public void findOne() {
        RoleDto roleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto savedRoleDto = roleRepository.save(roleDto);

        Optional<RoleDto> foundRoleDto = roleRepository.findOne(savedRoleDto.getId());

        roleRepository.delete(roleDto);

        Assert.assertTrue(foundRoleDto.isPresent());
    }

    @Test
    public void exists() {
        RoleDto roleDto = RoleDto.builder()
                .name("name")
                .build();

        RoleDto savedRoleDto = roleRepository.save(roleDto);

        boolean exists = roleRepository.exists(savedRoleDto.getId());

        roleRepository.delete(roleDto);

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
