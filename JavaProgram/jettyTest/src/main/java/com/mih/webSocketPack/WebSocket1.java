package com.mih.webSocketPack;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/websocket1")
public class WebSocket1 {

    @OnMessage
    public void onMessage(Session session, String string) {

    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText("websocket1 open");
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        session.getBasicRemote().sendText("websocket1 close");
    }
}
