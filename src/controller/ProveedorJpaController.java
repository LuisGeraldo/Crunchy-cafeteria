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
import entidades.Estado;
import entidades.Articulo;
import entidades.Proveedor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luis
 */
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) {
        if (proveedor.getArticuloCollection() == null) {
            proveedor.setArticuloCollection(new ArrayList<Articulo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado idEstado = proveedor.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                proveedor.setIdEstado(idEstado);
            }
            Collection<Articulo> attachedArticuloCollection = new ArrayList<Articulo>();
            for (Articulo articuloCollectionArticuloToAttach : proveedor.getArticuloCollection()) {
                articuloCollectionArticuloToAttach = em.getReference(articuloCollectionArticuloToAttach.getClass(), articuloCollectionArticuloToAttach.getId());
                attachedArticuloCollection.add(articuloCollectionArticuloToAttach);
            }
            proveedor.setArticuloCollection(attachedArticuloCollection);
            em.persist(proveedor);
            if (idEstado != null) {
                idEstado.getProveedorCollection().add(proveedor);
                idEstado = em.merge(idEstado);
            }
            for (Articulo articuloCollectionArticulo : proveedor.getArticuloCollection()) {
                Proveedor oldIdProveedorOfArticuloCollectionArticulo = articuloCollectionArticulo.getIdProveedor();
                articuloCollectionArticulo.setIdProveedor(proveedor);
                articuloCollectionArticulo = em.merge(articuloCollectionArticulo);
                if (oldIdProveedorOfArticuloCollectionArticulo != null) {
                    oldIdProveedorOfArticuloCollectionArticulo.getArticuloCollection().remove(articuloCollectionArticulo);
                    oldIdProveedorOfArticuloCollectionArticulo = em.merge(oldIdProveedorOfArticuloCollectionArticulo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getId());
            Estado idEstadoOld = persistentProveedor.getIdEstado();
            Estado idEstadoNew = proveedor.getIdEstado();
            Collection<Articulo> articuloCollectionOld = persistentProveedor.getArticuloCollection();
            Collection<Articulo> articuloCollectionNew = proveedor.getArticuloCollection();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                proveedor.setIdEstado(idEstadoNew);
            }
            Collection<Articulo> attachedArticuloCollectionNew = new ArrayList<Articulo>();
            for (Articulo articuloCollectionNewArticuloToAttach : articuloCollectionNew) {
                articuloCollectionNewArticuloToAttach = em.getReference(articuloCollectionNewArticuloToAttach.getClass(), articuloCollectionNewArticuloToAttach.getId());
                attachedArticuloCollectionNew.add(articuloCollectionNewArticuloToAttach);
            }
            articuloCollectionNew = attachedArticuloCollectionNew;
            proveedor.setArticuloCollection(articuloCollectionNew);
            proveedor = em.merge(proveedor);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getProveedorCollection().remove(proveedor);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getProveedorCollection().add(proveedor);
                idEstadoNew = em.merge(idEstadoNew);
            }
            for (Articulo articuloCollectionOldArticulo : articuloCollectionOld) {
                if (!articuloCollectionNew.contains(articuloCollectionOldArticulo)) {
                    articuloCollectionOldArticulo.setIdProveedor(null);
                    articuloCollectionOldArticulo = em.merge(articuloCollectionOldArticulo);
                }
            }
            for (Articulo articuloCollectionNewArticulo : articuloCollectionNew) {
                if (!articuloCollectionOld.contains(articuloCollectionNewArticulo)) {
                    Proveedor oldIdProveedorOfArticuloCollectionNewArticulo = articuloCollectionNewArticulo.getIdProveedor();
                    articuloCollectionNewArticulo.setIdProveedor(proveedor);
                    articuloCollectionNewArticulo = em.merge(articuloCollectionNewArticulo);
                    if (oldIdProveedorOfArticuloCollectionNewArticulo != null && !oldIdProveedorOfArticuloCollectionNewArticulo.equals(proveedor)) {
                        oldIdProveedorOfArticuloCollectionNewArticulo.getArticuloCollection().remove(articuloCollectionNewArticulo);
                        oldIdProveedorOfArticuloCollectionNewArticulo = em.merge(oldIdProveedorOfArticuloCollectionNewArticulo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedor.getId();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
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
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            Estado idEstado = proveedor.getIdEstado();
            if (idEstado != null) {
                idEstado.getProveedorCollection().remove(proveedor);
                idEstado = em.merge(idEstado);
            }
            Collection<Articulo> articuloCollection = proveedor.getArticuloCollection();
            for (Articulo articuloCollectionArticulo : articuloCollection) {
                articuloCollectionArticulo.setIdProveedor(null);
                articuloCollectionArticulo = em.merge(articuloCollectionArticulo);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
