package com.codeaim.urlcheck.repository;

import com.codeaim.urlcheck.Application;
import com.codeaim.urlcheck.domain.*;
import com.codeaim.urlcheck.repository.jdbc.CheckRepositoryJdbc;
import com.codeaim.urlcheck.repository.jdbc.ResultRepositoryJdbc;
import com.codeaim.urlcheck.repository.jdbc.UserRepositoryJdbc;
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

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
public class ResultRepositoryJdbcTest
{
    private UserRepository userRepository;
    private CheckRepository checkRepository;
    private ResultRepository resultRepository;

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("db/migration"));

    @Before
    public void setup()
    {
        userRepository = new UserRepositoryJdbc(new JdbcTemplate(db.getTestDatabase()), new NamedParameterJdbcTemplate(db.getTestDatabase()));
        userRepository.deleteAll();
        checkRepository = new CheckRepositoryJdbc(new JdbcTemplate(db.getTestDatabase()), new NamedParameterJdbcTemplate(db.getTestDatabase()));
        checkRepository.deleteAll();
        resultRepository = new ResultRepositoryJdbc(new JdbcTemplate(db.getTestDatabase()), new NamedParameterJdbcTemplate(db.getTestDatabase()));
        resultRepository.deleteAll();
    }

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

        CheckDto checkDto = CheckDto.builder()
                .userId(savedUserDto.getId())
                .name("name")
                .url("http://www.example.com")
                .probe("probe")
                .status(Status.UNKNOWN)
                .state(State.WAITING)
                .interval(1)
                .confirming(true)
                .version(1)
                .build();

        CheckDto savedCheckDto = checkRepository.save(checkDto);

        ResultDto firstResultDto = ResultDto.builder()
                .checkId(savedCheckDto.getId())
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

        ResultDto secondResultDto = ResultDto.builder()
                .checkId(savedCheckDto.getId())
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

        Collection<ResultDto> savedResultDtos = resultRepository.save(Arrays.asList(firstResultDto, secondResultDto));

        userRepository.delete(savedUserDto);
        checkRepository.delete(checkDto);
        resultRepository.delete(firstResultDto);
        resultRepository.delete(secondResultDto);

        Assert.assertEquals(2, savedResultDtos.size());
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

        CheckDto checkDto = CheckDto.builder()
                .userId(savedUserDto.getId())
                .name("name")
                .url("http://www.example.com")
                .probe("probe")
                .status(Status.UNKNOWN)
                .state(State.WAITING)
                .interval(1)
                .confirming(true)
                .version(1)
                .build();

        CheckDto savedCheckDto = checkRepository.save(checkDto);

        ResultDto resultDto = ResultDto.builder()
                .checkId(savedCheckDto.getId())
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

        ResultDto savedResultDto = resultRepository.save(resultDto);

        Optional<ResultDto> foundResultDto = resultRepository.findOne(savedResultDto.getId());

        userRepository.delete(savedUserDto);
        checkRepository.delete(savedCheckDto);
        resultRepository.delete(savedResultDto);

        Assert.assertTrue(foundResultDto.isPresent());
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

        CheckDto checkDto = CheckDto.builder()
                .userId(savedUserDto.getId())
                .name("name")
                .url("http://www.example.com")
                .probe("probe")
                .status(Status.UNKNOWN)
                .state(State.WAITING)
                .interval(1)
                .confirming(true)
                .version(1)
                .build();

        CheckDto savedCheckDto = checkRepository.save(checkDto);

        ResultDto resultDto = ResultDto.builder()
                .checkId(savedCheckDto.getId())
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

        ResultDto savedResultDto = resultRepository.save(resultDto);

        boolean exists = resultRepository.exists(savedResultDto.getId());

        userRepository.delete(savedUserDto);
        checkRepository.delete(savedCheckDto);
        resultRepository.delete(savedResultDto);

        Assert.assertTrue(exists);
    }

    @Test
    public void count()
    {
        Long resultCount = resultRepository.count();

        Assert.assertNotNull(resultCount);
    }

    @Test
    public void findAll()
    {
        Collection<ResultDto> results = resultRepository.findAll();

        Assert.assertNotNull(results);
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

        CheckDto checkDto = CheckDto.builder()
                .userId(savedUserDto.getId())
                .name("name")
                .url("http://www.example.com")
                .probe("probe")
                .status(Status.UNKNOWN)
                .state(State.WAITING)
                .interval(1)
                .confirming(true)
                .version(1)
                .build();

        CheckDto savedCheckDto = checkRepository.save(checkDto);

        ResultDto firstResultDto = ResultDto.builder()
                .checkId(savedCheckDto.getId())
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

        ResultDto savedFirstResultDto = resultRepository.save(firstResultDto);

        ResultDto secondResultDto = ResultDto.builder()
                .checkId(savedCheckDto.getId())
                .status(Status.UNKNOWN)
                .probe("probe2")
                .statusCode(200)
                .responseTime(1000)
                .changed(true)
                .confirmation(true)
                .created(Instant.now())
                .modified(Instant.now())
                .version(1)
                .build();

        ResultDto savedSecondResultDto = resultRepository.save(secondResultDto);


        Collection<ResultDto> foundResultDtos = resultRepository.findAll(Arrays.asList(savedFirstResultDto.getId(), savedSecondResultDto.getId()));

        userRepository.delete(savedUserDto);
        checkRepository.delete(savedCheckDto);
        resultRepository.delete(savedFirstResultDto);
        resultRepository.delete(savedSecondResultDto);

        Assert.assertEquals(2, foundResultDtos.size());
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

        CheckDto checkDto = CheckDto.builder()
                .userId(savedUserDto.getId())
                .name("name")
                .url("http://www.example.com")
                .probe("probe")
                .status(Status.UNKNOWN)
                .state(State.WAITING)
                .interval(1)
                .confirming(true)
                .version(1)
                .build();

        CheckDto savedCheckDto = checkRepository.save(checkDto);

        ResultDto resultDto = ResultDto.builder()
                .checkId(savedCheckDto.getId())
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

        ResultDto savedResultDto = resultRepository.save(resultDto);

        userRepository.delete(savedUserDto);
        checkRepository.delete(savedCheckDto);
        resultRepository.delete(savedResultDto);

        Assert.assertNotNull(savedCheckDto);
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

        CheckDto checkDto = CheckDto.builder()
                .userId(savedUserDto.getId())
                .name("name")
                .url("http://www.example.com")
                .probe("probe")
                .status(Status.UNKNOWN)
                .state(State.WAITING)
                .interval(1)
                .confirming(true)
                .version(1)
                .build();

        CheckDto savedCheckDto = checkRepository.save(checkDto);

        ResultDto resultDto = ResultDto.builder()
                .checkId(savedCheckDto.getId())
                .status(Status.UNKNOWN)
                .probe("probe")
                .statusCode(200)
                .responseTime(1000)
                .changed(true)
                .confirmation(true)
                .modified(Instant.now())
                .version(1)
                .build();

        ResultDto savedResultDto = resultRepository.save(resultDto);

        ResultDto updatedResultDto = ResultDto.buildFrom(savedResultDto)
                .probe("updated")
                .build();

        ResultDto savedUpdatedResultDto = resultRepository.save(updatedResultDto);

        userRepository.delete(savedUserDto);
        checkRepository.delete(savedCheckDto);
        resultRepository.delete(savedUpdatedResultDto);

        Assert.assertEquals("updated", savedUpdatedResultDto.getProbe());
    }
}
