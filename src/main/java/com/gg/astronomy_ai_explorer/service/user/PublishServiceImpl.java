package com.gg.astronomy_ai_explorer.service.user;

import com.gg.astronomy_ai_explorer.dto.user.PublishDto;
import com.gg.astronomy_ai_explorer.entity.user.Publish;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.user.PublishRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublishServiceImpl implements PublishService {

    private final PublishRepository publishRepository;

    public PublishServiceImpl(PublishRepository publishRepository) {
        this.publishRepository = publishRepository;
    }

    @Override
    public List<PublishDto> getAllPublishes() {

        return publishRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public PublishDto getPublishById(Long id) {

        Publish publish = publishRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Publication not found with id: " + id, HttpStatus.NOT_FOUND));

        return convertToDto(publish);
    }

    @Override
    public PublishDto createPublish(PublishDto publishDto) {

        Publish publish = new Publish();

        publish.setDatePublished(
                publishDto.getDatePublished());

        publish.setPlace(
                publishDto.getPlace());

        Publish savedPublish =
                publishRepository.save(publish);

        return convertToDto(savedPublish);
    }

    @Override
    public PublishDto updatePublish(
            Long id,
            PublishDto publishDto) {

        Publish publish = publishRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Publication not found with id: " + id, HttpStatus.NOT_FOUND));

        publish.setDatePublished(
                publishDto.getDatePublished());

        publish.setPlace(
                publishDto.getPlace());

        Publish updatedPublish =
                publishRepository.save(publish);

        return convertToDto(updatedPublish);
    }

    @Override
    public void deletePublish(Long id) {

        if (!publishRepository.existsById(id)) {
            throw new ApiException(
                    "Publication not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        publishRepository.deleteById(id);
    }

    private PublishDto convertToDto(Publish publish) {

        PublishDto dto = new PublishDto();

        dto.setId(publish.getId());
        dto.setDatePublished(publish.getDatePublished());
        dto.setPlace(publish.getPlace());

        return dto;
    }
}
