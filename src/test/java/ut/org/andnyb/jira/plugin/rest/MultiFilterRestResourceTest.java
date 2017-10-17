package ut.org.andnyb.jira.plugin.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.MockJqlSearchRequest;
import com.atlassian.jira.issue.search.SearchRequest;
import com.atlassian.jira.mock.issue.MockIssue;
import org.andnyb.jira.plugin.rest.Category;
import org.andnyb.jira.plugin.rest.Granularity;
import org.andnyb.jira.plugin.rest.MultiFilterRestResource;
import org.andnyb.jira.plugin.rest.MultiFilterRestResourceModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MultiFilterRestResourceTest {

   @Before
   public void setUp() {}

   @BeforeClass
   public static void setUpClass() {

   }

   @After
   public void tearDown() {

   }

   @Test
   public void labelSplitting() {
      MultiFilterRestResourceModel mfrrm = new MultiFilterRestResourceModel();
      String[] ss = mfrrm.split("a,b,c");
      Assert.assertThat(ss[0], is(equalTo("a")));
      Assert.assertThat(ss[1], is(equalTo("b")));
      Assert.assertThat(ss[2], is(equalTo("c")));
   }

   @Test
   public void labelSplittingDecoded() {
      MultiFilterRestResourceModel mfrrm = new MultiFilterRestResourceModel();
      String[] ss = mfrrm.split("bugs%2Cincidents%2Cstories");
      Assert.assertThat(ss[0], is(equalTo("bugs")));
      Assert.assertThat(ss[1], is(equalTo("incidents")));
      Assert.assertThat(ss[2], is(equalTo("stories")));
   }

   @Test
   public void labelSplittingNull() {
      MultiFilterRestResourceModel mfrrm = new MultiFilterRestResourceModel();
      String[] ss = mfrrm.split(null);
      Assert.assertNull(ss);
   }

   // _12_,______343__,_2,__
   @Test
   public void stringTrimming() {
      MultiFilterRestResourceModel mfrrm = new MultiFilterRestResourceModel();
      String[] ss = mfrrm.split(" 12 ,      343  , 2,  ");
      Assert.assertThat(ss[0], is(equalTo("12")));
      Assert.assertThat(ss[1], is(equalTo("343")));
      Assert.assertThat(ss[2], is(equalTo("2")));
      Assert.assertThat(ss.length, is(equalTo(3)));
   }

   @Test
   public void filterMatching() {
      MultiFilterRestResourceModel mfrrm = new MultiFilterRestResourceModel();
      ArrayList<SearchRequest> list = new ArrayList<SearchRequest>();
      SearchRequest sr1 = new MockJqlSearchRequest(1L,null);
      list.add(sr1);
      SearchRequest sr2 = new MockJqlSearchRequest(2L,null);
      list.add(sr2);
      SearchRequest sr3 = new MockJqlSearchRequest(3L,null);
      list.add(sr3);
      SearchRequest sr4 = new MockJqlSearchRequest(4L,null);
      list.add(sr4);
      Assert.assertTrue(mfrrm.allFiltersAreValid(list, "2,3"));
   }

   @Test
   public void filtersNotMatching() {
      MultiFilterRestResourceModel mfrrm = new MultiFilterRestResourceModel();
      ArrayList<SearchRequest> list = new ArrayList<SearchRequest>();
      SearchRequest sr1 = new MockJqlSearchRequest(1L,null);
      list.add(sr1);
      SearchRequest sr2 = new MockJqlSearchRequest(2L,null);
      list.add(sr2);
      SearchRequest sr3 = new MockJqlSearchRequest(3L,null);
      list.add(sr3);
      SearchRequest sr4 = new MockJqlSearchRequest(4L,null);
      list.add(sr4);
      Assert.assertFalse(mfrrm.allFiltersAreValid(list, "2,5"));
   }

   @Test
   public void issueIncrementation() {
      ArrayList<Issue> issues = new ArrayList<Issue>();
      MockIssue mi1 = new MockIssue();
      mi1.setCreated(Timestamp.valueOf("2015-12-31 10:10:10.0"));
      mi1.getCreated().toString();
      issues.add(mi1);

      MultiFilterRestResourceModel mfrrm = new MultiFilterRestResourceModel();
      mfrrm.incrementIssues(issues, Granularity.WEEK, Category.CREATED);
   }
}
