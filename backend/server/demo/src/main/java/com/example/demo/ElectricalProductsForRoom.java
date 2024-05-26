/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity
@Table(name = "ElectricalProductsForRoom")
@NamedQueries({
    @NamedQuery(name = "ElectricalProductsForRoom.findAll", query = "SELECT e FROM ElectricalProductsForRoom e"),
    @NamedQuery(name = "ElectricalProductsForRoom.findById", query = "SELECT e FROM ElectricalProductsForRoom e WHERE e.id = :id"),
    @NamedQuery(name = "ElectricalProductsForRoom.findByDeviceType", query = "SELECT e FROM ElectricalProductsForRoom e WHERE e.deviceType = :deviceType"),
    @NamedQuery(name = "ElectricalProductsForRoom.findByPowerConsumptionAmps", query = "SELECT e FROM ElectricalProductsForRoom e WHERE e.powerConsumptionAmps = :powerConsumptionAmps"),
    @NamedQuery(name = "ElectricalProductsForRoom.findByPowerConsumptionWatts", query = "SELECT e FROM ElectricalProductsForRoom e WHERE e.powerConsumptionWatts = :powerConsumptionWatts"),
    @NamedQuery(name = "ElectricalProductsForRoom.findByCostPerHourILS", query = "SELECT e FROM ElectricalProductsForRoom e WHERE e.costPerHourILS = :costPerHourILS"),
//   @NamedQuery(
//    name = "HouseholdElectricalProducts.findByDeviceType",
//    query = "SELECT e.costPerHourILS FROM HouseholdElectricalProducts e WHERE e.deviceType = :deviceType"
//)
})
public class ElectricalProductsForRoom implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DeviceType")
    private String deviceType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PowerConsumptionAmps")
    private BigDecimal powerConsumptionAmps;
    @Basic(optional = false)
    @Column(name = "PowerConsumptionWatts")
    private int powerConsumptionWatts;
    @Basic(optional = false)
    @Column(name = "CostPerHourILS")
    private BigDecimal costPerHourILS;

    public ElectricalProductsForRoom() {
    }

    public ElectricalProductsForRoom(Integer id) {
        this.id = id;
    }

    public ElectricalProductsForRoom(Integer id, String deviceType, BigDecimal powerConsumptionAmps, int powerConsumptionWatts, BigDecimal costPerHourILS) {
        this.id = id;
        this.deviceType = deviceType;
        this.powerConsumptionAmps = powerConsumptionAmps;
        this.powerConsumptionWatts = powerConsumptionWatts;
        this.costPerHourILS = costPerHourILS;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public BigDecimal getPowerConsumptionAmps() {
        return powerConsumptionAmps;
    }

    public void setPowerConsumptionAmps(BigDecimal powerConsumptionAmps) {
        this.powerConsumptionAmps = powerConsumptionAmps;
    }

    public int getPowerConsumptionWatts() {
        return powerConsumptionWatts;
    }

    public void setPowerConsumptionWatts(int powerConsumptionWatts) {
        this.powerConsumptionWatts = powerConsumptionWatts;
    }

    public BigDecimal getCostPerHourILS() {
        return costPerHourILS;
    }

    public void setCostPerHourILS(BigDecimal costPerHourILS) {
        this.costPerHourILS = costPerHourILS;
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
        if (!(object instanceof ElectricalProductsForRoom)) {
            return false;
        }
        ElectricalProductsForRoom other = (ElectricalProductsForRoom) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.ElectricalProductsForRoom[ id=" + id + " ]";
    }
    
}
