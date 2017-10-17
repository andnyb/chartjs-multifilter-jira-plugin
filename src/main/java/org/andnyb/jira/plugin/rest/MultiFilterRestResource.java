package org.andnyb.jira.plugin.rest;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class MultiFilterRestResource {

   @GET
   @Path("/data")
   @AnonymousAllowed
   @Produces({ MediaType.APPLICATION_JSON })
   public Response getData(
      @QueryParam("filters") final String filterIds,
      @QueryParam("labels") final String labels,
      @QueryParam("granularity") final String granularity,
      @QueryParam("category") final String category,
      @QueryParam("type") final String type,
      @QueryParam("theme") final String theme) throws Exception {
      if (!Granularity.validate(granularity)) {
         ValidationError ve = new ValidationError("Time granularity", "Invalid time granularity: '" + granularity + "'.");
         return Response.status(Response.Status.BAD_REQUEST).entity(ve).build();
      }
      if (!Category.validate(category)) {
         ValidationError ve = new ValidationError("Time category", "Invalid category: '" + category + "'.");
         return Response.status(Response.Status.BAD_REQUEST).entity(ve).build();
      }
      return Response.ok(new MultiFilterRestResourceModel().data(filterIds, labels, granularity, category, type, theme)).build();
   }

   /*
    http://localhost:2990/jira/rest/multifilter/1.0/validate
    ?gadgetTitleUserPref=Chart
    &barTypeUserPref=line
    &filterIdUserPref=10003%2C10100
    &graphLabelUserPref=Undefined+label&isConfigured=true
    &refresh=false
    &_=1505985015238
   */
   @GET
   @Path("/validate")
   @AnonymousAllowed
   @Produces({ MediaType.APPLICATION_JSON })
   public Response validate(@QueryParam("filterIdUserPref") String filterIds) {
      MultiFilterRestResourceModel mfrrm = new MultiFilterRestResourceModel();
      String regex = "[0-9, /,]+";
      if (filterIds.length() == 0) {
         ValidationError ve = new ValidationError("Filter id", "There needs to be at least one valid filter provided.");
         return Response.status(Response.Status.BAD_REQUEST).entity(ve).build();
      } else if (!filterIds.matches(regex)) {
         ValidationError ve = new ValidationError("Filter id", "The provided list of filter ids can only contain integers and each filter needs to be "
            + "separated by a comma. Input string was: '" + filterIds + "'.");
         return Response.status(Response.Status.BAD_REQUEST).entity(ve).build();
      } else if (!mfrrm.onlyValidIds(filterIds)) {
         ValidationError ve = new ValidationError("Filter id", "At least one of the provided filters was not a valid filter id. Check the id and that "
            + "the filter is not private. Input string was: "
            + "'" + filterIds + "'. Public filters for current user are: '"+mfrrm.getAccessibleFiltersAsString()+"'");
         return Response.status(Response.Status.BAD_REQUEST).entity(ve).build();
      }
      return Response.ok().build();
   }
}