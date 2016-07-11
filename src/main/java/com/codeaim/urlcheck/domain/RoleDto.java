package com.codeaim.urlcheck.domain;

import java.time.Duration;

import org.apache.commons.lang3.Validate;

public final class RoleDto
{
    private long id;
    private String name;
    private long checkLimit;
    private long resultEventLimit;
    private Duration resultRetentionDuration;

    private RoleDto(
            final long id,
            final String name,
            final long checkLimit,
            final long resultEventLimit,
            final Duration resultRetentionDuration
    )
    {
        this.id = id;
        this.name = name;
        this.checkLimit = checkLimit;
        this.resultEventLimit = resultEventLimit;
        this.resultRetentionDuration = resultRetentionDuration;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder buildFrom(RoleDto roleDto)
    {
        return builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .checkLimit(roleDto.getCheckLimit())
                .resultEventLimit(roleDto.getResultEventLimit())
                .resultRetentionDuration(roleDto.getResultRetentionDuration());
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public long getCheckLimit()
    {
        return checkLimit;
    }

    public long getResultEventLimit()
    {
        return resultEventLimit;
    }

    public Duration getResultRetentionDuration()
    {
        return resultRetentionDuration;
    }

    public final static class Builder
    {
        private long id;
        private String name;
        private long checkLimit;
        private long resultEventLimit;
        private Duration resultRetentionDuration = Duration.ZERO;

        public Builder id(final long id)
        {
            Validate.notNull(id);

            this.id = id;
            return this;
        }

        public Builder name(final String name)
        {
            Validate.notNull(name);

            this.name = name;
            return this;
        }

        public Builder checkLimit(final long checkLimit)
        {
            Validate.notNull(checkLimit);

            this.checkLimit = checkLimit;
            return this;
        }

        public Builder resultEventLimit(final long resultEventLimit)
        {
            Validate.notNull(resultEventLimit);

            this.resultEventLimit = resultEventLimit;
            return this;
        }

        public Builder resultRetentionDuration(final Duration resultRetentionDuration)
        {
            Validate.notNull(resultRetentionDuration);

            this.resultRetentionDuration = resultRetentionDuration;
            return this;
        }

        public RoleDto build()
        {
            return new RoleDto(
                    this.id,
                    this.name,
                    this.checkLimit,
                    this.resultEventLimit,
                    this.resultRetentionDuration
            );
        }
    }
}