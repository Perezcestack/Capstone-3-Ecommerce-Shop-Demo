package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController {
    private ProfileDao profileDao;
    private UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public Profile addProfile(@RequestBody Profile profile){
        return profileDao.create(profile);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public Profile getByUserId(Principal principal){

        User user = userDao.getByUserName(principal.getName());
        Profile profile = profileDao.getByUserId(user.getId());
        return profileDao.getByUserId(user.getId());
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public void updateProfile(Principal principal, @RequestBody Profile profile){
        User user = userDao.getByUserName(principal.getName());
        profileDao.updateUser(user.getId(),profile);
    }
}
