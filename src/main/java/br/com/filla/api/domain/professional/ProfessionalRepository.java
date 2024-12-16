package br.com.filla.api.domain.professional;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

  Page<Professional> findAllByActiveTrue(Pageable pageable);
  
  Optional<Professional> findByIdAndActiveTrue(Long id);

  List<Professional> findByServiceProvidedAndActiveTrue(ServiceProvided serviceProvided);

}
