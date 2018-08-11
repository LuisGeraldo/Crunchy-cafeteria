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
import entidades.Cafeteria;
import entidades.Cliente;
import entidades.Empleado;
import entidades.EstadoFactura;
import entidades.ModoPago;
import entidades.DetalleFactura;
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
public class FacturaJpaController implements Serializable {

    public FacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) {
        if (factura.getDetalleFacturaCollection() == null) {
            factura.setDetalleFacturaCollection(new ArrayList<DetalleFactura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cafeteria idCafeteria = factura.getIdCafeteria();
            if (idCafeteria != null) {
                idCafeteria = em.getReference(idCafeteria.getClass(), idCafeteria.getId());
                factura.setIdCafeteria(idCafeteria);
            }
            Cliente idCliente = factura.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getId());
                factura.setIdCliente(idCliente);
            }
            Empleado idEmpleado = factura.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getId());
                factura.setIdEmpleado(idEmpleado);
            }
            EstadoFactura idEstado = factura.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                factura.setIdEstado(idEstado);
            }
            ModoPago idModoPago = factura.getIdModoPago();
            if (idModoPago != null) {
                idModoPago = em.getReference(idModoPago.getClass(), idModoPago.getId());
                factura.setIdModoPago(idModoPago);
            }
            Collection<DetalleFactura> attachedDetalleFacturaCollection = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionDetalleFacturaToAttach : factura.getDetalleFacturaCollection()) {
                detalleFacturaCollectionDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionDetalleFacturaToAttach.getClass(), detalleFacturaCollectionDetalleFacturaToAttach.getId());
                attachedDetalleFacturaCollection.add(detalleFacturaCollectionDetalleFacturaToAttach);
            }
            factura.setDetalleFacturaCollection(attachedDetalleFacturaCollection);
            em.persist(factura);
            if (idCafeteria != null) {
                idCafeteria.getFacturaCollection().add(factura);
                idCafeteria = em.merge(idCafeteria);
            }
            if (idCliente != null) {
                idCliente.getFacturaCollection().add(factura);
                idCliente = em.merge(idCliente);
            }
            if (idEmpleado != null) {
                idEmpleado.getFacturaCollection().add(factura);
                idEmpleado = em.merge(idEmpleado);
            }
            if (idEstado != null) {
                idEstado.getFacturaCollection().add(factura);
                idEstado = em.merge(idEstado);
            }
            if (idModoPago != null) {
                idModoPago.getFacturaCollection().add(factura);
                idModoPago = em.merge(idModoPago);
            }
            for (DetalleFactura detalleFacturaCollectionDetalleFactura : factura.getDetalleFacturaCollection()) {
                Factura oldIdFacturaOfDetalleFacturaCollectionDetalleFactura = detalleFacturaCollectionDetalleFactura.getIdFactura();
                detalleFacturaCollectionDetalleFactura.setIdFactura(factura);
                detalleFacturaCollectionDetalleFactura = em.merge(detalleFacturaCollectionDetalleFactura);
                if (oldIdFacturaOfDetalleFacturaCollectionDetalleFactura != null) {
                    oldIdFacturaOfDetalleFacturaCollectionDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionDetalleFactura);
                    oldIdFacturaOfDetalleFacturaCollectionDetalleFactura = em.merge(oldIdFacturaOfDetalleFacturaCollectionDetalleFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getId());
            Cafeteria idCafeteriaOld = persistentFactura.getIdCafeteria();
            Cafeteria idCafeteriaNew = factura.getIdCafeteria();
            Cliente idClienteOld = persistentFactura.getIdCliente();
            Cliente idClienteNew = factura.getIdCliente();
            Empleado idEmpleadoOld = persistentFactura.getIdEmpleado();
            Empleado idEmpleadoNew = factura.getIdEmpleado();
            EstadoFactura idEstadoOld = persistentFactura.getIdEstado();
            EstadoFactura idEstadoNew = factura.getIdEstado();
            ModoPago idModoPagoOld = persistentFactura.getIdModoPago();
            ModoPago idModoPagoNew = factura.getIdModoPago();
            Collection<DetalleFactura> detalleFacturaCollectionOld = persistentFactura.getDetalleFacturaCollection();
            Collection<DetalleFactura> detalleFacturaCollectionNew = factura.getDetalleFacturaCollection();
            if (idCafeteriaNew != null) {
                idCafeteriaNew = em.getReference(idCafeteriaNew.getClass(), idCafeteriaNew.getId());
                factura.setIdCafeteria(idCafeteriaNew);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getId());
                factura.setIdCliente(idClienteNew);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getId());
                factura.setIdEmpleado(idEmpleadoNew);
            }
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                factura.setIdEstado(idEstadoNew);
            }
            if (idModoPagoNew != null) {
                idModoPagoNew = em.getReference(idModoPagoNew.getClass(), idModoPagoNew.getId());
                factura.setIdModoPago(idModoPagoNew);
            }
            Collection<DetalleFactura> attachedDetalleFacturaCollectionNew = new ArrayList<DetalleFactura>();
            for (DetalleFactura detalleFacturaCollectionNewDetalleFacturaToAttach : detalleFacturaCollectionNew) {
                detalleFacturaCollectionNewDetalleFacturaToAttach = em.getReference(detalleFacturaCollectionNewDetalleFacturaToAttach.getClass(), detalleFacturaCollectionNewDetalleFacturaToAttach.getId());
                attachedDetalleFacturaCollectionNew.add(detalleFacturaCollectionNewDetalleFacturaToAttach);
            }
            detalleFacturaCollectionNew = attachedDetalleFacturaCollectionNew;
            factura.setDetalleFacturaCollection(detalleFacturaCollectionNew);
            factura = em.merge(factura);
            if (idCafeteriaOld != null && !idCafeteriaOld.equals(idCafeteriaNew)) {
                idCafeteriaOld.getFacturaCollection().remove(factura);
                idCafeteriaOld = em.merge(idCafeteriaOld);
            }
            if (idCafeteriaNew != null && !idCafeteriaNew.equals(idCafeteriaOld)) {
                idCafeteriaNew.getFacturaCollection().add(factura);
                idCafeteriaNew = em.merge(idCafeteriaNew);
            }
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getFacturaCollection().remove(factura);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getFacturaCollection().add(factura);
                idClienteNew = em.merge(idClienteNew);
            }
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getFacturaCollection().remove(factura);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getFacturaCollection().add(factura);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getFacturaCollection().remove(factura);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getFacturaCollection().add(factura);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idModoPagoOld != null && !idModoPagoOld.equals(idModoPagoNew)) {
                idModoPagoOld.getFacturaCollection().remove(factura);
                idModoPagoOld = em.merge(idModoPagoOld);
            }
            if (idModoPagoNew != null && !idModoPagoNew.equals(idModoPagoOld)) {
                idModoPagoNew.getFacturaCollection().add(factura);
                idModoPagoNew = em.merge(idModoPagoNew);
            }
            for (DetalleFactura detalleFacturaCollectionOldDetalleFactura : detalleFacturaCollectionOld) {
                if (!detalleFacturaCollectionNew.contains(detalleFacturaCollectionOldDetalleFactura)) {
                    detalleFacturaCollectionOldDetalleFactura.setIdFactura(null);
                    detalleFacturaCollectionOldDetalleFactura = em.merge(detalleFacturaCollectionOldDetalleFactura);
                }
            }
            for (DetalleFactura detalleFacturaCollectionNewDetalleFactura : detalleFacturaCollectionNew) {
                if (!detalleFacturaCollectionOld.contains(detalleFacturaCollectionNewDetalleFactura)) {
                    Factura oldIdFacturaOfDetalleFacturaCollectionNewDetalleFactura = detalleFacturaCollectionNewDetalleFactura.getIdFactura();
                    detalleFacturaCollectionNewDetalleFactura.setIdFactura(factura);
                    detalleFacturaCollectionNewDetalleFactura = em.merge(detalleFacturaCollectionNewDetalleFactura);
                    if (oldIdFacturaOfDetalleFacturaCollectionNewDetalleFactura != null && !oldIdFacturaOfDetalleFacturaCollectionNewDetalleFactura.equals(factura)) {
                        oldIdFacturaOfDetalleFacturaCollectionNewDetalleFactura.getDetalleFacturaCollection().remove(detalleFacturaCollectionNewDetalleFactura);
                        oldIdFacturaOfDetalleFacturaCollectionNewDetalleFactura = em.merge(oldIdFacturaOfDetalleFacturaCollectionNewDetalleFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = factura.getId();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
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
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            Cafeteria idCafeteria = factura.getIdCafeteria();
            if (idCafeteria != null) {
                idCafeteria.getFacturaCollection().remove(factura);
                idCafeteria = em.merge(idCafeteria);
            }
            Cliente idCliente = factura.getIdCliente();
            if (idCliente != null) {
                idCliente.getFacturaCollection().remove(factura);
                idCliente = em.merge(idCliente);
            }
            Empleado idEmpleado = factura.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getFacturaCollection().remove(factura);
                idEmpleado = em.merge(idEmpleado);
            }
            EstadoFactura idEstado = factura.getIdEstado();
            if (idEstado != null) {
                idEstado.getFacturaCollection().remove(factura);
                idEstado = em.merge(idEstado);
            }
            ModoPago idModoPago = factura.getIdModoPago();
            if (idModoPago != null) {
                idModoPago.getFacturaCollection().remove(factura);
                idModoPago = em.merge(idModoPago);
            }
            Collection<DetalleFactura> detalleFacturaCollection = factura.getDetalleFacturaCollection();
            for (DetalleFactura detalleFacturaCollectionDetalleFactura : detalleFacturaCollection) {
                detalleFacturaCollectionDetalleFactura.setIdFactura(null);
                detalleFacturaCollectionDetalleFactura = em.merge(detalleFacturaCollectionDetalleFactura);
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
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

    public Factura findFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
