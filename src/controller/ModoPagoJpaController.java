/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Factura;
import entidades.ModoPago;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luis
 */
public class ModoPagoJpaController implements Serializable {

    public ModoPagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ModoPago modoPago) {
        if (modoPago.getFacturaCollection() == null) {
            modoPago.setFacturaCollection(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : modoPago.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getId());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            modoPago.setFacturaCollection(attachedFacturaCollection);
            em.persist(modoPago);
            for (Factura facturaCollectionFactura : modoPago.getFacturaCollection()) {
                ModoPago oldIdModoPagoOfFacturaCollectionFactura = facturaCollectionFactura.getIdModoPago();
                facturaCollectionFactura.setIdModoPago(modoPago);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldIdModoPagoOfFacturaCollectionFactura != null) {
                    oldIdModoPagoOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldIdModoPagoOfFacturaCollectionFactura = em.merge(oldIdModoPagoOfFacturaCollectionFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ModoPago modoPago) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ModoPago persistentModoPago = em.find(ModoPago.class, modoPago.getId());
            Collection<Factura> facturaCollectionOld = persistentModoPago.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = modoPago.getFacturaCollection();
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getId());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            modoPago.setFacturaCollection(facturaCollectionNew);
            modoPago = em.merge(modoPago);
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    facturaCollectionOldFactura.setIdModoPago(null);
                    facturaCollectionOldFactura = em.merge(facturaCollectionOldFactura);
                }
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    ModoPago oldIdModoPagoOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getIdModoPago();
                    facturaCollectionNewFactura.setIdModoPago(modoPago);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldIdModoPagoOfFacturaCollectionNewFactura != null && !oldIdModoPagoOfFacturaCollectionNewFactura.equals(modoPago)) {
                        oldIdModoPagoOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldIdModoPagoOfFacturaCollectionNewFactura = em.merge(oldIdModoPagoOfFacturaCollectionNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = modoPago.getId();
                if (findModoPago(id) == null) {
                    throw new NonexistentEntityException("The modoPago with id " + id + " no longer exists.");
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
            ModoPago modoPago;
            try {
                modoPago = em.getReference(ModoPago.class, id);
                modoPago.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modoPago with id " + id + " no longer exists.", enfe);
            }
            Collection<Factura> facturaCollection = modoPago.getFacturaCollection();
            for (Factura facturaCollectionFactura : facturaCollection) {
                facturaCollectionFactura.setIdModoPago(null);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
            }
            em.remove(modoPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ModoPago> findModoPagoEntities() {
        return findModoPagoEntities(true, -1, -1);
    }

    public List<ModoPago> findModoPagoEntities(int maxResults, int firstResult) {
        return findModoPagoEntities(false, maxResults, firstResult);
    }

    private List<ModoPago> findModoPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ModoPago.class));
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

    public ModoPago findModoPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ModoPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getModoPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ModoPago> rt = cq.from(ModoPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
