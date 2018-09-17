package mih;

import mih.model.*;
import mih.model.mihEnum.*;
import org.hibernate.Session;

import java.util.UUID;

public class Test {

    public static void main(String args[]) {
        System.out.println("hibernate demo");
        Session session = HibernateFactory.getSessionFactory().openSession();

        session.beginTransaction();

//        Tunnel tunnel = new Tunnel();
//        tunnel.setName("testTunnel");
//        tunnel.setUserLabel("mih");
//        tunnel.setParentNcdId("none");
//        tunnel.setSrcNodeId("none");
//        tunnel.setDstNodeId("none");
//        tunnel.setSncswitchId("none");
//        tunnel.setLspId("none");
//        tunnel.setQosId("none");
//        tunnel.setAdminSatus(AdminSatus.admin_up);
//        tunnel.setOperateStatus(OperateStatus.operate_up);
//        tunnel.setFaultStatus(FaultStatus.no_effect);
//        tunnel.setDirection(Direction.bidirection);
//        tunnel.setTunnelType(TunnelType.mpls_tp);

        Node node = new Node();
        node.setId(UUID.randomUUID().toString());
        node.setName("none");
        node.setUserLabel("none");
        node.setAdminSatus(AdminSatus.admin_up);
        node.setOperateStatus(OperateStatus.operate_up);
        node.setTopoId("none");
        node.setRouteMapId("none");
        node.setLspId("none");


        session.save(node);
        session.getTransaction().commit();
    }

}
