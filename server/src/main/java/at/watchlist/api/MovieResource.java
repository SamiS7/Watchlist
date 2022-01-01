package at.watchlist.api;

import at.watchlist.workloads.movie.MovieServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Produces("application/json")
@Consumes("application/json")
@Path("/movie")
public class MovieResource {
    @Inject
    MovieServiceImpl movieService;

    @GET
    public Response getAllMovies() {
        return Response.ok(movieService.getAll()).build();
    }
}
