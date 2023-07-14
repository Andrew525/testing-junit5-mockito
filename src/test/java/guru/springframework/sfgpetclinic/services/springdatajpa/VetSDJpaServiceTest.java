package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {

    public static final long MOCK_ID = 1L;
    @Mock
    VetRepository repository;


    @InjectMocks
    VetSDJpaService service;

    @Test
    void testDeleteById() {
        service.deleteById(MOCK_ID);

        verify(repository).deleteById(MOCK_ID);
    }
}