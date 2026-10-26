package com.gg.astronomy_ai_explorer.controller.user;

import com.gg.astronomy_ai_explorer.dto.user.AppUserDto;
import com.gg.astronomy_ai_explorer.dto.user.AppUserResponseDto;
import com.gg.astronomy_ai_explorer.dto.user.AppUserUpdateDto;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.service.user.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping
    public ResponseEntity<AppUserResponseDto> createUser(
            @Valid @RequestBody AppUserDto appUserDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appUserService.createUser(appUserDto));
    }

    @GetMapping
    public ResponseEntity<List<AppUserResponseDto>> getAllUsers() {

        return ResponseEntity.ok(
                appUserService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponseDto> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                appUserService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody AppUserUpdateDto appUserUpdateDto) {

        return ResponseEntity.ok(
                appUserService.updateUser(id, appUserUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        appUserService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }


    // adding role , only by admin
    @PutMapping("/{id}/roles")
    public ResponseEntity<Void> updateUserRoles(
            @PathVariable Long id,
            @RequestBody List<Integer> roleIds
    ) {
        if (roleIds == null || roleIds.isEmpty()) {
            throw new ApiException(
                    "At least one role is required",
                    HttpStatus.BAD_REQUEST
            );
        }
        appUserService.updateUserRoles(id, roleIds);
        return ResponseEntity.noContent().build();
    }
}
