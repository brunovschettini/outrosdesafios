package br.com.xptosystems.security.ws;

import br.com.xptosystems.address.Cities;
import br.com.xptosystems.security.UserToken;
import br.com.xptosystems.utils.Defaults;
import br.com.xptosystems.utils.Messages;
import com.google.gson.Gson;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UsersWS {

    public UserToken auth(String login, String password) {
        new Defaults().loadJson();
        Client client = ClientBuilder.newClient();
        Form form = new Form();
        form.param("login", login);
        form.param("password", password);
        WebTarget webTarget = client.target(Defaults.WEBSERVICE);
        WebTarget resourceWebTarget = webTarget.path("auth/in");

        Cities cities = new Cities();

        String citiesS = new Gson().toJson(cities);

        //WebTarget deleteWeb = resourceWebTarget.path("");
        Invocation.Builder builder = resourceWebTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = builder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        Gson gson = new Gson();
        Object result = response.getEntity();
        NotifyResponse notifyResponse = null;
        try {
            notifyResponse = gson.fromJson(result + "", NotifyResponse.class);
            if (notifyResponse != null) {
                Messages.warn("Validação", notifyResponse.getObject().toString(), Boolean.TRUE);
                return null;
            }
        } catch (Exception e) {

        }
        try {
            UserToken ut = gson.fromJson(result + "", UserToken.class);
            if (ut != null) {
                return ut;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
