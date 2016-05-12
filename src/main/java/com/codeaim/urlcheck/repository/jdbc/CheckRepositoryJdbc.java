package com.codeaim.urlcheck.repository.jdbc;

import com.codeaim.urlcheck.domain.CheckDto;
import com.codeaim.urlcheck.domain.State;
import com.codeaim.urlcheck.domain.Status;
import com.codeaim.urlcheck.domain.UserDto;
import com.codeaim.urlcheck.repository.CheckRepository;
import com.codeaim.urlcheck.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CheckRepositoryJdbc implements CheckRepository
{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CheckRepositoryJdbc(
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
        String countSql = "SELECT COUNT(*) FROM \"check\"";

        return this.jdbcTemplate.queryForObject(countSql, Long.class);
    }

    @Override
    public void delete(CheckDto checkDto)
    {
        String deleteSql = "DELETE FROM \"check\" WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", checkDto.getId());

        this.namedParameterJdbcTemplate.update(deleteSql, parameters);
    }

    @Override
    public void deleteAll()
    {
        String deleteAllSql = "DELETE FROM \"check\"";

        this.jdbcTemplate.update(deleteAllSql);
    }

    @Override
    public boolean exists(Long id)
    {
        String existsSql = "SELECT EXISTS(SELECT 1 FROM \"check\" WHERE id = :id)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return this.namedParameterJdbcTemplate.queryForObject(existsSql, parameters, Boolean.class);
    }

    @Override
    public Collection<CheckDto> findAll()
    {
        String findAllSql = "SELECT * FROM \"check\"";

        return this.jdbcTemplate.query(findAllSql, mapCheckDto());
    }

    @Override
    public Collection<CheckDto> findAll(Collection<Long> ids)
    {
        String findAllSql = "SELECT * FROM \"check\" WHERE id in (:ids)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("ids", ids);

        return this.namedParameterJdbcTemplate
                .query(findAllSql, parameters, mapCheckDto());
    }

    @Override
    public Optional<CheckDto> findOne(Long id)
    {
        String findSql = "SELECT * FROM \"check\" WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return this.namedParameterJdbcTemplate
                .query(findSql, parameters, mapCheckDto())
                .stream()
                .findFirst();
    }

    @Override
    public CheckDto save(CheckDto checkDto)
    {
        if (checkDto.getId() == 0)
            return insert(checkDto);

        return update(checkDto);
    }

    @Override
    public Collection<CheckDto> save(Collection<CheckDto> entities)
    {
        return entities.stream().map(this::save).collect(Collectors.toList());
    }

    private CheckDto insert(CheckDto checkDto)
    {
        String insertSql = "INSERT INTO \"check\"(user_id, latest_result_id, name, url, probe, status, state, created, modified, refresh, locked, interval, confirming, version) VALUES(:user_id, :latest_result_id, :name, :url, :probe, :status::status, :state::state, :created, :modified, :refresh, :locked, :interval, :confirming, :version)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user_id", checkDto.getUserId())
                .addValue("latest_result_id", checkDto.getLatestResultId() == 0 ? null : checkDto.getLatestResultId())
                .addValue("name", checkDto.getName())
                .addValue("url", checkDto.getUrl())
                .addValue("probe", checkDto.getProbe())
                .addValue("status", checkDto.getStatus().toString())
                .addValue("state", checkDto.getState().toString())
                .addValue("created", Timestamp.from(checkDto.getCreated()))
                .addValue("modified", Timestamp.from(checkDto.getModified()))
                .addValue("refresh", Timestamp.from(checkDto.getRefresh()))
                .addValue("locked", Timestamp.from(checkDto.getLocked()))
                .addValue("interval", checkDto.getInterval())
                .addValue("confirming", checkDto.isConfirming())
                .addValue("version", checkDto.getVersion());

        this.namedParameterJdbcTemplate.update(insertSql, parameters, keyHolder, new String[]{"id"});

        return CheckDto.buildFrom(checkDto)
                .id(keyHolder.getKey().longValue())
                .build();
    }

    private CheckDto update(CheckDto checkDto)
    {
        String updateSql = "UPDATE \"check\" SET user_id = :user_id, latest_result_id = :latest_result_id, name = :name, url = :url, probe = :probe, status = :status::status, state = :state::state, created = :created, modified = :modified, refresh = :refresh, locked = :locked, interval = :interval, confirming = :confirming, version = :version WHERE id = :id";

        CheckDto updatedCheckDto = CheckDto.buildFrom(checkDto)
                .modified(Instant.now())
                .version(checkDto.getVersion() + 1)
                .build();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", updatedCheckDto.getId())
                .addValue("user_id", updatedCheckDto.getUserId())
                .addValue("latest_result_id", checkDto.getLatestResultId() == 0 ? null : checkDto.getLatestResultId())
                .addValue("name", updatedCheckDto.getName())
                .addValue("url", updatedCheckDto.getUrl())
                .addValue("probe", updatedCheckDto.getProbe())
                .addValue("status", updatedCheckDto.getStatus().toString())
                .addValue("state", updatedCheckDto.getState().toString())
                .addValue("created", Timestamp.from(updatedCheckDto.getCreated()))
                .addValue("modified", Timestamp.from(updatedCheckDto.getModified()))
                .addValue("refresh", Timestamp.from(updatedCheckDto.getRefresh()))
                .addValue("locked", Timestamp.from(updatedCheckDto.getLocked()))
                .addValue("interval", updatedCheckDto.getInterval())
                .addValue("confirming", updatedCheckDto.isConfirming())
                .addValue("version", updatedCheckDto.getVersion());

        this.namedParameterJdbcTemplate.update(updateSql, parameters);

        return updatedCheckDto;
    }

    private RowMapper<CheckDto> mapCheckDto()
    {
        return (rs, rowNum) -> CheckDto.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .latestResultId(rs.getLong("latest_result_id"))
                .name(rs.getString("name"))
                .url(rs.getString("url"))
                .probe(rs.getString("probe"))
                .status(Status.valueOf(rs.getString("status")))
                .state(State.valueOf(rs.getString("state")))
                .created(rs.getTimestamp("created").toInstant())
                .modified(rs.getTimestamp("modified").toInstant())
                .refresh(rs.getTimestamp("refresh").toInstant())
                .locked(rs.getTimestamp("locked").toInstant())
                .interval(rs.getInt("interval"))
                .confirming(rs.getBoolean("confirming"))
                .version(rs.getLong("version"))
                .build();
    }

    @Override
    public Collection<CheckDto> findElectableChecks(String probe, boolean isClustered, Instant instant)
    {
        String findElectableChecksSql = "SELECT * FROM \"check\" WHERE ((state = 'WAITING'::state AND refresh <= :instant) OR (state = 'ELECTED'::state AND locked <= :instant)) AND ((:isClustered = FALSE) OR (confirming = FALSE) OR (:isClustered = TRUE AND probe <> :probe))";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("instant", Timestamp.from(instant))
                .addValue("probe", probe)
                .addValue("isClustered", isClustered);

        return this.namedParameterJdbcTemplate
                .query(findElectableChecksSql, parameters, mapCheckDto());
    }

    @Override
    public Collection<CheckDto> markChecksElected(Collection<CheckDto> checkDtos)
    {
        String markChecksElectedSql = "UPDATE \"check\" SET state = 'ELECTED'::state WHERE id in (:ids)";

        List<CheckDto> electedChecks = checkDtos.stream()
                .map(checkDto -> CheckDto.buildFrom(checkDto)
                        .state(State.ELECTED)
                        .build())
                .collect(Collectors.toList());

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("ids", electedChecks
                        .stream()
                        .map(CheckDto::getId)
                        .collect(Collectors.toList()));

        this.namedParameterJdbcTemplate.update(markChecksElectedSql, parameters);

        return electedChecks;
    }
}
