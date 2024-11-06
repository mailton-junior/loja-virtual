package org.project.loja_virtual.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USERS")
@SequenceGenerator(name = "SEQ_USERS", sequenceName = "SEQ_USERS", allocationSize = 1, initialValue = 1)
public class Users implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USERS")
    private Long id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date actualDatePassword;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "PERSON_ID", nullable = false,
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FK_USERS_PERSON"))
    private Person person;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ACCESS", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "access_id"},
    name = "UK_USER_ACCESS"),
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", table = "USERS", unique = false,
    foreignKey = @ForeignKey(name = "FK_USER_ACCESS_USER", value = ConstraintMode.CONSTRAINT)),
    inverseJoinColumns = @JoinColumn(name = "access_id", referencedColumnName = "id", table = "ACCESS", unique = false,
    foreignKey = @ForeignKey(name = "FK_USER_ACCESS_ACCESS", value = ConstraintMode.CONSTRAINT)))
    private List<Access> accesses;


    /*Autoridades = Acessos, ou seja ROLE_ADMIN, ROLE_SECRETARIO, ROLE_FINANCEIRO*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.accesses;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
