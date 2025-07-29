package com.eva.EvaSupportAgent.user.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eva.EvaSupportAgent.user.mapper.UserMapper;
import com.eva.EvaSupportAgent.user.model.User;
import com.eva.EvaSupportAgent.user.repository.UserRepository;
import com.eva.EvaSupportAgent.user.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;



    @Override
    public List<UserVO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public UserVO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toVO)
                .orElse(null);
    }

    @Override
    public UserVO createUser(UserVO vo) {
    	try {
        User user = UserMapper.toEntity(vo);
        user.setRegisteredAt(new Date());
        User saved = userRepository.save(user);
        return UserMapper.toVO(saved);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	return new UserVO();
    }

    @Override
    public UserVO updateUser(Long id, UserVO vo) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(vo.getName());
            user.setPhone(vo.getPhone());
            user.setActive(vo.isActive());
            user.setEmail(vo.getEmail());
            User updated = userRepository.save(user);
            return UserMapper.toVO(updated);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}


