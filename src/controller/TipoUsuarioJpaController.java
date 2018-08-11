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
import entidades.Empleado;
import entidades.TipoUsuario;
import java.util.ArrayList;
import java.util.Collection;
import entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luis
 */
public class TipoUsuarioJpaController implements Serializable {

    public TipoUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoUsuario tipoUsuario) {
        if (tipoUsuario.getEmpleadoCollection() == null) {
            tipoUsuario.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        if (tipoUsuario.getUsuarioCollection() == null) {
            tipoUsuario.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : tipoUsuario.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getId());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            tipoUsuario.setEmpleadoCollection(attachedEmpleadoCollection);
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : tipoUsuario.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getId());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            tipoUsuario.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(tipoUsuario);
            for (Empleado empleadoCollectionEmpleado : tipoUsuario.getEmpleadoCollection()) {
                TipoUsuario oldIdTipoEmpleadoOfEmpleadoCollectionEmpleado = empleadoCollectionEmpleado.getIdTipoEmpleado();
                empleadoCollectionEmpleado.setIdTipoEmpleado(tipoUsuario);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
                if (oldIdTipoEmpleadoOfEmpleadoCollectionEmpleado != null) {
                    oldIdTipoEmpleadoOfEmpleadoCollectionEmpleado.getEmpleadoCollection().remove(empleadoCollectionEmpleado);
                    oldIdTipoEmpleadoOfEmpleadoCollectionEmpleado = em.merge(oldIdTipoEmpleadoOfEmpleadoCollectionEmpleado);
                }
            }
            for (Usuario usuarioCollectionUsuario : tipoUsuario.getUsuarioCollection()) {
                TipoUsuario oldIdTipoUsuarioOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getIdTipoUsuario();
                usuarioCollectionUsuario.setIdTipoUsuario(tipoUsuario);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldIdTipoUsuarioOfUsuarioCollectionUsuario != null) {
                    oldIdTipoUsuarioOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldIdTipoUsuarioOfUsuarioCollectionUsuario = em.merge(oldIdTipoUsuarioOfUsuarioCollectionUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoUsuario tipoUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsuario persistentTipoUsuario = em.find(TipoUsuario.class, tipoUsuario.getId());
            Collection<Empleado> empleadoCollectionOld = persistentTipoUsuario.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = tipoUsuario.getEmpleadoCollection();
            Collection<Usuario> usuarioCollectionOld = persistentTipoUsuario.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = tipoUsuario.getUsuarioCollection();
            Collection<Empleado> attachedEmpleadoCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionNewEmpleadoToAttach : empleadoCollectionNew) {
                empleadoCollectionNewEmpleadoToAttach = em.getReference(empleadoCollectionNewEmpleadoToAttach.getClass(), empleadoCollectionNewEmpleadoToAttach.getId());
                attachedEmpleadoCollectionNew.add(empleadoCollectionNewEmpleadoToAttach);
            }
            empleadoCollectionNew = attachedEmpleadoCollectionNew;
            tipoUsuario.setEmpleadoCollection(empleadoCollectionNew);
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getId());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            tipoUsuario.setUsuarioCollection(usuarioCollectionNew);
            tipoUsuario = em.merge(tipoUsuario);
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    empleadoCollectionOldEmpleado.setIdTipoEmpleado(null);
                    empleadoCollectionOldEmpleado = em.merge(empleadoCollectionOldEmpleado);
                }
            }
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    TipoUsuario oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado = empleadoCollectionNewEmpleado.getIdTipoEmpleado();
                    empleadoCollectionNewEmpleado.setIdTipoEmpleado(tipoUsuario);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                    if (oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado != null && !oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado.equals(tipoUsuario)) {
                        oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado.getEmpleadoCollection().remove(empleadoCollectionNewEmpleado);
                        oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado = em.merge(oldIdTipoEmpleadoOfEmpleadoCollectionNewEmpleado);
                    }
                }
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    usuarioCollectionOldUsuario.setIdTipoUsuario(null);
                    usuarioCollectionOldUsuario = em.merge(usuarioCollectionOldUsuario);
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    TipoUsuario oldIdTipoUsuarioOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getIdTipoUsuario();
                    usuarioCollectionNewUsuario.setIdTipoUsuario(tipoUsuario);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldIdTipoUsuarioOfUsuarioCollectionNewUsuario != null && !oldIdTipoUsuarioOfUsuarioCollectionNewUsuario.equals(tipoUsuario)) {
                        oldIdTipoUsuarioOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldIdTipoUsuarioOfUsuarioCollectionNewUsuario = em.merge(oldIdTipoUsuarioOfUsuarioCollectionNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoUsuario.getId();
                if (findTipoUsuario(id) == null) {
                    throw new NonexistentEntityException("The tipoUsuario with id " + id + " no longer exists.");
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
            TipoUsuario tipoUsuario;
            try {
                tipoUsuario = em.getReference(TipoUsuario.class, id);
                tipoUsuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoUsuario with id " + id + " no longer exists.", enfe);
            }
            Collection<Empleado> empleadoCollection = tipoUsuario.getEmpleadoCollection();
            for (Empleado empleadoCollectionEmpleado : empleadoCollection) {
                empleadoCollectionEmpleado.setIdTipoEmpleado(null);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
            }
            Collection<Usuario> usuarioCollection = tipoUsuario.getUsuarioCollection();
            for (Usuario usuarioCollectionUsuario : usuarioCollection) {
                usuarioCollectionUsuario.setIdTipoUsuario(null);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            em.remove(tipoUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoUsuario> findTipoUsuarioEntities() {
        return findTipoUsuarioEntities(true, -1, -1);
    }

    public List<TipoUsuario> findTipoUsuarioEntities(int maxResults, int firstResult) {
        return findTipoUsuarioEntities(false, maxResults, firstResult);
    }

    private List<TipoUsuario> findTipoUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoUsuario.class));
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

    public TipoUsuario findTipoUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoUsuario> rt = cq.from(TipoUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
