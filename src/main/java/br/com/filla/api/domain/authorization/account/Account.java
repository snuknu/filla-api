package br.com.filla.api.domain.authorization.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import br.com.filla.api.domain.authorization.role.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

  private Boolean active;

  @ManyToMany
  @JoinTable(
      name = "account_role",
      joinColumns = @JoinColumn(
          name = "account_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;

  public Account(AccountDtoCreate dto) {
    this.username = dto.getUsername();
    this.password = dto.getPassword();
    this.active = Boolean.TRUE;
  }

  public void update(AccountDtoUpdate dto) {
    this.id = dto.getId();
    Optional.ofNullable(dto.getPassword()).ifPresent(value -> this.password = value);
    Optional.ofNullable(dto.getUsername()).ifPresent(value -> this.password = value);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    this.roles.forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r)));
    return authorities;
  }

  public void disable() {
    this.active = Boolean.FALSE;

  }

}
