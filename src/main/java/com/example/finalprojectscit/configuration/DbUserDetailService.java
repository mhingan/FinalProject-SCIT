/**
 * @author Mihaita Hingan
 */
package com.example.finalprojectscit.configuration;


import com.example.finalprojectscit.model.User;
import com.example.finalprojectscit.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDatabase = (User) userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database with this name"));
        if(!userFromDatabase.is_active()){
            //todo: throw custom exception
            throw new RuntimeException("User is not active");
        }

        return new MyUserDetails(userFromDatabase);
    }
}
