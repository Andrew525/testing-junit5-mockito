package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    public static final long MOCK_ID = 1L;
    @Mock
    SpecialtyRepository repository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void testDeleteSpecialty() {
        Speciality speciality = new Speciality();
        
        service.delete(speciality);
        
        verify(repository).delete(any(Speciality.class));
    }

    @Test
    void findById_TddTest() {
        //given
        Speciality speciality = new Speciality();
        given(repository.findById(MOCK_ID))
                .willReturn(Optional.of(speciality));

        //when
        Speciality result = service.findById(MOCK_ID);

        //then
        assertThat(result)
                .isNotNull()
                .isEqualTo(speciality);
        then(repository).should(times(1)).findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testFindById() {
        Speciality speciality = new Speciality();

        when(repository.findById(MOCK_ID))
                .thenReturn(Optional.of(speciality));

        Speciality result = service.findById(MOCK_ID);

        assertThat(result).isNotNull();

        verify(repository).findById(MOCK_ID);
    }

    @Test
    void testDeleteById() {
        service.deleteById(MOCK_ID);
        service.deleteById(MOCK_ID);

        verify(repository, times(2)).deleteById(MOCK_ID);
    }

    @Test
    void testDeleteBYId_atLeastOnce() {
        service.deleteById(MOCK_ID);
        service.deleteById(MOCK_ID);

        verify(repository, atLeastOnce()).deleteById(MOCK_ID);
    }

    @Test
    void testDeleteById_atMost() {
        service.deleteById(MOCK_ID);
        service.deleteById(MOCK_ID);

        verify(repository, atMost(3)).deleteById(MOCK_ID);
    }

    @Test
    void testDeleteById_neverInvoke() {
        service.deleteById(MOCK_ID);
        verify(repository).deleteById(MOCK_ID);

        verify(repository, never()).deleteById(3L);
    }

    @Test
    void testDelete() {
        service.delete(new Speciality());
    }
}