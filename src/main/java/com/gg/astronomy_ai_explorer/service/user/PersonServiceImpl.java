package com.gg.astronomy_ai_explorer.service.user;

import com.gg.astronomy_ai_explorer.dto.user.MyPersonDto;
import com.gg.astronomy_ai_explorer.dto.user.PersonDto;
import com.gg.astronomy_ai_explorer.dto.user.PersonUpdateDto;
import com.gg.astronomy_ai_explorer.entity.user.AppUser;
import com.gg.astronomy_ai_explorer.entity.user.Person;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.user.AppUserRepository;
import com.gg.astronomy_ai_explorer.repository.user.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final AppUserRepository appUserRepository;

    public PersonServiceImpl(
            PersonRepository personRepository,
            AppUserRepository appUserRepository) {

        this.personRepository = personRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public List<PersonDto> getAllPersons() {

        return personRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public PersonDto getPersonById(Long id) {

        Person person = personRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Person not found with id: " + id, HttpStatus.NOT_FOUND));

        return convertToDto(person);
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {

        /*
         * Person.id must match an existing AppUser.id
         * because person.id is a foreign key to app_user.id.
         */
        AppUser appUser = appUserRepository.findById(personDto.getAppUserId())
                .orElseThrow(() ->
                        new ApiException(
                                "App user not found with id: "
                                        + personDto.getAppUserId(), HttpStatus.NOT_FOUND));

        Person person = new Person();

        person.setAppUser(appUser);
        person.setFirstname(personDto.getFirstname());
        person.setLastname(personDto.getLastname());


        Person savedPerson = personRepository.save(person);

        return convertToDto(savedPerson);
    }

    @Override
    public PersonDto updatePerson(
            Long id,
            PersonUpdateDto personDto) {

        Person person = personRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Person not found with id: " + id, HttpStatus.NOT_FOUND));

        // authorization for profile manipulation
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN")
                );

        if (!isAdmin &&
                !person.getAppUser()
                        .getUsername()
                        .equals(username)) {

            throw new ApiException(
                    "You can only modify your own profile", HttpStatus.FORBIDDEN
            );
        }
            person.setFirstname(personDto.getFirstname());
            person.setLastname(personDto.getLastname());

            Person updatedPerson = personRepository.save(person);

            return convertToDto(updatedPerson);
    }

    @Override
    public void deletePerson(Long id) {

        if (!personRepository.existsById(id)) {
            throw new ApiException(
                    "Person not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        personRepository.deleteById(id);
    }

    // MY PROFILE

    @Override
    public PersonDto updateMyProfile(MyPersonDto request) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        AppUser appUser = appUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new ApiException(
                        "User not found",
                        HttpStatus.NOT_FOUND
                ));

        Person person = personRepository
                .findById(appUser.getId())
                .orElseGet(() -> {
                    Person newPerson = new Person();
                    newPerson.setId(appUser.getId());
                    newPerson.setAppUser(appUser);
                    return newPerson;
                });

        person.setFirstname(request.getFirstname());
        person.setLastname(request.getLastname());

        Person savedPerson = personRepository.save(person);

        return convertToDto(savedPerson);
    }

    @Override
    public PersonDto getMyProfile() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        AppUser appUser = appUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new ApiException(
                        "User not found",
                        HttpStatus.NOT_FOUND
                ));

        Person person = personRepository
                .findById(appUser.getId())
                .orElseThrow(() -> new ApiException(
                        "Profile not found",
                        HttpStatus.NOT_FOUND
                ));

        return convertToDto(person);
    }

    private PersonDto convertToDto(Person person) {

        PersonDto dto = new PersonDto();

        dto.setAppUserId(person.getId());
        dto.setFirstname(person.getFirstname());
        dto.setLastname(person.getLastname());

        return dto;
    }
}
