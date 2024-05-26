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
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author user
 */
@Entity
@Table(name = "HouseholdElectricalProducts")
@NamedQueries({
    @NamedQuery(name = "HouseholdElectricalProducts.findAll", query = "SELECT h FROM HouseholdElectricalProducts h"),
    @NamedQuery(name = "HouseholdElectricalProducts.findById", query = "SELECT h FROM HouseholdElectricalProducts h WHERE h.id = :id"),
    @NamedQuery(name = "HouseholdElectricalProducts.findByDeviceType", query = "SELECT h FROM HouseholdElectricalProducts h WHERE h.deviceType = :deviceType"),
    @NamedQuery(name = "HouseholdElectricalProducts.findByPowerConsumptionAmps", query = "SELECT h FROM HouseholdElectricalProducts h WHERE h.powerConsumptionAmps = :powerConsumptionAmps"),
    @NamedQuery(name = "HouseholdElectricalProducts.findByPowerConsumptionWatts", query = "SELECT h FROM HouseholdElectricalProducts h WHERE h.powerConsumptionWatts = :powerConsumptionWatts"),
    @NamedQuery(name = "HouseholdElectricalProducts.findByCostPerHourILS", query = "SELECT h FROM HouseholdElectricalProducts h WHERE h.costPerHourILS = :costPerHourILS")})
public class HouseholdElectricalProducts implements Serializable {

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

    public HouseholdElectricalProducts() {
    }

    public HouseholdElectricalProducts(Integer id) {
        this.id = id;
    }

    public HouseholdElectricalProducts(Integer id, String deviceType, BigDecimal powerConsumptionAmps, int powerConsumptionWatts, BigDecimal costPerHourILS) {
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
        if (!(object instanceof HouseholdElectricalProducts)) {
            return false;
        }
        HouseholdElectricalProducts other = (HouseholdElectricalProducts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.HouseholdElectricalProducts[ id=" + id + " ]";
    }

    
}
