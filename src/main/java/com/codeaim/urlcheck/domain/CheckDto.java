package com.codeaim.urlcheck.domain;

import java.time.Instant;

public final class CheckDto
{
    private long id;
    private long userId;
    private long latestResultId;
    private String name;
    private String url;
    private String probe;
    private Status status;
    private State state;
    private Instant created;
    private Instant modified;
    private Instant refresh;
    private Instant locked;
    private int interval;
    private boolean confirming;
    private long version;

    private CheckDto(
            final long id,
            final long userId,
            final long latestResultId,
            final String name,
            final String url,
            final String probe,
            final Status status,
            final State state,
            final Instant created,
            final Instant modified,
            final Instant refresh,
            final Instant locked,
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

    public long getId()
    {
        return id;
    }

    public long getUserId()
    {
        return userId;
    }

    public long getLatestResultId()
    {
        return latestResultId;
    }

    public String getName()
    {
        return name;
    }

    public String getUrl()
    {
        return url;
    }

    public String getProbe()
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

    public Instant getLocked()
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
                .modified(checkDto.getModified())
                .refresh(checkDto.getRefresh())
                .locked(checkDto.getLocked())
                .interval(checkDto.getInterval())
                .confirming(checkDto.isConfirming())
                .version(checkDto.getVersion());
    }

    public final static class Builder
    {
        private long id;
        private long userId;
        private long latestResultId;
        private String name;
        private String url;
        private String probe;
        private Status status;
        private State state;
        private Instant created;
        private Instant modified;
        private Instant refresh;
        private Instant locked;
        private int interval;
        private boolean confirming;
        private long version;

        public Builder id(final long id)
        {
            this.id = id;
            return this;
        }

        public Builder userId(final long userId)
        {
            this.userId = userId;
            return this;
        }

        public Builder latestResultId(final long latestResultId)
        {
            this.latestResultId = latestResultId;
            return this;
        }

        public Builder name(final String name)
        {
            this.name = name;
            return this;
        }

        public Builder url(final String url)
        {
            this.url = url;
            return this;
        }

        public Builder probe(final String probe)
        {
            this.probe = probe;
            return this;
        }

        public Builder status(final Status status)
        {
            this.status = status;
            return this;
        }

        public Builder state(final State state)
        {
            this.state = state;
            return this;
        }

        public Builder created(final Instant created)
        {
            this.created = created;
            return this;
        }

        public Builder modified(final Instant modified)
        {
            this.modified = modified;
            return this;
        }

        public Builder refresh(final Instant refresh)
        {
            this.refresh = refresh;
            return this;
        }

        public Builder locked(final Instant locked)
        {
            this.locked = locked;
            return this;
        }

        public Builder interval(final int interval)
        {
            this.interval = interval;
            return this;
        }

        public Builder confirming(final boolean confirming)
        {
            this.confirming = confirming;
            return this;
        }

        public Builder version(final long version)
        {
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
                    Instant.now(),
                    this.refresh == null ? Instant.now() : this.refresh,
                    this.locked == null ? Instant.now() : this.locked,
                    this.interval,
                    this.confirming,
                    this.version <= 0 ? 1 : this.version
            );
        }
    }
}