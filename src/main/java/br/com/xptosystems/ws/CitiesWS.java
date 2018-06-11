package br.com.xptosystems.ws;

import br.com.xptosystems.address.Cities;
import br.com.xptosystems.address.dao.CitiesDao;
import br.com.xptosystems.security.ws.NotifyResponse;
import br.com.xptosystems.dao.Dao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/cities")
public class CitiesWS {

    @POST
    @Path("/import")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response auth(@FormParam("list_cities") String list_cities) {
        NotifyResponse notifyResponse = new NotifyResponse();
        Gson gson = new Gson();
        List<Cities> listCities = new ArrayList();
        listCities = gson.fromJson(list_cities, new TypeToken<List<Cities>>() {
        }.getType());
        Dao dao = new Dao();
        for (int i = 0; i < listCities.size(); i++) {
            dao.save(listCities.get(i), true);
        }
        notifyResponse.setObject("OK");
        return Response.status(200).entity(gson.toJson(notifyResponse)).build();
    }

    @GET
    @Path("/capitals")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response capitals() {
        return Response.status(200).entity(new Gson().toJson(new CitiesDao().capitals())).build();
    }

    @GET
    @Path("/min_max_cities_by_state")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response min_max_cities_by_state() {
        List<Object[]> rows = new CitiesDao().min_max_cities_by_state();
        JSONArray array = new JSONArray();
        for (Object[] row : rows) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("uf", row[0]);
            jSONObject.put("amount", row[1]);
            array.put(jSONObject);
        }
        return Response.status(200).entity(array.toString()).build();
    }

    @GET
    @Path("/count_cities_by_state/{uf}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response count_cities_by_state(@PathParam("uf") String uf) {
        Integer count = new CitiesDao().count_cities_by_state(uf);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("uf", uf);
        jSONObject.put("amount", count);
        return Response.status(200).entity(jSONObject.toString()).build();
    }

    @GET
    @Path("/find/ibge_id/{ibge_id}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response find_by_ibge_id(@PathParam("ibge_id") String ibge_id) {
        Cities cities = new CitiesDao().find_by_ibge_id(ibge_id);
        return Response.status(200).entity(new Gson().toJson(cities)).build();
    }

    @GET
    @Path("/find/uf/{uf}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response find_by_uf(@PathParam("uf") String uf) {
        List<Cities> list = new CitiesDao().find_by_uf(uf);
        return Response.status(200).entity(new Gson().toJson(list)).build();
    }
    
    @GET
    @Path("/total/{uf}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response find_by_uf(@PathParam("uf") String uf) {
        List<Cities> list = new CitiesDao().find_by_uf(uf);
        return Response.status(200).entity(new Gson().toJson(list)).build();
    }
    
    @GET
    @Path("/total/{uf}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response find_by_uf(@PathParam("uf") String uf) {
        List<Cities> list = new CitiesDao().find_by_uf(uf);
        return Response.status(200).entity(new Gson().toJson(list)).build();
    }

}
