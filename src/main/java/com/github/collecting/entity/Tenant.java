package com.github.collecting.entity;

import com.github.collecting.jpa.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//租户
@Data
@Entity
@Table(name = "tenant")
public class Tenant extends BaseEntity {
    private int recordStatus;
    @Column(unique = true, nullable = false, updatable = false, length = 64)
    private String tenantCode;
    private String tenantName;
}
