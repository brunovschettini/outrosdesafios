package test;

import br.com.xptosystems.utils.Defaults;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class PutTest {

    public static void main(String[] args) {
        // new Defaults().loadJson();
//        try {
////            WebResource webResource2 = client.resource("http://localhost:8080/xptosystems/ws/cities/").post(GenericType)new Gson().toJson(cities));
//
//            Client client = ClientBuilder.newClient();
//            Form form = new Form();
//            form.param("ibge_id", "");
//            form.param("name", "");
//            form.param("uf", "");
//            WebTarget webTarget = client.target("http://localhost:8080/xptosystems2/ws/cities/");
//            WebTarget resourceWebTarget = webTarget.path("insert");
//
//            Cities cities = new Cities();
//
//            String citiesS = new Gson().toJson(cities);
//
//            //WebTarget deleteWeb = resourceWebTarget.path("");
//            Invocation.Builder deleteInvocationBuilder = resourceWebTarget.request(MediaType.APPLICATION_JSON_TYPE);
//            Response putResponse = deleteInvocationBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
//
//            System.out.println(putResponse.getStatus());
//            System.out.println(putResponse.readEntity(String.class));
//        } catch (Exception e) {
//            e.getMessage();
//        }

        String urlString = "http://localhost:8080/xptosystems2/ws/cities/insert/";
        try {
            String result = "";
            HttpEntity entity;

            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("ibge_id", "6556465"));
            params.add(new BasicNameValuePair("name", ""));

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlString);
            // StringEntity input = new StringEntity(new Gson().toJson(c), Charset.defaultCharset());
            // input.setContentType("application/json");
//            httpPost.setEntity(input);
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            // httpPost.setHeader("Accept", "application/json");
            // httpPost.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            entity = response.getEntity();
            result = EntityUtils.toString(entity);

            httpPost.abort();
            httpClient.close();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
