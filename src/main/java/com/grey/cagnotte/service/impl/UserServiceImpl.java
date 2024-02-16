package com.grey.cagnotte.service.impl;

import com.grey.cagnotte.entity.User;
import com.grey.cagnotte.exception.CagnotteCustomException;
import com.grey.cagnotte.payload.request.UserRequest;
import com.grey.cagnotte.payload.response.UserResponse;
import com.grey.cagnotte.repository.UserRepository;
import com.grey.cagnotte.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final String NOT_FOUND = "USER_NOT_FOUND";

    private final UserRepository userRepository;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public long addUser(UserRequest userRequest) {
        log.info("UserServiceImpl | addUser is called");

        String type;
        if(userRequest.getType() != null && !userRequest.getType().isBlank())
            type = userRequest.getType();
        else
            type = "user";

        User user;
        if(!userRepository.existsByEmailEquals(userRequest.getEmail())){
            user = User.builder()
                    .nom(userRequest.getNom())
                    .prenoms(userRequest.getPrenoms())
                    .email(userRequest.getEmail())
                    .tel1(userRequest.getTel1())
                    .tel2(userRequest.getTel2())
                    .adresse(userRequest.getAdresse())
                    .password(userRequest.getPassword())
                    .type(type)
                    .build();
            user = userRepository.save(user);
        }else{
            user = userRepository.findByEmailEquals(userRequest.getEmail()).orElseThrow();
            editUser(userRequest, user.getId());
        }

        log.info("UserServiceImpl | addUser | User Created | Id : " + user.getId());
        return user.getId();
    }

    @Override
    public void addUser(List<UserRequest> userRequests) {
        log.info("UserServiceImpl | addUser is called");

        for (UserRequest userRequest: userRequests) {
            String type;
            if(userRequest.getType() != null && !userRequest.getType().isBlank())
                type = userRequest.getType();
            else
                type = "user";

            User user;
            if(!userRepository.existsByEmailEquals(userRequest.getEmail())){
                user = User.builder()
                        .nom(userRequest.getNom())
                        .prenoms(userRequest.getPrenoms())
                        .email(userRequest.getEmail())
                        .tel1(userRequest.getTel1())
                        .tel2(userRequest.getTel2())
                        .adresse(userRequest.getAdresse())
                        .password(userRequest.getPassword())
                        .type(type)
                        .build();

                userRepository.save(user);
            }
        }

        log.info("UserServiceImpl | addUser | Users Created");
    }

    @Override
    public UserResponse getUserByEmail(String userEmail) {
        log.info("UserServiceImpl | getUserByEmail is called");

        User user
                = userRepository.findByEmailEquals(userEmail)
                .orElseThrow(
                        () -> new CagnotteCustomException("User with given Email not found", NOT_FOUND));

        UserResponse userResponse = new UserResponse();

        copyProperties(user, userResponse);

        log.info("UserServiceImpl | getUserByEmail | userResponse :" + userResponse.toString());

        return userResponse;
    }

    @Override
    public void editUser(UserRequest userRequest, long userId) {
        log.info("UserServiceImpl | editUser is called");

        User user
                = userRepository.findById(userId)
                .orElseThrow(() -> new CagnotteCustomException(
                        "User with given Id not found",
                        NOT_FOUND
                ));
        user.setNom(userRequest.getNom());
        user.setPrenoms(userRequest.getPrenoms());
        user.setEmail(userRequest.getEmail());
        user.setTel1(userRequest.getTel1());
        user.setTel2(userRequest.getTel2());
        user.setAdresse(userRequest.getAdresse());
        user.setPassword(userRequest.getPassword());
        user.setType(userRequest.getType());
        userRepository.save(user);

        log.info("UserServiceImpl | editUser | User Updated | User Id :" + user.getId());
    }

    @Override
    public void deleteUserById(long userId) {
        log.info("User id: {}", userId);

        if (!userRepository.existsById(userId)) {
            log.info("Im in this loop {}", !userRepository.existsById(userId));
            throw new CagnotteCustomException(
                    "User with given with Id: " + userId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting User with id: {}", userId);
        userRepository.deleteById(userId);
    }
}
