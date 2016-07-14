package com.codeaim.urlcheck.repository.jdbc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.codeaim.urlcheck.domain.UserDto;
import com.codeaim.urlcheck.repository.UserRepository;

@Repository
public class UserRepositoryJdbc implements UserRepository
{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserRepositoryJdbc(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    )
    {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public long count()
    {
        String countSql = "SELECT COUNT(*) FROM \"user\"";

        return this.jdbcTemplate.queryForObject(countSql, Long.class);
    }

    @Override
    public void delete(UserDto userDto)
    {
        String deleteSql = "DELETE FROM \"user\" WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", userDto.getId());

        this.namedParameterJdbcTemplate.update(deleteSql, parameters);
    }

    @Override
    public void deleteAll()
    {
        String deleteAllSql = "DELETE FROM \"user\"";

        this.jdbcTemplate.update(deleteAllSql);
    }

    @Override
    public boolean exists(Long id)
    {
        String existsSql = "SELECT EXISTS(SELECT 1 FROM \"user\" WHERE id = :id)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return this.namedParameterJdbcTemplate.queryForObject(existsSql, parameters, Boolean.class);
    }

    @Override
    public Collection<UserDto> findAll()
    {
        String findAllSql = "SELECT * FROM \"user\"";

        return this.jdbcTemplate.query(findAllSql, mapUserDto());
    }

    @Override
    public Collection<UserDto> findAll(Collection<Long> ids)
    {
        String findAllSql = "SELECT * FROM \"user\" WHERE id in (:ids)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("ids", ids);

        return this.namedParameterJdbcTemplate
                .query(findAllSql, parameters, mapUserDto());
    }

    @Override
    public Optional<UserDto> findOne(Long id)
    {
        String findSql = "SELECT * FROM \"user\" WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return this.namedParameterJdbcTemplate
                .query(findSql, parameters, mapUserDto())
                .stream()
                .findFirst();
    }

    @Override
    public UserDto save(UserDto userDto)
    {
        if (userDto.getId() == 0)
            return insert(userDto);

        return update(userDto);
    }

    @Override
    public Collection<UserDto> save(Collection<UserDto> entities)
    {
        return entities.stream().map(this::save).collect(Collectors.toList());
    }

    private UserDto insert(UserDto userDto)
    {
        String insertSql = "INSERT INTO \"user\"(username, email, reset_token, access_token, password, email_verified, created, modified, version) VALUES(:username, :email, :reset_token, :access_token, :password, :email_verified, :created, :modified, :version)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", userDto.getUsername())
                .addValue("email", userDto.getEmail())
                .addValue("reset_token", userDto.getResetToken())
                .addValue("access_token", userDto.getAccessToken())
                .addValue("password", userDto.getPassword())
                .addValue("email_verified", userDto.isEmailVerified())
                .addValue("created", Timestamp.from(userDto.getCreated()))
                .addValue("modified", Timestamp.from(userDto.getModified()))
                .addValue("version", userDto.getVersion());

        this.namedParameterJdbcTemplate.update(insertSql, parameters, keyHolder, new String[]{"id"});

        return UserDto.buildFrom(userDto)
                .id(keyHolder.getKey().longValue())
                .build();
    }

    private UserDto update(UserDto userDto)
    {
        String updateSql = "UPDATE \"user\" SET username = :username, email = :email, reset_token = :reset_token, access_token = :access_token, password = :password, email_verified = :email_verified, created = :created, modified = :modified, version = :version WHERE id = :id";

        UserDto updatedUserDto = UserDto.buildFrom(userDto)
                .modified(Instant.now())
                .version(userDto.getVersion() + 1)
                .build();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", updatedUserDto.getId())
                .addValue("username", updatedUserDto.getUsername())
                .addValue("email", updatedUserDto.getEmail())
                .addValue("reset_token", updatedUserDto.getResetToken())
                .addValue("access_token", updatedUserDto.getAccessToken())
                .addValue("password", updatedUserDto.getPassword())
                .addValue("email_verified", updatedUserDto.isEmailVerified())
                .addValue("created", Timestamp.from(updatedUserDto.getCreated()))
                .addValue("modified", Timestamp.from(updatedUserDto.getModified()))
                .addValue("version", updatedUserDto.getVersion());

        this.namedParameterJdbcTemplate.update(updateSql, parameters);

        return updatedUserDto;
    }

    private RowMapper<UserDto> mapUserDto()
    {
        return (rs, rowNum) -> UserDto.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .resetToken(rs.getString("reset_token"))
                .accessToken(rs.getString("access_token"))
                .password(rs.getString("password"))
                .emailVerified(rs.getBoolean("email_verified"))
                .created(rs.getTimestamp("created").toInstant())
                .modified(rs.getTimestamp("modified").toInstant())
                .version(rs.getLong("version"))
                .build();
    }
}
