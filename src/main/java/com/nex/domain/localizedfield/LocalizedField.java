package com.nex.domain.localizedfield;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Rozhrani ktere musi implementovat entity ktere nesou nejake lokalizovane texty pro
 * hlavni entitu nesouci data.
 * @author MacikJ
 */
public interface LocalizedField {

  public String getLanguageCode();
  public void setLanguageCode(String langCode);

  public Object getLocalizable();
    
  
  
}
