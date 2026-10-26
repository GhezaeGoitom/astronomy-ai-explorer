package com.gg.astronomy_ai_explorer.service.user;

import com.gg.astronomy_ai_explorer.dto.user.PublishDto;

import java.util.List;

public interface PublishService {

    List<PublishDto> getAllPublishes();

    PublishDto getPublishById(Long id);

    PublishDto createPublish(PublishDto publishDto);

    PublishDto updatePublish(Long id, PublishDto publishDto);

    void deletePublish(Long id);
}
