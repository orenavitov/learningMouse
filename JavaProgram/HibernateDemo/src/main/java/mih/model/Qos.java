package mih.model;

import mih.model.mihEnum.exp;

import javax.persistence.*;

@Entity
@Table(name = "qos")
public class Qos {

    @Id
    @Column(name = "belongedId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String belongedId;

    @Column(name = "exp")
    private exp exp;

    @Column(name = "cacMode")
    private byte cacMode;

    @Column(name = "convgMode")
    private byte convgMode;

    @Column(name = "trafficAdjMode")
    private byte trafficAdjMode;

    @Column(name = "a2Zpolicing")
    private byte a2Zpolicing;

    @Column(name = "z2Apolicing")
    private byte z2Apolicing;

    @Column(name = "a2Zcir")
    private int a2Zcir;

    @Column(name = "z2Acir")
    private int z2Acir;

    @Column(name = "a2Zpir")
    private int a2Zpir;

    @Column(name = "z3Apir")
    private int z3Apir;

    @Column(name = "a2Zcbs")
    private int a2Zcbs;

    @Column(name = "z2Acbs")
    private int z2Acbs;

    @Column(name = "a2Zpbs")
    private int a2Zpbs;

    @Column(name = "z2Apbs")
    private int z2Apbs;

    @Column(name = "a2ZcolorMode")
    private int a2ZcolorMode;

    @Column(name = "z2AcolorMode")
    private int z2AcolorMode;

    public mih.model.mihEnum.exp getExp() {
        return exp;
    }

    public void setExp(mih.model.mihEnum.exp exp) {
        this.exp = exp;
    }

    public byte getCacMode() {
        return cacMode;
    }

    public void setCacMode(byte cacMode) {
        this.cacMode = cacMode;
    }

    public byte getConvgMode() {
        return convgMode;
    }

    public void setConvgMode(byte convgMode) {
        this.convgMode = convgMode;
    }

    public byte getTrafficAdjMode() {
        return trafficAdjMode;
    }

    public void setTrafficAdjMode(byte trafficAdjMode) {
        this.trafficAdjMode = trafficAdjMode;
    }

    public byte getA2Zpolicing() {
        return a2Zpolicing;
    }

    public void setA2Zpolicing(byte a2Zpolicing) {
        this.a2Zpolicing = a2Zpolicing;
    }

    public byte getZ2Apolicing() {
        return z2Apolicing;
    }

    public void setZ2Apolicing(byte z2Apolicing) {
        this.z2Apolicing = z2Apolicing;
    }

    public int getA2Zcir() {
        return a2Zcir;
    }

    public void setA2Zcir(int a2Zcir) {
        this.a2Zcir = a2Zcir;
    }

    public int getZ2Acir() {
        return z2Acir;
    }

    public void setZ2Acir(int z2Acir) {
        this.z2Acir = z2Acir;
    }

    public int getA2Zpir() {
        return a2Zpir;
    }

    public void setA2Zpir(int a2Zpir) {
        this.a2Zpir = a2Zpir;
    }

    public int getZ3Apir() {
        return z3Apir;
    }

    public void setZ3Apir(int z3Apir) {
        this.z3Apir = z3Apir;
    }

    public int getA2Zcbs() {
        return a2Zcbs;
    }

    public void setA2Zcbs(int a2Zcbs) {
        this.a2Zcbs = a2Zcbs;
    }

    public int getZ2Acbs() {
        return z2Acbs;
    }

    public void setZ2Acbs(int z2Acbs) {
        this.z2Acbs = z2Acbs;
    }

    public int getA2Zpbs() {
        return a2Zpbs;
    }

    public void setA2Zpbs(int a2Zpbs) {
        this.a2Zpbs = a2Zpbs;
    }

    public int getZ2Apbs() {
        return z2Apbs;
    }

    public void setZ2Apbs(int z2Apbs) {
        this.z2Apbs = z2Apbs;
    }

    public int getA2ZcolorMode() {
        return a2ZcolorMode;
    }

    public void setA2ZcolorMode(int a2ZcolorMode) {
        this.a2ZcolorMode = a2ZcolorMode;
    }

    public int getZ2AcolorMode() {
        return z2AcolorMode;
    }

    public void setZ2AcolorMode(int z2AcolorMode) {
        this.z2AcolorMode = z2AcolorMode;
    }

    public String getBelongedId() {
        return belongedId;
    }

    public void setBelongedId(String belongedId) {
        this.belongedId = belongedId;
    }
}
