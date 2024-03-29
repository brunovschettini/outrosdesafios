package br.com.xptosystems.ws;

import br.com.xptosystems.address.Cities;
import br.com.xptosystems.address.dao.CitiesDao;
import br.com.xptosystems.security.ws.NotifyResponse;
import br.com.xptosystems.dao.Dao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/cities")
public class CitiesWS {

    @Context
    HttpHeaders headers;

    @POST
    @Path("/import")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response importCities(@FormParam("list_cities") String list_cities) {
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

    /**
     * 2 Retornar somente as cidades que são capitais ordenadas por nome
     *
     * @return
     */
    @GET
    @Path("/capitals")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response capitals() {
        return Response.status(200).entity(new Gson().toJson(new CitiesDao().capitals())).build();
    }

    /**
     * 3 Retornar o nome do estado com a maior e menor quantidade de cidades e a
     * quantidade de cidades
     *
     * @return
     */
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

    /**
     * 4 Retornar a quantidade de cidades por estao
     *
     * @param uf
     * @return
     */
    @GET
    @Path("/count_by_state/{uf}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response count_by_state(@PathParam("uf") String uf) {
        Integer count = new CitiesDao().count_by_state(uf);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("uf", uf);
        jSONObject.put("amount", count);
        return Response.status(200).entity(jSONObject.toString()).build();
    }

    /**
     * 5 Obter os dados da cidade informando o id do IBGE
     *
     * @param ibge_id
     * @return
     */
    @GET
    @Path("/ibge_id/{ibge_id}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response ibge_id(@PathParam("ibge_id") String ibge_id) {
        Cities cities = new CitiesDao().find_by_ibge_id(ibge_id);
        List<Cities> list = new ArrayList();
        if(cities != null) {
            list.add(cities);            
        }
        return Response.status(200).entity(new Gson().toJson(list)).build();
    }

    /**
     * 6 Retornar o nome das cidades baseado em um estado selecionado
     *
     * @param uf
     * @return
     */
    @GET
    @Path("/uf/{uf}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response find_uf(@PathParam("uf") String uf) {
        List<Cities> list = new CitiesDao().find_by_uf(uf);
        return Response.status(200).entity(new Gson().toJson(list)).build();
    }

    /**
     * 7 Permitir adicionar uma nova Cidade
     *
     *
     * @param ibge_id
     * @param uf
     * @param name
     * @param capital
     * @param lon
     * @param lat
     * @param no_accents
     * @param alternative_names
     * @param micro_region
     * @param meso_region
     * @return
     */
    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public synchronized Response insert(
            @FormParam("ibge_id") String ibge_id,
            @FormParam("uf") String uf,
            @FormParam("name") String name,
            @FormParam("capital") String capital,
            @FormParam("lon") String lon,
            @FormParam("lat") String lat,
            @FormParam("no_accents") String no_accents,
            @FormParam("alternative_names") String alternative_names,
            @FormParam("micro_region") String micro_region,
            @FormParam("meso_region") String meso_region
    ) {
        NotifyResponse notifyResponse = new NotifyResponse();
        Gson gson = new Gson();

        if (ibge_id == null || ibge_id.isEmpty()) {
            notifyResponse.setObject("Informar o Id do Ibge!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        if (name == null || name.isEmpty()) {
            notifyResponse.setObject("Informar o nome da ciade!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        if (uf == null || uf.isEmpty()) {
            notifyResponse.setObject("Informar a UF / Estado da ciade!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        if (lat == null || lat.isEmpty()) {
            notifyResponse.setObject("Informar as cordenadas de latitude!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        if (lon == null || lon.isEmpty()) {
            notifyResponse.setObject("Informar as cordenadas de longitude!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        if (capital == null || capital.isEmpty()) {
            notifyResponse.setObject("Informar se a cidade é uma capital!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        Cities city = new Cities(null, Long.parseLong(ibge_id), uf, name, Boolean.parseBoolean(capital), Double.parseDouble(lon), Double.parseDouble(lat), no_accents, alternative_names, micro_region, meso_region, new Date());
        String result = "";
        if (!new Dao().save(city, true)) {
            notifyResponse.setObject("Error");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        notifyResponse.setObject("Success");
        return Response.status(200).entity(gson.toJson(notifyResponse)).build();
    }

    @POST
    @Path("/store")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response store(@FormParam("cities") String citiesJson) {
        Gson gson = new Gson();
        NotifyResponse notifyResponse = new NotifyResponse();
        Cities c = new Gson().fromJson(citiesJson, Cities.class);

        if (c.getIbge_id() == null || c.getIbge_id().equals(new Long(0))) {
            notifyResponse.setObject("Informar o Id do Ibge!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        if (c.getName() == null || c.getName().isEmpty()) {
            notifyResponse.setObject("Informar o nome da ciade!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        if (c.getUf() == null || c.getUf().isEmpty()) {
            notifyResponse.setObject("Informar a UF / Estado da ciade!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        if (c.getLat() == null) {
            notifyResponse.setObject("Informar as cordenadas de latitude!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        if (c.getLon() == null) {
            notifyResponse.setObject("Informar as cordenadas de longitude!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        if (c.getCapital() == null) {
            notifyResponse.setObject("Informar se a cidade é uma capital!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        String result = "Cities cities: " + c;
        CitiesDao citiesDao = new CitiesDao();
        if (citiesDao.exists(c) != null) {
            notifyResponse.setObject("Cidade para esse UF já cadastrada!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        if (citiesDao.findByIBGE(c.getIbge_id()) != null) {
            notifyResponse.setObject("Cidade já cadastrada com esse código do IBGE!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        Dao dao = new Dao();
        if (!dao.save(c, true)) {
            notifyResponse.setObject("Erro ao inserir registro!");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        return Response.status(200).entity(gson.toJson(c)).build();

    }

    /**
     * 8 Permitir deletar uma cidade
     *
     * @return
     */
    @DELETE
    @Path("/delete")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response delete() {
        NotifyResponse notifyResponse = new NotifyResponse();
        Gson gson = new Gson();
        String ibge_id = headers.getRequestHeader("ibge_id").get(0);
        if (ibge_id == null || ibge_id.isEmpty()) {
            notifyResponse.setObject("Empty / null ibge id");
            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
        }
        Cities c = new CitiesDao().findByIBGE(Long.parseLong(ibge_id));
        if (c != null) {
            Dao dao = new Dao();
            if (!dao.delete(c, true)) {
                notifyResponse.setObject("Erro ao excluir registro!");
                return Response.status(200).entity(gson.toJson(notifyResponse)).build();
            }
        }
        return Response.status(200).entity(gson.toJson(c)).build();
    }

    /**
     * 9 Permitir selecionar uma coluna (do CSV) e através dela entrar com uma
     * string para filtrar. retornar assim todos os objetos que contenham tal
     * string
     *
     * @param column
     * @param query
     * @return
     */
    @GET
    @Path("/search/{column}/{query}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response find(@PathParam("column") String column, @PathParam("query") String query) {
        List<Cities> list = new CitiesDao().find(column, query);
        return Response.status(200).entity(new Gson().toJson(list)).build();
    }

    /**
     * 10 Retornar a quantidade de registro baseado em uma coluna. Não deve
     * contar itens iguais
     *
     * @param column
     * @return
     */
    @GET
    @Path("/total_column/{column}")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response total_column(@PathParam("column") String column) {
        Integer total = new CitiesDao().total(column);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("total", total);
        return Response.status(200).entity(jSONObject.toString()).build();
    }

    @GET
    @Path("/total")
    @Produces({MediaType.APPLICATION_JSON})
    public synchronized Response total() {
        Integer total = new CitiesDao().total();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("total", total);
        return Response.status(200).entity(jSONObject.toString()).build();
    }

}
