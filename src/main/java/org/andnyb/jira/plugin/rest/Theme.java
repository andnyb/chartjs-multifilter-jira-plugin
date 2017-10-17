package org.andnyb.jira.plugin.rest;

public class Theme {

   private String[] lines;
   private String[] fills;

   public Theme(String[] lines, String[] fills) {
      //lines = new String[lines.length];
      this.lines = lines;
      //fills = new String[fills.length];
      this.fills = fills;
   }

   public String[] getLines() {
      return lines;
   }

   public String[] getFills() {
      return fills;
   }
}
