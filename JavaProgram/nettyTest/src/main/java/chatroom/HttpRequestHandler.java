package chatroom;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private String wsuri;

    public HttpRequestHandler(String wsuri) {
        this.wsuri = wsuri;
    }

    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

    }
}
