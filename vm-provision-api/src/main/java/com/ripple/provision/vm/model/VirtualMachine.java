package com.ripple.provision.vm.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "VM_DETAILS")
public class VirtualMachine {
    private static final long serialVersionUID = -5832767428535043665L;

    public VirtualMachine(){}
    public VirtualMachine(int id, String vmName, String domainName, String os, Integer ramSize, Integer hardDiskSize, Integer noOfCPUCores, String userId, Timestamp updateDate, Timestamp createDate) {
        this.id = id;
        this.vmName = vmName;
        this.domainName = domainName;
        this.os = os;
        this.ramSize = ramSize;
        this.hardDiskSize = hardDiskSize;
        this.noOfCPUCores = noOfCPUCores;
        this.userId = userId;
        this.updateDate = updateDate;
        this.createDate = createDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name= "VM_NAME")
    private String vmName;

    @Column(name= "DOMAIN_NAME")
    private String domainName;

    @Column(name = "OS")
    private String os;

    @Column(name = "RAM_SIZE")
    private Integer ramSize;

    @Column(name = "HARD_DISK_SIZE")
    private Integer hardDiskSize;

    @Column(name = "NO_OF_CPU_CORES")
    private Integer noOfCPUCores;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "UPDATE_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updateDate;

    @Column(name = "CREATE_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Integer getRamSize() {
        return ramSize;
    }

    public void setRamSize(Integer ramSize) {
        this.ramSize = ramSize;
    }

    public Integer getHardDiskSize() {
        return hardDiskSize;
    }

    public void setHardDiskSize(Integer hardDiskSize) {
        this.hardDiskSize = hardDiskSize;
    }

    public Integer getNoOfCPUCores() {
        return noOfCPUCores;
    }

    public void setNoOfCPUCores(Integer noOfCPUCores) {
        this.noOfCPUCores = noOfCPUCores;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
