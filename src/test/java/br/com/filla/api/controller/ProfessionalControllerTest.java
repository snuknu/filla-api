package br.com.filla.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import br.com.filla.api.domain.address.AddressDtoCreate;
import br.com.filla.api.domain.address.AddressDtoRead;
import br.com.filla.api.domain.professional.Professional;
import br.com.filla.api.domain.professional.ProfessionalDtoCreate;
import br.com.filla.api.domain.professional.ProfessionalDtoRead;
import br.com.filla.api.domain.professional.ProfessionalRepository;
import br.com.filla.api.domain.professional.ServiceProvided;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ProfessionalControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<ProfessionalDtoCreate> professionalDtoCreateJson;

  @Autowired
  private JacksonTester<ProfessionalDtoRead> professionalDtoReadJson;


  @SuppressWarnings("removal")
  @MockBean
  private ProfessionalRepository repository;

  @Test
  @DisplayName("Deveria devolver código http 400 quando informações estão inválidas")
  @WithMockUser
  void testCreateT1() throws Exception {

    // when
    var response = mvc
        .perform(post("/professional"))
        .andReturn().getResponse();

    // assert
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 201 quando informações estão válidas")
  @WithMockUser
  void testCreateT2() throws Exception {

    // given

    var professionalDtoCreate = new ProfessionalDtoCreate(
        "fulano",
        "fulano@filla.com",
        "00910001000",
        "100100",
        ServiceProvided.PHYSIOTHERAPY,
        new AddressDtoCreate(
            "Rua Direta",
            "Centro",
            "10100100",
            "Salvador",
            "UF",
            "SN",
            "Térreo"));

    var professional = new Professional(professionalDtoCreate);

    // when

    when(repository.save(any())).thenReturn(professional);

    var response = mvc
        .perform(post("/professional")
            .contentType(MediaType.APPLICATION_JSON)
            .content(professionalDtoCreateJson.write(professionalDtoCreate).getJson()))
        .andReturn().getResponse();


    // assert

    var professionalDtoRead = new ProfessionalDtoRead(
        professional.getId(),
        professional.getName(),
        professional.getEmail(),
        professional.getPhone(),
        professional.getEnrollment(),
        professional.getServiceProvided(),
        new AddressDtoRead(professional.getAddress()),
        professional.getActive());

    var jsonEsperado = professionalDtoReadJson.write(professionalDtoRead).getJson();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

  }



}
