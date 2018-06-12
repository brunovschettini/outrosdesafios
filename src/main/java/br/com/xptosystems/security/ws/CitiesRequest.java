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
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class CitiesRequest {

    public List<Cities> find(String tcase, String by, String query, String uf) {

        new Defaults().loadJson();
        String urlString = Defaults.WEBSERVICE + "cities/";
        switch (tcase) {
            case "capitals":
                urlString += "capitals";
                break;
            case "find_by_ibge_id":
                if (query.isEmpty()) {
                    return new ArrayList();
                }
                urlString += "ibge_id/" + query;
                break;
            case "find_by_uf":
                if (query.isEmpty()) {
                    return new ArrayList();
                }
                urlString += "uf/" + uf;
                break;
            case "find_column_query":
                if (by.isEmpty()) {
                    return new ArrayList();
                }
                if (query.isEmpty()) {
                    return new ArrayList();
                }
                urlString += "search/" + by + "/" + query;
                break;
            case "min_max_cities_by_state":
                urlString += "min_max_cities_by_state";
                break;
            case "total":
                urlString += "total";
                break;
            case "total_columns":
                if (by.isEmpty()) {
                    return new ArrayList();
                }
                urlString += "total_column/" + by;
                break;
            case "count_by_state":
                if (uf.isEmpty()) {
                    return new ArrayList();
                }
                urlString += "count_by_state/" + uf;
                break;
            default:
                break;
        }

        String result = get(urlString);
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

            if (others(tcase, result)) {
                return new ArrayList();
            }

            List<Cities> list = new ArrayList();
            try {
                list = gson.fromJson(result, new TypeToken<List<Cities>>() {
                }.getType());

            } catch (Exception e) {

            }
            Messages.warn("Total de registros", list.size() + "", Boolean.TRUE);
            return list;
        }

        return new ArrayList();
    }

    public Boolean others(String tcase, String result) {

        try {
            switch (tcase) {
                case "min_max_cities_by_state":
                case "total":
                case "total_columns":
                case "count_by_state":
                    Messages.info("Resposta do servidor: ", result, true);
                    return true;
                default:
                    return false;
            }
        } catch (Exception e) {

        }
        return false;
    }

    protected String get(String url) {
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

    public Cities store(Cities c) {
        String urlString = Defaults.WEBSERVICE + "cities/store";
        try {
            String result = "";
            HttpEntity entity;

            List<NameValuePair> params = new ArrayList();
            String cities = new Gson().toJson(c);
            params.add(new BasicNameValuePair("cities", cities));
            // params.add(new BasicNameValuePair("name", ""));
            Gson gson = new Gson();
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(urlString);
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                HttpResponse response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() != 200) {
//                    throw new RuntimeException("Failed : HTTP error code : "
//                            + response.getStatusLine().getStatusCode());
                }

                entity = response.getEntity();
                result = EntityUtils.toString(entity);

                httpPost.abort();
            }

            if (result != null && !result.isEmpty()) {
                try {
                    NotifyResponse notifyResponse = gson.fromJson(result, NotifyResponse.class);
                    if (notifyResponse != null) {
                        Messages.warn("Validação", notifyResponse.getObject().toString(), Boolean.TRUE);
                        return null;
                    }
                } catch (Exception e) {

                }
                c = gson.fromJson(result, Cities.class);
                if (c == null) {
                    Messages.warn("Validação", "Erro ao inserir registro", Boolean.TRUE);
                    return c;
                }
                Messages.info("Sucesso", "Registro inserido", Boolean.TRUE);
                return c;
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }

    public Cities delete(Cities c) {
        String urlString = Defaults.WEBSERVICE + "cities/delete";
        try {
            String result = "";
            HttpEntity entity;

            String cities = new Gson().toJson(c);
            // params.add(new BasicNameValuePair("name", ""));
            Gson gson = new Gson();
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpDelete httpDelete = new HttpDelete(urlString);
                httpDelete.setHeader("ibge_id", c.getIbge_id_string());
                HttpResponse response = httpClient.execute(httpDelete);
                if (response.getStatusLine().getStatusCode() != 200) {
//                    throw new RuntimeException("Failed : HTTP error code : "
//                            + response.getStatusLine().getStatusCode());
                }
                entity = response.getEntity();
                result = EntityUtils.toString(entity);
                httpDelete.abort();
            }

            if (result != null && !result.isEmpty()) {
                try {
                    NotifyResponse notifyResponse = gson.fromJson(result, NotifyResponse.class);
                    if (notifyResponse != null) {
                        Messages.warn("Validação", notifyResponse.getObject().toString(), Boolean.TRUE);
                        return null;
                    }
                } catch (Exception e) {

                }
                c = gson.fromJson(result, Cities.class);
                if (c == null) {
                    Messages.warn("Validação", "Erro ao inserir registro", Boolean.TRUE);
                    return c;
                }
                Messages.info("Sucesso", "Registro inserido", Boolean.TRUE);
                return c;
            }

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }

}
