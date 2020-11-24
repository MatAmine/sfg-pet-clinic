package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerService;

    private static final String LASTNAME= "lastname";
    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(LASTNAME).build();
    }

    @Test
    void findByLastName() {
        doReturn(Optional.of(returnOwner)).when(ownerRepository).findByLastName(LASTNAME);

        assertEquals(ownerService.findByLastName(LASTNAME), returnOwner);
        verify(ownerRepository).findByLastName(LASTNAME);
    }

    @Test
    void findAll() {
        Owner returnOwner2 = Owner.builder().id(2L).lastName("lastname2").build();
        Set<Owner> owners = Set.of(returnOwner, returnOwner2);

        doReturn(owners).when(ownerRepository).findAll();

        assertEquals(owners, ownerService.findAll());
        verify(ownerRepository).findAll();

    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
        ownerService.delete(returnOwner);
        verify(ownerRepository, times(1)).delete(returnOwner);
    }

    @Test
    void deleteById() {
    }
}