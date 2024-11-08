package org.project.loja_virtual.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ACCESS")
@SequenceGenerator(name = "SEQ_ACCESS", sequenceName = "SEQ_ACCESS", allocationSize = 1, initialValue = 1)
public class Access implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACCESS")
    private Long id;

    @Column(nullable = false)
    private String description; /*Acesso ex: ROLE_ADMIN, ROLE_SECRETARIO*/

    @JsonIgnore
    @Override
    public String getAuthority() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Access access = (Access) object;
        return Objects.equals(id, access.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
