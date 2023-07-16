package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Spy
    PetMapService petService;
//    @Mock
//    PetService petService;

    @InjectMocks
    VisitController controller;

    @Test
    void loadPetWithVisit() {
        //given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(12L);
        given(petService.findById(anyLong())).willReturn(pet);

        //when
        Visit result = controller.loadPetWithVisit(12L, model);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getPet()).isNotNull();
    }

    @Test
    void loadPetWithVisit_withSpy() {
        //given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(22L);
        Pet pet3 = new Pet(33L);
        petService.save(pet);
        petService.save(pet3);
        given(petService.findById(anyLong())).willCallRealMethod();

        //when
        Visit result = controller.loadPetWithVisit(22L, model);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getPet()).isNotNull();
        assertThat(result.getPet().getId()).isEqualTo(22L);
        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    void loadPetWithVisit_withSubbing() {
        //given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(22L);
        Pet pet3 = new Pet(33L);
        petService.save(pet);
        petService.save(pet3);
        given(petService.findById(anyLong())).willReturn(pet3); // override - stubbing

        //when
        Visit result = controller.loadPetWithVisit(22L, model);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getPet()).isNotNull();
        assertThat(result.getPet().getId()).isEqualTo(33L);
        verify(petService, times(1)).findById(anyLong());
    }
}