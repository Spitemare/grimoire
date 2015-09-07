package org.grimoire.model;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Role extends BaseEntity {

    @Basic(optional = false)
    private String role;

    public String getRole() {
        return role;
    }

    protected void setRole(String role) {
        this.role = role;
    }

    @Override
    protected Map<String, Object> toStringParts() {
        return Collections.singletonMap("role", role);
    }

}
