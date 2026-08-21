package com.github.nhordiienko23.springmysql.controller;

import com.github.nhordiienko23.springmysql.dto.UserDto;

import com.github.nhordiienko23.springmysql.repository.ProfileService;
import com.github.nhordiienko23.springmysql.service.CustomUserDetails;
import com.github.nhordiienko23.springmysql.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final OrderService orderService;

    public ProfileController(ProfileService profileService, OrderService orderService) {
        this.profileService = profileService;
        this.orderService = orderService;
    }


    @GetMapping
    public UserDto profile(@AuthenticationPrincipal(errorOnInvalidType = true) CustomUserDetails userDetails) {
        return profileService.getProfile(userDetails.getId());
    }

    @PutMapping
    public UserDto updateProfile(@AuthenticationPrincipal(errorOnInvalidType = true) CustomUserDetails userDetails,
                                 @RequestBody @Valid UserDto userDto) {

        return profileService.updateProfile(userDetails.getId(), userDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProfile(@AuthenticationPrincipal(errorOnInvalidType = true) CustomUserDetails userDetails,
                                                HttpServletRequest request) throws ServletException {
        profileService.deleteProfile(userDetails.getId());
        request.logout();
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("my-orders")
    public ResponseEntity<List> getMyOrders(@AuthenticationPrincipal(errorOnInvalidType = true) CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.searchById(userDetails.getId()));
    }
}
