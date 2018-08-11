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
import entidades.Cliente;
import entidades.TipoCliente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author luis
 */
public class TipoClienteJpaController implements Serializable {

    public TipoClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCliente tipoCliente) {
        if (tipoCliente.getClienteCollection() == null) {
            tipoCliente.setClienteCollection(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : tipoCliente.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getId());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            tipoCliente.setClienteCollection(attachedClienteCollection);
            em.persist(tipoCliente);
            for (Cliente clienteCollectionCliente : tipoCliente.getClienteCollection()) {
                TipoCliente oldIdTipoClienteOfClienteCollectionCliente = clienteCollectionCliente.getIdTipoCliente();
                clienteCollectionCliente.setIdTipoCliente(tipoCliente);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
                if (oldIdTipoClienteOfClienteCollectionCliente != null) {
                    oldIdTipoClienteOfClienteCollectionCliente.getClienteCollection().remove(clienteCollectionCliente);
                    oldIdTipoClienteOfClienteCollectionCliente = em.merge(oldIdTipoClienteOfClienteCollectionCliente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoCliente tipoCliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCliente persistentTipoCliente = em.find(TipoCliente.class, tipoCliente.getId());
            Collection<Cliente> clienteCollectionOld = persistentTipoCliente.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = tipoCliente.getClienteCollection();
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getId());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            tipoCliente.setClienteCollection(clienteCollectionNew);
            tipoCliente = em.merge(tipoCliente);
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    clienteCollectionOldCliente.setIdTipoCliente(null);
                    clienteCollectionOldCliente = em.merge(clienteCollectionOldCliente);
                }
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    TipoCliente oldIdTipoClienteOfClienteCollectionNewCliente = clienteCollectionNewCliente.getIdTipoCliente();
                    clienteCollectionNewCliente.setIdTipoCliente(tipoCliente);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                    if (oldIdTipoClienteOfClienteCollectionNewCliente != null && !oldIdTipoClienteOfClienteCollectionNewCliente.equals(tipoCliente)) {
                        oldIdTipoClienteOfClienteCollectionNewCliente.getClienteCollection().remove(clienteCollectionNewCliente);
                        oldIdTipoClienteOfClienteCollectionNewCliente = em.merge(oldIdTipoClienteOfClienteCollectionNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoCliente.getId();
                if (findTipoCliente(id) == null) {
                    throw new NonexistentEntityException("The tipoCliente with id " + id + " no longer exists.");
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
            TipoCliente tipoCliente;
            try {
                tipoCliente = em.getReference(TipoCliente.class, id);
                tipoCliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCliente with id " + id + " no longer exists.", enfe);
            }
            Collection<Cliente> clienteCollection = tipoCliente.getClienteCollection();
            for (Cliente clienteCollectionCliente : clienteCollection) {
                clienteCollectionCliente.setIdTipoCliente(null);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
            }
            em.remove(tipoCliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoCliente> findTipoClienteEntities() {
        return findTipoClienteEntities(true, -1, -1);
    }

    public List<TipoCliente> findTipoClienteEntities(int maxResults, int firstResult) {
        return findTipoClienteEntities(false, maxResults, firstResult);
    }

    private List<TipoCliente> findTipoClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCliente.class));
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

    public TipoCliente findTipoCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCliente> rt = cq.from(TipoCliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
