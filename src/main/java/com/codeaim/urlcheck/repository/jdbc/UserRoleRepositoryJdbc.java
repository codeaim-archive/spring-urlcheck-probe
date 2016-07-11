package com.codeaim.urlcheck.repository.jdbc;

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

import com.codeaim.urlcheck.domain.UserRoleDto;
import com.codeaim.urlcheck.repository.UserRoleRepository;

@Repository
public class UserRoleRepositoryJdbc implements UserRoleRepository
{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserRoleRepositoryJdbc(
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
        String countSql = "SELECT COUNT(*) FROM user_role";

        return this.jdbcTemplate.queryForObject(countSql, Long.class);
    }

    @Override
    public void delete(UserRoleDto userRoleDto)
    {
        String deleteSql = "DELETE FROM user_role WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", userRoleDto.getId());

        this.namedParameterJdbcTemplate.update(deleteSql, parameters);
    }

    @Override
    public void deleteAll()
    {
        String deleteAllSql = "DELETE FROM user_role";

        this.jdbcTemplate.update(deleteAllSql);
    }

    @Override
    public boolean exists(Long id)
    {
        String existsSql = "SELECT EXISTS(SELECT 1 FROM user_role WHERE id = :id)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return this.namedParameterJdbcTemplate.queryForObject(existsSql, parameters, Boolean.class);
    }

    @Override
    public Collection<UserRoleDto> findAll()
    {
        String findAllSql = "SELECT * FROM user_role";

        return this.jdbcTemplate.query(findAllSql, mapUserRoleDto());
    }

    @Override
    public Collection<UserRoleDto> findAll(Collection<Long> ids)
    {
        String findAllSql = "SELECT * FROM user_role WHERE id in (:ids)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("ids", ids);

        return this.namedParameterJdbcTemplate
                .query(findAllSql, parameters, mapUserRoleDto());
    }

    @Override
    public Optional<UserRoleDto> findOne(Long id)
    {
        String findSql = "SELECT * FROM user_role WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return this.namedParameterJdbcTemplate
                .query(findSql, parameters, mapUserRoleDto())
                .stream()
                .findFirst();
    }

    @Override
    public UserRoleDto save(UserRoleDto userRoleDto)
    {
        String insertSql = "INSERT INTO user_role(user_id, role_id) VALUES(:user_id, :role_id)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user_id", userRoleDto.getUserId())
                .addValue("role_id", userRoleDto.getRoleId());

        this.namedParameterJdbcTemplate.update(insertSql, parameters, keyHolder, new String[]{"id"});

        return UserRoleDto.buildFrom(userRoleDto)
                .id(keyHolder.getKey().longValue())
                .build();
    }

    @Override
    public Collection<UserRoleDto> save(Collection<UserRoleDto> entities)
    {
        return entities.stream().map(this::save).collect(Collectors.toList());
    }


    private RowMapper<UserRoleDto> mapUserRoleDto()
    {
        return (rs, rowNum) -> UserRoleDto.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .roleId(rs.getLong("role_id"))
                .build();
    }
}
