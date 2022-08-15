package com.learn.auth;

import com.google.common.collect.Lists;
import com.learn.entities.Student;
import com.learn.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.learn.security.ApplicationUserRole.*;

@Repository("test")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "abdo",
                        passwordEncoder.encode("abdo"),
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "ziza",
                        passwordEncoder.encode("ziza"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "zozo",
                        passwordEncoder.encode("zozo"),
                        ADMINTRAINEE.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );
        return applicationUsers;
    }

}
