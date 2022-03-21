package at.watchlist.api;

import at.watchlist.entities.Account;
import at.watchlist.entities.MovieInfos;
import at.watchlist.entities.Watchlist;
import at.watchlist.models.AccountDTO;
import at.watchlist.workloads.account.AccountService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Produces("application/json")
@Consumes("application/json")
@Path("/account")
public class AccountResource {
    @Inject
    private AccountService accountService;

    @GET
    public Response getAccounts() {
        return Response.ok(accountService.getAll()).build();
    }

    @GET
    @Path("{id}")
    public Response getAccount(@PathParam("id") Long id) {
        Account account = accountService.get(id);
        return (account != null ? Response.ok(account) : Response.status(404)).build();
    }

    @POST
    @Path("signUp")
    @Transactional
    public Response signUp(AccountDTO accountDTO) {
        var response = accountService.add(accountDTO);
        return (response.isSuccess() ? Response.ok(response) : Response.status(404)).entity((response)).build();
    }

    @POST
    @Path("logIn")
    public Response logIn(AccountDTO accountDTO) {
        var response = accountService.logIn(accountDTO);
        return (response.isSuccess() ? Response.ok(response) : Response.status(404)).entity((response)).build();
    }
    
    @POST
    @Path("{accountId}/movie")
    @Transactional
    public Response addMovie(@PathParam("accountId") Long accountId, MovieInfos movieInfos) {
        if (accountId != null && movieInfos != null) {
            return (accountService.addMovie(accountId, movieInfos) ? Response.ok() : Response.status(404)).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @Path("/movie")
    @Transactional
    public Response updateWatchlist(Watchlist watchlist) {
        if (watchlist != null) {
            return (accountService.updateWatchlist(watchlist) ? Response.ok() : Response.status(404)).build();
        }
        return Response.status(404).build();
    }

    @DELETE
    @Path("{accountId}/movie")
    @Transactional
    public Response removeMovie(@PathParam("accountId") Long accountId, String movieId) {
        if (accountId != null && movieId != null) {
            return (accountService.removeSavedMovie(accountId, movieId) ? Response.ok() : Response.status(404)).build();
        }
        return Response.status(404).build();
    }

    @GET
    @Path("{accountId}/{movieId}")
    public Response getWatchlist(@PathParam("accountId") Long accountId, @PathParam("movieId") String movieId) {
        var movie = accountService.getWatchlist(accountId, movieId);
        return (movie != null ? Response.ok(movie) : Response.status(404)).build();
    }
}
