package com.gg.astronomy_ai_explorer.service.user;

import com.gg.astronomy_ai_explorer.dto.user.RoleDto;
import com.gg.astronomy_ai_explorer.entity.user.AppRole;
import com.gg.astronomy_ai_explorer.entity.user.AppUser;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.user.AppRoleRepository;
import com.gg.astronomy_ai_explorer.repository.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final AppRoleRepository appRoleRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public List<RoleDto> getAllRoles() {
        return appRoleRepository.findAll()
                .stream()
                .map(role -> new RoleDto(
                        role.getId(),
                        role.getName()
                ))
                .toList();
    }
}