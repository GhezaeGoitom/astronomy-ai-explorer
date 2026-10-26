package com.gg.astronomy_ai_explorer.service.user;

import com.gg.astronomy_ai_explorer.dto.user.AppUserDto;
import com.gg.astronomy_ai_explorer.dto.user.AppUserResponseDto;
import com.gg.astronomy_ai_explorer.dto.user.AppUserUpdateDto;

import java.util.List;

public interface AppUserService {

    List<AppUserResponseDto> getAllUsers();

    AppUserResponseDto getUserById(Long id);

    AppUserResponseDto createUser(AppUserDto appUserDto);

    AppUserResponseDto updateUser(Long id, AppUserUpdateDto appUserUpdateDto);

    void deleteUser(Long id);

    void updateUserRoles(Long userId, List<Integer> roleIds);
}
