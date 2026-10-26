package com.gg.astronomy_ai_explorer.service.user;

import com.gg.astronomy_ai_explorer.dto.user.DiscoverDto;
import com.gg.astronomy_ai_explorer.entity.user.Discover;
import com.gg.astronomy_ai_explorer.entity.user.Person;
import com.gg.astronomy_ai_explorer.entity.user.Publish;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.user.DiscoverRepository;
import com.gg.astronomy_ai_explorer.repository.user.PersonRepository;
import com.gg.astronomy_ai_explorer.repository.user.PublishRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscoverServiceImpl implements DiscoverService {

    private final DiscoverRepository discoverRepository;
    private final PublishRepository publishRepository;
    private final PersonRepository personRepository;

    public DiscoverServiceImpl(
            DiscoverRepository discoverRepository,
            PublishRepository publishRepository,
            PersonRepository personRepository) {

        this.discoverRepository = discoverRepository;
        this.publishRepository = publishRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<DiscoverDto> getAllDiscoveries() {

        return discoverRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public DiscoverDto getDiscoveryById(Long id) {

        Discover discover = discoverRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Discovery not found with id: " + id, HttpStatus.NOT_FOUND));

        return convertToDto(discover);
    }

    @Override
    public DiscoverDto createDiscovery(
            DiscoverDto discoverDto) {

        Publish publish = null;
        Person person = null;

        if (discoverDto.getPublishId() != null) {

            publish = publishRepository.findById(
                            discoverDto.getPublishId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Publication not found with id: "
                                            + discoverDto.getPublishId(), HttpStatus.NOT_FOUND));
        }

        if (discoverDto.getDiscoveredById() != null) {

            person = personRepository.findById(
                            discoverDto.getDiscoveredById())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Person not found with id: "
                                            + discoverDto.getDiscoveredById(), HttpStatus.NOT_FOUND));
        }

        Discover discover = new Discover();

        discover.setDescription(
                discoverDto.getDescription());

        discover.setCoordinate(
                discoverDto.getCoordinate());

        discover.setPublish(publish);
        discover.setDiscoveredBy(person);

        Discover savedDiscovery =
                discoverRepository.save(discover);

        return convertToDto(savedDiscovery);
    }

    @Override
    public DiscoverDto updateDiscovery(
            Long id,
            DiscoverDto discoverDto) {

        Discover discover = discoverRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Discovery not found with id: " + id, HttpStatus.NOT_FOUND));

        Publish publish = null;
        Person person = null;

        if (discoverDto.getPublishId() != null) {

            publish = publishRepository.findById(
                            discoverDto.getPublishId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Publication not found with id: "
                                            + discoverDto.getPublishId(), HttpStatus.NOT_FOUND));
        }

        if (discoverDto.getDiscoveredById() != null) {

            person = personRepository.findById(
                            discoverDto.getDiscoveredById())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Person not found with id: "
                                            + discoverDto.getDiscoveredById(), HttpStatus.NOT_FOUND));
        }

        discover.setDateDiscovered(
                discoverDto.getDateDiscovered());

        discover.setDescription(
                discoverDto.getDescription());

        discover.setCoordinate(
                discoverDto.getCoordinate());

        discover.setPublish(publish);
        discover.setDiscoveredBy(person);

        Discover updatedDiscovery =
                discoverRepository.save(discover);

        return convertToDto(updatedDiscovery);
    }

    @Override
    public void deleteDiscovery(Long id) {

        if (!discoverRepository.existsById(id)) {

            throw new ApiException(
                    "Discovery not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        discoverRepository.deleteById(id);
    }

    private DiscoverDto convertToDto(
            Discover discover) {

        DiscoverDto dto = new DiscoverDto();

        dto.setId(discover.getId());

        dto.setDateDiscovered(
                discover.getDateDiscovered());

        dto.setDescription(
                discover.getDescription());

        dto.setCoordinate(
                discover.getCoordinate());

        if (discover.getPublish() != null) {
            dto.setPublishId(
                    discover.getPublish().getId());
        }

        if (discover.getDiscoveredBy() != null) {
            dto.setDiscoveredById(
                    discover.getDiscoveredBy().getId());
        }

        return dto;
    }
}
