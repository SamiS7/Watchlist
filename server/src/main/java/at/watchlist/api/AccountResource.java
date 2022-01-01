package at.watchlist.api;

import at.watchlist.db.entities.Account;
import at.watchlist.db.entities.MovieInfos;
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
    @Transactional
    public Response addAccount(AccountDTO accountDTO) {
        Account account = accountService.add(accountDTO);
        return (account != null ? Response.ok(account) : Response.status(404)).build();
    }

    @PUT
    @Transactional
    public Response updateAccount(Account account, String oldPassword) {
        return (accountService.update(account, oldPassword) ? Response.ok() : Response.status(404)).build();
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
}
