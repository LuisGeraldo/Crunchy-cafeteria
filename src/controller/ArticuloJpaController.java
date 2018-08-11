/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import entidades.Articulo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Estado;
import entidades.Marca;
import entidades.Proveedor;
import entidades.DetalleFactura;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luis
 */
public class ArticuloJpaController implements Serializable {

    public ArticuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulo articulo) {
        if (articulo.getDetalleFacturaCollection() == null) {
            articulo.setDetalleFacturaCollection(new ArrayList<DetalleFactura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado idEstado = articulo.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                articulo.setIdEstado(idEstado);
            }
            Marca idMarca = articulo.getIdMarca();
            if (idMarca != null) {
                idMarca = em.getReference(idMarca.getClass(), idMarca.getId());
                articulo.setIdMarca(idMarca);
            }
            Proveedor idProveedor = articulo.getIdProveedor();
            if (idProveedor != null) {
                idProveedor = em.getReference(idProveedor.getClass(), idProveedor.getId());
                articulo.setIdProveedor(idProveedor);
            }
            Collection<DetalleFactura> attachedDetalleFacturaCollection = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionDetalleFacturaToAttach : articulo.getDetalleFacturaCollection()) {
                detalleFacturaCollectionDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionDetalleFacturaToAttach.getClass(), detalleFacturaCollectionDetalleFacturaToAttach.getId());
                attachedDetalleFacturaCollection.add(detalleFacturaCollectionDetalleFacturaToAttach);
            }
            articulo.setDetalleFacturaCollection(attachedDetalleFacturaCollection);
            em.persist(articulo);
            if (idEstado != null) {
                idEstado.getArticuloCollection().add(articulo);
                idEstado = em.merge(idEstado);
            }
            if (idMarca != null) {
                idMarca.getArticuloCollection().add(articulo);
                idMarca = em.merge(idMarca);
            }
            if (idProveedor != null) {
                idProveedor.getArticuloCollection().add(articulo);
                idProveedor = em.merge(idProveedor);
            }
            for (DetalleFactura detalleFacturaCollectionDetalleFactura : articulo.getDetalleFacturaCollection()) {
                Articulo oldIdArticuloOfDetalleFacturaCollectionDetalleFactura = detalleFacturaCollectionDetalleFactura.getIdArticulo();
                detalleFacturaCollectionDetalleFactura.setIdArticulo(articulo);
                detalleFacturaCollectionDetalleFactura = em.merge(detalleFacturaCollectionDetalleFactura);
                if (oldIdArticuloOfDetalleFacturaCollectionDetalleFactura != null) {
                    oldIdArticuloOfDetalleFacturaCollectionDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionDetalleFactura);
                    oldIdArticuloOfDetalleFacturaCollectionDetalleFactura = em.merge(oldIdArticuloOfDetalleFacturaCollectionDetalleFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Articulo articulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulo persistentArticulo = em.find(Articulo.class, articulo.getId());
            Estado idEstadoOld = persistentArticulo.getIdEstado();
            Estado idEstadoNew = articulo.getIdEstado();
            Marca idMarcaOld = persistentArticulo.getIdMarca();
            Marca idMarcaNew = articulo.getIdMarca();
            Proveedor idProveedorOld = persistentArticulo.getIdProveedor();
            Proveedor idProveedorNew = articulo.getIdProveedor();
            Collection<DetalleFactura> detalleFacturaCollectionOld = persistentArticulo.getDetalleFacturaCollection();
            Collection<DetalleFactura> detalleFacturaCollectionNew = articulo.getDetalleFacturaCollection();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                articulo.setIdEstado(idEstadoNew);
            }
            if (idMarcaNew != null) {
                idMarcaNew = em.getReference(idMarcaNew.getClass(), idMarcaNew.getId());
                articulo.setIdMarca(idMarcaNew);
            }
            if (idProveedorNew != null) {
                idProveedorNew = em.getReference(idProveedorNew.getClass(), idProveedorNew.getId());
                articulo.setIdProveedor(idProveedorNew);
            }
            Collection<DetalleFactura> attachedDetalleFacturaCollectionNew = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionNewDetalleFacturaToAttach : detalleFacturaCollectionNew) {
                detalleFacturaCollectionNewDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionNewDetalleFacturaToAttach.getClass(), detalleFacturaCollectionNewDetalleFacturaToAttach.getId());
                attachedDetalleFacturaCollectionNew.add(detalleFacturaCollectionNewDetalleFacturaToAttach);
            }
            detalleFacturaCollectionNew = attachedDetalleFacturaCollectionNew;
            articulo.setDetalleFacturaCollection(detalleFacturaCollectionNew);
            articulo = em.merge(articulo);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getArticuloCollection().remove(articulo);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getArticuloCollection().add(articulo);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idMarcaOld != null && !idMarcaOld.equals(idMarcaNew)) {
                idMarcaOld.getArticuloCollection().remove(articulo);
                idMarcaOld = em.merge(idMarcaOld);
            }
            if (idMarcaNew != null && !idMarcaNew.equals(idMarcaOld)) {
                idMarcaNew.getArticuloCollection().add(articulo);
                idMarcaNew = em.merge(idMarcaNew);
            }
            if (idProveedorOld != null && !idProveedorOld.equals(idProveedorNew)) {
                idProveedorOld.getArticuloCollection().remove(articulo);
                idProveedorOld = em.merge(idProveedorOld);
            }
            if (idProveedorNew != null && !idProveedorNew.equals(idProveedorOld)) {
                idProveedorNew.getArticuloCollection().add(articulo);
                idProveedorNew = em.merge(idProveedorNew);
            }
            for (DetalleFactura detalleFacturaCollectionOldDetalleFactura : detalleFacturaCollectionOld) {
                if (!detalleFacturaCollectionNew.contains(detalleFacturaCollectionOldDetalleFactura)) {
                    detalleFacturaCollectionOldDetalleFactura.setIdArticulo(null);
                    detalleFacturaCollectionOldDetalleFactura = em.merge(detalleFacturaCollectionOldDetalleFactura);
                }
            }
            for (DetalleFactura detalleFacturaCollectionNewDetalleFactura : detalleFacturaCollectionNew) {
                if (!detalleFacturaCollectionOld.contains(detalleFacturaCollectionNewDetalleFactura)) {
                    Articulo oldIdArticuloOfDetalleFacturaCollectionNewDetalleFactura = detalleFacturaCollectionNewDetalleFactura.getIdArticulo();
                    detalleFacturaCollectionNewDetalleFactura.setIdArticulo(articulo);
                    detalleFacturaCollectionNewDetalleFactura = em.merge(detalleFacturaCollectionNewDetalleFactura);
                    if (oldIdArticuloOfDetalleFacturaCollectionNewDetalleFactura != null && !oldIdArticuloOfDetalleFacturaCollectionNewDetalleFactura.equals(articulo)) {
                        oldIdArticuloOfDetalleFacturaCollectionNewDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionNewDetalleFactura);
                        oldIdArticuloOfDetalleFacturaCollectionNewDetalleFactura = em.merge(oldIdArticuloOfDetalleFacturaCollectionNewDetalleFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = articulo.getId();
                if (findArticulo(id) == null) {
                    throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.");
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
            Articulo articulo;
            try {
                articulo = em.getReference(Articulo.class, id);
                articulo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.", enfe);
            }
            Estado idEstado = articulo.getIdEstado();
            if (idEstado != null) {
                idEstado.getArticuloCollection().remove(articulo);
                idEstado = em.merge(idEstado);
            }
            Marca idMarca = articulo.getIdMarca();
            if (idMarca != null) {
                idMarca.getArticuloCollection().remove(articulo);
                idMarca = em.merge(idMarca);
            }
            Proveedor idProveedor = articulo.getIdProveedor();
            if (idProveedor != null) {
                idProveedor.getArticuloCollection().remove(articulo);
                idProveedor = em.merge(idProveedor);
            }
            Collection<DetalleFactura> detalleFacturaCollection = articulo.getDetalleFacturaCollection();
            for (DetalleFactura detalleFacturaCollectionDetalleFactura : detalleFacturaCollection) {
                detalleFacturaCollectionDetalleFactura.setIdArticulo(null);
                detalleFacturaCollectionDetalleFactura = em.merge(detalleFacturaCollectionDetalleFactura);
            }
            em.remove(articulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Articulo> findArticuloEntities() {
        return findArticuloEntities(true, -1, -1);
    }

    public List<Articulo> findArticuloEntities(int maxResults, int firstResult) {
        return findArticuloEntities(false, maxResults, firstResult);
    }

    private List<Articulo> findArticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articulo.class));
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

    public Articulo findArticulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articulo> rt = cq.from(Articulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
