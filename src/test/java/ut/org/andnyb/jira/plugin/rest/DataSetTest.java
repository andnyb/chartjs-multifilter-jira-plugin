package ut.org.andnyb.jira.plugin.rest;

import static org.junit.Assert.assertEquals;

import org.andnyb.jira.plugin.rest.DataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataSetTest {

   @Before
   public void setUp() {}

   @After
   public void tearDown() {}

   @Test
   public void incrementingMonth() {
      DataSet ds = new DataSet("Label1");
      ds.incrementMonthly(2014, 1);
      ds.incrementMonthly(2014, 1);
      ds.incrementMonthly(2017, 5);
      ds.incrementMonthly(2017, 5);
      ds.incrementMonthly(2017, 5);
      assertEquals(2, ds.getCounterAt(0+168));
      assertEquals(0, ds.getCounterAt(1+168));
      assertEquals(3, ds.getCounterAt(40+168));
   }

   @Test
   public void incrementingYear() {
      DataSet ds = new DataSet("Label1");
      ds.incrementYearly(2014);
      ds.incrementYearly(2014);
      ds.incrementYearly(2016);
      ds.incrementYearly(2016);
      ds.incrementYearly(2016);
      ds.incrementYearly(2016);
      assertEquals(2, ds.getCounterAt(0+14));
      assertEquals(0, ds.getCounterAt(1+14));
      assertEquals(4, ds.getCounterAt(2+14));
   }

   @Test
   public void incrementingWeek() {
      DataSet ds = new DataSet("Label1");
      ds.incrementWeekly(2014,1);
      ds.incrementWeekly(2014,1);
      ds.incrementWeekly(2014,52);
      ds.incrementWeekly(2014,52);
      ds.incrementWeekly(2016,45);
      ds.incrementWeekly(2016,45);
      ds.incrementWeekly(2016,45);
      assertEquals(2, ds.getCounterAt(0+730));
      assertEquals(0, ds.getCounterAt(1+730));
      assertEquals(2, ds.getCounterAt(51+730));
      assertEquals(3, ds.getCounterAt(149+730));
   }

   @Test
   public void returnZero() {
      DataSet ds = new DataSet("Label1");
      assertEquals(0, ds.getCounterAt(0));
      assertEquals(0, ds.getCounterAt(34));
      ds.incrementMonthly(2015, 4);
      assertEquals(0, ds.getCounterAt(43+168));
   }
}
