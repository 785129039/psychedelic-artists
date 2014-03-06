// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.nex.domain.localized;

import com.nex.domain.localized.TypeText;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect TypeText_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "puTest")
    transient EntityManager TypeText.entityManager;
    
    public static final EntityManager TypeText.entityManager() {
        EntityManager em = new TypeText().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TypeText.countTypeTexts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM TypeText o", Long.class).getSingleResult();
    }
    
    public static List<TypeText> TypeText.findAllTypeTexts() {
        return entityManager().createQuery("SELECT o FROM TypeText o", TypeText.class).getResultList();
    }
    
    public static TypeText TypeText.findTypeText(Long id) {
        if (id == null) return null;
        return entityManager().find(TypeText.class, id);
    }
    
    public static List<TypeText> TypeText.findTypeTextEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM TypeText o", TypeText.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void TypeText.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void TypeText.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TypeText attached = TypeText.findTypeText(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void TypeText.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void TypeText.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public TypeText TypeText.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TypeText merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
