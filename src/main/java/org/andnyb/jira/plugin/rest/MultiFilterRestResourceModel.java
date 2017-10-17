package org.andnyb.jira.plugin.rest;

import com.atlassian.jira.bc.JiraServiceContext;
import com.atlassian.jira.bc.JiraServiceContextImpl;
import com.atlassian.jira.bc.filter.SearchRequestService;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchRequest;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.builder.JqlClauseBuilder;
import com.atlassian.jira.jql.builder.JqlQueryBuilder;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.sharing.SharedEntityColumn;
import com.atlassian.jira.sharing.search.SharedEntitySearchContext;
import com.atlassian.jira.sharing.search.SharedEntitySearchParameters;
import com.atlassian.jira.sharing.search.SharedEntitySearchParametersBuilder;
import com.atlassian.jira.sharing.search.SharedEntitySearchResult;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.query.Query;
import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MultiFilterRestResourceModel {

   private SearchService searchService;

   public MultiFilterRestResourceModel() {
   }

   public DataSets data(String idsString, String labelsString, String granularity, String category, String type, String theme) throws Exception {

      DataSets dataSets = dataSets = new DataSets(Granularity.eval(granularity), Category.eval(category), type, theme);

      for (String id : split(idsString)) {
         ArrayList<Issue> issues = getIssuesForFilter(id);
         dataSets.addDataSet(incrementIssues(issues, dataSets.getGranularity(), dataSets.getCategory()));
      }

      int i = 0;
      for (String label : split(labelsString)) {
         if (dataSets.size() > i) {
            dataSets.getDataSet(i).setLabel(label);
         }
         i++;
      }

      if (dataSets.size() > 0) {
         return dataSets.trim();
      }
      return dataSets;
   }

   public DataSet incrementIssues(ArrayList<Issue> issues, Granularity granularity, Category category) {
      DataSet ds = new DataSet();
      for (Issue issue : issues) {
         Timestamp ts = null;
         if (category==Category.CREATED) {
            ts = issue.getCreated(); // TODO getTimeStamp(which field)
         } else if (category==Category.UPDATED) {
            ts = issue.getUpdated();
         } else if (category==Category.RESOLVED) {
            ts = issue.getResolutionDate();
         }
         if (ts!=null) {
            if (granularity == Granularity.YEAR) {
               ds.incrementYearly(new DateTime(ts).getYear());
            } else if (granularity == Granularity.MONTH) {
               ds.incrementMonthly(new DateTime(ts).getYear(), new DateTime(ts).getMonthOfYear());
            } else if (granularity == Granularity.WEEK) {
               ds.incrementWeekly(new DateTime(ts).getWeekyear(), new DateTime(ts).getWeekOfWeekyear());
            } else {
               ds.incrementMonthly(new DateTime(ts).getYear(), new DateTime(ts).getMonthOfYear());
            }
         }
      }
      return ds;
   }

   public String[] split(String s) {
      if (s == null) {
         return null;
      }
      try {
         String[] splits = java.net.URLDecoder.decode(s, "UTF-8").split(",");
         ArrayList<String> cleanedUp = new ArrayList<String>();
         for (int i = 0; i < splits.length; i++) {
            String trimmed = splits[i].trim();

            if (trimmed.length() != 0) {
               cleanedUp.add(trimmed);
            }
         }
         return cleanedUp.toArray(new String[cleanedUp.size()]);
      } catch (UnsupportedEncodingException uee) {
         return null;
      }
   }

   public ArrayList<Issue> getIssuesForFilter(String filterId) {

      JiraAuthenticationContext jac = ComponentAccessor.getJiraAuthenticationContext();
      ApplicationUser user = jac.getLoggedInUser();

      JqlClauseBuilder jqlClauseBuilder = JqlQueryBuilder.newClauseBuilder();
      Query query = jqlClauseBuilder.savedFilter(filterId).buildQuery();
      PagerFilter pagerFilter = PagerFilter.getUnlimitedFilter();
      searchService = ComponentAccessor.getComponent(SearchService.class);
      SearchResults searchResults = null;

      ArrayList<Issue> issues = new ArrayList<Issue>();
      try {
         searchResults = searchService.search(user, query, pagerFilter);
         for (Issue issue : searchResults.getIssues()) {
            issues.add(issue);
         }
      } catch (Exception e) {
         // void
      }
      return issues;
   }

   public boolean onlyValidIds(String filterIds) {
      SharedEntitySearchResult<SearchRequest> filtersResult = getAccessibleFilters();
      List<SearchRequest> filters = filtersResult.getResults();
      return allFiltersAreValid(filters, filterIds);
   }

   public boolean allFiltersAreValid(List<SearchRequest> filters, String filterIds) {
      if (filters.size() == 0) {
         return false;
      }
      String[] ids = split(filterIds);
      for (String id : ids) {
         boolean isExisting = false;
         for (SearchRequest filter : filters) {
            if (id.equals(filter.getId().toString())) {
               isExisting = true;
            }
         }
         if (!isExisting) {
            return false;
         }
      }
      return true;
   }

   public SharedEntitySearchResult<SearchRequest> getAccessibleFilters() {
      JiraAuthenticationContext jac = ComponentAccessor.getJiraAuthenticationContext();
      ApplicationUser user = jac.getLoggedInUser();
      JiraServiceContext jsc = new JiraServiceContextImpl(user);

      SharedEntitySearchParameters searchParams = new SharedEntitySearchParametersBuilder().
         setEntitySearchContext(SharedEntitySearchContext.USE).
         setName(null).
         setDescription(null).
         setFavourite(null).
         setSortColumn(SharedEntityColumn.NAME, true).
         setUserName(null).
         setShareTypeParameter(null).
         setTextSearchMode(null).
         toSearchParameters();

      SearchRequestService searchRequestService = ComponentAccessor.getComponent(SearchRequestService.class);

      return searchRequestService.search(jsc, searchParams, 0, 1000); // TODO: Page 0, 1000 items
   }

   public String getAccessibleFiltersAsString() {
      List<SearchRequest> filters = getAccessibleFilters().getResults();
      if (filters.size()==0) {
         return "no filters are accessible";
      }
      String listOfFilters = "";
      for (SearchRequest filter : filters) {
         if (listOfFilters.length()!=0) {
            listOfFilters+=", ";
         }
         listOfFilters+=filter.getId();
      }
      return listOfFilters;
   }
}