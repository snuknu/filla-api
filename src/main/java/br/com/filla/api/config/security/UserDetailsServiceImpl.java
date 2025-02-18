package br.com.filla.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.filla.api.domain.authorization.account.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private AccountRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByUsername(username);
  }

}
