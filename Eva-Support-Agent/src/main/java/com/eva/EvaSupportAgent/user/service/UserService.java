package com.eva.EvaSupportAgent.user.service;

import java.util.List;

import com.eva.EvaSupportAgent.user.model.User;
import com.eva.EvaSupportAgent.user.vo.UserVO;

public interface UserService {

    List<UserVO> getAllUsers();

    UserVO getUserById(Long id);

    UserVO createUser(UserVO user);

    UserVO updateUser(Long id, UserVO user);

    void deleteUser(Long id);
}