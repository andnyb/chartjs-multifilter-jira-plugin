package org.andnyb.jira.plugin.rest;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

@XmlRootElement
public class DataSets {

   static int startYear = 2000; // TODO: Indicator to where we are counting datasets from.
   private Category category;
   private Granularity granularity;
   private String type;
   private String theme = "default";

   @XmlElement
   private ArrayList<String> labels = new ArrayList<String>();

   @XmlElement
   private ArrayList<DataSet> datasets = new ArrayList<DataSet>();

   public DataSets(Granularity granularity, Category category, String type, String theme) {

      this.category = category;
      this.granularity = granularity;
      this.type = type;
      this.theme = theme;

      if (granularity== Granularity.YEAR) {
         for (int i=0; i<=Calendar.getInstance().get(Calendar.YEAR); i++) {
            labels.add(i, constructYearLabel(i));
         }
      }
      else if (granularity== Granularity.WEEK) {
         DateTime now = new DateTime();

         int weekOffset = 0;
         for (int i=startYear; i<now.getYear(); i++) {
            int week = new LocalDate(i, 12, 28).getWeekOfWeekyear();
            if (week == 1) {
               weekOffset += 52;
            } else {
               weekOffset += week;
            }
         }
         weekOffset+=now.getWeekOfWeekyear();

         for (int i=0; i<=weekOffset; i++) {
            labels.add(i, constructWeekLabel(i));
         }
      }
      else {
         Calendar c = Calendar.getInstance();
         int lastIndex = (c.get(Calendar.YEAR)-startYear)*12+c.get(Calendar.MONTH)+1;
         for (int i=0; i<=lastIndex; i++) {
            labels.add(i, constructMonthLabel(i));
         }
      }
   }

   public String constructYearLabel(int index) {
      return new Integer(startYear+index).toString();
   }

   public String constructMonthLabel(int index) {
      int year = startYear + index / 12;
      int month = index % 12;
      return year+"-"+new DateFormatSymbols().getShortMonths()[month];
   }

   public String constructWeekLabel(int index) {
      if (index==0) {
         return "1999-W53";
      }
      DateTime dateTime = new DateTime(startYear,1,1,0,0);
      DateTime dateTime2 = new DateTime(dateTime.plusWeeks(index));
      return dateTime2.getYear()+"-W"+dateTime2.getWeekOfWeekyear();
   }

   public static int getStartYear() {
      return startYear;
   }

   public void addDataSet(DataSet ds) {
      datasets.add(ds);
   }

   public DataSets trim() {
      int firstIndexInAllDataSets = firstIndex();
      int lastIndexInAllDataSets = lastIndex();

      int size = labels.size();
      labels = new ArrayList<String>(labels.subList(firstIndexInAllDataSets, Math.min(size, lastIndexInAllDataSets+1)));

      int i = 0;
      for (DataSet ds : datasets) {
         ds.trim(firstIndexInAllDataSets, Math.min(size, lastIndexInAllDataSets+1));
         ds.setColors(Themes.getInstance().getTheme(theme).getLines()[i], Themes.getInstance().getTheme(theme).getFills()[i], type);
         i++;
      }
      return this;
   }

   public int firstIndex() {
      int firstIndexInAllDataSets = Integer.MAX_VALUE;
      for (DataSet ds : datasets) {
         for (int i=0; i<ds.size(); i++) {
            if (ds.getCounterAt(i) != 0 && i < firstIndexInAllDataSets) {
               firstIndexInAllDataSets = i;
            }
         }
      }
      return firstIndexInAllDataSets != Integer.MAX_VALUE ? firstIndexInAllDataSets : 0;
   }

   public int lastIndex() {
      int lastIndexInAllDataSets = 0;
      for (DataSet ds : datasets) {
         for (int i=ds.size()-1; i>=0; i--) {
            if (ds.getCounterAt(i) != 0 && i > lastIndexInAllDataSets) {
               lastIndexInAllDataSets = i;
            }
         }
      }
      return lastIndexInAllDataSets;
   }

   public DataSet getDataSet(int idx) {
      return datasets.get(idx);
   }

   public int size() {
      return datasets.size();
   }

   public Granularity getGranularity() {
      return granularity;
   }

   public Category getCategory() {
      return category;
   }
}
