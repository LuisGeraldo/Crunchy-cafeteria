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
import entidades.Marca;
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
public class MarcaJpaController implements Serializable {

    public MarcaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("prjCafeteriaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Marca marca) {
        if (marca.getArticuloCollection() == null) {
            marca.setArticuloCollection(new ArrayList<Articulo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado idEstado = marca.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                marca.setIdEstado(idEstado);
            }
            Collection<Articulo> attachedArticuloCollection = new ArrayList<Articulo>();
            for (Articulo articuloCollectionArticuloToAttach : marca.getArticuloCollection()) {
                articuloCollectionArticuloToAttach = em.getReference(articuloCollectionArticuloToAttach.getClass(), articuloCollectionArticuloToAttach.getId());
                attachedArticuloCollection.add(articuloCollectionArticuloToAttach);
            }
            marca.setArticuloCollection(attachedArticuloCollection);
            em.persist(marca);
            if (idEstado != null) {
                idEstado.getMarcaCollection().add(marca);
                idEstado = em.merge(idEstado);
            }
            for (Articulo articuloCollectionArticulo : marca.getArticuloCollection()) {
                Marca oldIdMarcaOfArticuloCollectionArticulo = articuloCollectionArticulo.getIdMarca();
                articuloCollectionArticulo.setIdMarca(marca);
                articuloCollectionArticulo = em.merge(articuloCollectionArticulo);
                if (oldIdMarcaOfArticuloCollectionArticulo != null) {
                    oldIdMarcaOfArticuloCollectionArticulo.getArticuloCollection().remove(articuloCollectionArticulo);
                    oldIdMarcaOfArticuloCollectionArticulo = em.merge(oldIdMarcaOfArticuloCollectionArticulo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Marca marca) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca persistentMarca = em.find(Marca.class, marca.getId());
            Estado idEstadoOld = persistentMarca.getIdEstado();
            Estado idEstadoNew = marca.getIdEstado();
            Collection<Articulo> articuloCollectionOld = persistentMarca.getArticuloCollection();
            Collection<Articulo> articuloCollectionNew = marca.getArticuloCollection();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                marca.setIdEstado(idEstadoNew);
            }
            Collection<Articulo> attachedArticuloCollectionNew = new ArrayList<Articulo>();
            for (Articulo articuloCollectionNewArticuloToAttach : articuloCollectionNew) {
                articuloCollectionNewArticuloToAttach = em.getReference(articuloCollectionNewArticuloToAttach.getClass(), articuloCollectionNewArticuloToAttach.getId());
                attachedArticuloCollectionNew.add(articuloCollectionNewArticuloToAttach);
            }
            articuloCollectionNew = attachedArticuloCollectionNew;
            marca.setArticuloCollection(articuloCollectionNew);
            marca = em.merge(marca);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getMarcaCollection().remove(marca);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getMarcaCollection().add(marca);
                idEstadoNew = em.merge(idEstadoNew);
            }
            for (Articulo articuloCollectionOldArticulo : articuloCollectionOld) {
                if (!articuloCollectionNew.contains(articuloCollectionOldArticulo)) {
                    articuloCollectionOldArticulo.setIdMarca(null);
                    articuloCollectionOldArticulo = em.merge(articuloCollectionOldArticulo);
                }
            }
            for (Articulo articuloCollectionNewArticulo : articuloCollectionNew) {
                if (!articuloCollectionOld.contains(articuloCollectionNewArticulo)) {
                    Marca oldIdMarcaOfArticuloCollectionNewArticulo = articuloCollectionNewArticulo.getIdMarca();
                    articuloCollectionNewArticulo.setIdMarca(marca);
                    articuloCollectionNewArticulo = em.merge(articuloCollectionNewArticulo);
                    if (oldIdMarcaOfArticuloCollectionNewArticulo != null && !oldIdMarcaOfArticuloCollectionNewArticulo.equals(marca)) {
                        oldIdMarcaOfArticuloCollectionNewArticulo.getArticuloCollection().remove(articuloCollectionNewArticulo);
                        oldIdMarcaOfArticuloCollectionNewArticulo = em.merge(oldIdMarcaOfArticuloCollectionNewArticulo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marca.getId();
                if (findMarca(id) == null) {
                    throw new NonexistentEntityException("The marca with id " + id + " no longer exists.");
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
            Marca marca;
            try {
                marca = em.getReference(Marca.class, id);
                marca.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marca with id " + id + " no longer exists.", enfe);
            }
            Estado idEstado = marca.getIdEstado();
            if (idEstado != null) {
                idEstado.getMarcaCollection().remove(marca);
                idEstado = em.merge(idEstado);
            }
            Collection<Articulo> articuloCollection = marca.getArticuloCollection();
            for (Articulo articuloCollectionArticulo : articuloCollection) {
                articuloCollectionArticulo.setIdMarca(null);
                articuloCollectionArticulo = em.merge(articuloCollectionArticulo);
            }
            em.remove(marca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Marca> findMarcaEntities() {
        return findMarcaEntities(true, -1, -1);
    }

    public List<Marca> findMarcaEntities(int maxResults, int firstResult) {
        return findMarcaEntities(false, maxResults, firstResult);
    }

    private List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marca.class));
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

    public Marca findMarca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marca.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marca> rt = cq.from(Marca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
