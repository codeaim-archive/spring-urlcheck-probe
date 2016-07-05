package com.codeaim.urlcheck.domain;

public final class RoleDto
{
    private long id;
    private String name;

    private RoleDto(
            final long id,
            final String name
    )
    {
        this.id = id;
        this.name = name;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder buildFrom(RoleDto roleDto)
    {
        return builder()
                .id(roleDto.getId())
                .name(roleDto.getName());
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public final static class Builder
    {
        private long id;
        private String name;

        public Builder id(final long id)
        {
            this.id = id;
            return this;
        }

        public Builder name(final String name)
        {
            this.name = name;
            return this;
        }

        public RoleDto build()
        {
            return new RoleDto(
                    this.id,
                    this.name
            );
        }
    }
}