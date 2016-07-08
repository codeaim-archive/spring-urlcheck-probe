package com.codeaim.urlcheck.repository.jdbc;

import com.codeaim.urlcheck.domain.ResultDto;
import com.codeaim.urlcheck.domain.Status;
import com.codeaim.urlcheck.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@Repository
public class ResultRepositoryJdbc implements ResultRepository
{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ResultRepositoryJdbc(
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
        String countSql = "SELECT COUNT(*) FROM result";

        return this.jdbcTemplate.queryForObject(countSql, Long.class);
    }

    @Override
    public void delete(ResultDto resultDto)
    {
        String deleteSql = "DELETE FROM result WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", resultDto.getId());

        this.namedParameterJdbcTemplate.update(deleteSql, parameters);
    }

    @Override
    public void deleteAll()
    {
        String deleteAllSql = "DELETE FROM result";

        this.jdbcTemplate.update(deleteAllSql);
    }

    @Override
    public boolean exists(Long id)
    {
        String existsSql = "SELECT EXISTS(SELECT 1 FROM result WHERE id = :id)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return this.namedParameterJdbcTemplate.queryForObject(existsSql, parameters, Boolean.class);
    }

    @Override
    public Collection<ResultDto> findAll()
    {
        String findAllSql = "SELECT * FROM result";

        return this.jdbcTemplate.query(findAllSql, mapResultDto());
    }

    @Override
    public Collection<ResultDto> findAll(Collection<Long> ids)
    {
        String findAllSql = "SELECT * FROM result WHERE id in (:ids)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("ids", ids);

        return this.namedParameterJdbcTemplate
                .query(findAllSql, parameters, mapResultDto());
    }

    @Override
    public Optional<ResultDto> findOne(Long id)
    {
        String findSql = "SELECT * FROM result WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        return this.namedParameterJdbcTemplate
                .query(findSql, parameters, mapResultDto())
                .stream()
                .findFirst();
    }

    @Override
    public ResultDto save(ResultDto resultDto)
    {
        if (resultDto.getId() == 0)
            return insert(resultDto);

        return update(resultDto);
    }

    @Override
    public Collection<ResultDto> save(Collection<ResultDto> entities)
    {
        return entities.stream().map(this::save).collect(Collectors.toList());
    }

    private ResultDto insert(ResultDto resultDto)
    {
        String insertSql = "INSERT INTO result(check_id, previous_result_id, status, probe, status_code, response_time, changed, confirmation, created, modified, version) VALUES(:check_id, :previous_result_id, :status::status, :probe, :status_code, :response_time, :changed, :confirmation, :created, :modified, :version)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("check_id", resultDto.getCheckId())
                .addValue("previous_result_id", resultDto.getPreviousResultId().isPresent() ? resultDto.getPreviousResultId().getAsLong() : null)
                .addValue("status", resultDto.getStatus().toString())
                .addValue("probe", resultDto.getProbe())
                .addValue("status_code", resultDto.getStatusCode().value())
                .addValue("response_time", resultDto.getResponseTime().isPresent() ? resultDto.getResponseTime().getAsLong() : null)
                .addValue("changed", resultDto.isChanged())
                .addValue("confirmation", resultDto.isConfirmation())
                .addValue("created", Timestamp.from(resultDto.getCreated()))
                .addValue("modified", Timestamp.from(resultDto.getModified()))
                .addValue("version", resultDto.getVersion());

        this.namedParameterJdbcTemplate.update(insertSql, parameters, keyHolder, new String[]{"id"});

        return ResultDto.buildFrom(resultDto)
                .id(keyHolder.getKey().longValue())
                .build();
    }

    private ResultDto update(ResultDto resultDto)
    {
        String updateSql = "UPDATE result SET check_id = :check_id, previous_result_id = :previous_result_id, status = :status::status, probe = :probe, status_code = :status_code, response_time = :response_time, changed = :changed, confirmation = :confirmation, created = :created, modified = :modified, version = :version WHERE id = :id";

        ResultDto updatedResultDto = ResultDto.buildFrom(resultDto)
                .modified(Instant.now())
                .version(resultDto.getVersion() + 1)
                .build();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", updatedResultDto.getId())
                .addValue("check_id", updatedResultDto.getCheckId())
                .addValue("previous_result_id", updatedResultDto.getPreviousResultId().isPresent() ? resultDto.getPreviousResultId().getAsLong() : null)
                .addValue("status", updatedResultDto.getStatus().toString())
                .addValue("probe", updatedResultDto.getProbe())
                .addValue("status_code", updatedResultDto.getStatusCode().value())
                .addValue("response_time", updatedResultDto.getResponseTime().isPresent() ? resultDto.getResponseTime().getAsLong() : null)
                .addValue("changed", updatedResultDto.isChanged())
                .addValue("confirmation", updatedResultDto.isConfirmation())
                .addValue("created", Timestamp.from(updatedResultDto.getCreated()))
                .addValue("modified", Timestamp.from(updatedResultDto.getModified()))
                .addValue("version", updatedResultDto.getVersion());

        this.namedParameterJdbcTemplate.update(updateSql, parameters);

        return updatedResultDto;
    }

    private RowMapper<ResultDto> mapResultDto()
    {
        return (rs, rowNum) -> ResultDto.builder()
                .id(rs.getLong("id"))
                .checkId(rs.getLong("check_id"))
                .previousResultId(rs.getLong("previous_result_id") != 0 ? OptionalLong.of(rs.getLong("previous_result_id")) : OptionalLong.empty())
                .status(Status.valueOf(rs.getString("status")))
                .probe(rs.getString("probe"))
                .statusCode(HttpStatus.valueOf(rs.getInt("status_code")))
                .responseTime(rs.getLong("response_time") != 0 ? OptionalLong.of(rs.getLong("response_time")) : OptionalLong.empty())
                .changed(rs.getBoolean("changed"))
                .confirmation(rs.getBoolean("confirmation"))
                .created(rs.getTimestamp("created").toInstant())
                .modified(rs.getTimestamp("modified").toInstant())
                .version(rs.getLong("version"))
                .build();
    }

    @Override
    public int[] batchInsert(List<ResultDto> resultDtos)
    {
        String insertSql = "INSERT INTO result(check_id, previous_result_id, status, probe, status_code, response_time, changed, confirmation, created, modified, version) VALUES(:check_id, :previous_result_id, :status::status, :probe, :status_code, :response_time, :changed, :confirmation, :created, :modified, :version)";

        SqlParameterSource[] parameters =
                new SqlParameterSource[resultDtos.size()];

        for (int i = 0; i < resultDtos.size(); i++)
        {
            ResultDto resultDto = resultDtos.get(i);
            parameters[i] = new MapSqlParameterSource()
                    .addValue("check_id", resultDto.getCheckId())
                    .addValue("previous_result_id", resultDto.getPreviousResultId().isPresent() ? resultDto.getPreviousResultId().getAsLong() : null)
                    .addValue("status", resultDto.getStatus().toString())
                    .addValue("probe", resultDto.getProbe())
                    .addValue("status_code", resultDto.getStatusCode().value())
                    .addValue("response_time", resultDto.getResponseTime().isPresent() ? resultDto.getResponseTime().getAsLong() : null)
                    .addValue("changed", resultDto.isChanged())
                    .addValue("confirmation", resultDto.isConfirmation())
                    .addValue("created", Timestamp.from(resultDto.getCreated()))
                    .addValue("modified", Timestamp.from(resultDto.getModified()))
                    .addValue("version", resultDto.getVersion());
        }

        return this.namedParameterJdbcTemplate.batchUpdate(insertSql, parameters);
    }
}
