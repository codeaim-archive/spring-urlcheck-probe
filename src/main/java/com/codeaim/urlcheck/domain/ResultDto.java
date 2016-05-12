package com.codeaim.urlcheck.domain;

import java.time.Instant;

public final class ResultDto
{
    private long id;
    private long checkId;
    private long previousResultId;
    private String probe;
    private Status status;
    private int statusCode;
    private int responseTime;
    private boolean changed;
    private boolean confirmation;
    private Instant created;
    private Instant modified;
    private long version;

    private ResultDto(
            final long id,
            final long checkId,
            final long previousResultId,
            final String probe,
            final Status status,
            final int statusCode,
            final int responseTime,
            final boolean changed,
            final boolean confirmation,
            final Instant created,
            final Instant modified,
            final long version
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
        this.modified = modified;
        this.version = version;
    }

    public long getId()
    {
        return id;
    }

    public long getCheckId()
    {
        return checkId;
    }

    public long getPreviousResultId()
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

    public int getStatusCode()
    {
        return statusCode;
    }

    public int getResponseTime()
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

    public Instant getModified()
    {
        return modified;
    }

    public long getVersion()
    {
        return version;
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
                .created(resultDto.getCreated())
                .modified(resultDto.getModified())
                .version(resultDto.getVersion());
    }

    public final static class Builder
    {
        private long id;
        private long checkId;
        private long previousResultId;
        private String probe;
        private Status status;
        private int statusCode;
        private int responseTime;
        private boolean changed;
        private boolean confirmation;
        private Instant created;
        private Instant modified;
        private long version;

        public Builder id(final long id)
        {
            this.id = id;
            return this;
        }

        public Builder checkId(final long checkId)
        {
            this.checkId = checkId;
            return this;
        }

        public Builder previousResultId(final long previousResultId)
        {
            this.previousResultId = previousResultId;
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

        public Builder statusCode(final int statusCode)
        {
            this.statusCode = statusCode;
            return this;
        }

        public Builder responseTime(final int responseTime)
        {
            this.responseTime = responseTime;
            return this;
        }

        public Builder changed(final boolean changed)
        {
            this.changed = changed;
            return this;
        }

        public Builder confirmation(final boolean confirmation)
        {
            this.confirmation = confirmation;
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

        public Builder version(final long version)
        {
            this.version = version;
            return this;
        }

        public ResultDto build()
        {
            return new ResultDto(
                    this.id,
                    this.checkId,
                    this.previousResultId,
                    this.probe,
                    this.status == null ? Status.UNKNOWN : this.status,
                    this.statusCode,
                    this.responseTime,
                    this.changed,
                    this.confirmation,
                    this.created == null ? Instant.now() : this.created,
                    Instant.now(),
                    this.version <= 0 ? 1 : this.version
            );
        }
    }
}