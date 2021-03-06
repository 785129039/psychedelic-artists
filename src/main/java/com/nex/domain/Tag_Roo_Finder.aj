// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.nex.domain;

import com.nex.domain.Tag;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Tag_Roo_Finder {
    
    public static TypedQuery<Tag> Tag.findTagsByName(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        EntityManager em = Tag.entityManager();
        TypedQuery<Tag> q = em.createQuery("SELECT o FROM Tag AS o WHERE o.name = :name", Tag.class);
        q.setParameter("name", name);
        return q;
    }
    
}
