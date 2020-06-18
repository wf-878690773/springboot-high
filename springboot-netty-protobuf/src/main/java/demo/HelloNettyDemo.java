package demo;

import com.netty.sever.NettyServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class HelloNettyDemo {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        //设置主从线程组
        try {
            bootstrap.group(bossGroup,workGroup)
                    // 指定Channel，设置nio的双向通道
                    .channel(NioServerSocketChannel.class)
                    // 子处理器，用于处理work
                    .childHandler((ChannelHandler) new HelloHandlerInitializer());
            //启动server,并且设置8088为启动端口号，同时启动方式为同步
            ChannelFuture channelFuture = bootstrap.bind(8088).sync();
            //监听关闭的Channel,设置为同步方式
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully().sync();
            workGroup.shutdownGracefully().sync();
        }


    }
}
