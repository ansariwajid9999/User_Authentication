package com.example.vivatech.assignment.Service;
import com.example.vivatech.assignment.Dto.addUserDto;
import com.example.vivatech.assignment.Exceptions.UserProfileNotFoundException;
import com.example.vivatech.assignment.Models.User;
import com.example.vivatech.assignment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String addUser(addUserDto addUserDto) {

        User user = new User();
        user.setEmail(addUserDto.getEmail());
        user.setUsername(addUserDto.getUsername());
        userRepository.save(user);
        return "User has been added successfully...";
    }

    public User getUserProfile(String username) throws UserProfileNotFoundException {
        Optional<User> userProfile = userRepository.findByUsername(username);
        if (userProfile.isPresent()) {
            return userProfile.get();
        } else {
            throw new UserProfileNotFoundException("User profile not found for username: " + username);
        }
    }
}
