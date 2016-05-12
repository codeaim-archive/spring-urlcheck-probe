package com.codeaim.urlcheck.domain;

public final class UserRoleDto
{
    private long id;
    private long userId;
    private long roleId;

    private UserRoleDto(
            final long id,
            final long userId,
            final long roleId
    )
    {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public long getId()
    {
        return id;
    }

    public long getUserId()
    {
        return userId;
    }

    public long getRoleId()
    {
        return roleId;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder buildFrom(UserRoleDto userRoleDto)
    {
        return builder()
                .id(userRoleDto.getId())
                .userId(userRoleDto.getUserId())
                .roleId(userRoleDto.getRoleId());
    }

    public final static class Builder
    {
        private long id;
        private long userId;
        private long roleId;

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

        public Builder roleId(final long roleId)
        {
            this.roleId = roleId;
            return this;
        }

        public UserRoleDto build()
        {
            return new UserRoleDto(
                    this.id,
                    this.userId,
                    this.roleId
            );
        }
    }
}