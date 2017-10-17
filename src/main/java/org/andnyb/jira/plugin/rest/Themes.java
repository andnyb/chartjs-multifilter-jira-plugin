package org.andnyb.jira.plugin.rest;

import java.util.HashMap;

public class Themes {

   private static Themes myObj;

   private HashMap<String, Theme> themes = new HashMap<String, Theme>();

   private final String[] baseColors = new String[] {
      "#C0392B",
      "#16A085",
      "#8E44AD",
      "#2980B9",
      "#27AE60",
      "#F39C12",
      "#D35400",
      "#7F8C8D",
      "#2C3E50"
   };

   private final String[] baseFills = new String[] {
      "#E74C3C",
      "#1ABC9C",
      "#9B59B6",
      "#3498DB",
      "#2ECC71",
      "#F1C40F",
      "#E67E22",
      "#AAB7B8",
      "#5D6D7E"
   };

   private Themes() {
      themes.put("default", new Theme(
         baseColors,
         new String[]{"rgba(231,76,60,0.1)","rgba(9,63,52,0.1)","rgba(155,89,182,0.1)","rgba(214,234,248,0.1)","#27AE60","#F39C12","#D35400","#7F8C8D",
            "#2C3E50"}
         ));
      themes.put("solid", new Theme(
         baseColors,
         baseFills
      ));
      themes.put("empty", new Theme(
         baseColors,
         new String[]{"rgba(231,76,60,0.0)","rgba(9,63,52,0.0)","rgba(155,89,182,0.0)","rgba(214,234,248,0.0)","#27AE60","#F39C12","#D35400","#7F8C8D",
            "#2C3E50"}
      ));
   }

   public static Themes getInstance(){
      if(myObj == null){
         myObj = new Themes();
      }
      return myObj;
   }

   public Theme getTheme(String name) {
      return themes.get(name);
   }
}
