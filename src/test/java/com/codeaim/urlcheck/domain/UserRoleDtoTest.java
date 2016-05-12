package com.codeaim.urlcheck.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleDtoTest
{
    @Test
    public void build() {
        UserRoleDto userRoleDto = UserRoleDto.builder()
                .id(1)
                .userId(1)
                .roleId(1)
                .build();

        Assert.assertEquals(1, userRoleDto.getId());
        Assert.assertEquals(1, userRoleDto.getUserId());
        Assert.assertEquals(1, userRoleDto.getRoleId());
    }
}
