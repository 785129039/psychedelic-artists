// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.nex.domain;

import com.nex.domain.Statistic;
import javax.persistence.Entity;
import javax.persistence.Table;

privileged aspect Statistic_Roo_Jpa_Entity {
    
    declare @type: Statistic: @Entity;
    
    declare @type: Statistic: @Table(name = "record_statistic");
    
}
