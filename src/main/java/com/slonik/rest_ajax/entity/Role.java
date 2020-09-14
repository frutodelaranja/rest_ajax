package com.slonik.rest_ajax.entity;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        if (id != null){
            this.id = id;
            this.name = name;
        }else {
            if (name.equals("ROLE_ADMIN")) {
                setId(1L);
                setName("ROLE_ADMIN");
            } else {
                setId(2L);
                setName("ROLE_USER");
            }
        }
    }

    public Role(String name) {
        if (name.equals("ROLE_ADMIN")) {
            setId(1L);
            setName("ROLE_ADMIN");
        } else {
            setId(2L);
            setName("ROLE_USER");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
