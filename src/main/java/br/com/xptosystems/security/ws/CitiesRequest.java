package br.com.xptosystems.security.ws;

import br.com.xptosystems.address.Cities;
import br.com.xptosystems.utils.Defaults;
import br.com.xptosystems.utils.Messages;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class CitiesRequest {

    public List<Cities> find(String tcase, String by, String query) {
        new Defaults().loadJson();
        String urlString = Defaults.WEBSERVICE + "cities/";
        if (tcase.equals("capitals")) {
            urlString += "capitals";
        } else if (tcase.equals("find_by_ibge_id")) {
            urlString += "find/ibge_id/" + query;
        } else if (tcase.equals("find_column_query")) {
            urlString += "find/" + by + "/" + query;
        }

        String result = request(urlString);
        if (result != null && !result.isEmpty()) {
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

            List<Cities> list = gson.fromJson(result, new TypeToken<List<Cities>>() {
            }.getType());
            return list;
        }

        return new ArrayList();
    }

    protected String request(String url) {
        try {
            HttpGet httpGet;
            HttpEntity entity;
            String result = "";
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                httpGet = new HttpGet(url);
                HttpResponse response = httpClient.execute(httpGet);
                entity = response.getEntity();
                result = EntityUtils.toString(entity);
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
                }
            }
            httpGet.abort();
            return result;
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return "";
    }

}
