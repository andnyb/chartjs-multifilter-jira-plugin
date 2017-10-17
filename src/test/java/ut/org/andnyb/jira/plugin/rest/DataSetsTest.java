package ut.org.andnyb.jira.plugin.rest;

import static org.junit.Assert.assertEquals;

import org.andnyb.jira.plugin.rest.Category;
import org.andnyb.jira.plugin.rest.Granularity;
import org.andnyb.jira.plugin.rest.DataSet;
import org.andnyb.jira.plugin.rest.DataSets;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DataSetsTest {

   @Test
   public void firstIndexSingleDataSet() {
      DataSets dss = new DataSets(Granularity.MONTH, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet();
      ds1.incrementMonthly(2017,5);
      ds1.incrementMonthly(2017,5);
      dss.addDataSet(ds1);

      assertEquals(40+168, dss.firstIndex()); // 40 -> 208 = 168 months
   }

   @Test
   public void firstIndexMultipleDataSet() {
      DataSets dss = new DataSets(Granularity.MONTH, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet();
      ds1.incrementMonthly(2017,5);
      ds1.incrementMonthly(2017,5);
      dss.addDataSet(ds1);
      DataSet ds2 = new DataSet();
      ds2.incrementMonthly(2017,3);
      ds2.incrementMonthly(2017,7);
      dss.addDataSet(ds2);
      assertEquals(38+168, dss.firstIndex());
   }

   @Test
   public void firstIndexMultipleDataSet_02() {
      DataSets dss = new DataSets(Granularity.MONTH, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet();
      ds1.incrementMonthly(2017,5);
      ds1.incrementMonthly(2017,5);
      dss.addDataSet(ds1);
      DataSet ds2 = new DataSet();
      ds2.incrementMonthly(2017,3);
      dss.addDataSet(ds2);
      DataSet ds3 = new DataSet();
      ds3.incrementMonthly(2017,4);
      ds3.incrementMonthly(2017,8);
      dss.addDataSet(ds3);
      DataSet ds4 = new DataSet();
      ds4.incrementMonthly(2016,3);
      ds4.incrementMonthly(2017,8);
      ds4.incrementMonthly(2017,9);
      dss.addDataSet(ds4);
      DataSet ds5 = new DataSet();
      dss.addDataSet(ds5);

      assertEquals(26+168, dss.firstIndex()); // 2016/3
   }

   @Test
   public void firstIndexNoDataInSet() {
      DataSets dss = new DataSets(Granularity.MONTH, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet();
      dss.addDataSet(ds1);

      assertEquals(0, dss.firstIndex());
   }

   @Test
   public void firstIndexNoDataSet() {
      DataSets dss = new DataSets(Granularity.MONTH, Category.CREATED, "bar", "default");
      assertEquals(0, dss.firstIndex());
   }

   @Test
   public void lastIndexNoDataSet() {
      DataSets dss = new DataSets(Granularity.MONTH, Category.CREATED, "bar", "default");
      assertEquals(0, dss.lastIndex());
   }

   @Test
   public void lastIndexSingleMonthlyDataSet() {
      DataSets dss = new DataSets(Granularity.MONTH, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet();
      ds1.incrementMonthly(2017,5);
      ds1.incrementMonthly(2017,5);
      dss.addDataSet(ds1);

      assertEquals(40+168, dss.lastIndex());
   }

   @Test
   public void indexSingleWeeklyDataSet_01() {
      DataSets dss = new DataSets(Granularity.WEEK, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet();
      ds1.incrementWeekly(2017,5);
      ds1.incrementWeekly(2017,5);
      ds1.incrementWeekly(2017,5);
      ds1.incrementWeekly(2017,38);
      ds1.incrementWeekly(2017,38);
      ds1.incrementWeekly(2017,38);
      ds1.incrementWeekly(2017,38);
      ds1.incrementWeekly(2017,38);
      ds1.incrementWeekly(2017,38);
      dss.addDataSet(ds1);
      assertEquals(161+730, dss.firstIndex());
      assertEquals(194+730, dss.lastIndex());
   }

   @Test
   public void indexSingleWeeklyDataSet_02() {
      DataSets dss = new DataSets(Granularity.WEEK, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet();
      ds1.incrementWeekly(2014,5);
      ds1.incrementWeekly(2014,5);
      ds1.incrementWeekly(2014,5);
      ds1.incrementWeekly(2015,53);
      ds1.incrementWeekly(2015,53);
      ds1.incrementWeekly(2015,53);
      ds1.incrementWeekly(2015,53);
      ds1.incrementWeekly(2017,38);
      ds1.incrementWeekly(2017,38);
      ds1.incrementWeekly(2017,38);
      ds1.incrementWeekly(2017,39);
      ds1.incrementWeekly(2017,39);
      dss.addDataSet(ds1);
      assertEquals(4+730, dss.firstIndex()); // +730W
      assertEquals(195+730, dss.lastIndex());
   }

   @Test
   public void indexSingleWeeklyDataSet_03() {
      DataSets dss = new DataSets(Granularity.WEEK, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet();
      ds1.incrementWeekly(2015,53);
      ds1.incrementWeekly(2016,1);
      dss.addDataSet(ds1);
      assertEquals(104+730, dss.firstIndex());
      assertEquals(105+730, dss.lastIndex());
   }

   @Test
   public void lastIndexMultipleDataSet_02() {
      DataSets dss = new DataSets(Granularity.MONTH, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet();
      ds1.incrementMonthly(2017,5);
      ds1.incrementMonthly(2017,5);
      dss.addDataSet(ds1);
      DataSet ds2 = new DataSet();
      ds2.incrementMonthly(2017,3);
      dss.addDataSet(ds2);
      DataSet ds3 = new DataSet();
      ds3.incrementMonthly(2017,4);
      ds3.incrementMonthly(2017,8);
      dss.addDataSet(ds3);
      DataSet ds4 = new DataSet();
      ds4.incrementMonthly(2016,3);
      ds4.incrementMonthly(2017,8);
      ds4.incrementMonthly(2017,9);
      dss.addDataSet(ds4);
      DataSet ds5 = new DataSet();
      dss.addDataSet(ds5);

      assertEquals(44+168, dss.lastIndex()); // 2016/3
   }

   @Test
   public void labelMonth() {
      DataSets dss = new DataSets(Granularity.MONTH, Category.CREATED, "bar", "default");
      assertEquals("2000-Jan", dss.constructMonthLabel(0));
      assertEquals("2001-Jan", dss.constructMonthLabel(12));
      assertEquals("2002-Jan", dss.constructMonthLabel(24));
      assertEquals("2003-Jan", dss.constructMonthLabel(36));
      assertEquals("2003-May", dss.constructMonthLabel(40));
      assertEquals("2003-Jul", dss.constructMonthLabel(42));
      assertEquals("2002-Dec", dss.constructMonthLabel(35));
      assertEquals("2003-Oct", dss.constructMonthLabel(45));
   }

   @Test
   public void labelYear() {
      DataSets dss = new DataSets(Granularity.YEAR, Category.CREATED, "bar", "default");
      assertEquals("2000", dss.constructYearLabel(0));
      assertEquals("2002", dss.constructYearLabel(2));
      assertEquals("2022", dss.constructYearLabel(22));
   }

   @Test
   public void labelWeek() {
      DataSets dss = new DataSets(Granularity.WEEK, Category.CREATED, "bar", "default");
      assertEquals("1999-W53", dss.constructWeekLabel(0));
      assertEquals("2000-W1", dss.constructWeekLabel(1));
      assertEquals("2000-W2", dss.constructWeekLabel(2));
      assertEquals("2000-W22", dss.constructWeekLabel(22));
      assertEquals("2001-W52", dss.constructWeekLabel(104));
      assertEquals("2004-W45", dss.constructWeekLabel(253));
   }

   @Test
   public void trimMonthly() throws Exception {
      DataSets dss = new DataSets(Granularity.MONTH, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet("# of Bugs");
      ds1.incrementMonthly(2017,5); // Index 40
      ds1.incrementMonthly(2017,5);
      ds1.incrementMonthly(2017,5);
      ds1.incrementMonthly(2017,5); // 4
      ds1.incrementMonthly(2017,7);
      ds1.incrementMonthly(2017,7);
      ds1.incrementMonthly(2017,7);
      ds1.incrementMonthly(2017,7);
      ds1.incrementMonthly(2017,7);
      ds1.incrementMonthly(2017,7); // 6

      DataSet ds2 = new DataSet("# of Incidents");
      ds2.incrementMonthly(2017,4); // Index 39
      ds2.incrementMonthly(2017,4);
      ds2.incrementMonthly(2017,4); // 3
      ds2.incrementMonthly(2017,5);
      ds2.incrementMonthly(2017,5);
      ds2.incrementMonthly(2017,5);
      ds2.incrementMonthly(2017,5); // 4
      ds2.incrementMonthly(2017,6);
      ds2.incrementMonthly(2017,6);
      ds2.incrementMonthly(2017,6);
      ds2.incrementMonthly(2017,6);
      ds2.incrementMonthly(2017,6);
      ds2.incrementMonthly(2017,6); // 6
      ds2.incrementMonthly(2017,9);
      ds2.incrementMonthly(2017,9);
      ds2.incrementMonthly(2017,9);
      ds2.incrementMonthly(2017,9);
      ds2.incrementMonthly(2017,9);
      ds2.incrementMonthly(2017,9);
      ds2.incrementMonthly(2017,9);
      ds2.incrementMonthly(2017,9);
      ds2.incrementMonthly(2017,9);
      ds2.incrementMonthly(2017,9);
      ds2.incrementMonthly(2017,9);
      ds2.incrementMonthly(2017,9); // 12 // Index 44

      dss.addDataSet(ds1);
      dss.addDataSet(ds2);

      dss.trim();

      Field f = dss.getClass().getDeclaredField("labels");
      f.setAccessible(true);
      assertEquals(6, ((ArrayList<String>)f.get(dss)).size()); // 5,6,7,8,9,10
   }

   @Test
   public void trimDataSetsSingle() throws Exception {
      DataSets dss = new DataSets(Granularity.MONTH, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet();
      ds1.incrementMonthly(2017, 5);
      dss.addDataSet(ds1);
      dss.trim();

      Field f = dss.getClass().getDeclaredField("labels");
      f.setAccessible(true);
      assertEquals(1, ((ArrayList<String>)f.get(dss)).size());
   }

   @Test
   public void trimDataSetsWeekly() throws Exception {
      DataSets dss = new DataSets(Granularity.WEEK, Category.CREATED, "bar", "default");
      DataSet ds1 = new DataSet("# of Bugs");
      ds1.incrementWeekly(2017, 35);
      ds1.incrementWeekly(2017, 35);
      ds1.incrementWeekly(2017, 35); // 3
      ds1.incrementWeekly(2017, 38);
      ds1.incrementWeekly(2017, 38);
      ds1.incrementWeekly(2017, 38);
      ds1.incrementWeekly(2017, 38);
      ds1.incrementWeekly(2017, 38); // 5
      ds1.incrementWeekly(2017, 39);
      ds1.incrementWeekly(2017, 39);
      ds1.incrementWeekly(2017, 39);
      ds1.incrementWeekly(2017, 39);
      ds1.incrementWeekly(2017, 39);
      ds1.incrementWeekly(2017, 39);
      ds1.incrementWeekly(2017, 39);
      ds1.incrementWeekly(2017, 39);
      ds1.incrementWeekly(2017, 39);
      ds1.incrementWeekly(2017, 39); // 10

      DataSet ds2 = new DataSet("# of Incidents");
      ds2.incrementWeekly(2017, 36);
      ds2.incrementWeekly(2017, 36);
      ds2.incrementWeekly(2017, 36);
      ds2.incrementWeekly(2017, 36);
      ds2.incrementWeekly(2017, 36); // 5
      ds2.incrementWeekly(2017, 37);
      ds2.incrementWeekly(2017, 37);
      ds2.incrementWeekly(2017, 37);
      ds2.incrementWeekly(2017, 37); // 4

      dss.addDataSet(ds1);
      dss.addDataSet(ds2);

      dss.trim();

      Field f = dss.getClass().getDeclaredField("labels");
      f.setAccessible(true);
      assertEquals(5, ((ArrayList<String>)f.get(dss)).size());
   }

   @Test
   public void testEval() throws Exception {
      assertEquals(Granularity.MONTH, Granularity.eval("month"));
      assertEquals(Granularity.WEEK, Granularity.eval("week"));
      assertEquals(Granularity.YEAR, Granularity.eval("year"));
   }

   @Test(expected = Exception.class)
   public void testEvalException() throws Exception {
      Granularity.eval("sdflögkj");
   }
}
