package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    public static final long MOCK_ID = 1L;
    @Mock
    VisitRepository repository;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    @DisplayName("Test Find All")
    void findAll() {
        //given
        Visit visit = new Visit();
        Set<Visit> visits = Set.of(visit);
        given(repository.findAll()).willReturn(visits);

        //when
        Set<Visit> result = service.findAll();

        //then
        then(repository).should().findAll();
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(result)
                .hasSize(1)
                .contains(visit);
    }

    @Test
    @DisplayName("Test Find By Id")
    void findById() {
        //given
        Visit visit = new Visit();
        given(repository.findById(anyLong()))
                .willReturn(Optional.of(visit));

        //when
        Visit result = service.findById(MOCK_ID);

        //then
        then(repository).should().findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(result)
                .isNotNull()
                .isEqualTo(visit);
    }

    @Test
    @DisplayName("Test Save")
    void save() {
        //given
        Visit visit = new Visit();
        given(repository.save(any(Visit.class))).willReturn(visit);

        //when
        Visit result = service.save(visit);

        //then
        then(repository).should().save(any(Visit.class));
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(result)
                .isNotNull()
                .isEqualTo(visit);
    }

    @Test
    @DisplayName("Test Delete By visit")
    void delete() {
        //given - none

        //when
        service.delete(new Visit());

        //then
        then(repository).should().delete(any(Visit.class));
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Test delete By Id")
    void deleteById() {
        //given - none

        //when
        service.deleteById(MOCK_ID);

        //then
        then(repository).should().deleteById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();
    }
}