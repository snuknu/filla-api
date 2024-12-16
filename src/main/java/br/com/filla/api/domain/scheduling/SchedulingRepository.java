package br.com.filla.api.domain.scheduling;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

  boolean existsByCustomerIdAndServiceDateBetween(Long professionalId, LocalDateTime firstTime,
      LocalDateTime lastTime);

  boolean existsByProfessionalIdAndServiceDate(Long professionalId, LocalDateTime serviceDate);

}
