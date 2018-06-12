package br.com.xptosystems.security.ws;

import br.com.xptosystems.address.Cities;
import br.com.xptosystems.security.UserToken;
import br.com.xptosystems.utils.Defaults;
import br.com.xptosystems.utils.Messages;
import com.google.gson.Gson;
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

public class ImportCitiesRequest {

    public String importCities(List<Cities> list) {
        new Defaults().loadJson();
        String queryString = new Gson().toJson(list);
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("list_cities", queryString));
        String urlString = Defaults.WEBSERVICE + "cities/import";
        try {
            HttpPost httpPost;
            HttpEntity entity;
            String result = "";
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                httpPost = new HttpPost(urlString);
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                HttpResponse response = httpClient.execute(httpPost);
                entity = response.getEntity();
                result = EntityUtils.toString(entity);
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
                }
            }
            httpPost.abort();
            if (entity != null) {
                Gson gson = new Gson();
                NotifyResponse notifyResponse = null;

                try {
                    notifyResponse = gson.fromJson(result, NotifyResponse.class);
                    if (notifyResponse != null) {
                        Messages.warn("Validação", notifyResponse.getObject().toString(), Boolean.TRUE);
                        return null;
                    }
                } catch (Exception e) {

                }
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return "";
    }
}
