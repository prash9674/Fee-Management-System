package com.fee.management.controllers;

import com.fee.management.models.FeeDetails;
import com.fee.management.models.User;
import com.fee.management.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Fetch user details",
            description = "Fetch user details  by userId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "user details found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "user details not found")
            }
    )
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Fetch fee details",
            description = "Fetch fee details for user by userId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "user fee details found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "user fee details not found")
            }
    )
    @GetMapping("/{userId}/fee-details")
    public ResponseEntity<FeeDetails> getFeeDetailsByUserId(@PathVariable String userId) {
        Optional<FeeDetails> feeDetails = userService.getFeeDetailsByUserId(userId);
        return feeDetails.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Register user",
            description = "Register user by userId , name , email",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User Successfully Registered", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getUserId() == null) {
            // Generate userId based on email or other criteria if necessary
            user.setUserId(user.getEmail().split("@")[0]);
        }
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
