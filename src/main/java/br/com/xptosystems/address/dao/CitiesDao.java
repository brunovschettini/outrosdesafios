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

    public Integer count_cities_by_state(String uf) {
        try {
            String queryString = ""
                    + "SELECT count(*) \n"
                    + "FROM cities C\n"
                    + "WHERE UPPER(C.uf) = UPPER('" + uf + "')";
            Query query = getEntityManager().createNativeQuery(queryString);
            List result = query.getResultList();
            List<Object[]> rows = result;
            for(Object[] row : rows) {
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
            Query query = getEntityManager().createQuery("SELECT C FROM Cities C WHERE C.id = :uf ORDER BY C.name ASC");
            query.setParameter("uf", uf);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList();
        }
    }
    
    public List<Cities> find_by_uf(String uf) {
        try {
            Query query = getEntityManager().createQuery("SELECT C FROM Cities C WHERE C.id = :uf ORDER BY C.name ASC");
            query.setParameter("uf", uf);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

}
