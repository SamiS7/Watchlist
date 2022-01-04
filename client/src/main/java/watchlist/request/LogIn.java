package watchlist.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import watchlist.forServer.models.Account;
import watchlist.forServer.models.LogInModel;

public class LogIn {

    public static HttpResponse<LogInModel> logIn(String url, Account account) throws UnirestException {
        var response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .body(account).asObject(LogInModel.class);
        return response;
    }

    public static void initObjectMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return mapper.readValue(value, valueType);
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}