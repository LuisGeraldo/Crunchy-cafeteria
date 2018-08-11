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
import entidades.TandaLabor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luis
 */
public class TandaLaborJpaController implements Serializable {

    public TandaLaborJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TandaLabor tandaLabor) {
        if (tandaLabor.getEmpleadoCollection() == null) {
            tandaLabor.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : tandaLabor.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getId());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            tandaLabor.setEmpleadoCollection(attachedEmpleadoCollection);
            em.persist(tandaLabor);
            for (Empleado empleadoCollectionEmpleado : tandaLabor.getEmpleadoCollection()) {
                TandaLabor oldIdTandaLaborOfEmpleadoCollectionEmpleado = empleadoCollectionEmpleado.getIdTandaLabor();
                empleadoCollectionEmpleado.setIdTandaLabor(tandaLabor);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
                if (oldIdTandaLaborOfEmpleadoCollectionEmpleado != null) {
                    oldIdTandaLaborOfEmpleadoCollectionEmpleado.getEmpleadoCollection().remove(empleadoCollectionEmpleado);
                    oldIdTandaLaborOfEmpleadoCollectionEmpleado = em.merge(oldIdTandaLaborOfEmpleadoCollectionEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TandaLabor tandaLabor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TandaLabor persistentTandaLabor = em.find(TandaLabor.class, tandaLabor.getId());
            Collection<Empleado> empleadoCollectionOld = persistentTandaLabor.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = tandaLabor.getEmpleadoCollection();
            Collection<Empleado> attachedEmpleadoCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionNewEmpleadoToAttach : empleadoCollectionNew) {
                empleadoCollectionNewEmpleadoToAttach = em.getReference(empleadoCollectionNewEmpleadoToAttach.getClass(), empleadoCollectionNewEmpleadoToAttach.getId());
                attachedEmpleadoCollectionNew.add(empleadoCollectionNewEmpleadoToAttach);
            }
            empleadoCollectionNew = attachedEmpleadoCollectionNew;
            tandaLabor.setEmpleadoCollection(empleadoCollectionNew);
            tandaLabor = em.merge(tandaLabor);
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    empleadoCollectionOldEmpleado.setIdTandaLabor(null);
                    empleadoCollectionOldEmpleado = em.merge(empleadoCollectionOldEmpleado);
                }
            }
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    TandaLabor oldIdTandaLaborOfEmpleadoCollectionNewEmpleado = empleadoCollectionNewEmpleado.getIdTandaLabor();
                    empleadoCollectionNewEmpleado.setIdTandaLabor(tandaLabor);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                    if (oldIdTandaLaborOfEmpleadoCollectionNewEmpleado != null && !oldIdTandaLaborOfEmpleadoCollectionNewEmpleado.equals(tandaLabor)) {
                        oldIdTandaLaborOfEmpleadoCollectionNewEmpleado.getEmpleadoCollection().remove(empleadoCollectionNewEmpleado);
                        oldIdTandaLaborOfEmpleadoCollectionNewEmpleado = em.merge(oldIdTandaLaborOfEmpleadoCollectionNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tandaLabor.getId();
                if (findTandaLabor(id) == null) {
                    throw new NonexistentEntityException("The tandaLabor with id " + id + " no longer exists.");
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
            TandaLabor tandaLabor;
            try {
                tandaLabor = em.getReference(TandaLabor.class, id);
                tandaLabor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tandaLabor with id " + id + " no longer exists.", enfe);
            }
            Collection<Empleado> empleadoCollection = tandaLabor.getEmpleadoCollection();
            for (Empleado empleadoCollectionEmpleado : empleadoCollection) {
                empleadoCollectionEmpleado.setIdTandaLabor(null);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
            }
            em.remove(tandaLabor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TandaLabor> findTandaLaborEntities() {
        return findTandaLaborEntities(true, -1, -1);
    }

    public List<TandaLabor> findTandaLaborEntities(int maxResults, int firstResult) {
        return findTandaLaborEntities(false, maxResults, firstResult);
    }

    private List<TandaLabor> findTandaLaborEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TandaLabor.class));
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

    public TandaLabor findTandaLabor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TandaLabor.class, id);
        } finally {
            em.close();
        }
    }

    public int getTandaLaborCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TandaLabor> rt = cq.from(TandaLabor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
