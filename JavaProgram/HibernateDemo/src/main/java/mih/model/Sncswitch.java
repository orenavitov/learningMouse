package mih.model;

import mih.model.mihEnum.LayerRate;
import mih.model.mihEnum.LinearProtectionType;
import mih.model.mihEnum.RevertiveMode;

import javax.persistence.*;

@Entity
@Table(name = "sncswitch")
public class Sncswitch {

    @Id
    @Column(name = "belongedId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String belongedId;

    @Column(name = "name")
    private String name;

    @Column(name = "wtr")
    private long wtr;

    @Column(name = "holdOffTime")
    private long holdOffTime;

    @Column(name = "rerouteWtr")
    private long rerouteWtr;

    @Column(name = "layerRate")
    @Enumerated(EnumType.STRING)
    private LayerRate layerRate;

    @Column(name = "linearProtectionType")
    @Enumerated(EnumType.STRING)
    private LinearProtectionType linearProtectionType;

    @Column(name = "revertiveMode")
    @Enumerated(EnumType.STRING)
    private RevertiveMode revertiveMode;

    public String getBelongedId() {
        return belongedId;
    }

    public void setBelongedId(String belongedId) {
        this.belongedId = belongedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHoldOffTime() {
        return holdOffTime;
    }

    public void setHoldOffTime(long holdOffTime) {
        this.holdOffTime = holdOffTime;
    }

    public long getRerouteWtr() {
        return rerouteWtr;
    }

    public void setRerouteWtr(long rerouteWtr) {
        this.rerouteWtr = rerouteWtr;
    }

    public LayerRate getLayerRate() {
        return layerRate;
    }

    public void setLayerRate(LayerRate layerRate) {
        this.layerRate = layerRate;
    }

    public LinearProtectionType getLinearProtectionType() {
        return linearProtectionType;
    }

    public void setLinearProtectionType(LinearProtectionType linearProtectionType) {
        this.linearProtectionType = linearProtectionType;
    }

    public RevertiveMode getRevertiveMode() {
        return revertiveMode;
    }

    public void setRevertiveMode(RevertiveMode revertiveMode) {
        this.revertiveMode = revertiveMode;
    }
}
