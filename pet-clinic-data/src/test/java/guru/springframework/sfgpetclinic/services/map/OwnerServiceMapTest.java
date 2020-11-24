package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;
    PetTypeMapService petTypeMapService;
    PetServiceMap petServiceMap;

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(petTypeMapService, petServiceMap);
        ownerServiceMap.save(Owner.builder().id(1L).lastName("lastname").pets(Collections.emptySet()).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerServiceMap.findAll();

        assertEquals(1, ownerSet.size());
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(1L);
        assertEquals(1, owner.getId());
    }

    @Test
    void save() {
        Owner owner2 = Owner.builder().pets(Collections.emptySet()).build();
        Owner savedOwner = ownerServiceMap.save(owner2);

        assertEquals(savedOwner, owner2);
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(1L));
        assertEquals(Collections.emptySet(), ownerServiceMap.findAll());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(1L);
        assertEquals(Collections.emptySet(), ownerServiceMap.findAll());
    }

    @Test
    void findByLastName() {
        Owner savedOwner = ownerServiceMap.findByLastName("lastname");
        assertNotNull(savedOwner);
        assertEquals("lastname", savedOwner.getLastName());
    }
}