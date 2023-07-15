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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

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


    @Test
    void testDoThrow() {
        //mockito throw exception
        doThrow(new RuntimeException("Boom")).when(repository).delete(any(Speciality.class));

        //junit catching
        assertThrows(RuntimeException.class, () -> repository.delete(new Speciality()));

        //mockito verifying invocation
        verify(repository).delete(any(Speciality.class));
    }

    @Test
    void testFindByIdThrows() {
        //given
        given(repository.findById(MOCK_ID)).willThrow(new RuntimeException("boom"));

        //when
        // mockito catching
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> repository.findById(MOCK_ID));

        //then
        then(repository).should().findById(anyLong());
    }

    @Test
    void testDeleteBdd() {
        //given
        willThrow(new RuntimeException("boom")).given(repository).delete(any(Speciality.class));

        //when
        // mockito catching
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> repository.delete(new Speciality()));

        //then
        then(repository).should().delete(any(Speciality.class));
    }


    @Test
    void testSaveLambda_match() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription(MATCH_ME);

        Speciality saved = new Speciality();
        saved.setId(MOCK_ID);

        // not valid logic but works :)
        given(repository.save(argThat(arg -> arg.getDescription().equals(MATCH_ME))))
                .willReturn(saved);

        //when
        Speciality result = service.save(speciality);

        //then
        assertThat(result.getId()).isEqualTo(MOCK_ID);
    }

    @Test
    void testSaveLambda_NoMatch() {
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription("Not a match");

        Speciality saved = new Speciality();
        saved.setId(MOCK_ID);

//        version 1
//        lenient().when(repository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME))))
//                .thenReturn(saved);

//        version 2
        given(repository.save(any(Speciality.class)))
                .willAnswer(inv -> ((Speciality) inv.getArgument(0)).getDescription().equals(MATCH_ME) ? saved : null);

        //when
        Speciality result = service.save(speciality);

        //then
        assertThat(result).isNull();

//        then(repository).should().save(argThat(argument -> !argument.getDescription().equals(MATCH_ME)));
    }
}