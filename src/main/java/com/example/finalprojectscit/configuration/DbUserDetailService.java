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

    /**
     * Load user details by username (email) from the database.
     * @param username The username (email) of the user to load.
     * @return UserDetails object representing the user.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDatabase = (User) userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database with this name"));
        return new MyUserDetails(userFromDatabase);
    }
}
