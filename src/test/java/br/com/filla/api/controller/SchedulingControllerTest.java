package br.com.filla.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import br.com.filla.api.domain.address.Address;
import br.com.filla.api.domain.customer.Customer;
import br.com.filla.api.domain.professional.Professional;
import br.com.filla.api.domain.professional.ServiceProvided;
import br.com.filla.api.domain.scheduling.Scheduling;
import br.com.filla.api.domain.scheduling.SchedulingDtoCreate;
import br.com.filla.api.domain.scheduling.SchedulingDtoRead;
import br.com.filla.api.domain.scheduling.SchedulingService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class SchedulingControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<SchedulingDtoCreate> schedulingDtoCreateJson;

  @Autowired
  private JacksonTester<SchedulingDtoRead> schedulingDtoReadJson;

  @SuppressWarnings("removal")
  @MockBean
  private SchedulingService schedulingService;

  @Test
  @DisplayName("Deveria devolver código http 400 quando os dados estão inválidos")
  @WithMockUser
  void testCreateT1() throws Exception {

    MockHttpServletResponse mockResponse = mvc.perform(post("/scheduling")).andReturn().getResponse();

    assertThat(mockResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  @DisplayName("Deveria devolver código http 200 quando os dados estão válidos.")
  @WithMockUser
  void testCreateT2() throws Exception {

    // given
    int hora = 10, minuto = 0;
    var proximaSegundaAs10 = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .atTime(hora, minuto);
    var customer = new Customer(1L, null, null, null, new Address(), null);
    var professional = new Professional(1L, null, null, null, null, ServiceProvided.PHYSIOTHERAPY, new Address(), null);
    var expectedScheduling = new Scheduling(null, customer, professional, proximaSegundaAs10, null);

    when(schedulingService.schedule(any())).thenReturn(expectedScheduling);
  
    
    // when
    MockHttpServletResponse mockResponse = mvc
        .perform(
            post("/scheduling")
                .contentType(MediaType.APPLICATION_JSON)
                .content(schedulingDtoCreateJson.write(
                    new SchedulingDtoCreate(
                        customer.getId(),
                        professional.getId(),
                        ServiceProvided.PHYSIOTHERAPY,
                        proximaSegundaAs10))
                    .getJson()))
        .andReturn().getResponse();

    // assert
    var expectedJson = schedulingDtoReadJson.write(new SchedulingDtoRead(expectedScheduling)).getJson();
    assertThat(mockResponse.getContentAsString()).isEqualTo(expectedJson);
    
  }
}
