// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.nex.domain.localized;

import com.nex.domain.localized.Type;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Type_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "puTest")
    transient EntityManager Type.entityManager;
    
    public static final EntityManager Type.entityManager() {
        EntityManager em = new Type().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Type.countTypes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Type o", Long.class).getSingleResult();
    }
    
    public static List<Type> Type.findAllTypes() {
        return entityManager().createQuery("SELECT o FROM Type o", Type.class).getResultList();
    }
    
    public static Type Type.findType(Long id) {
        if (id == null) return null;
        return entityManager().find(Type.class, id);
    }
    
    public static List<Type> Type.findTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Type o", Type.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Type.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Type.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Type attached = Type.findType(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Type.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Type.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Type Type.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Type merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
