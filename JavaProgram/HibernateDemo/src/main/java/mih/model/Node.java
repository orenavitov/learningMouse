package mih.model;

import mih.model.mihEnum.AdminSatus;
import mih.model.mihEnum.OperateStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "node")
public class Node {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "userLabel")
    private String userLabel;

    @Column(name = "adminStatus")
    @Enumerated(EnumType.STRING)
    private AdminSatus adminSatus;

    @Column(name = "operateStatus")
    @Enumerated(EnumType.STRING)
    private OperateStatus operateStatus;

    @Column(name = "topoId")
    private String topoId;

    @Column(name = "routeMapId")
    private String routeMapId;

    @Column(name = "lspId")
    private String lspId;

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

    public String getRouteMapId() {
        return routeMapId;
    }

    public void setRouteMapId(String routeMapId) {
        this.routeMapId = routeMapId;
    }

    public String getLspId() {
        return lspId;
    }

    public void setLspId(String lspId) {
        this.lspId = lspId;
    }

    public String getTopoId() {
        return topoId;
    }

    public void setTopoId(String topoId) {
        this.topoId = topoId;
    }
}
