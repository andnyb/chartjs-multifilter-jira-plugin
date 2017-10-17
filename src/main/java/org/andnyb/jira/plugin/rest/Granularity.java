package org.andnyb.jira.plugin.rest;

public enum Granularity {
   WEEK,
   MONTH,
   YEAR;

   public static Granularity eval(String granularity) throws Exception {
      if (granularity.equalsIgnoreCase("week")) {
         return WEEK;
      }
      else if (granularity.equalsIgnoreCase("year")) {
         return YEAR;
      }
      else if (granularity.equalsIgnoreCase("month")) {
         return MONTH;
      }
      else {
         throw new Exception();
      }
   }

   public static boolean validate(String granularity) {
      try {
         eval(granularity);
         return true;
      }
      catch (Exception e) {
         return false;
      }
   }
}