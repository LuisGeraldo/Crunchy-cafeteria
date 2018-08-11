/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import entidades.Empleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Estado;
import entidades.TandaLabor;
import entidades.TipoUsuario;
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
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) {
        if (empleado.getFacturaCollection() == null) {
            empleado.setFacturaCollection(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado idEstado = empleado.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                empleado.setIdEstado(idEstado);
            }
            TandaLabor idTandaLabor = empleado.getIdTandaLabor();
            if (idTandaLabor != null) {
                idTandaLabor = em.getReference(idTandaLabor.getClass(), idTandaLabor.getId());
                empleado.setIdTandaLabor(idTandaLabor);
            }
            TipoUsuario idTipoEmpleado = empleado.getIdTipoEmpleado();
            if (idTipoEmpleado != null) {
                idTipoEmpleado = em.getReference(idTipoEmpleado.getClass(), idTipoEmpleado.getId());
                empleado.setIdTipoEmpleado(idTipoEmpleado);
            }
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : empleado.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getId());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            empleado.setFacturaCollection(attachedFacturaCollection);
            em.persist(empleado);
            if (idEstado != null) {
                idEstado.getEmpleadoCollection().add(empleado);
                idEstado = em.merge(idEstado);
            }
            if (idTandaLabor != null) {
                idTandaLabor.getEmpleadoCollection().add(empleado);
                idTandaLabor = em.merge(idTandaLabor);
            }
            if (idTipoEmpleado != null) {
                idTipoEmpleado.getEmpleadoCollection().add(empleado);
                idTipoEmpleado = em.merge(idTipoEmpleado);
            }
            for (Factura facturaCollectionFactura : empleado.getFacturaCollection()) {
                Empleado oldIdEmpleadoOfFacturaCollectionFactura = facturaCollectionFactura.getIdEmpleado();
                facturaCollectionFactura.setIdEmpleado(empleado);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldIdEmpleadoOfFacturaCollectionFactura != null) {
                    oldIdEmpleadoOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldIdEmpleadoOfFacturaCollectionFactura = em.merge(oldIdEmpleadoOfFacturaCollectionFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getId());
            Estado idEstadoOld = persistentEmpleado.getIdEstado();
            Estado idEstadoNew = empleado.getIdEstado();
            TandaLabor idTandaLaborOld = persistentEmpleado.getIdTandaLabor();
            TandaLabor idTandaLaborNew = empleado.getIdTandaLabor();
            TipoUsuario idTipoEmpleadoOld = persistentEmpleado.getIdTipoEmpleado();
            TipoUsuario idTipoEmpleadoNew = empleado.getIdTipoEmpleado();
            Collection<Factura> facturaCollectionOld = persistentEmpleado.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = empleado.getFacturaCollection();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                empleado.setIdEstado(idEstadoNew);
            }
            if (idTandaLaborNew != null) {
                idTandaLaborNew = em.getReference(idTandaLaborNew.getClass(), idTandaLaborNew.getId());
                empleado.setIdTandaLabor(idTandaLaborNew);
            }
            if (idTipoEmpleadoNew != null) {
                idTipoEmpleadoNew = em.getReference(idTipoEmpleadoNew.getClass(), idTipoEmpleadoNew.getId());
                empleado.setIdTipoEmpleado(idTipoEmpleadoNew);
            }
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getId());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            empleado.setFacturaCollection(facturaCollectionNew);
            empleado = em.merge(empleado);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getEmpleadoCollection().remove(empleado);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getEmpleadoCollection().add(empleado);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idTandaLaborOld != null && !idTandaLaborOld.equals(idTandaLaborNew)) {
                idTandaLaborOld.getEmpleadoCollection().remove(empleado);
                idTandaLaborOld = em.merge(idTandaLaborOld);
            }
            if (idTandaLaborNew != null && !idTandaLaborNew.equals(idTandaLaborOld)) {
                idTandaLaborNew.getEmpleadoCollection().add(empleado);
                idTandaLaborNew = em.merge(idTandaLaborNew);
            }
            if (idTipoEmpleadoOld != null && !idTipoEmpleadoOld.equals(idTipoEmpleadoNew)) {
                idTipoEmpleadoOld.getEmpleadoCollection().remove(empleado);
                idTipoEmpleadoOld = em.merge(idTipoEmpleadoOld);
            }
            if (idTipoEmpleadoNew != null && !idTipoEmpleadoNew.equals(idTipoEmpleadoOld)) {
                idTipoEmpleadoNew.getEmpleadoCollection().add(empleado);
                idTipoEmpleadoNew = em.merge(idTipoEmpleadoNew);
            }
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    facturaCollectionOldFactura.setIdEmpleado(null);
                    facturaCollectionOldFactura = em.merge(facturaCollectionOldFactura);
                }
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    Empleado oldIdEmpleadoOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getIdEmpleado();
                    facturaCollectionNewFactura.setIdEmpleado(empleado);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldIdEmpleadoOfFacturaCollectionNewFactura != null && !oldIdEmpleadoOfFacturaCollectionNewFactura.equals(empleado)) {
                        oldIdEmpleadoOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldIdEmpleadoOfFacturaCollectionNewFactura = em.merge(oldIdEmpleadoOfFacturaCollectionNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleado.getId();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Estado idEstado = empleado.getIdEstado();
            if (idEstado != null) {
                idEstado.getEmpleadoCollection().remove(empleado);
                idEstado = em.merge(idEstado);
            }
            TandaLabor idTandaLabor = empleado.getIdTandaLabor();
            if (idTandaLabor != null) {
                idTandaLabor.getEmpleadoCollection().remove(empleado);
                idTandaLabor = em.merge(idTandaLabor);
            }
            TipoUsuario idTipoEmpleado = empleado.getIdTipoEmpleado();
            if (idTipoEmpleado != null) {
                idTipoEmpleado.getEmpleadoCollection().remove(empleado);
                idTipoEmpleado = em.merge(idTipoEmpleado);
            }
            Collection<Factura> facturaCollection = empleado.getFacturaCollection();
            for (Factura facturaCollectionFactura : facturaCollection) {
                facturaCollectionFactura.setIdEmpleado(null);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
