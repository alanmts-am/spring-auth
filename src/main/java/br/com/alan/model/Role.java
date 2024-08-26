package br.com.alan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "co_role")
    private Long id;

    @Column(name = "no_role")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String role) {
        this.name = role;
    }

    public enum Values {

        ADMIN(1L),
        MEMBER(2L);

        long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }

        public long getRoleId() {
            return roleId;
        }
    }

}
