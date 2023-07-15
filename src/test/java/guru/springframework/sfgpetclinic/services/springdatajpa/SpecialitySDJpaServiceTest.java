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
        //given
        Speciality speciality = new Speciality();

        //when
        service.delete(speciality);

        //then
        then(repository).should().delete(any(Speciality.class));
    }

    @Test
    void testFindById() {
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

//    @Test //-------------- Example of simple approach
//    void testFindById() {
//        Speciality speciality = new Speciality();
//
//        when(repository.findById(MOCK_ID))
//                .thenReturn(Optional.of(speciality));
//
//        Speciality result = service.findById(MOCK_ID);
//
//        assertThat(result).isNotNull();
//
//        verify(repository).findById(MOCK_ID);
//    }

    @Test
    void testDeleteById() {
        //given - none

        //when
        service.deleteById(MOCK_ID);
        service.deleteById(MOCK_ID);

        //then
        then(repository).should(times(2)).deleteById(anyLong());
    }

    @Test
    void testDeleteBYId_atLeastOnce() {
        //given - none

        //when
        service.deleteById(MOCK_ID);
        service.deleteById(MOCK_ID);

        //then
        then(repository).should(atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void testDeleteById_atMost() {
        //given - none

        //when
        service.deleteById(MOCK_ID);
        service.deleteById(MOCK_ID);

        //then
        then(repository).should(atMost(3)).deleteById(anyLong());
    }

    @Test
    void testDeleteById_neverInvoke() {
        //given - none

        //when
        service.deleteById(MOCK_ID);

        //then
        then(repository).should(never()).deleteById(MOCK_ID + 1L);
        then(repository).should().deleteById(anyLong());
    }

    @Test
    void testDelete() {
        //given - none

        //when
        service.delete(new Speciality());

        //then
        then(repository).should().delete(any(Speciality.class));
        then(repository).shouldHaveNoMoreInteractions();
    }
}