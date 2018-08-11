/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import entidades.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Estado;
import entidades.TipoCliente;
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
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getFacturaCollection() == null) {
            cliente.setFacturaCollection(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado idEstado = cliente.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getId());
                cliente.setIdEstado(idEstado);
            }
            TipoCliente idTipoCliente = cliente.getIdTipoCliente();
            if (idTipoCliente != null) {
                idTipoCliente = em.getReference(idTipoCliente.getClass(), idTipoCliente.getId());
                cliente.setIdTipoCliente(idTipoCliente);
            }
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : cliente.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getId());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            cliente.setFacturaCollection(attachedFacturaCollection);
            em.persist(cliente);
            if (idEstado != null) {
                idEstado.getClienteCollection().add(cliente);
                idEstado = em.merge(idEstado);
            }
            if (idTipoCliente != null) {
                idTipoCliente.getClienteCollection().add(cliente);
                idTipoCliente = em.merge(idTipoCliente);
            }
            for (Factura facturaCollectionFactura : cliente.getFacturaCollection()) {
                Cliente oldIdClienteOfFacturaCollectionFactura = facturaCollectionFactura.getIdCliente();
                facturaCollectionFactura.setIdCliente(cliente);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldIdClienteOfFacturaCollectionFactura != null) {
                    oldIdClienteOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldIdClienteOfFacturaCollectionFactura = em.merge(oldIdClienteOfFacturaCollectionFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            Estado idEstadoOld = persistentCliente.getIdEstado();
            Estado idEstadoNew = cliente.getIdEstado();
            TipoCliente idTipoClienteOld = persistentCliente.getIdTipoCliente();
            TipoCliente idTipoClienteNew = cliente.getIdTipoCliente();
            Collection<Factura> facturaCollectionOld = persistentCliente.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = cliente.getFacturaCollection();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getId());
                cliente.setIdEstado(idEstadoNew);
            }
            if (idTipoClienteNew != null) {
                idTipoClienteNew = em.getReference(idTipoClienteNew.getClass(), idTipoClienteNew.getId());
                cliente.setIdTipoCliente(idTipoClienteNew);
            }
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getId());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            cliente.setFacturaCollection(facturaCollectionNew);
            cliente = em.merge(cliente);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getClienteCollection().remove(cliente);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getClienteCollection().add(cliente);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idTipoClienteOld != null && !idTipoClienteOld.equals(idTipoClienteNew)) {
                idTipoClienteOld.getClienteCollection().remove(cliente);
                idTipoClienteOld = em.merge(idTipoClienteOld);
            }
            if (idTipoClienteNew != null && !idTipoClienteNew.equals(idTipoClienteOld)) {
                idTipoClienteNew.getClienteCollection().add(cliente);
                idTipoClienteNew = em.merge(idTipoClienteNew);
            }
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    facturaCollectionOldFactura.setIdCliente(null);
                    facturaCollectionOldFactura = em.merge(facturaCollectionOldFactura);
                }
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    Cliente oldIdClienteOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getIdCliente();
                    facturaCollectionNewFactura.setIdCliente(cliente);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldIdClienteOfFacturaCollectionNewFactura != null && !oldIdClienteOfFacturaCollectionNewFactura.equals(cliente)) {
                        oldIdClienteOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldIdClienteOfFacturaCollectionNewFactura = em.merge(oldIdClienteOfFacturaCollectionNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            Estado idEstado = cliente.getIdEstado();
            if (idEstado != null) {
                idEstado.getClienteCollection().remove(cliente);
                idEstado = em.merge(idEstado);
            }
            TipoCliente idTipoCliente = cliente.getIdTipoCliente();
            if (idTipoCliente != null) {
                idTipoCliente.getClienteCollection().remove(cliente);
                idTipoCliente = em.merge(idTipoCliente);
            }
            Collection<Factura> facturaCollection = cliente.getFacturaCollection();
            for (Factura facturaCollectionFactura : facturaCollection) {
                facturaCollectionFactura.setIdCliente(null);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
