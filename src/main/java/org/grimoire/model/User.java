package org.grimoire.model;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static org.grimoire.util.Maps.entry;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User extends BaseEntity {

    @Basic(optional = false)
    private String username;

    @Basic(optional = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "USER_ID") , inverseJoinColumns = @JoinColumn(name = "ROLE_ID") )
    private List<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    protected Map<String, Object> toStringParts() {
        //@formatter:off
        return Collections.unmodifiableMap(Stream.of(
                entry("username", username),
                entry("roles", roles.stream().map(Role::getRole).collect(joining(", ", "[", "]")))
        ).collect(toMap(Map.Entry::getKey, Map.Entry::getValue)));
        //@formatter:on
    }

}
