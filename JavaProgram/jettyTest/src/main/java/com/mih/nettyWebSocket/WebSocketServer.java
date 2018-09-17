package com.mih.nettyWebSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {
    public void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /*
                             * HttpServerCodec ,将请求和应答消息编码或者解码为HTTP消息
                             */
                            pipeline.addLast("http-codec",
                                    new HttpServerCodec());
                            /*
                             * HttpObjectAggregator ,
                             * 将HTTP消息的多个部分组合成一条完整的HTTP消息;
                             */
                            pipeline.addLast("aggregator",
                                    new HttpObjectAggregator(65536));
                            /*
                             * ChunkedWriteHandler ,
                             * 的主要作用是支持异步发送大的码流(例如大文件传输),但不占用过多的内存,防止JAVA内存溢出
                             */
                            ch.pipeline().addLast("http-chunked",
                                    new ChunkedWriteHandler());
                            /*
                             * WebSocketServerHandler 自己的业务处理
                             */
                            pipeline.addLast("handler",
                                    new WebSocketServerHandler());
                        }
                    });

            Channel ch = b.bind(port).sync().channel();
            System.out.println("Web socket server started at port " + port
                    + '.');
            System.out
                    .println("Open your browser and navigate to http://localhost:"
                            + port + '/');

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
