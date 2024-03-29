package br.com.xptosystems.dao;

import br.com.xptosystems.utils.Types;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

public class Dao extends DB {

    /**
     * <p>
     * <strong>Open Transaction</strong></p>
     *
     * @author Bruno
     */
    public void openTransaction() {
        getEntityManager().getTransaction().begin();
    }

    /**
     * <p>
     * <strong>Commit</strong></p>
     *
     * @author Bruno
     */
    public void commit() {
        getEntityManager().getTransaction().commit();
    }

    /**
     * <p>
     * <strong>Rollback</strong></p>
     *
     * @author Bruno
     */
    public void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public void flush() {
        getEntityManager().flush();
    }

    /**
     * <p>
     * <strong>Active Session</strong></p>
     *
     * @author Bruno
     *
     * @return true or false
     */
    public boolean activeSession() {
        return getEntityManager().getTransaction().isActive();
    }

    /**
     * <p>
     * <strong>Save</strong></p>
     *
     * @param object
     *
     * @author Bruno
     *
     * @return true or false
     */
    public boolean save(final Object object) {
        if (!activeSession()) {
            return false;
        }
        try {
            getEntityManager().persist(object);
            getEntityManager().flush();
            return true;
        } catch (Exception e) {

            return false;
        } finally {
            // getEntityManager().close();
        }
    }

