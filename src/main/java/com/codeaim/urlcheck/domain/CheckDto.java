package com.codeaim.urlcheck.domain;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalLong;

import org.apache.commons.lang3.Validate;

import okhttp3.HttpUrl;

public final class CheckDto
{
    private long id;
    private long userId;
    private OptionalLong latestResultId;
    private String name;
    private HttpUrl url;
    private Optional<String> probe;
    private Status status;
    private State state;
    private Instant created;
    private Instant modified;
    private Instant refresh;
    private Optional<Instant> locked;
    private int interval;
    private boolean confirming;
    private long version;

    private CheckDto(
            final long id,
            final long userId,
            final OptionalLong latestResultId,
            final String name,
            final HttpUrl url,
            final Optional<String> probe,
            final Status status,
            final State state,
            final Instant created,
            final Instant modified,
            final Instant refresh,
            final Optional<Instant> locked,
            final int interval,
            final boolean confirming,
            final long version
    )
    {
        this.id = id;
        this.userId = userId;
        this.latestResultId = latestResultId;
        this.name = name;
        this.url = url;
        this.probe = probe;
        this.status = status;
        this.state = state;
        this.created = created;
        this.modified = modified;
        this.refresh = refresh;
        this.locked = locked;
        this.interval = interval;
        this.confirming = confirming;
        this.version = version;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder buildFrom(CheckDto checkDto)
    {
        return builder()
                .id(checkDto.getId())
                .userId(checkDto.getUserId())
                .latestResultId(checkDto.getLatestResultId())
                .name(checkDto.getName())
                .url(checkDto.getUrl())
                .probe(checkDto.getProbe())
                .status(checkDto.getStatus())
                .state(checkDto.getState())
                .created(checkDto.getCreated())
                .modified(Instant.now())
                .refresh(checkDto.getRefresh())
                .locked(checkDto.getLocked())
                .interval(checkDto.getInterval())
                .confirming(checkDto.isConfirming())
                .version(checkDto.getVersion());
    }

    public long getId()
    {
        return id;
    }

    public long getUserId()
    {
        return userId;
    }

    public OptionalLong getLatestResultId()
    {
        return latestResultId;
    }

    public String getName()
    {
        return name;
    }

    public HttpUrl getUrl()
    {
        return url;
    }

    public Optional<String> getProbe()
    {
        return probe;
    }

    public Status getStatus()
    {
        return status;
    }

    public State getState()
    {
        return state;
    }

    public Instant getCreated()
    {
        return created;
    }

    public Instant getModified()
    {
        return modified;
    }

    public Instant getRefresh()
    {
        return refresh;
    }

    public Optional<Instant> getLocked()
    {
        return locked;
    }

    public int getInterval()
    {
        return interval;
    }

    public boolean isConfirming()
    {
        return confirming;
    }

    public long getVersion()
    {
        return version;
    }

    public final static class Builder
    {
        private long id;
        private long userId;
        private OptionalLong latestResultId = OptionalLong.empty();
        private String name;
        private HttpUrl url;
        private Optional<String> probe = Optional.empty();
        private Status status;
        private State state;
        private Instant created;
        private Instant modified;
        private Instant refresh;
        private Optional<Instant> locked = Optional.empty();
        private int interval;
        private boolean confirming;
        private long version;

        public Builder id(final long id)
        {
            Validate.notNull(id);

            this.id = id;
            return this;
        }

        public Builder userId(final long userId)
        {
            Validate.notNull(userId);

            this.userId = userId;
            return this;
        }

        public Builder latestResultId(final OptionalLong latestResultId)
        {
            Validate.notNull(latestResultId);

            this.latestResultId = latestResultId;
            return this;
        }

        public Builder name(final String name)
        {
            Validate.notNull(name);

            this.name = name;
            return this;
        }

        public Builder url(final HttpUrl url)
        {
            Validate.notNull(url);

            this.url = url;
            return this;
        }

        public Builder probe(final Optional<String> probe)
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

        public Builder state(final State state)
        {
            Validate.notNull(state);

            this.state = state;
            return this;
        }

        public Builder created(final Instant created)
        {
            Validate.notNull(created);

            this.created = created;
            return this;
        }

        public Builder modified(final Instant modified)
        {
            Validate.notNull(modified);

            this.modified = modified;
            return this;
        }

        public Builder refresh(final Instant refresh)
        {
            Validate.notNull(refresh);

            this.refresh = refresh;
            return this;
        }

        public Builder locked(final Optional<Instant> locked)
        {
            Validate.notNull(locked);

            this.locked = locked;
            return this;
        }

        public Builder interval(final int interval)
        {
            Validate.notNull(interval);

            this.interval = interval;
            return this;
        }

        public Builder confirming(final boolean confirming)
        {
            Validate.notNull(confirming);

            this.confirming = confirming;
            return this;
        }

        public Builder version(final long version)
        {
            Validate.notNull(version);

            this.version = version;
            return this;
        }

        public CheckDto build()
        {
            return new CheckDto(
                    this.id,
                    this.userId,
                    this.latestResultId,
                    this.name,
                    this.url,
                    this.probe,
                    this.status == null ? Status.UNKNOWN : this.status,
                    this.state == null ? State.WAITING : this.state,
                    this.created == null ? Instant.now() : this.created,
                    this.modified == null ? Instant.now() : this.modified,
                    this.refresh == null ? Instant.now() : this.refresh,
                    this.locked == null ? Optional.of(Instant.now()) : this.locked,
                    this.interval,
                    this.confirming,
                    this.version <= 0 ? 1 : this.version
            );
        }
    }
}