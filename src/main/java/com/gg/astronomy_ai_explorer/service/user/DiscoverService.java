package com.gg.astronomy_ai_explorer.service.user;

import com.gg.astronomy_ai_explorer.dto.user.DiscoverDto;

import java.util.List;

public interface DiscoverService {

    List<DiscoverDto> getAllDiscoveries();

    DiscoverDto getDiscoveryById(Long id);

    DiscoverDto createDiscovery(DiscoverDto discoverDto);

    DiscoverDto updateDiscovery(Long id, DiscoverDto discoverDto);

    void deleteDiscovery(Long id);
}
