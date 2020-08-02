package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "device_type")
public class Type implements Serializable {
    @Id
    @Column(name = "device_type_id")
    private Integer deviceTypeId;

    @Column(name = "type_name")
    private String typeName;

    private static final long serialVersionUID = 1L;

    /**
     * @return device_type_id
     */
    public Integer getDeviceTypeId() {
        return deviceTypeId;
    }

    /**
     * @param deviceTypeId
     */
    public void setDeviceTypeId(Integer deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    /**
     * @return type_name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deviceTypeId=").append(deviceTypeId);
        sb.append(", typeName=").append(typeName);
        sb.append("]");
        return sb.toString();
    }
}