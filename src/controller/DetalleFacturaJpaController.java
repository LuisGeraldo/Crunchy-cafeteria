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
import entidades.Articulo;
import entidades.DetalleFactura;
import entidades.Factura;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luis
 */
public class DetalleFacturaJpaController implements Serializable {

    public DetalleFacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleFactura detalleFactura) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulo idArticulo = detalleFactura.getIdArticulo();
            if (idArticulo != null) {
                idArticulo = em.getReference(idArticulo.getClass(), idArticulo.getId());
                detalleFactura.setIdArticulo(idArticulo);
            }
            Factura idFactura = detalleFactura.getIdFactura();
            if (idFactura != null) {
                idFactura = em.getReference(idFactura.getClass(), idFactura.getId());
                detalleFactura.setIdFactura(idFactura);
            }
            em.persist(detalleFactura);
            if (idArticulo != null) {
                idArticulo.getDetalleFacturaCollection().add(detalleFactura);
                idArticulo = em.merge(idArticulo);
            }
            if (idFactura != null) {
                idFactura.getDetalleFacturaCollection().add(detalleFactura);
                idFactura = em.merge(idFactura);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleFactura detalleFactura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleFactura persistentDetalleFactura = em.find(DetalleFactura.class, detalleFactura.getId());
            Articulo idArticuloOld = persistentDetalleFactura.getIdArticulo();
            Articulo idArticuloNew = detalleFactura.getIdArticulo();
            Factura idFacturaOld = persistentDetalleFactura.getIdFactura();
            Factura idFacturaNew = detalleFactura.getIdFactura();
            if (idArticuloNew != null) {
                idArticuloNew = em.getReference(idArticuloNew.getClass(), idArticuloNew.getId());
                detalleFactura.setIdArticulo(idArticuloNew);
            }
            if (idFacturaNew != null) {
                idFacturaNew = em.getReference(idFacturaNew.getClass(), idFacturaNew.getId());
                detalleFactura.setIdFactura(idFacturaNew);
            }
            detalleFactura = em.merge(detalleFactura);
            if (idArticuloOld != null && !idArticuloOld.equals(idArticuloNew)) {
                idArticuloOld.getDetalleFacturaCollection().remove(detalleFactura);
                idArticuloOld = em.merge(idArticuloOld);
            }
            if (idArticuloNew != null && !idArticuloNew.equals(idArticuloOld)) {
                idArticuloNew.getDetalleFacturaCollection().add(detalleFactura);
                idArticuloNew = em.merge(idArticuloNew);
            }
            if (idFacturaOld != null && !idFacturaOld.equals(idFacturaNew)) {
                idFacturaOld.getDetalleFacturaCollection().remove(detalleFactura);
                idFacturaOld = em.merge(idFacturaOld);
            }
            if (idFacturaNew != null && !idFacturaNew.equals(idFacturaOld)) {
                idFacturaNew.getDetalleFacturaCollection().add(detalleFactura);
                idFacturaNew = em.merge(idFacturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleFactura.getId();
                if (findDetalleFactura(id) == null) {
                    throw new NonexistentEntityException("The detalleFactura with id " + id + " no longer exists.");
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
            DetalleFactura detalleFactura;
            try {
                detalleFactura = em.getReference(DetalleFactura.class, id);
                detalleFactura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleFactura with id " + id + " no longer exists.", enfe);
            }
            Articulo idArticulo = detalleFactura.getIdArticulo();
            if (idArticulo != null) {
                idArticulo.getDetalleFacturaCollection().remove(detalleFactura);
                idArticulo = em.merge(idArticulo);
            }
            Factura idFactura = detalleFactura.getIdFactura();
            if (idFactura != null) {
                idFactura.getDetalleFacturaCollection().remove(detalleFactura);
                idFactura = em.merge(idFactura);
            }
            em.remove(detalleFactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleFactura> findDetalleFacturaEntities() {
        return findDetalleFacturaEntities(true, -1, -1);
    }

    public List<DetalleFactura> findDetalleFacturaEntities(int maxResults, int firstResult) {
        return findDetalleFacturaEntities(false, maxResults, firstResult);
    }

    private List<DetalleFactura> findDetalleFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleFactura.class));
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

    public DetalleFactura findDetalleFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleFactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleFactura> rt = cq.from(DetalleFactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
