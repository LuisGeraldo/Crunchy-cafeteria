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
@Table(name = "factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f")
    , @NamedQuery(name = "Factura.findById", query = "SELECT f FROM Factura f WHERE f.id = :id")
    , @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha")
    , @NamedQuery(name = "Factura.findByComentario", query = "SELECT f FROM Factura f WHERE f.comentario = :comentario")
    , @NamedQuery(name = "Factura.findByMontoTotal", query = "SELECT f FROM Factura f WHERE f.montoTotal = :montoTotal")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    private String fecha;
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "montoTotal")
    private Long montoTotal;
    @OneToMany(mappedBy = "idFactura")
    private Collection<DetalleFactura> detalleFacturaCollection;
    @JoinColumn(name = "idCafeteria", referencedColumnName = "id")
    @ManyToOne
    private Cafeteria idCafeteria;
    @JoinColumn(name = "idCliente", referencedColumnName = "id")
    @ManyToOne
    private Cliente idCliente;
    @JoinColumn(name = "idEmpleado", referencedColumnName = "id")
    @ManyToOne
    private Empleado idEmpleado;
    @JoinColumn(name = "idEstado", referencedColumnName = "id")
    @ManyToOne
    private EstadoFactura idEstado;
    @JoinColumn(name = "idModoPago", referencedColumnName = "id")
    @ManyToOne
    private ModoPago idModoPago;

    public Factura() {
    }

    public Factura(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Long montoTotal) {
        this.montoTotal = montoTotal;
    }

    @XmlTransient
    public Collection<DetalleFactura> getDetalleFacturaCollection() {
        return detalleFacturaCollection;
    }

    public void setDetalleFacturaCollection(Collection<DetalleFactura> detalleFacturaCollection) {
        this.detalleFacturaCollection = detalleFacturaCollection;
    }

    public Cafeteria getIdCafeteria() {
        return idCafeteria;
    }

    public void setIdCafeteria(Cafeteria idCafeteria) {
        this.idCafeteria = idCafeteria;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public EstadoFactura getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EstadoFactura idEstado) {
        this.idEstado = idEstado;
    }

    public ModoPago getIdModoPago() {
        return idModoPago;
    }

    public void setIdModoPago(ModoPago idModoPago) {
        this.idModoPago = idModoPago;
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
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Factura[ id=" + id + " ]";
    }
    
}
