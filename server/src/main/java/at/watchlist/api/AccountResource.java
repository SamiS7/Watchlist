package at.watchlist.api;

import at.watchlist.entities.Account;
import at.watchlist.entities.MovieInfos;
import at.watchlist.models.AccountDTO;
import at.watchlist.workloads.account.AccountServiceImpl;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Produces("application/json")
@Consumes("application/json")
@Path("/account")
public class AccountResource {
    @Inject
    private AccountServiceImpl accountService;

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

    @PUT
    @Transactional
    public Response updateAccount(Account account, String oldPassword) {
        String s = accountService.update(account, oldPassword);
        return (s.length() <= 0 ? Response.ok() : Response.status(404, s)).build();
    }

    @DELETE
    @Transactional
    public Response removeAccount(Long id) {
        return accountService.remove(id) ? Response.ok().build() : Response.status(404).build();
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

    @DELETE
    @Path("{accountId}/movie")
    @Transactional
    public Response removeMovie(@PathParam("accountId") Long accountId, String movieId) {
        if (accountId != null && movieId != null) {
            return (accountService.removeSavedMovie(accountId, movieId) ? Response.ok() : Response.status(404)).build();
        }
        return Response.status(404).build();
    }
}
