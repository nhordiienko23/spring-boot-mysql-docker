package com.github.nhordiienko23.springmysql.controller;

import com.github.nhordiienko23.springmysql.dto.UserDto;
import com.github.nhordiienko23.springmysql.repository.ProfileService;
import com.github.nhordiienko23.springmysql.service.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping("/profile")
    public UserDto profile(@AuthenticationPrincipal(errorOnInvalidType = true) CustomUserDetails userDetails) {
        return profileService.getProfile(userDetails.getId());
    }

    @PutMapping("/profile")
    public UserDto updateProfile(@AuthenticationPrincipal(errorOnInvalidType = true) CustomUserDetails userDetails,
                                 @RequestBody @Valid UserDto userDto) {

        return profileService.updateProfile(userDetails.getId(),userDto);
    }
    @DeleteMapping("/profile")
    public ResponseEntity<String> deleteProfile(@AuthenticationPrincipal(errorOnInvalidType = true) CustomUserDetails userDetails,
                                        HttpServletRequest request) throws ServletException {
        profileService.deleteProfile(userDetails.getId());
        request.logout();
        return ResponseEntity.ok("User deleted successfully");
    }
}
