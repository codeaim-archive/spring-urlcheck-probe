package com.codeaim.urlcheck.domain;

import org.apache.commons.lang3.Validate;

import java.time.Instant;

public final class UserDto
{
    private long id;
    private String name;
    private String email;
    private String resetToken;
    private String accessToken;
    private String password;
    private boolean emailVerified;
    private Instant created;
    private Instant modified;
    private long version;

    private UserDto(
            final long id,
            final String name,
            final String email,
            final String resetToken,
            final String accessToken,
            final String password,
            final boolean emailVerified,
            final Instant created,
            final Instant modified,
            final long version
    )
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.resetToken = resetToken;
        this.accessToken = accessToken;
        this.password = password;
        this.emailVerified = emailVerified;
        this.created = created;
        this.modified = modified;
        this.version = version;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder buildFrom(UserDto userDto)
    {
        return builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .resetToken(userDto.getResetToken())
                .accessToken(userDto.getAccessToken())
                .password(userDto.getPassword())
                .emailVerified(userDto.isEmailVerified())
                .created(userDto.getCreated())
                .version(userDto.getVersion());
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getResetToken()
    {
        return resetToken;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean isEmailVerified()
    {
        return emailVerified;
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

    public final static class Builder
    {
        private long id;
        private String name;
        private String email;
        private String resetToken;
        private String accessToken;
        private String password;
        private boolean emailVerified;
        private Instant created;
        private Instant modified;
        private long version;

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

        public Builder email(final String email)
        {
            Validate.notNull(email);

            this.email = email;
            return this;
        }

        public Builder resetToken(final String resetToken)
        {
            Validate.notNull(resetToken);

            this.resetToken = resetToken;
            return this;
        }

        public Builder accessToken(final String accessToken)
        {
            Validate.notNull(accessToken);

            this.accessToken = accessToken;
            return this;
        }

        public Builder password(final String password)
        {
            Validate.notNull(password);

            this.password = password;
            return this;
        }

        public Builder emailVerified(final boolean emailVerified)
        {
            Validate.notNull(emailVerified);

            this.emailVerified = emailVerified;
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

        public Builder version(final long version)
        {
            Validate.notNull(version);

            this.version = version;
            return this;
        }

        public UserDto build()
        {
            return new UserDto(
                    this.id,
                    this.name,
                    this.email,
                    this.resetToken,
                    this.accessToken,
                    this.password,
                    this.emailVerified,
                    Instant.now(),
                    this.created == null ? Instant.now() : this.created,
                    this.version <= 0 ? 1 : this.version
            );
        }
    }
}