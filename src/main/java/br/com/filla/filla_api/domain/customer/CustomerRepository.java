package br.com.filla.filla_api.domain.customer;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.filla.filla_api.domain.professional.Professional;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

  Page<Customer> findAllByActiveTrue(Pageable pageable);
  
  Optional<Professional> findByIdAndActiveTrue(Long id);
  
}
