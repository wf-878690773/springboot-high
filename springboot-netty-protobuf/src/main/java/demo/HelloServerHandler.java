package demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;

/**
 * 自定义助手类
 */
public class HelloServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
        // 获取Channel
        Channel channel = channelHandlerContext.channel();
        // 显示客户端的远程地址
        SocketAddress socketAddress = channel.remoteAddress();
        // 判断响应的类型
        if (msg instanceof HttpRequest){
            System.out.println(socketAddress);

            // 缓存区
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello,netty", CharsetUtil.UTF_8);

            // 构建一个http response
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, byteBuf);
            // 为响应增加数据类型和长度
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());

            // 把响应刷到客户端
            channelHandlerContext.writeAndFlush(response);
        }

    }
}
