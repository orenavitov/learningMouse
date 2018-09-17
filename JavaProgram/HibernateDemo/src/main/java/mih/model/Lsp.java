package mih.model;

import mih.model.mihEnum.AdminSatus;
import mih.model.mihEnum.Direction;
import mih.model.mihEnum.OperateStatus;
import mih.model.mihEnum.Type;

import javax.persistence.*;

@Entity
@Table(name = "lsp")
public class Lsp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "userLabel")
    private String userLabel;

    @Column(name = "direction")
    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Column(name = "ingeressNeId")
    private String ingressNeId;

    @Column(name = "egerssNeId")
    private String egressNeId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "adminSatus")
    @Enumerated(EnumType.STRING)
    private AdminSatus adminSatus;

    @Column(name = "operateStatus")
    @Enumerated(EnumType.STRING)
    private OperateStatus operateStatus;

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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getIngressNeId() {
        return ingressNeId;
    }

    public void setIngressNeId(String ingressNeId) {
        this.ingressNeId = ingressNeId;
    }

    public String getEgressNeId() {
        return egressNeId;
    }

    public void setEgressNeId(String egressNeId) {
        this.egressNeId = egressNeId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public AdminSatus getAdminSatus() {
        return adminSatus;
    }

    public void setAdminSatus(AdminSatus adminSatus) {
        this.adminSatus = adminSatus;
    }

    public OperateStatus getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(OperateStatus operateStatus) {
        this.operateStatus = operateStatus;
    }
}
