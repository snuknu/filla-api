package br.com.filla.filla_api.domain.professional;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

  Page<Professional> findAllByActiveTrue(Pageable pageable);

  List<Professional> findByServiceProvidedAndActiveTrue(ServiceProvided serviceProvided);

}
