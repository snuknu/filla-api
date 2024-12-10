package br.com.filla.filla_api.domain.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Account")
@Table(name = "account")
public class Account implements UserDetails {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String password;

  public Account(AccountDtoUpdate dto) {
    this.username = dto.getUsername();
    this.password = dto.getPassword();
  }

  public void update(AccountDtoUpdate dto) {
    Optional.ofNullable(dto.getPassword()).ifPresent(value -> this.password = value);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    return authorities;
  }

}