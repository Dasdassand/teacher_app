package ru.vsu.app.teacher.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetAddress;

public class ServerApp {
    private static final int PORT = 8189;
    //  private static final String HOST = "172.20.10.3";
    private Thread t;
    public ServerApp() {
        t = new Thread(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(), new MainHandler());
                            }
                        });
                ChannelFuture future = b.bind(PORT).sync();
                InetAddress IP = InetAddress.getLocalHost();
                System.out.println(IP.toString());
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        });
        t.start();
    }
    public void close(){
        t.interrupt();
    }
}
