package br.com.filla.api.domain.professional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

  Page<Professional> findAllByActiveTrue(Pageable pageable);
  
  Optional<Professional> findByIdAndActiveTrue(Long id);

  List<Professional> findByServiceProvidedAndActiveTrue(ServiceProvided serviceProvided);
  
  @Query("""
      select p from Professional p
      where 1=1
      and p.active = true
      and p.serviceProvided = :serviceProvided
      and p.id not in(
          select s.professional.id from Scheduling s
          where 1=1
          and s.serviceDate = :serviceDate
          and s.reasonCancellation is null
      )
      order by random()
      limit 1
  """)
  Professional findFreeRandomProfessionalOnDate(ServiceProvided serviceProvided, LocalDateTime serviceDate);

}
