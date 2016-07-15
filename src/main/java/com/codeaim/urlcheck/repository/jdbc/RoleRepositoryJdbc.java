package com.codeaim.urlcheck.repository.jdbc;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.codeaim.urlcheck.domain.RoleDto;
import com.codeaim.urlcheck.repository.RoleRepository;
import com.codeaim.urlcheck.utility.Durations;

@Repository
public class RoleRepositoryJdbc implements RoleRepository
{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public RoleRepositoryJdbc(
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
        String countSql = "SELECT COUNT(*) FROM role";

        return this.jdbcTemplate.queryForObject(countSql, Long.class);
    }

    @Override
    public void delete(RoleDto roleDto)
    {
        String deleteSql = "DELETE FROM role WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", roleDto.getId());

        this.namedParameterJdbcTemplate.update(deleteSql, parameters);
    }

    @Override
    public void deleteAll()
    {
        String deleteAllSql = "DELETE FROM role";

        this.jdbcTemplate.update(deleteAllSql);
    }

    @Override
    public boolean exists(Long id)
    {
        String existsSql = "SELECT EXISTS(SELECT 1 FROM role WHERE id = :id)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return this.namedParameterJdbcTemplate.queryForObject(existsSql, parameters, Boolean.class);
    }

    @Override
    public Collection<RoleDto> findAll()
    {
        String findAllSql = "SELECT * FROM role";

        return this.jdbcTemplate.query(findAllSql, mapRoleDto());
    }

    @Override
    public Collection<RoleDto> findAll(Collection<Long> ids)
    {
        String findAllSql = "SELECT * FROM role WHERE id in (:ids)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("ids", ids);

        return this.namedParameterJdbcTemplate
                .query(findAllSql, parameters, mapRoleDto());
    }

    @Override
    public Optional<RoleDto> findOne(Long id)
    {
        String findSql = "SELECT * FROM role WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return this.namedParameterJdbcTemplate
                .query(findSql, parameters, mapRoleDto())
                .stream()
                .findFirst();
    }

    @Override
    public RoleDto save(RoleDto roleDto)
    {
        String insertSql = "INSERT INTO role(name, check_limit, result_event_limit, result_retention_duration, price) VALUES(:name, :check_limit, :result_event_limit, :result_retention_duration, :price)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", roleDto.getName())
                .addValue("check_limit", roleDto.getCheckLimit())
                .addValue("result_event_limit", roleDto.getResultEventLimit())
                .addValue("result_retention_duration", Durations.convert(roleDto.getResultRetentionDuration()))
                .addValue("price", roleDto.getPrice());

        this.namedParameterJdbcTemplate.update(insertSql, parameters, keyHolder, new String[]{"id"});

        return RoleDto.buildFrom(roleDto)
                .id(keyHolder.getKey().longValue())
                .build();
    }

    @Override
    public Collection<RoleDto> save(Collection<RoleDto> entities)
    {
        return entities.stream().map(this::save).collect(Collectors.toList());
    }

    private RowMapper<RoleDto> mapRoleDto()
    {
        return (rs, rowNum) -> RoleDto.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .checkLimit(rs.getLong("check_limit"))
                .resultEventLimit(rs.getLong("result_event_limit"))
                .resultRetentionDuration(Durations.convert((PGInterval) rs.getObject("result_retention_duration")))
                .price(rs.getDouble("price"))
                .build();
    }
}
