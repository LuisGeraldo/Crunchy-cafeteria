/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "cafeteria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cafeteria.findAll", query = "SELECT c FROM Cafeteria c")
    , @NamedQuery(name = "Cafeteria.findById", query = "SELECT c FROM Cafeteria c WHERE c.id = :id")
    , @NamedQuery(name = "Cafeteria.findByNombre", query = "SELECT c FROM Cafeteria c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Cafeteria.findByDescripcion", query = "SELECT c FROM Cafeteria c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Cafeteria.findByEncargado", query = "SELECT c FROM Cafeteria c WHERE c.encargado = :encargado")})
public class Cafeteria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "encargado")
    private String encargado;
    @JoinColumn(name = "idSucursal", referencedColumnName = "id")
    @ManyToOne
    private Estado idSucursal;
    @JoinColumn(name = "idEstado", referencedColumnName = "id")
    @ManyToOne
    private Estado idEstado;
    @OneToMany(mappedBy = "idCafeteria")
    private Collection<Factura> facturaCollection;

    public Cafeteria() {
    }

    public Cafeteria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Estado getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Estado idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cafeteria)) {
            return false;
        }
        Cafeteria other = (Cafeteria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Cafeteria[ id=" + id + " ]";
    }
    
}
