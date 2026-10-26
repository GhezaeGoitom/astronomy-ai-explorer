package com.gg.astronomy_ai_explorer.service.user;

import com.gg.astronomy_ai_explorer.dto.user.MyPersonDto;
import com.gg.astronomy_ai_explorer.dto.user.PersonDto;
import com.gg.astronomy_ai_explorer.dto.user.PersonUpdateDto;

import java.util.List;

public interface PersonService {

    List<PersonDto> getAllPersons();

    PersonDto getPersonById(Long id);

    PersonDto createPerson(PersonDto personDto);

    PersonDto updatePerson(Long id, PersonUpdateDto personDto);

    void deletePerson(Long id);

    PersonDto getMyProfile();

    PersonDto updateMyProfile(MyPersonDto request);
}
