package at.watchlist.api;

import at.watchlist.db.entities.Account;
import at.watchlist.models.AccountDTO;
import at.watchlist.workloads.account.AccountServiceImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;

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
        if (validAccount(accountDTO)) {
            accountDTO.setPassword(BCrypt.hashpw(accountDTO.getPassword(), BCrypt.gensalt()));
            return Response.ok(accountService.add(accountDTO)).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @Transactional
    public Response updateAccount(Account account) {
        if (validAccount(new AccountDTO(account.getUsername(), account.getPassword()))) {
            account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt()));
            return accountService.update(account) ? Response.ok().build() : Response.status(404).build();
        }
        return Response.status(404).build();
    }

    @DELETE
    @Transactional
    public Response removeAccount(Long id) {
        return accountService.remove(id) ? Response.ok().build() : Response.status(404).build();
    }

    public static boolean checkLength(String s1, String s2) {
        return s1.length() >= 3 && s2.length() >= 5;
    }

    public boolean validAccount(AccountDTO accountDTO) {
        return checkLength(accountDTO.getUsername(), accountDTO.getPassword()) && !accountService.getAccountRepo().usernameExists(accountDTO.getUsername());
    }
}
