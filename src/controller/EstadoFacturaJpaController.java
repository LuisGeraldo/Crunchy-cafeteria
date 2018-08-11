/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import entidades.EstadoFactura;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Factura;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author luis
 */
public class EstadoFacturaJpaController implements Serializable {

    public EstadoFacturaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("prjCafeteriaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoFactura estadoFactura) {
        if (estadoFactura.getFacturaCollection() == null) {
            estadoFactura.setFacturaCollection(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : estadoFactura.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getId());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            estadoFactura.setFacturaCollection(attachedFacturaCollection);
            em.persist(estadoFactura);
            for (Factura facturaCollectionFactura : estadoFactura.getFacturaCollection()) {
                EstadoFactura oldIdEstadoOfFacturaCollectionFactura = facturaCollectionFactura.getIdEstado();
                facturaCollectionFactura.setIdEstado(estadoFactura);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldIdEstadoOfFacturaCollectionFactura != null) {
                    oldIdEstadoOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldIdEstadoOfFacturaCollectionFactura = em.merge(oldIdEstadoOfFacturaCollectionFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoFactura estadoFactura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoFactura persistentEstadoFactura = em.find(EstadoFactura.class, estadoFactura.getId());
            Collection<Factura> facturaCollectionOld = persistentEstadoFactura.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = estadoFactura.getFacturaCollection();
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getId());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            estadoFactura.setFacturaCollection(facturaCollectionNew);
            estadoFactura = em.merge(estadoFactura);
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    facturaCollectionOldFactura.setIdEstado(null);
                    facturaCollectionOldFactura = em.merge(facturaCollectionOldFactura);
                }
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    EstadoFactura oldIdEstadoOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getIdEstado();
                    facturaCollectionNewFactura.setIdEstado(estadoFactura);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldIdEstadoOfFacturaCollectionNewFactura != null && !oldIdEstadoOfFacturaCollectionNewFactura.equals(estadoFactura)) {
                        oldIdEstadoOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldIdEstadoOfFacturaCollectionNewFactura = em.merge(oldIdEstadoOfFacturaCollectionNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadoFactura.getId();
                if (findEstadoFactura(id) == null) {
                    throw new NonexistentEntityException("The estadoFactura with id " + id + " no longer exists.");
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
            EstadoFactura estadoFactura;
            try {
                estadoFactura = em.getReference(EstadoFactura.class, id);
                estadoFactura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoFactura with id " + id + " no longer exists.", enfe);
            }
            Collection<Factura> facturaCollection = estadoFactura.getFacturaCollection();
            for (Factura facturaCollectionFactura : facturaCollection) {
                facturaCollectionFactura.setIdEstado(null);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
            }
            em.remove(estadoFactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoFactura> findEstadoFacturaEntities() {
        return findEstadoFacturaEntities(true, -1, -1);
    }

    public List<EstadoFactura> findEstadoFacturaEntities(int maxResults, int firstResult) {
        return findEstadoFacturaEntities(false, maxResults, firstResult);
    }

    private List<EstadoFactura> findEstadoFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoFactura.class));
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

    public EstadoFactura findEstadoFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoFactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoFactura> rt = cq.from(EstadoFactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
