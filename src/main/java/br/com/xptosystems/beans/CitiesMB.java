package br.com.xptosystems.beans;

import br.com.xptosystems.address.Cities;
import br.com.xptosystems.security.ws.CitiesRequest;
import br.com.xptosystems.utils.Messages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CitiesMB implements Serializable {

    private List<Cities> listCities;
    private Cities city;
    private Integer amount;
    private String filter;
    private String by;
    private String query;
    private String uf;

    public CitiesMB() {
        listCities = new ArrayList();
        city = new Cities();
        amount = 0;
        uf = "sp";
    }

    public void save() {
        if (city.getName().isEmpty()) {
            Messages.warn("Validação", "Informar o nome da cidade!");
            return;
        }
        if (city.getUf().isEmpty()) {
            Messages.warn("Validação", "Informar o UF!");
            return;
        }
    }

    public void load() {

        listCities = new ArrayList();

        switch (filter) {
            case "capitals":
                listCities = new CitiesRequest().find(filter, by, query);
                break;
            case "find_by_ibge_id":
                listCities = new CitiesRequest().find(filter, by, query);
                break;
            case "find_column_query":
                listCities = new CitiesRequest().find(filter, by, query);
                break;
            default:
                break;
        }

    }

    public List<Cities> getListCities() {
        return listCities;
    }

    public void setListCities(List<Cities> listCities) {
        this.listCities = listCities;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

}
