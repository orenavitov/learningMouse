package mih.model;

import mih.model.mihEnum.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tunnel")
public class Tunnel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "userLabel")
    private String userLabel;

    @Column(name = "parentNcdId")
    private String parentNcdId;

    @Column(name = "srcNodeId")
    private String srcNodeId;

    @Column(name = "dstNodeId")
    private String dstNodeId;

    @Column(name = "sncswitchId")
    private String sncswitchId;

    @Column(name = "lspId")
    private String lspId;

    @Column(name = "qosId")
    private String qosId;

    @Column(name = "adminStatus")
    @Enumerated(EnumType.STRING)
    private AdminSatus adminSatus;

    @Column(name = "direction")
    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Column(name = "faultStatus")
    @Enumerated(EnumType.STRING)
    private FaultStatus faultStatus;

    @Column(name = "operateStatus")
    @Enumerated(EnumType.STRING)
    private OperateStatus operateStatus;

    @Column(name = "tunnelType")
    @Enumerated(EnumType.STRING)
    private TunnelType tunnelType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public String getParentNcdId() {
        return parentNcdId;
    }

    public void setParentNcdId(String parentNcdId) {
        this.parentNcdId = parentNcdId;
    }

    public String getSrcNodeId() {
        return srcNodeId;
    }

    public void setSrcNodeId(String srcNodeId) {
        this.srcNodeId = srcNodeId;
    }

    public String getDstNodeId() {
        return dstNodeId;
    }

    public void setDstNodeId(String dstNodeId) {
        this.dstNodeId = dstNodeId;
    }

    public String getSncswitchId() {
        return sncswitchId;
    }

    public void setSncswitchId(String sncswitchId) {
        this.sncswitchId = sncswitchId;
    }

    public String getLspId() {
        return lspId;
    }

    public void setLspId(String lspId) {
        this.lspId = lspId;
    }

    public String getQosId() {
        return qosId;
    }

    public void setQosId(String qosId) {
        this.qosId = qosId;
    }

    public AdminSatus getAdminSatus() {
        return adminSatus;
    }

    public void setAdminSatus(AdminSatus adminSatus) {
        this.adminSatus = adminSatus;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public FaultStatus getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(FaultStatus faultStatus) {
        this.faultStatus = faultStatus;
    }

    public OperateStatus getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(OperateStatus operateStatus) {
        this.operateStatus = operateStatus;
    }

    public TunnelType getTunnelType() {
        return tunnelType;
    }

    public void setTunnelType(TunnelType tunnelType) {
        this.tunnelType = tunnelType;
    }
}
