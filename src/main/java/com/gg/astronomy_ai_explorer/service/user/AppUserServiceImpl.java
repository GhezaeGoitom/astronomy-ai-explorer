package com.gg.astronomy_ai_explorer.service.user;

import com.gg.astronomy_ai_explorer.dto.user.AppUserDto;
import com.gg.astronomy_ai_explorer.dto.user.AppUserResponseDto;
import com.gg.astronomy_ai_explorer.dto.user.AppUserUpdateDto;
import com.gg.astronomy_ai_explorer.entity.user.AppRole;
import com.gg.astronomy_ai_explorer.entity.user.AppUser;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.user.AppRoleRepository;
import com.gg.astronomy_ai_explorer.repository.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppRoleRepository appRoleRepository;

    @Override
    public List<AppUserResponseDto> getAllUsers() {

        return appUserRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public AppUserResponseDto getUserById(Long id) {

        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "User not found with id: " + id, HttpStatus.NOT_FOUND));

        return convertToDto(user);
    }

    @Override
    public AppUserResponseDto createUser(AppUserDto appUserDto) {

        if (appUserRepository.existsByUsername(
                appUserDto.getUsername())) {

            throw new ApiException(
                    "Username already exists",
                    HttpStatus.CONFLICT
            );
        }

        if (appUserRepository.existsByEmail(
                appUserDto.getEmail())) {

            throw new ApiException(
                    "Email already exists",
                    HttpStatus.CONFLICT
            );
        }

        AppUser user = new AppUser();

        user.setUsername(appUserDto.getUsername());
        user.setEmail(appUserDto.getEmail());

        user.setPassword(
                passwordEncoder.encode(appUserDto.getPassword())
        );

        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        AppRole guestRole = appRoleRepository
                .findByName("ROLE_GUEST")
                .orElseThrow(() ->
                        new ApiException("ROLE_GUEST not found", HttpStatus.NOT_FOUND)
                );

        user.setRoles(Set.of(guestRole));

        AppUser savedUser = appUserRepository.save(user);

        return convertToDto(savedUser);
    }

    @Override
    public AppUserResponseDto updateUser(
            Long id,
            AppUserUpdateDto appUserUpdateDto) {

        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "User not found with id: " + id, HttpStatus.NOT_FOUND));

        user.setUsername(appUserUpdateDto.getUsername());
        user.setEmail(appUserUpdateDto.getEmail());

        if (appUserUpdateDto.getEnabled() != null) {
            user.setEnabled(appUserUpdateDto.getEnabled());
        }

        AppUser updatedUser = appUserRepository.save(user);

        return convertToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        if (!appUserRepository.existsById(id)) {

            throw new ApiException(
                    "User not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        appUserRepository.deleteById(id);
    }

    private AppUserResponseDto convertToDto(AppUser user) {

        AppUserResponseDto dto = new AppUserResponseDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.getEnabled());

        dto.setRoles(
                user.getRoles()
                        .stream()
                        .map(AppRole::getName)
                        .collect(Collectors.toSet())
        );

        dto.setHasPersonProfile(user.getPerson() != null);
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }

    // add roles, only by admin
    @Override
    public void updateUserRoles(Long userId, List<Integer> roleIds) {

        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ApiException(
                        "User not found",
                        HttpStatus.NOT_FOUND
                ));

        List<AppRole> roles = appRoleRepository.findAllById(roleIds);

        if (roles.size() != roleIds.size()) {
            throw new ApiException(
                    "One or more roles do not exist",
                    HttpStatus.BAD_REQUEST
            );
        }

        user.setRoles(new HashSet<>(roles));

        appUserRepository.save(user);
    }
}
