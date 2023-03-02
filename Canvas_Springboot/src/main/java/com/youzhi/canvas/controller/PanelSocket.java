package com.youzhi.canvas.controller;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/colorSocket")
public class PanelSocket {
    private static CopyOnWriteArraySet<PanelSocket> socketSet = new CopyOnWriteArraySet<PanelSocket>();

    private String name;
    private Session session;

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("connect error");
        error.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message, Session session) throws Exception{
        String[] colorArray = message.split(";");
        //TODO save database

        for (PanelSocket mySocket : socketSet) {
            if (this.name.equals(mySocket.name)) {
                System.out.println(this.name + "draw" + message);
            } else {
                // send message to others
                try {
                    mySocket.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        this.name = session.getId();
        socketSet.add(this);
        System.out.println(this.name + "join");
    }

    @OnClose
    public void onClose() {
        socketSet.remove(this);
    }
}
