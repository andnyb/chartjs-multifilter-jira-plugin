package org.andnyb.jira.plugin.rest;

import org.joda.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class DataSet {

   private final int c_ARRAY_SIZE = 1000;
   private int arrayStart = DataSets.getStartYear();

   @XmlElement
   private String label = "undefined";

   @XmlElement
   ArrayList<Integer> data = new ArrayList<Integer>(c_ARRAY_SIZE);

   @XmlElement
   ArrayList<String> backgroundColor = new ArrayList<String>();

   @XmlElement
   ArrayList<String> borderColor = new ArrayList<String>();

   @XmlElement
   private final int borderWidth = 2;

   public DataSet() {
      for (int i=0; i<c_ARRAY_SIZE; i++) {
         data.add(i, 0);
      }
   }

   public DataSet(String label) {
      this();
      if (label != null) {
         this.label = label;
      }
   }

   public int getCounterAt(int index) {
      return data.get(index);
   }

   public int size() {
      return data.size();
   }

   public void trim(int start, int end) {
      data = new ArrayList<Integer>(data.subList(start, end));
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public void incrementYearly(int year) {
      int offset = year-arrayStart;
      data.set(offset, data.get(offset)+1);
   }

   public void incrementMonthly(int year, int month) {
      int offset = (year-arrayStart)*12+month-1;
      data.set(offset, data.get(offset)+1);
   }

   public void incrementWeekly(int year, int weekOfWeekyear) {
      int weekOffset = 0;
      for (int i=arrayStart; i<year; i++) {
         int week = new LocalDate(i, 12, 28).getWeekOfWeekyear();
         if (week == 1) {
            weekOffset += 52;
         } else {
            weekOffset += week;
         }
      }
      weekOffset+=weekOfWeekyear;
      data.set(weekOffset-1, data.get(weekOffset-1)+1);
   }

   public void setColors(String line, String fill, String type) {
      if (type.equalsIgnoreCase("bar")
         || type.equalsIgnoreCase("stacked")
         || type.equalsIgnoreCase("horizontalbar")) {
         for (int i = 0; i < data.size(); i++) {
            backgroundColor.add(fill);
            borderColor.add(line);
         }
      } else {
         borderColor.add(line);
         backgroundColor.add(fill);
      }
   }
}
