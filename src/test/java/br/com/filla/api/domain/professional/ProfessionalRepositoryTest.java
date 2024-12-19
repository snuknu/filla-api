package br.com.filla.api.domain.professional;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import br.com.filla.api.domain.address.AddressDtoCreate;
import br.com.filla.api.domain.customer.Customer;
import br.com.filla.api.domain.customer.CustomerDtoCreate;
import br.com.filla.api.domain.scheduling.Scheduling;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class ProfessionalRepositoryTest {

  @Autowired
  private ProfessionalRepository professionalRepository;

  @Autowired
  private TestEntityManager em;

  @Test()
  @DisplayName("Deveria devolver null quando o médico selecionado não está disponível na data")
  void findFreeRandomProfessionalOnDateT1() {

    // given
    int hora = 10, minuto = 0;
    var proximaSegundaAs10 = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .atTime(hora, minuto);
    var professional = createProfessional("Um", "um@mail.com", "00910001000", "100100", ServiceProvided.PHYSIOTHERAPY);
    var customer = createCustomer("Dois", "dois@email.com", "00910001000");
    cadastrarScheduling(customer, professional, proximaSegundaAs10);

    // when
    var professionalLivre = professionalRepository.findFreeRandomProfessionalOnDate(
        ServiceProvided.PHYSIOTHERAPY, proximaSegundaAs10);

    // assert
    assertThat(professionalLivre).isNull();
  }

  @Test()
  @DisplayName("Deveria devolver o médico quando ele estiver disponível na data")
  void findFreeRandomProfessionalOnDateT2() {

    // given
    int hora = 10, minuto = 0;
    var proximaSegundaAs10 = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .atTime(hora, minuto);
    var medico = createProfessional("Um", "um@mail.com", "00910001000", "100100", ServiceProvided.PHYSIOTHERAPY);

    // when
    var professionalLivre = professionalRepository.findFreeRandomProfessionalOnDate(
        ServiceProvided.PHYSIOTHERAPY, proximaSegundaAs10);

    // assert
    assertThat(professionalLivre).isEqualTo(medico);
  }

  private void cadastrarScheduling(Customer customer, Professional professional,
      LocalDateTime data) {
    var scheduling = new Scheduling(null, customer, professional, data, null);
    em.persist(scheduling);
  }

  private Professional createProfessional(String name, String email, String phone,
      String enrollment, ServiceProvided serviceProvided) {
    var professional =
        new Professional(professionalData(name, email, phone, enrollment, serviceProvided));
    em.persist(professional);
    return professional;
  }

  private Customer createCustomer(String nome, String email, String phone) {
    var customer = new Customer(customerData(nome, email, phone));
    em.persist(customer);
    return customer;
  }

  private ProfessionalDtoCreate professionalData(String name, String email, String phone,
      String enrollment, ServiceProvided serviceProvided) {
    return new ProfessionalDtoCreate(
        name,
        email,
        "71910001000",
        enrollment,
        serviceProvided,
        addressData());
  }

  private CustomerDtoCreate customerData(String nome, String email, String phone) {
    return new CustomerDtoCreate(
        nome,
        email,
        "71910001000",
        addressData());
  }

  private AddressDtoCreate addressData() {
    return new AddressDtoCreate(
        "rua xpto",
        "bairro",
        "00000000",
        "Brasilia",
        "DF",
        null,
        null);
  }
}