    /**
     * <p>
     * <strong>Save transaction automatic</strong></p>
     *
     * @param object
     * @param transactionComplete
     * @author Bruno INSERT INTO security_group_routines (a, d, g, p, r,
     * record_date, w, client, group, routine) VALUES (0, 0, 0, 0, 0,
     * '2018-12-01 19:12:16.142', 0, 1, 1, 11)
     * @return true or false
     */
    public boolean save(final Object object, boolean transactionComplete) {
        if (activeSession()) {
            return false;
        }
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(object);
            getEntityManager().flush();
            getEntityManager().getTransaction().commit();
            return true;
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();

            return false;
        }
    }

    /**
     * <p>
     * <strong>Update</strong></p>
     *
     * @param objeto
     * @author Bruno
     *
     * @return true or false
     */
    public boolean update(final Object objeto) {
        if (!activeSession()) {
            return false;
        }
        Class classe = objeto.getClass();
        Long id;
        try {
            Method metodo = classe.getMethod("getId", new Class[]{});
            id = (Long) metodo.invoke(objeto, (Object[]) null);
            if (id == null) {
                return false;
            }
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            return false;
        }
        try {
            getEntityManager().merge(objeto);
            getEntityManager().flush();
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * <p>
     * <strong>Updatetransaction automatic</strong></p>
     *
     * @param objeto
     * @param transactionComplete
     *
     * @author Bruno
     *
     * @return true or false
     */
    public boolean update(final Object objeto, boolean transactionComplete) {
        if (activeSession()) {
            return false;
        }
        Class classe = objeto.getClass();
        Long id;
        try {
            Method metodo = classe.getMethod("getId", new Class[]{});
            id = (Long) metodo.invoke(objeto, (Object[]) null);
            if (id == null) {
                return false;
            }
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            return false;
        }
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().merge(objeto);
            getEntityManager().flush();
            getEntityManager().getTransaction().commit();
            return true;
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();

            return false;
        }
    }

    /**
     * <p>
     * <strong>Delete</strong></p>
     *
     * @param object
     * @author Bruno
     *
     * @return true or false
     */
    public boolean delete(final Object object) {
        if (!activeSession()) {
            return false;
        }
        try {
            getEntityManager().remove(find(object));
            getEntityManager().flush();
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * <p>
     * <strong>Delete automatic</strong></p>
     *
     * @param object
     * @param transactionComplete
     *
     * @author Bruno
     *
     * @return true or false
     */
    public boolean delete(final Object object, boolean transactionComplete) {
        if (activeSession()) {
            return false;
        }
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(find(object));
            getEntityManager().flush();
            getEntityManager().getTransaction().commit();
            return true;
        } catch (Exception e) {
            getEntityManager().getTransaction().rollback();

            return false;
        }
    }

    public Object rebind(Object object) {
        try {
            openTransaction();
            object = find(object);
            getEntityManager().merge(object);
            getEntityManager().refresh(object);
            getEntityManager().flush();
            commit();
        } catch (Exception e) {
            
        }
        return object;
    }

    public void refresh(Object object) {
        try {
            openTransaction();
            object = find(object);
            getEntityManager().merge(object);
            getEntityManager().refresh(object);
            if (!getEntityManager().getTransaction().isActive()) {
                return;
            }
            getEntityManager().flush();
            commit();
        } catch (Exception e) {

        }
    }

    /**
     * <p>
     * <strong>Find Object</strong></p>
     * <p>
     * <strong>Exemplo:</strong>User user = new User(1, "Paul"); find(user);</p>
     *
     * @param object (Nome do objeto String)
     *
     * @author Bruno
     *
     * @return Object
     */
    public Object find(final Object object) {
        return find(object, null);
    }

    /**
     * <p>
     * <strong>Find Object</strong></p>
     * <p>
     * <strong>Exemplo:</strong>find("User" or new User(), objectId); </p>
     *
     * @param object (Nome do objeto String)
     * @param objectId (Id a ser pesquisado)
     *
     * @author Bruno
     *
     * @return Object
     */
    public Object find(Object object, final Object objectId) {
        if (object == null) {
            return null;
        }
        if (objectId == null) {
            Long id;
            try {
                Class classe = object.getClass();
                Method metodo = classe.getMethod("getId", new Class[]{});
                id = (Long) metodo.invoke(object, (Object[]) null);
                if (id == null) {
                    return null;
                }
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                return null;
            }
            object = getEntityManager().find(object.getClass(), id);
        } else {
            try {
                object = getEntityManager().find(object.getClass(), objectId);
            } catch (Exception e) {
                return null;
            }
        }
        return object;
    }

    /**
     * <p>
     * <strong>Find Object</strong></p>
     * <p>
     * <strong>Exemplo:</strong>find("User", new int[]{1,2,3,4,5}); </p>
     *
     * @param id (Lista de ids)
     * @param className (Nome da classe)
     *
     * @author Bruno
     *
     * @return List
     */
    public List find(String className, Long id[]) {
        return find(className, id, "");
    }

    /**
     * <p>
     * <strong>Find Object</strong></p>
     * <p>
     * <strong>Exemplo:</strong>find("User", new int[]{1,2,3,4,5}, "id_person");
     * </p>
     *
     * @param id (Lista de ids)
     * @param className (Nome da classe)
     * @param field (Faz a consulta utilizando outro campo que não id como
     * parâmetro)
     *
     * @author Bruno
     *
     * @return List
     */
    public List find(String className, Long id[], String field) {
        String stringCampo = "id";
        if (!field.isEmpty()) {
            stringCampo = field;
        }
        List list = new ArrayList<>();
        String queryPesquisaString = "";
        for (int i = 0; i < id.length; i++) {
            if (i == 0) {
                queryPesquisaString = Long.toString(id[i]);
            } else {
                queryPesquisaString += ", " + Long.toString(id[i]);
            }
            String queryString = "SELECT OB FROM " + className + " OB WHERE OB." + stringCampo + " IN (" + queryPesquisaString + ")";
            Query query = getEntityManager().createQuery(queryString);
            list = query.getResultList();
            if (list.isEmpty()) {
                return list;
            }
        }
        return list;
    }

    /**
     * <p>
     * <strong>List</strong></p>
     * <p>
     * <strong>Exemplo:</strong> list(new User()).</p>
     *
     * @param className (Nome do objeto String)
     *
     * @author Bruno
     *
     * @return List
     */
    public List list(Object className) {
        String name = className.getClass().getSimpleName();
        return list(name);
    }

    /**
     * <p>
     * <strong>List</strong></p>
     * <p>
     * <strong>Exemplo:</strong> list("User").</p>
     *
     * @param className (Nome do objeto String)
     *
     * @author Bruno
     *
     * @return List
     */
    public List list(String className) {
        List result = new ArrayList();
        String queryString = "SELECT OB FROM " + className + " AS OB";
        try {
            Query qry = getEntityManager().createQuery(queryString);
            List list = qry.getResultList();
            if (!list.isEmpty()) {
                result = qry.getResultList();
            }
        } catch (Exception e) {

        }
        return result;
    }

    public List list(Object className, boolean order) {
        return list(className.getClass().getSimpleName(), order);
    }

    /**
     * <p>
     * <strong>List</strong></p>
     * <p>
     * <strong>Exemplo:</strong> list("User", boolean (true or false)).</p>
     *
     * @param className (Nome do objeto String)
     * @param order [Se o resultado deve ser ordenado (Verificar se a namedQuery
     * esta na Classe/Entidade)]
     *
     * @author Bruno
     *
     * @return List
     */
    public List list(String className, boolean order) {
        try {
            Query query = getEntityManager().createNamedQuery(className + ".findAll");
            List list = query.getResultList();
            if (!list.isEmpty()) {
                return list;
            }
        } catch (Exception e) {
            return new ArrayList();
        }
        return new ArrayList();
    }

    /**
     * <p>
     * <strong>List Query</strong></p>
     * <p>
     * <strong>Exemplo:</strong> E@NamedQuery(name = "Object.find", query =
     * "SELECT O FROM Object AS O WHERE O.id = :p1") Uso: listQuery(Object,
     * find, {1}) Exemplo 2 @NamedQuery(name = "Object.find", query = "SELECT O
     * FROM Object AS O WHERE O.id = :p1 AND O.description = :p2") Uso:
     * listQuery(Object, find, {1, 'Feliz'}).</p>
     *
     * @param className (Nome do objeto)
     * @param find (Nome da NamedQuery dentro do objeto)
     *
     * @author Bruno
     *
     * @return List
     */
    public List listQuery(Object className, String find) {
        return listQuery(className.getClass().getSimpleName(), find, new Object[]{});
    }

    /**
     * <p>
     * <strong>List Query</strong></p>
     * <p>
     * <strong>Exemplo:</strong> E@NamedQuery(name = "Object.find", query =
     * "SELECT O FROM Object AS O WHERE O.id = :p1") Uso: listQuery(Object,
     * find, {1}) Exemplo 2 @NamedQuery(name = "Object.find", query = "SELECT O
     * FROM Object AS O WHERE O.id = :p1 AND O.description = :p2") Uso:
     * listQuery(Object, find, {1, 'Feliz'}).</p>
     *
     * @param className (Nome do objeto)
     * @param find (Nome da NamedQuery dentro do objeto)
     * @param params (Cria se parâmetros organizados para realizar a consulta)
     *
     * @author Bruno
     *
     * @return List
     */
    public List listQuery(Object className, String find, Object[] params) {
        return listQuery(className.getClass().getSimpleName(), find, params);
    }

    /**
     * <p>
     * <strong>List Query</strong></p>
     * <p>
     * <strong>Exemplo:</strong> E@NamedQuery(name = "Object.find", query =
     * "SELECT O FROM Object AS O WHERE O.id = :p1") Uso: listQuery("Object",
     * find, {1}) Exemplo 2 @NamedQuery(name = "Object.find", query = "SELECT O
     * FROM Object AS O WHERE O.id = :p1 AND O.description = :p2") Uso:
     * listQuery(Object, find, {1, 'Feliz'}).</p>
     *
     * @param className (Nome do objeto)
     * @param find (Nome da NamedQuery dentro do objeto)
     * @param params (Cria se parâmetros organizados para realizar a consulta)
     *
     * @author Bruno
     *
     * @return List
     */
    public List listQuery(String className, String find, Object[] params) {
        try {
            Query query = getEntityManager().createNamedQuery(className + "." + find);
            int y = 1;
            for (Object param : params) {
                if (Types.isInteger(param)) {
                    try {
                        query.setParameter("p" + y, Integer.parseInt((String) param));
                    } catch (NumberFormatException e) {
                        query.setParameter("p" + y, param);
                    }
                } else if (Types.isFloat(param)) {
                    query.setParameter("p" + y, Float.parseFloat((String) param));
                } else if (Types.isBigInteger(param)) {
                    query.setParameter("p" + y, Double.parseDouble((String) param));
                } else {
                    query.setParameter("p" + y, (String) param);
                }
                y++;
            }
            List list = query.getResultList();
            if (!list.isEmpty()) {
                return list;
            }
        } catch (Exception e) {
            return new ArrayList();
        }
        return new ArrayList();
    }

    /**
     * <p>
     * <strong>Live List</strong></p>
     * <p>
     * <strong>Exemplos:</strong>Exemplo 1: liveList(SELECT U FROM User AS U);
     * Set nativeQuery = true. Caso não encontre nenhum resultado retorna uma
     * lista vazia; Se houver algum erro retorna lista vazia;</p>
     *
     * @param queryString
     *
     * @author Bruno
     *
     * @return List
     */
    public List liveList(String queryString) {
        return liveList(queryString, false, 0);
    }

    /**
     * <p>
     * <strong>Live List</strong></p>
     * <p>
     * <strong>Exemplos:</strong>Exemplo 1: liveList(SELECT U FROM User AS U);
     * Set nativeQuery = true Exemplo 2: liveList(select * from user as u,
     * true); Set maxResults = 5. Caso não encontre nenhum resultado retorna uma
     * lista vazia; Se houver algum erro retorna lista vazia;</p>
     *
     * @param queryString
     * @param nativeQuery
     *
     * @author Bruno
     *
     * @return List
     */
    public List liveList(String queryString, boolean nativeQuery) {
        return liveList(queryString, nativeQuery, 0);
    }

    /**
     * <p>
     * <strong>Live List</strong></p>
     * <p>
     * <strong>Exemplos:</strong>Exemplo 1: liveList(SELECT U FROM User AS U);
     * Set nativeQuery = true Exemplo 2: liveList(select * from user as u,
     * true); Set maxResults = 5 Exemplo 3: liveList(SELECT U FROM User AS U,
     * true, 5) Caso não encontre nenhum resultado retorna uma lista vazia; Se
     * houver algum erro retorna lista vazia;</p>
     *
     * @param queryString
     * @param nativeQuery
     * @param maxResults
     *
     * @author Bruno
     *
     * @return List
     */
    public List liveList(String queryString, boolean nativeQuery, int maxResults) {
        try {
            Query query;
            if (nativeQuery) {
                query = getEntityManager().createNativeQuery(queryString);
            } else {
                query = getEntityManager().createQuery(queryString);
            }
            if (maxResults > 0) {
                query.setMaxResults(maxResults);
            }
            List list = query.getResultList();
            if (!list.isEmpty()) {
                return list;
            }
        } catch (Exception e) {

        }
        return new ArrayList();
    }

    /**
     * <p>
     * <strong>Live Object</strong></p>
     * <p>
     * <strong>Exemplos:</strong>Exemplo 1: liveSingle(SELECT U FROM User AS U);
     * Set nativeQuery = true; Retorna somente um resultado, se houver mais de
     * um retornará null; Caso não encontre nenhum resultado retorna null;</p>
     *
     * @param queryString
     *
     * @author Bruno
     *
     * @return Object
     */
    public Object liveSingle(String queryString) {
        return liveSingle(queryString, false);
    }

    /**
     * <p>
     * <strong>Live Object</strong></p>
     * <p>
     * <strong>Exemplos:</strong>Exemplo 1: liveSingle(SELECT U FROM User AS U);
     * Set nativeQuery = true Exemplo 2: liveSingle(select * from user as u,
     * true); Retorna somente um resultado, se houver mais de um retornará null;
     * Caso não encontre nenhum resultado retorna null;</p>
     *
     * @param queryString
     * @param nativeQuery
     *
     *
     * @author Bruno
     *
     * @return Object
     */
    public Object liveSingle(String queryString, boolean nativeQuery) {
        try {
            Query query;
            if (nativeQuery) {
                query = getEntityManager().createNativeQuery(queryString);
            } else {
                query = getEntityManager().createQuery(queryString);
            }
            List list = query.getResultList();
            if (!list.isEmpty()) {
                if (list.size() == 1) {
                    return query.getSingleResult();
                }
                return null;
            }
        } catch (Exception e) {

        }
        return null;
    }
}
