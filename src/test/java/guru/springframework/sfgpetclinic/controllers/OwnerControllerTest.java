package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    public static final long MOCK_ID = 1L;
    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    public static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    @Mock
    OwnerService service;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @Test
    void processFindFormWildcardString() {
        //given
        Owner owner = new Owner(MOCK_ID, "Jim", "Russell");
        ArrayList<Owner> ownerList = new ArrayList<>();
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(service.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

        //when
        String result = controller.processFindForm(owner, bindingResult, null);

        //then
        assertThat(captor.getValue()).isEqualToIgnoringCase("%Russell%");
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        //given
        Owner owner = new Owner(MOCK_ID, "Jim", "Russell");
        ArrayList<Owner> ownerList = new ArrayList<>();
        given(service.findAllByLastNameLike(stringCaptor.capture())).willReturn(ownerList);

        //when
        String result = controller.processFindForm(owner, bindingResult, null);

        //then
        assertThat(stringCaptor.getValue()).isEqualToIgnoringCase("%Russell%");
    }

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