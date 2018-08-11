/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import entidades.Cafeteria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Estado;
import entidades.Factura;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luis
 */
public class CafeteriaJpaController implements Serializable {

    public CafeteriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cafeteria cafeteria) {
        if (cafeteria.getFacturaCollection() == null) {
            cafeteria.setFacturaCollection(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado idSucursal = cafeteria.getIdSucursal();
            if (idSucursal != null) {
                idSucursal = em.getReference(idSucursal.getClass(), idSucursal.getId());
                cafeteria.setIdSucursal(idSucursal);
            }
            Estado idEstado = cafeteria.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                cafeteria.setIdEstado(idEstado);
            }
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : cafeteria.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getId());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            cafeteria.setFacturaCollection(attachedFacturaCollection);
            em.persist(cafeteria);
            if (idSucursal != null) {
                idSucursal.getCafeteriaCollection().add(cafeteria);
                idSucursal = em.merge(idSucursal);
            }
            if (idEstado != null) {
                idEstado.getCafeteriaCollection().add(cafeteria);
                idEstado = em.merge(idEstado);
            }
            for (Factura facturaCollectionFactura : cafeteria.getFacturaCollection()) {
                Cafeteria oldIdCafeteriaOfFacturaCollectionFactura = facturaCollectionFactura.getIdCafeteria();
                facturaCollectionFactura.setIdCafeteria(cafeteria);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldIdCafeteriaOfFacturaCollectionFactura != null) {
                    oldIdCafeteriaOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldIdCafeteriaOfFacturaCollectionFactura = em.merge(oldIdCafeteriaOfFacturaCollectionFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cafeteria cafeteria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cafeteria persistentCafeteria = em.find(Cafeteria.class, cafeteria.getId());
            Estado idSucursalOld = persistentCafeteria.getIdSucursal();
            Estado idSucursalNew = cafeteria.getIdSucursal();
            Estado idEstadoOld = persistentCafeteria.getIdEstado();
            Estado idEstadoNew = cafeteria.getIdEstado();
            Collection<Factura> facturaCollectionOld = persistentCafeteria.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = cafeteria.getFacturaCollection();
            if (idSucursalNew != null) {
                idSucursalNew = em.getReference(idSucursalNew.getClass(), idSucursalNew.getId());
                cafeteria.setIdSucursal(idSucursalNew);
            }
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                cafeteria.setIdEstado(idEstadoNew);
            }
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getId());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            cafeteria.setFacturaCollection(facturaCollectionNew);
            cafeteria = em.merge(cafeteria);
            if (idSucursalOld != null && !idSucursalOld.equals(idSucursalNew)) {
                idSucursalOld.getCafeteriaCollection().remove(cafeteria);
                idSucursalOld = em.merge(idSucursalOld);
            }
            if (idSucursalNew != null && !idSucursalNew.equals(idSucursalOld)) {
                idSucursalNew.getCafeteriaCollection().add(cafeteria);
                idSucursalNew = em.merge(idSucursalNew);
            }
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getCafeteriaCollection().remove(cafeteria);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getCafeteriaCollection().add(cafeteria);
                idEstadoNew = em.merge(idEstadoNew);
            }
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    facturaCollectionOldFactura.setIdCafeteria(null);
                    facturaCollectionOldFactura = em.merge(facturaCollectionOldFactura);
                }
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    Cafeteria oldIdCafeteriaOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getIdCafeteria();
                    facturaCollectionNewFactura.setIdCafeteria(cafeteria);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldIdCafeteriaOfFacturaCollectionNewFactura != null && !oldIdCafeteriaOfFacturaCollectionNewFactura.equals(cafeteria)) {
                        oldIdCafeteriaOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldIdCafeteriaOfFacturaCollectionNewFactura = em.merge(oldIdCafeteriaOfFacturaCollectionNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cafeteria.getId();
                if (findCafeteria(id) == null) {
                    throw new NonexistentEntityException("The cafeteria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cafeteria cafeteria;
            try {
                cafeteria = em.getReference(Cafeteria.class, id);
                cafeteria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cafeteria with id " + id + " no longer exists.", enfe);
            }
            Estado idSucursal = cafeteria.getIdSucursal();
            if (idSucursal != null) {
                idSucursal.getCafeteriaCollection().remove(cafeteria);
                idSucursal = em.merge(idSucursal);
            }
            Estado idEstado = cafeteria.getIdEstado();
            if (idEstado != null) {
                idEstado.getCafeteriaCollection().remove(cafeteria);
                idEstado = em.merge(idEstado);
            }
            Collection<Factura> facturaCollection = cafeteria.getFacturaCollection();
            for (Factura facturaCollectionFactura : facturaCollection) {
                facturaCollectionFactura.setIdCafeteria(null);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
            }
            em.remove(cafeteria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cafeteria> findCafeteriaEntities() {
        return findCafeteriaEntities(true, -1, -1);
    }

    public List<Cafeteria> findCafeteriaEntities(int maxResults, int firstResult) {
        return findCafeteriaEntities(false, maxResults, firstResult);
    }

    private List<Cafeteria> findCafeteriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cafeteria.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cafeteria findCafeteria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cafeteria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCafeteriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cafeteria> rt = cq.from(Cafeteria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
