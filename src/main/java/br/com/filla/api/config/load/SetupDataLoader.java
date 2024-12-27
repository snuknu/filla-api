package br.com.filla.api.config.load;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import br.com.filla.api.domain.authorization.role.Role;
import br.com.filla.api.domain.authorization.role.RoleEnum;
import br.com.filla.api.domain.authorization.role.RoleRepository;
import jakarta.transaction.Transactional;

@Component
public class SetupDataLoader implements
    ApplicationListener<ContextRefreshedEvent> {

  boolean alreadySetup = false;

  @Autowired
  private RoleRepository roleRepository;


  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {

    if (alreadySetup)
      return;

    List<Role> roles = new ArrayList<Role>();

    Arrays.asList(RoleEnum.values()).forEach(r -> {
      if (!roleRepository.existsByName(r.name()))
        roles.add(new Role(null, r.name(), null));
    });

    roleRepository.saveAll(roles);
    alreadySetup = true;
  }

}
