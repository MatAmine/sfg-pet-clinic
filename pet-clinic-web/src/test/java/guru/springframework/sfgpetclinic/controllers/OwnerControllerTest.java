package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @InjectMocks
    OwnerController ownerController;

    @Mock
    OwnerService ownerService;

    MockMvc mockMvc;

    Set<Owner> owners;

    @BeforeEach
    void setUp() {
        owners = Set.of(
                Owner.builder().id(1L).lastName("firstOwner").build(),
                Owner.builder().id(2L).lastName("secondOwner").build()
        );

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void listOwners() throws Exception {
        doReturn(owners).when(ownerService).findAll();
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)))
                .andExpect(model().attribute("owners", owners));
    }

    @Test
    void findOwners() throws Exception {
        doReturn(owners).when(ownerService).findAll();

        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)))
                .andExpect(model().attribute("owners", owners));
    }

    @Test
    void listOwnersIndex() throws Exception {
        doReturn(owners).when(ownerService).findAll();
        mockMvc.perform(get("/owners/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)))
                .andExpect(model().attribute("owners", owners));
    }

    @Test
    void listOwnersIndexDotHtml() throws Exception {
        doReturn(owners).when(ownerService).findAll();
        mockMvc.perform(get("/owners/index.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)))
                .andExpect(model().attribute("owners", owners));
    }

    @Test
    void displayOwner() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1l))));
    }
}