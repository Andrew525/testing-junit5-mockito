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
        Visit visit = new Visit();
        Set<Visit> visits = Set.of(visit);
        when(repository.findAll()).thenReturn(visits);

        Set<Visit> result = service.findAll();

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Test Find By Id")
    void findById() {
        Visit visit = new Visit();
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(visit));

        Visit result = service.findById(MOCK_ID);

        verify(repository).findById(anyLong());
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Test Save")
    void save() {
        Visit visit = new Visit();
        when(repository.save(any(Visit.class))).thenReturn(visit);

        Visit result = service.save(visit);

        verify(repository).save(any(Visit.class));
        verifyNoMoreInteractions(repository);
        assertThat(result)
                .isNotNull()
                .isEqualTo(visit);
    }

    @Test
    @DisplayName("Test Delete By visit")
    void delete() {
        service.delete(new Visit());
        verify(repository).delete(any(Visit.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Test delete By Id")
    void deleteById() {
        service.deleteById(MOCK_ID);
        verify(repository).deleteById(anyLong());
        verifyNoMoreInteractions(repository);
    }
}