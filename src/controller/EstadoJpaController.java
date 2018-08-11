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
import java.util.ArrayList;
import java.util.Collection;
import entidades.Cliente;
import entidades.Marca;
import entidades.Empleado;
import entidades.Sucursal;
import entidades.Proveedor;
import entidades.Articulo;
import entidades.Estado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author luis
 */
public class EstadoJpaController implements Serializable {

    public EstadoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("prjCafeteriaPU");;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) {
        if (estado.getCafeteriaCollection() == null) {
            estado.setCafeteriaCollection(new ArrayList<Cafeteria>());
        }
        if (estado.getCafeteriaCollection1() == null) {
            estado.setCafeteriaCollection1(new ArrayList<Cafeteria>());
        }
        if (estado.getClienteCollection() == null) {
            estado.setClienteCollection(new ArrayList<Cliente>());
        }
        if (estado.getMarcaCollection() == null) {
            estado.setMarcaCollection(new ArrayList<Marca>());
        }
        if (estado.getEmpleadoCollection() == null) {
            estado.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        if (estado.getSucursalCollection() == null) {
            estado.setSucursalCollection(new ArrayList<Sucursal>());
        }
        if (estado.getProveedorCollection() == null) {
            estado.setProveedorCollection(new ArrayList<Proveedor>());
        }
        if (estado.getArticuloCollection() == null) {
            estado.setArticuloCollection(new ArrayList<Articulo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cafeteria> attachedCafeteriaCollection = new ArrayList<Cafeteria>();
            for (Cafeteria cafeteriaCollectionCafeteriaToAttach : estado.getCafeteriaCollection()) {
                cafeteriaCollectionCafeteriaToAttach = em.getReference(cafeteriaCollectionCafeteriaToAttach.getClass(), cafeteriaCollectionCafeteriaToAttach.getId());
                attachedCafeteriaCollection.add(cafeteriaCollectionCafeteriaToAttach);
            }
            estado.setCafeteriaCollection(attachedCafeteriaCollection);
            Collection<Cafeteria> attachedCafeteriaCollection1 = new ArrayList<Cafeteria>();
            for (Cafeteria cafeteriaCollection1CafeteriaToAttach : estado.getCafeteriaCollection1()) {
                cafeteriaCollection1CafeteriaToAttach = em.getReference(cafeteriaCollection1CafeteriaToAttach.getClass(), cafeteriaCollection1CafeteriaToAttach.getId());
                attachedCafeteriaCollection1.add(cafeteriaCollection1CafeteriaToAttach);
            }
            estado.setCafeteriaCollection1(attachedCafeteriaCollection1);
            Collection<Cliente> attachedClienteCollection = new ArrayList<Cliente>();
            for (Cliente clienteCollectionClienteToAttach : estado.getClienteCollection()) {
                clienteCollectionClienteToAttach = em.getReference(clienteCollectionClienteToAttach.getClass(), clienteCollectionClienteToAttach.getId());
                attachedClienteCollection.add(clienteCollectionClienteToAttach);
            }
            estado.setClienteCollection(attachedClienteCollection);
            Collection<Marca> attachedMarcaCollection = new ArrayList<Marca>();
            for (Marca marcaCollectionMarcaToAttach : estado.getMarcaCollection()) {
                marcaCollectionMarcaToAttach = em.getReference(marcaCollectionMarcaToAttach.getClass(), marcaCollectionMarcaToAttach.getId());
                attachedMarcaCollection.add(marcaCollectionMarcaToAttach);
            }
            estado.setMarcaCollection(attachedMarcaCollection);
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : estado.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getId());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            estado.setEmpleadoCollection(attachedEmpleadoCollection);
            Collection<Sucursal> attachedSucursalCollection = new ArrayList<Sucursal>();
            for (Sucursal sucursalCollectionSucursalToAttach : estado.getSucursalCollection()) {
                sucursalCollectionSucursalToAttach = em.getReference(sucursalCollectionSucursalToAttach.getClass(), sucursalCollectionSucursalToAttach.getId());
                attachedSucursalCollection.add(sucursalCollectionSucursalToAttach);
            }
            estado.setSucursalCollection(attachedSucursalCollection);
            Collection<Proveedor> attachedProveedorCollection = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionProveedorToAttach : estado.getProveedorCollection()) {
                proveedorCollectionProveedorToAttach = em.getReference(proveedorCollectionProveedorToAttach.getClass(), proveedorCollectionProveedorToAttach.getId());
                attachedProveedorCollection.add(proveedorCollectionProveedorToAttach);
            }
            estado.setProveedorCollection(attachedProveedorCollection);
            Collection<Articulo> attachedArticuloCollection = new ArrayList<Articulo>();
            for (Articulo articuloCollectionArticuloToAttach : estado.getArticuloCollection()) {
                articuloCollectionArticuloToAttach = em.getReference(articuloCollectionArticuloToAttach.getClass(), articuloCollectionArticuloToAttach.getId());
                attachedArticuloCollection.add(articuloCollectionArticuloToAttach);
            }
            estado.setArticuloCollection(attachedArticuloCollection);
            em.persist(estado);
            for (Cafeteria cafeteriaCollectionCafeteria : estado.getCafeteriaCollection()) {
                Estado oldIdSucursalOfCafeteriaCollectionCafeteria = cafeteriaCollectionCafeteria.getIdSucursal();
                cafeteriaCollectionCafeteria.setIdSucursal(estado);
                cafeteriaCollectionCafeteria = em.merge(cafeteriaCollectionCafeteria);
                if (oldIdSucursalOfCafeteriaCollectionCafeteria != null) {
                    oldIdSucursalOfCafeteriaCollectionCafeteria.getCafeteriaCollection().remove(cafeteriaCollectionCafeteria);
                    oldIdSucursalOfCafeteriaCollectionCafeteria = em.merge(oldIdSucursalOfCafeteriaCollectionCafeteria);
                }
            }
            for (Cafeteria cafeteriaCollection1Cafeteria : estado.getCafeteriaCollection1()) {
                Estado oldIdEstadoOfCafeteriaCollection1Cafeteria = cafeteriaCollection1Cafeteria.getIdEstado();
                cafeteriaCollection1Cafeteria.setIdEstado(estado);
                cafeteriaCollection1Cafeteria = em.merge(cafeteriaCollection1Cafeteria);
                if (oldIdEstadoOfCafeteriaCollection1Cafeteria != null) {
                    oldIdEstadoOfCafeteriaCollection1Cafeteria.getCafeteriaCollection1().remove(cafeteriaCollection1Cafeteria);
                    oldIdEstadoOfCafeteriaCollection1Cafeteria = em.merge(oldIdEstadoOfCafeteriaCollection1Cafeteria);
                }
            }
            for (Cliente clienteCollectionCliente : estado.getClienteCollection()) {
                Estado oldIdEstadoOfClienteCollectionCliente = clienteCollectionCliente.getIdEstado();
                clienteCollectionCliente.setIdEstado(estado);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
                if (oldIdEstadoOfClienteCollectionCliente != null) {
                    oldIdEstadoOfClienteCollectionCliente.getClienteCollection().remove(clienteCollectionCliente);
                    oldIdEstadoOfClienteCollectionCliente = em.merge(oldIdEstadoOfClienteCollectionCliente);
                }
            }
            for (Marca marcaCollectionMarca : estado.getMarcaCollection()) {
                Estado oldIdEstadoOfMarcaCollectionMarca = marcaCollectionMarca.getIdEstado();
                marcaCollectionMarca.setIdEstado(estado);
                marcaCollectionMarca = em.merge(marcaCollectionMarca);
                if (oldIdEstadoOfMarcaCollectionMarca != null) {
                    oldIdEstadoOfMarcaCollectionMarca.getMarcaCollection().remove(marcaCollectionMarca);
                    oldIdEstadoOfMarcaCollectionMarca = em.merge(oldIdEstadoOfMarcaCollectionMarca);
                }
            }
            for (Empleado empleadoCollectionEmpleado : estado.getEmpleadoCollection()) {
                Estado oldIdEstadoOfEmpleadoCollectionEmpleado = empleadoCollectionEmpleado.getIdEstado();
                empleadoCollectionEmpleado.setIdEstado(estado);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
                if (oldIdEstadoOfEmpleadoCollectionEmpleado != null) {
                    oldIdEstadoOfEmpleadoCollectionEmpleado.getEmpleadoCollection().remove(empleadoCollectionEmpleado);
                    oldIdEstadoOfEmpleadoCollectionEmpleado = em.merge(oldIdEstadoOfEmpleadoCollectionEmpleado);
                }
            }
            for (Sucursal sucursalCollectionSucursal : estado.getSucursalCollection()) {
                Estado oldIdEstadoOfSucursalCollectionSucursal = sucursalCollectionSucursal.getIdEstado();
                sucursalCollectionSucursal.setIdEstado(estado);
                sucursalCollectionSucursal = em.merge(sucursalCollectionSucursal);
                if (oldIdEstadoOfSucursalCollectionSucursal != null) {
                    oldIdEstadoOfSucursalCollectionSucursal.getSucursalCollection().remove(sucursalCollectionSucursal);
                    oldIdEstadoOfSucursalCollectionSucursal = em.merge(oldIdEstadoOfSucursalCollectionSucursal);
                }
            }
            for (Proveedor proveedorCollectionProveedor : estado.getProveedorCollection()) {
                Estado oldIdEstadoOfProveedorCollectionProveedor = proveedorCollectionProveedor.getIdEstado();
                proveedorCollectionProveedor.setIdEstado(estado);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
                if (oldIdEstadoOfProveedorCollectionProveedor != null) {
                    oldIdEstadoOfProveedorCollectionProveedor.getProveedorCollection().remove(proveedorCollectionProveedor);
                    oldIdEstadoOfProveedorCollectionProveedor = em.merge(oldIdEstadoOfProveedorCollectionProveedor);
                }
            }
            for (Articulo articuloCollectionArticulo : estado.getArticuloCollection()) {
                Estado oldIdEstadoOfArticuloCollectionArticulo = articuloCollectionArticulo.getIdEstado();
                articuloCollectionArticulo.setIdEstado(estado);
                articuloCollectionArticulo = em.merge(articuloCollectionArticulo);
                if (oldIdEstadoOfArticuloCollectionArticulo != null) {
                    oldIdEstadoOfArticuloCollectionArticulo.getArticuloCollection().remove(articuloCollectionArticulo);
                    oldIdEstadoOfArticuloCollectionArticulo = em.merge(oldIdEstadoOfArticuloCollectionArticulo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estado estado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getId());
            Collection<Cafeteria> cafeteriaCollectionOld = persistentEstado.getCafeteriaCollection();
            Collection<Cafeteria> cafeteriaCollectionNew = estado.getCafeteriaCollection();
            Collection<Cafeteria> cafeteriaCollection1Old = persistentEstado.getCafeteriaCollection1();
            Collection<Cafeteria> cafeteriaCollection1New = estado.getCafeteriaCollection1();
            Collection<Cliente> clienteCollectionOld = persistentEstado.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = estado.getClienteCollection();
            Collection<Marca> marcaCollectionOld = persistentEstado.getMarcaCollection();
            Collection<Marca> marcaCollectionNew = estado.getMarcaCollection();
            Collection<Empleado> empleadoCollectionOld = persistentEstado.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = estado.getEmpleadoCollection();
            Collection<Sucursal> sucursalCollectionOld = persistentEstado.getSucursalCollection();
            Collection<Sucursal> sucursalCollectionNew = estado.getSucursalCollection();
            Collection<Proveedor> proveedorCollectionOld = persistentEstado.getProveedorCollection();
            Collection<Proveedor> proveedorCollectionNew = estado.getProveedorCollection();
            Collection<Articulo> articuloCollectionOld = persistentEstado.getArticuloCollection();
            Collection<Articulo> articuloCollectionNew = estado.getArticuloCollection();
            Collection<Cafeteria> attachedCafeteriaCollectionNew = new ArrayList<Cafeteria>();
            for (Cafeteria cafeteriaCollectionNewCafeteriaToAttach : cafeteriaCollectionNew) {
                cafeteriaCollectionNewCafeteriaToAttach = em.getReference(cafeteriaCollectionNewCafeteriaToAttach.getClass(), cafeteriaCollectionNewCafeteriaToAttach.getId());
                attachedCafeteriaCollectionNew.add(cafeteriaCollectionNewCafeteriaToAttach);
            }
            cafeteriaCollectionNew = attachedCafeteriaCollectionNew;
            estado.setCafeteriaCollection(cafeteriaCollectionNew);
            Collection<Cafeteria> attachedCafeteriaCollection1New = new ArrayList<Cafeteria>();
            for (Cafeteria cafeteriaCollection1NewCafeteriaToAttach : cafeteriaCollection1New) {
                cafeteriaCollection1NewCafeteriaToAttach = em.getReference(cafeteriaCollection1NewCafeteriaToAttach.getClass(), cafeteriaCollection1NewCafeteriaToAttach.getId());
                attachedCafeteriaCollection1New.add(cafeteriaCollection1NewCafeteriaToAttach);
            }
            cafeteriaCollection1New = attachedCafeteriaCollection1New;
            estado.setCafeteriaCollection1(cafeteriaCollection1New);
            Collection<Cliente> attachedClienteCollectionNew = new ArrayList<Cliente>();
            for (Cliente clienteCollectionNewClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewClienteToAttach = em.getReference(clienteCollectionNewClienteToAttach.getClass(), clienteCollectionNewClienteToAttach.getId());
                attachedClienteCollectionNew.add(clienteCollectionNewClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            estado.setClienteCollection(clienteCollectionNew);
            Collection<Marca> attachedMarcaCollectionNew = new ArrayList<Marca>();
            for (Marca marcaCollectionNewMarcaToAttach : marcaCollectionNew) {
                marcaCollectionNewMarcaToAttach = em.getReference(marcaCollectionNewMarcaToAttach.getClass(), marcaCollectionNewMarcaToAttach.getId());
                attachedMarcaCollectionNew.add(marcaCollectionNewMarcaToAttach);
            }
            marcaCollectionNew = attachedMarcaCollectionNew;
            estado.setMarcaCollection(marcaCollectionNew);
            Collection<Empleado> attachedEmpleadoCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionNewEmpleadoToAttach : empleadoCollectionNew) {
                empleadoCollectionNewEmpleadoToAttach = em.getReference(empleadoCollectionNewEmpleadoToAttach.getClass(), empleadoCollectionNewEmpleadoToAttach.getId());
                attachedEmpleadoCollectionNew.add(empleadoCollectionNewEmpleadoToAttach);
            }
            empleadoCollectionNew = attachedEmpleadoCollectionNew;
            estado.setEmpleadoCollection(empleadoCollectionNew);
            Collection<Sucursal> attachedSucursalCollectionNew = new ArrayList<Sucursal>();
            for (Sucursal sucursalCollectionNewSucursalToAttach : sucursalCollectionNew) {
                sucursalCollectionNewSucursalToAttach = em.getReference(sucursalCollectionNewSucursalToAttach.getClass(), sucursalCollectionNewSucursalToAttach.getId());
                attachedSucursalCollectionNew.add(sucursalCollectionNewSucursalToAttach);
            }
            sucursalCollectionNew = attachedSucursalCollectionNew;
            estado.setSucursalCollection(sucursalCollectionNew);
            Collection<Proveedor> attachedProveedorCollectionNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionNewProveedorToAttach : proveedorCollectionNew) {
                proveedorCollectionNewProveedorToAttach = em.getReference(proveedorCollectionNewProveedorToAttach.getClass(), proveedorCollectionNewProveedorToAttach.getId());
                attachedProveedorCollectionNew.add(proveedorCollectionNewProveedorToAttach);
            }
            proveedorCollectionNew = attachedProveedorCollectionNew;
            estado.setProveedorCollection(proveedorCollectionNew);
            Collection<Articulo> attachedArticuloCollectionNew = new ArrayList<Articulo>();
            for (Articulo articuloCollectionNewArticuloToAttach : articuloCollectionNew) {
                articuloCollectionNewArticuloToAttach = em.getReference(articuloCollectionNewArticuloToAttach.getClass(), articuloCollectionNewArticuloToAttach.getId());
                attachedArticuloCollectionNew.add(articuloCollectionNewArticuloToAttach);
            }
            articuloCollectionNew = attachedArticuloCollectionNew;
            estado.setArticuloCollection(articuloCollectionNew);
            estado = em.merge(estado);
            for (Cafeteria cafeteriaCollectionOldCafeteria : cafeteriaCollectionOld) {
                if (!cafeteriaCollectionNew.contains(cafeteriaCollectionOldCafeteria)) {
                    cafeteriaCollectionOldCafeteria.setIdSucursal(null);
                    cafeteriaCollectionOldCafeteria = em.merge(cafeteriaCollectionOldCafeteria);
                }
            }
            for (Cafeteria cafeteriaCollectionNewCafeteria : cafeteriaCollectionNew) {
                if (!cafeteriaCollectionOld.contains(cafeteriaCollectionNewCafeteria)) {
                    Estado oldIdSucursalOfCafeteriaCollectionNewCafeteria = cafeteriaCollectionNewCafeteria.getIdSucursal();
                    cafeteriaCollectionNewCafeteria.setIdSucursal(estado);
                    cafeteriaCollectionNewCafeteria = em.merge(cafeteriaCollectionNewCafeteria);
                    if (oldIdSucursalOfCafeteriaCollectionNewCafeteria != null && !oldIdSucursalOfCafeteriaCollectionNewCafeteria.equals(estado)) {
                        oldIdSucursalOfCafeteriaCollectionNewCafeteria.getCafeteriaCollection().remove(cafeteriaCollectionNewCafeteria);
                        oldIdSucursalOfCafeteriaCollectionNewCafeteria = em.merge(oldIdSucursalOfCafeteriaCollectionNewCafeteria);
                    }
                }
            }
            for (Cafeteria cafeteriaCollection1OldCafeteria : cafeteriaCollection1Old) {
                if (!cafeteriaCollection1New.contains(cafeteriaCollection1OldCafeteria)) {
                    cafeteriaCollection1OldCafeteria.setIdEstado(null);
                    cafeteriaCollection1OldCafeteria = em.merge(cafeteriaCollection1OldCafeteria);
                }
            }
            for (Cafeteria cafeteriaCollection1NewCafeteria : cafeteriaCollection1New) {
                if (!cafeteriaCollection1Old.contains(cafeteriaCollection1NewCafeteria)) {
                    Estado oldIdEstadoOfCafeteriaCollection1NewCafeteria = cafeteriaCollection1NewCafeteria.getIdEstado();
                    cafeteriaCollection1NewCafeteria.setIdEstado(estado);
                    cafeteriaCollection1NewCafeteria = em.merge(cafeteriaCollection1NewCafeteria);
                    if (oldIdEstadoOfCafeteriaCollection1NewCafeteria != null && !oldIdEstadoOfCafeteriaCollection1NewCafeteria.equals(estado)) {
                        oldIdEstadoOfCafeteriaCollection1NewCafeteria.getCafeteriaCollection1().remove(cafeteriaCollection1NewCafeteria);
                        oldIdEstadoOfCafeteriaCollection1NewCafeteria = em.merge(oldIdEstadoOfCafeteriaCollection1NewCafeteria);
                    }
                }
            }
            for (Cliente clienteCollectionOldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldCliente)) {
                    clienteCollectionOldCliente.setIdEstado(null);
                    clienteCollectionOldCliente = em.merge(clienteCollectionOldCliente);
                }
            }
            for (Cliente clienteCollectionNewCliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewCliente)) {
                    Estado oldIdEstadoOfClienteCollectionNewCliente = clienteCollectionNewCliente.getIdEstado();
                    clienteCollectionNewCliente.setIdEstado(estado);
                    clienteCollectionNewCliente = em.merge(clienteCollectionNewCliente);
                    if (oldIdEstadoOfClienteCollectionNewCliente != null && !oldIdEstadoOfClienteCollectionNewCliente.equals(estado)) {
                        oldIdEstadoOfClienteCollectionNewCliente.getClienteCollection().remove(clienteCollectionNewCliente);
                        oldIdEstadoOfClienteCollectionNewCliente = em.merge(oldIdEstadoOfClienteCollectionNewCliente);
                    }
                }
            }
            for (Marca marcaCollectionOldMarca : marcaCollectionOld) {
                if (!marcaCollectionNew.contains(marcaCollectionOldMarca)) {
                    marcaCollectionOldMarca.setIdEstado(null);
                    marcaCollectionOldMarca = em.merge(marcaCollectionOldMarca);
                }
            }
            for (Marca marcaCollectionNewMarca : marcaCollectionNew) {
                if (!marcaCollectionOld.contains(marcaCollectionNewMarca)) {
                    Estado oldIdEstadoOfMarcaCollectionNewMarca = marcaCollectionNewMarca.getIdEstado();
                    marcaCollectionNewMarca.setIdEstado(estado);
                    marcaCollectionNewMarca = em.merge(marcaCollectionNewMarca);
                    if (oldIdEstadoOfMarcaCollectionNewMarca != null && !oldIdEstadoOfMarcaCollectionNewMarca.equals(estado)) {
                        oldIdEstadoOfMarcaCollectionNewMarca.getMarcaCollection().remove(marcaCollectionNewMarca);
                        oldIdEstadoOfMarcaCollectionNewMarca = em.merge(oldIdEstadoOfMarcaCollectionNewMarca);
                    }
                }
            }
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    empleadoCollectionOldEmpleado.setIdEstado(null);
                    empleadoCollectionOldEmpleado = em.merge(empleadoCollectionOldEmpleado);
                }
            }
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    Estado oldIdEstadoOfEmpleadoCollectionNewEmpleado = empleadoCollectionNewEmpleado.getIdEstado();
                    empleadoCollectionNewEmpleado.setIdEstado(estado);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                    if (oldIdEstadoOfEmpleadoCollectionNewEmpleado != null && !oldIdEstadoOfEmpleadoCollectionNewEmpleado.equals(estado)) {
                        oldIdEstadoOfEmpleadoCollectionNewEmpleado.getEmpleadoCollection().remove(empleadoCollectionNewEmpleado);
                        oldIdEstadoOfEmpleadoCollectionNewEmpleado = em.merge(oldIdEstadoOfEmpleadoCollectionNewEmpleado);
                    }
                }
            }
            for (Sucursal sucursalCollectionOldSucursal : sucursalCollectionOld) {
                if (!sucursalCollectionNew.contains(sucursalCollectionOldSucursal)) {
                    sucursalCollectionOldSucursal.setIdEstado(null);
                    sucursalCollectionOldSucursal = em.merge(sucursalCollectionOldSucursal);
                }
            }
            for (Sucursal sucursalCollectionNewSucursal : sucursalCollectionNew) {
                if (!sucursalCollectionOld.contains(sucursalCollectionNewSucursal)) {
                    Estado oldIdEstadoOfSucursalCollectionNewSucursal = sucursalCollectionNewSucursal.getIdEstado();
                    sucursalCollectionNewSucursal.setIdEstado(estado);
                    sucursalCollectionNewSucursal = em.merge(sucursalCollectionNewSucursal);
                    if (oldIdEstadoOfSucursalCollectionNewSucursal != null && !oldIdEstadoOfSucursalCollectionNewSucursal.equals(estado)) {
                        oldIdEstadoOfSucursalCollectionNewSucursal.getSucursalCollection().remove(sucursalCollectionNewSucursal);
                        oldIdEstadoOfSucursalCollectionNewSucursal = em.merge(oldIdEstadoOfSucursalCollectionNewSucursal);
                    }
                }
            }
            for (Proveedor proveedorCollectionOldProveedor : proveedorCollectionOld) {
                if (!proveedorCollectionNew.contains(proveedorCollectionOldProveedor)) {
                    proveedorCollectionOldProveedor.setIdEstado(null);
                    proveedorCollectionOldProveedor = em.merge(proveedorCollectionOldProveedor);
                }
            }
            for (Proveedor proveedorCollectionNewProveedor : proveedorCollectionNew) {
                if (!proveedorCollectionOld.contains(proveedorCollectionNewProveedor)) {
                    Estado oldIdEstadoOfProveedorCollectionNewProveedor = proveedorCollectionNewProveedor.getIdEstado();
                    proveedorCollectionNewProveedor.setIdEstado(estado);
                    proveedorCollectionNewProveedor = em.merge(proveedorCollectionNewProveedor);
                    if (oldIdEstadoOfProveedorCollectionNewProveedor != null && !oldIdEstadoOfProveedorCollectionNewProveedor.equals(estado)) {
                        oldIdEstadoOfProveedorCollectionNewProveedor.getProveedorCollection().remove(proveedorCollectionNewProveedor);
                        oldIdEstadoOfProveedorCollectionNewProveedor = em.merge(oldIdEstadoOfProveedorCollectionNewProveedor);
                    }
                }
            }
            for (Articulo articuloCollectionOldArticulo : articuloCollectionOld) {
                if (!articuloCollectionNew.contains(articuloCollectionOldArticulo)) {
                    articuloCollectionOldArticulo.setIdEstado(null);
                    articuloCollectionOldArticulo = em.merge(articuloCollectionOldArticulo);
                }
            }
            for (Articulo articuloCollectionNewArticulo : articuloCollectionNew) {
                if (!articuloCollectionOld.contains(articuloCollectionNewArticulo)) {
                    Estado oldIdEstadoOfArticuloCollectionNewArticulo = articuloCollectionNewArticulo.getIdEstado();
                    articuloCollectionNewArticulo.setIdEstado(estado);
                    articuloCollectionNewArticulo = em.merge(articuloCollectionNewArticulo);
                    if (oldIdEstadoOfArticuloCollectionNewArticulo != null && !oldIdEstadoOfArticuloCollectionNewArticulo.equals(estado)) {
                        oldIdEstadoOfArticuloCollectionNewArticulo.getArticuloCollection().remove(articuloCollectionNewArticulo);
                        oldIdEstadoOfArticuloCollectionNewArticulo = em.merge(oldIdEstadoOfArticuloCollectionNewArticulo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estado.getId();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            Collection<Cafeteria> cafeteriaCollection = estado.getCafeteriaCollection();
            for (Cafeteria cafeteriaCollectionCafeteria : cafeteriaCollection) {
                cafeteriaCollectionCafeteria.setIdSucursal(null);
                cafeteriaCollectionCafeteria = em.merge(cafeteriaCollectionCafeteria);
            }
            Collection<Cafeteria> cafeteriaCollection1 = estado.getCafeteriaCollection1();
            for (Cafeteria cafeteriaCollection1Cafeteria : cafeteriaCollection1) {
                cafeteriaCollection1Cafeteria.setIdEstado(null);
                cafeteriaCollection1Cafeteria = em.merge(cafeteriaCollection1Cafeteria);
            }
            Collection<Cliente> clienteCollection = estado.getClienteCollection();
            for (Cliente clienteCollectionCliente : clienteCollection) {
                clienteCollectionCliente.setIdEstado(null);
                clienteCollectionCliente = em.merge(clienteCollectionCliente);
            }
            Collection<Marca> marcaCollection = estado.getMarcaCollection();
            for (Marca marcaCollectionMarca : marcaCollection) {
                marcaCollectionMarca.setIdEstado(null);
                marcaCollectionMarca = em.merge(marcaCollectionMarca);
            }
            Collection<Empleado> empleadoCollection = estado.getEmpleadoCollection();
            for (Empleado empleadoCollectionEmpleado : empleadoCollection) {
                empleadoCollectionEmpleado.setIdEstado(null);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
            }
            Collection<Sucursal> sucursalCollection = estado.getSucursalCollection();
            for (Sucursal sucursalCollectionSucursal : sucursalCollection) {
                sucursalCollectionSucursal.setIdEstado(null);
                sucursalCollectionSucursal = em.merge(sucursalCollectionSucursal);
            }
            Collection<Proveedor> proveedorCollection = estado.getProveedorCollection();
            for (Proveedor proveedorCollectionProveedor : proveedorCollection) {
                proveedorCollectionProveedor.setIdEstado(null);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
            }
            Collection<Articulo> articuloCollection = estado.getArticuloCollection();
            for (Articulo articuloCollectionArticulo : articuloCollection) {
                articuloCollectionArticulo.setIdEstado(null);
                articuloCollectionArticulo = em.merge(articuloCollectionArticulo);
            }
            em.remove(estado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
