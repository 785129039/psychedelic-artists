package com.nex.domain;

import java.util.Collections;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

import com.nex.annotation.Modifiable;
import com.nex.utils.StringUtils;

@RooJavaBean
@RooJpaActiveRecord(finders={"findTagsByName"}, persistenceUnit = "puPsyartists", table = "tag", versionField = "")
@Modifiable
@NamedQueries(value={@NamedQuery(name="likeTagsByName", query="select t from Tag t where lower(name) like lower(:text)")})
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min=1, max=50)
	private String name;
	
	
	public static List<Tag> likeTags(String searchString) {
		if(StringUtils.isEmpty(searchString)) return Collections.emptyList();
		TypedQuery<Tag> query = entityManager().createNamedQuery("likeTagsByName", Tag.class);
		query.setMaxResults(20);
		query.setParameter("text", "%"+searchString+"%");
		return query.getResultList();
	}
	
}
