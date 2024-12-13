package br.com.filla.filla_api.domain.scheduling;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

  List<Scheduling> findByProfessionalIdAndSchedulingDateBetweenAndReasonCancellationIsNull(
      Long professionalId, LocalDateTime startDate, LocalDateTime endDate);
  
  boolean existsByCustomerIdAndSchedulingDateBetween(Long professionalId, LocalDateTime firstTime, LocalDateTime lastTime);

  boolean existsByProfessionalIdAndServiceDate(Long professionalId, LocalDateTime serviceDate);
}
