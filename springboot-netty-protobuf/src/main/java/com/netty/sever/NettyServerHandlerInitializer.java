package com.netty.sever;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Description: 初始化器,channel注册后，执行里面的相应的初始化方法
 */
public class NettyServerHandlerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 通过SocketChannel去获得对应的管道pipeline
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 通过管道，添加handler

        // HttpServerCodec是Netty自己提供的助手类，可以理解为拦截器，
        // 对请求相应进行解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());

        // 添加自定义handle
        pipeline.addLast("NettyServerHandler",new NettyServerHandler());

    }
}
