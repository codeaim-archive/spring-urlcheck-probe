package com.codeaim.urlcheck.domain;

import org.apache.commons.lang3.Validate;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.OptionalLong;

public final class ResultDto
{
    private long id;
    private long checkId;
    private OptionalLong previousResultId;
    private String probe;
    private Status status;
    private HttpStatus statusCode;
    private OptionalLong responseTime;
    private boolean changed;
    private boolean confirmation;
    private Instant created;

    private ResultDto(
            final long id,
            final long checkId,
            final OptionalLong previousResultId,
            final String probe,
            final Status status,
            final HttpStatus statusCode,
            final OptionalLong responseTime,
            final boolean changed,
            final boolean confirmation,
            final Instant created
    )
    {
        this.id = id;
        this.checkId = checkId;
        this.previousResultId = previousResultId;
        this.probe = probe;
        this.status = status;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.changed = changed;
        this.confirmation = confirmation;
        this.created = created;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder buildFrom(ResultDto resultDto)
    {
        return builder()
                .id(resultDto.getId())
                .checkId(resultDto.getCheckId())
                .previousResultId(resultDto.getPreviousResultId())
                .status(resultDto.getStatus())
                .probe(resultDto.getProbe())
                .statusCode(resultDto.getStatusCode())
                .responseTime(resultDto.getResponseTime())
                .changed(resultDto.isChanged())
                .confirmation(resultDto.isConfirmation())
                .created(resultDto.getCreated());
    }

    public long getId()
    {
        return id;
    }

    public long getCheckId()
    {
        return checkId;
    }

    public OptionalLong getPreviousResultId()
    {
        return previousResultId;
    }

    public String getProbe()
    {
        return probe;
    }

    public Status getStatus()
    {
        return status;
    }

    public HttpStatus getStatusCode()
    {
        return statusCode;
    }

    public OptionalLong getResponseTime()
    {
        return responseTime;
    }

    public boolean isChanged()
    {
        return changed;
    }

    public boolean isConfirmation()
    {
        return confirmation;
    }

    public Instant getCreated()
    {
        return created;
    }

    public final static class Builder
    {
        private long id;
        private long checkId;
        private OptionalLong previousResultId = OptionalLong.empty();
        private String probe;
        private Status status = Status.UNKNOWN;
        private HttpStatus statusCode;
        private OptionalLong responseTime = OptionalLong.empty();
        private boolean changed;
        private boolean confirmation;
        private Instant created = Instant.now();

        public Builder id(final long id)
        {
            Validate.notNull(id);

            this.id = id;
            return this;
        }

        public Builder checkId(final long checkId)
        {
            Validate.notNull(checkId);

            this.checkId = checkId;
            return this;
        }

        public Builder previousResultId(final OptionalLong previousResultId)
        {
            Validate.notNull(previousResultId);

            this.previousResultId = previousResultId;
            return this;
        }

        public Builder probe(final String probe)
        {
            Validate.notNull(probe);

            this.probe = probe;
            return this;
        }

        public Builder status(final Status status)
        {
            Validate.notNull(status);

            this.status = status;
            return this;
        }

        public Builder statusCode(final HttpStatus statusCode)
        {
            Validate.notNull(statusCode);

            this.statusCode = statusCode;
            return this;
        }

        public Builder responseTime(final OptionalLong responseTime)
        {
            Validate.notNull(responseTime);

            this.responseTime = responseTime;
            return this;
        }

        public Builder changed(final boolean changed)
        {
            Validate.notNull(changed);

            this.changed = changed;
            return this;
        }

        public Builder confirmation(final boolean confirmation)
        {
            Validate.notNull(confirmation);

            this.confirmation = confirmation;
            return this;
        }

        public Builder created(final Instant created)
        {
            Validate.notNull(created);

            this.created = created;
            return this;
        }

        public ResultDto build()
        {
            Validate.notNull(probe);
            Validate.notNull(statusCode);

            return new ResultDto(
                    this.id,
                    this.checkId,
                    this.previousResultId,
                    this.probe,
                    this.status,
                    this.statusCode,
                    this.responseTime,
                    this.changed,
                    this.confirmation,
                    this.created
            );
        }
    }
}