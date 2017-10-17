package org.andnyb.jira.plugin.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValidationError
{
   @XmlElement
   private String field;

   @XmlElement
   private String error;

   public ValidationError(String field, String error) {
      this.field = field;
      this.error = error;
   }
}