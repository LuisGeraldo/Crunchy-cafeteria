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
import entidades.TipoUsuario;
import entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luis
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsuario idTipoUsuario = usuario.getIdTipoUsuario();
            if (idTipoUsuario != null) {
                idTipoUsuario = em.getReference(idTipoUsuario.getClass(), idTipoUsuario.getId());
                usuario.setIdTipoUsuario(idTipoUsuario);
            }
            em.persist(usuario);
            if (idTipoUsuario != null) {
                idTipoUsuario.getUsuarioCollection().add(usuario);
                idTipoUsuario = em.merge(idTipoUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            TipoUsuario idTipoUsuarioOld = persistentUsuario.getIdTipoUsuario();
            TipoUsuario idTipoUsuarioNew = usuario.getIdTipoUsuario();
            if (idTipoUsuarioNew != null) {
                idTipoUsuarioNew = em.getReference(idTipoUsuarioNew.getClass(), idTipoUsuarioNew.getId());
                usuario.setIdTipoUsuario(idTipoUsuarioNew);
            }
            usuario = em.merge(usuario);
            if (idTipoUsuarioOld != null && !idTipoUsuarioOld.equals(idTipoUsuarioNew)) {
                idTipoUsuarioOld.getUsuarioCollection().remove(usuario);
                idTipoUsuarioOld = em.merge(idTipoUsuarioOld);
            }
            if (idTipoUsuarioNew != null && !idTipoUsuarioNew.equals(idTipoUsuarioOld)) {
                idTipoUsuarioNew.getUsuarioCollection().add(usuario);
                idTipoUsuarioNew = em.merge(idTipoUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            TipoUsuario idTipoUsuario = usuario.getIdTipoUsuario();
            if (idTipoUsuario != null) {
                idTipoUsuario.getUsuarioCollection().remove(usuario);
                idTipoUsuario = em.merge(idTipoUsuario);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
