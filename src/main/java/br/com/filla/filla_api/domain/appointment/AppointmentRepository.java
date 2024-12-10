package br.com.filla.filla_api.domain.appointment;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
  
  List<Appointment> findByEmployeeIdAndAppointmentDateBetweenAndReasonCancellationIsNull(Long employeeId, LocalDateTime startDate, LocalDateTime endDate);

}
