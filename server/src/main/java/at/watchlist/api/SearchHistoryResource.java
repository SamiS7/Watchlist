package at.watchlist.api;

import at.watchlist.workloads.search.SearchHistoryService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Produces("application/json")
@Consumes("application/json")
@Path("/searchHistory")
public class SearchHistoryResource {
    @Inject
    private SearchHistoryService service;

    @GET
    @Path("{accountId}")
    public Response getMySearchHistory(@PathParam("accountId") Long accountId) {
        return Response.ok(service.get(accountId)).build();
    }

    @PUT
    @Path("{accountId}")
    @Transactional
    public Response updateSearchHistory(@PathParam("accountId") Long accountId, String searchStr) {
        service.update(accountId, searchStr);
        return Response.ok().build();
    }
}
