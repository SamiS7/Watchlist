package at.watchlist.api;

import at.watchlist.workloads.movie.MovieServiceImpl;
import at.watchlist.workloads.movie.WatchlistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Produces("application/json")
@Consumes("application/json")
@Path("/movie")
public class MovieResource {
    @Inject
    MovieServiceImpl movieService;
    @Inject
    WatchlistService watchlistService;

    @GET
    public Response getAllMovies() {
        return Response.ok(movieService.getAll()).build();
    }

    @GET
    @Path("/{accountId}/shortlyAdded/{startIndex}/{endIndex}")
    public Response getShortlyAdded(@PathParam("accountId") Long id, @PathParam("startIndex") int start, @PathParam("endIndex") int end) {
        var result = watchlistService.getShortlyAdded(id, start, end);
        return (result != null ? Response.ok(result) : Response.ok(new ArrayList<>())).build();
    }
}
