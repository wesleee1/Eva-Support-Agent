package com.eva.EvaSupportAgent.user.mapper;

import com.eva.EvaSupportAgent.user.model.User;
import com.eva.EvaSupportAgent.user.vo.UserVO;

public class UserMapper {

    public static User toEntity(UserVO vo) {
        User user = new User();
        user.setId(vo.getId());
        user.setName(vo.getName());
        user.setEmail(vo.getEmail());
        user.setPhone(vo.getPhone());
        user.setActive(vo.isActive());
        return user;
    }

    public static UserVO toVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setName(user.getName());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getPhone());
        vo.setActive(user.isActive());
        return vo;
    }
}
