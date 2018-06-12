package br.com.xptosystems.address.dao;

import br.com.xptosystems.address.Cities;
import br.com.xptosystems.dao.DB;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

public class CitiesDao extends DB {

    public List<Cities> capitals() {
        try {
            Query query = getEntityManager().createQuery("SELECT C FROM Cities AS C WHERE C.capital = true ORDER BY C.name");
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List min_max_cities_by_state() {
        try {
            String queryString = ""
                    + "(\n"
                    + "    SELECT \n"
                    + "    uf, count(*) city_count\n"
                    + "    FROM cities\n"
                    + "    GROUP BY uf\n"
                    + "    ORDER BY count(*) ASC \n"
                    + "    LIMIT 1\n"
                    + ") \n"
                    + "UNION ALL\n"
                    + "(\n"
                    + "    SELECT \n"
                    + "    uf, count(*) city_count\n"
                    + "    FROM cities\n"
                    + "    GROUP BY uf\n"
                    + "    ORDER BY count(*) DESC \n"
                    + "    LIMIT 1\n"
                    + ") ";
            Query query = getEntityManager().createNativeQuery(queryString);

            List list = query.getResultList();
            return list;
        } catch (Exception e) {
        }

        return null;
    }

    public Integer count_by_state(String uf) {
        try {
            String queryString = ""
                    + "SELECT count(*) \n"
                    + "FROM cities C\n"
                    + "WHERE UPPER(C.uf) = UPPER('" + uf + "')";
            Query query = getEntityManager().createNativeQuery(queryString);
            List result = query.getResultList();
            List<Object[]> rows = result;
            for (Object[] row : rows) {
                return Integer.parseInt(row[0].toString());
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    public Cities find_by_ibge_id(String code) {
        try {
            Query query = getEntityManager().createQuery("SELECT C FROM Cities C WHERE C.id = :code");
            query.setParameter("code", Long.parseLong(code));
            return (Cities) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Cities> find_by_uf(String uf) {
        try {
            Query query = getEntityManager().createQuery("SELECT C FROM Cities C WHERE UPPER(C.uf) = :uf ORDER BY C.name ASC");
            query.setParameter("uf", uf.toUpperCase());
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public List<Cities> find(String column, String query) {
        if (column == null || column.isEmpty()) {
            return new ArrayList();
        }
        if (query == null || query.isEmpty()) {
            return new ArrayList();
        }
        String queryString = "";
        switch (column) {
            case "ibde_id":
                queryString = "SELECT C.* FROM cities C WHERE C.ibde_id = " + query + " ORDER BY C.name ASC";
                break;
            case "uf":
                queryString = "SELECT C.* FROM cities C WHERE C.uf = '" + query + "' ORDER BY C.uf, C.name ASC";
                break;
            case "name":
                queryString = "SELECT C.* FROM cities C WHERE C.name LIKE '%" + query + "%' ORDER BY C.uf, C.name ASC";
                break;
            case "lon":
                queryString = "SELECT C.* FROM cities C WHERE C.lon = " + query + " ORDER BY C.name ASC";
                break;
            case "lat":
                queryString = "SELECT C.* FROM cities C WHERE C.lat = " + query + " ORDER BY C.name ASC";
                break;
            case "no_accents":
                queryString = "SELECT C.* FROM cities C WHERE C.no_accents LIKE '%" + query + "%' ORDER BY C.uf, C.no_accents ASC";
                break;
            case "alternative_names":
                queryString = "SELECT C.* FROM cities C WHERE C.alternative_names LIKE '%" + query + "%' ORDER BY C.uf, C.alternative_names ASC";
                break;
            case "micro_region":
                queryString = "SELECT C.* FROM cities C WHERE C.micro_region LIKE '%" + query + "%' ORDER BY C.uf, C.micro_region ASC";
                break;
            case "meso_region":
                queryString = "SELECT C.* FROM cities C WHERE C.meso_region LIKE '%" + query + "%' ORDER BY C.uf, C.meso_region ASC";
                break;
            default:
                break;
        }
        try {
            Query q = getEntityManager().createNativeQuery(queryString, Cities.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public Integer total() {
        try {
            String queryString = ""
                    + "SELECT count(*) \n"
                    + "FROM cities C\n";
            Query query = getEntityManager().createNativeQuery(queryString);
            List result = query.getResultList();
            List<Object[]> rows = result;
            for (Object[] row : rows) {
                return Integer.parseInt(row[0].toString());
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    public Integer total(String column) {
        if (column == null || column.isEmpty()) {
            return 0;
        }
        String queryString = "";
        switch (column) {
            case "ibde_id":
                queryString = "SELECT C.* FROM cities C WHERE C.ibde_id != null ORDER BY C.name ASC";
                break;
            case "uf":
                queryString = "SELECT C.* FROM cities C WHERE C.uf <> '' ORDER BY C.uf, C.name ASC";
                break;
            case "name":
                queryString = "SELECT C.* FROM cities C WHERE C.name <> '' ORDER BY C.uf, C.name ASC";
                break;
            case "lon":
                queryString = "SELECT C.* FROM cities C WHERE C.lon != null ORDER BY C.name ASC";
                break;
            case "lat":
                queryString = "SELECT C.* FROM cities C WHERE C.lat != null ORDER BY C.name ASC";
                break;
            case "no_accents":
                queryString = "SELECT C.* FROM cities C WHERE C.no_accents <> '' ORDER BY C.uf, C.no_accents ASC";
                break;
            case "alternative_names":
                queryString = "SELECT C.* FROM cities C WHERE C.alternative_names <> '' ORDER BY C.uf, C.alternative_names ASC";
                break;
            case "micro_region":
                queryString = "SELECT C.* FROM cities C WHERE C.micro_region <> ''' ORDER BY C.uf, C.micro_region ASC";
                break;
            case "meso_region":
                queryString = "SELECT C.* FROM cities C WHERE C.meso_region <> '' ORDER BY C.uf, C.meso_region ASC";
                break;
            case "capital":
                queryString = "SELECT C.* FROM cities C WHERE C.capital = 1 ORDER BY C.uf, C.meso_region ASC";
                break;
            default:
                break;
        }
        try {
            Query q = getEntityManager().createNativeQuery(queryString, Cities.class);
            return q.getResultList().size();
        } catch (Exception e) {
            return 0;
        }
    }

}
