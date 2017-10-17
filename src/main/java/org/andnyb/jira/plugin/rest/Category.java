package org.andnyb.jira.plugin.rest;

public enum Category {
   CREATED,
   UPDATED,
   RESOLVED;

   public static Category eval(String category) throws Exception {
      if (category.equalsIgnoreCase("created")) {
         return CREATED;
      }
      else if (category.equalsIgnoreCase("updated")) {
         return UPDATED;
      }
      else if (category.equalsIgnoreCase("resolved")) {
         return RESOLVED;
      }
      else {
         throw new Exception();
      }
   }

   public static boolean validate(String category) {
      try {
         eval(category);
         return true;
      }
      catch (Exception e) {
         return false;
      }
   }
}
