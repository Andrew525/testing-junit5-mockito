package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    public static final long MOCK_ID = 1L;
    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    public static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    @Mock
    OwnerService service;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @InjectMocks
    OwnerController controller;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @BeforeEach
    void setUp() {
        given(service.findAllByLastNameLike(stringCaptor.capture()))
                .willAnswer(inv -> {
                    List<Owner> ownerList = new ArrayList<>();
                    String name = inv.getArgument(0);
                    if (name.equals("%Wood%")) {
                        ownerList.add(new Owner(MOCK_ID, "Jim", "Wood"));
                        return ownerList;
                    } else if (name.equals("%NotFound%")){
                        return ownerList;
                    } else if (name.equals("%List%")) {
                        ownerList.add(new Owner(MOCK_ID, "Jim", "Wood"));
                        ownerList.add(new Owner(MOCK_ID + 1, "Jim2", "Wood2"));
                        return ownerList;
                    }
                    throw new RuntimeException("Invalid Argument");
                });
    }

    @AfterEach
    void cleanUp() {

    }

    @Test
    void processFindFormWildcardString_notFound() {
        //given
        Owner owner = new Owner(MOCK_ID, "Joe", "NotFound");

        //when
        String result = controller.processFindForm(owner, bindingResult, null);

        //then
        assertThat(stringCaptor.getValue()).isEqualToIgnoringCase("%NotFound%");
        assertThat(result).isEqualToIgnoringCase("owners/findOwners");
        then(model).shouldHaveZeroInteractions();
    }

    @Test
    void processFindFormWildcardString_single() {
        //given
        Owner owner = new Owner(MOCK_ID, "Jim", "Wood");

        //when
        String result = controller.processFindForm(owner, bindingResult, null);

        //then
        assertThat(stringCaptor.getValue()).isEqualToIgnoringCase("%Wood%");
        assertThat(result).isEqualToIgnoringCase("redirect:/owners/1");
        verifyZeroInteractions(model);
    }

    @Test
    void processFindFormWildcardString_list() {
        //given
        Owner owner = new Owner(MOCK_ID, "Joe", "List");
        InOrder inOrder = inOrder(service, model);

        //when
        String result = controller.processFindForm(owner, bindingResult, model);

        //then
        assertThat(stringCaptor.getValue()).isEqualToIgnoringCase("%List%");
        assertThat(result).isEqualToIgnoringCase("owners/ownersList");
        inOrder.verify(service).findAllByLastNameLike(anyString());
        inOrder.verify(model, times(1)).addAttribute(anyString(), anyList());
    }


    @Disabled
    @Test
    void processCreationForm_hasError() {
        //given
        Owner owner = new Owner(MOCK_ID, "Jim", "Bob");
        given(bindingResult.hasErrors()).willReturn(true);

        //when
        String viewName = controller.processCreationForm(owner, bindingResult);

        //then
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }

    @Disabled
    @Test
    void processCreationForm_noError() {
        //given
        Owner owner = new Owner(5L, "Jim", "Bob");
        given(bindingResult.hasErrors()).willReturn(false);
        given(service.save(any(Owner.class))).willReturn(owner);

        //when
        String viewName = controller.processCreationForm(owner, bindingResult);

        //then
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }
}