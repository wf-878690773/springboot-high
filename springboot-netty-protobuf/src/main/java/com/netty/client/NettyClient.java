package com.netty.client;

import com.google.protobuf.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;


@Component
public class NettyClient {

    private Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private EventLoopGroup group = new NioEventLoopGroup();
    @Value("${netty.port}")
    private int port;
    @Value("${netty.host}")
    private String host;

    private SocketChannel socketChannel;

    public void sendMsg(Message message){
        socketChannel.writeAndFlush(message);
    }

    @PostConstruct
    public void start(){
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(host,port)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ClientHandlerInitilizer());
        ChannelFuture future = bootstrap.connect();
        //客户端断线重连逻辑
        future.addListener((ChannelFutureListener)future1 -> {
           if (future1.isSuccess()){
               LOGGER.info("连接Netty服务端成功");
           }else {
               LOGGER.info("连接失败，进行断线重连");
               future1.channel().eventLoop().schedule(() -> start(),20, TimeUnit.SECONDS);
           }
        });
        socketChannel = (SocketChannel) future.channel();
    }

}
