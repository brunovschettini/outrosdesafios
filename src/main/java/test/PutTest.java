package test;

import br.com.xptosystems.address.Cities;
import com.google.gson.Gson;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PutTest {

    public static void main(String[] args) {
        try {
//            WebResource webResource2 = client.resource("http://localhost:8080/xptosystems/ws/cities/").post(GenericType)new Gson().toJson(cities));

            Client client = ClientBuilder.newClient();
            Form form = new Form();
            form.param("ibge_id", "");
            form.param("name", "");
            form.param("uf", "");
            WebTarget webTarget = client.target("http://localhost:8080/xptosystems/ws/cities/");
            WebTarget resourceWebTarget = webTarget.path("insert");

            Cities cities = new Cities();

            String citiesS = new Gson().toJson(cities);

            //WebTarget deleteWeb = resourceWebTarget.path("");
            Invocation.Builder deleteInvocationBuilder = resourceWebTarget.request(MediaType.APPLICATION_JSON_TYPE);
            Response putResponse = deleteInvocationBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

            System.out.println(putResponse.getStatus());
            System.out.println(putResponse.readEntity(String.class));
        } catch (Exception e) {
            e.getMessage();
        }

    }

}
