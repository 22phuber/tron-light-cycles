package gameserver.config;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsURI;

    public HttpRequestHandler(String wsURI) { this.wsURI = wsURI; }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        final String indexRelativeURI = "../../src/main/resources/static/index.html";
        final String path = getAbsoluteURI(indexRelativeURI).toString().substring(5);
        final File indexFile = new File(path);

        if (request.uri().equalsIgnoreCase(wsURI)) {

            System.out.println("Upgrade requested");

            ctx.fireChannelRead(request.retain());
        } else {
            RandomAccessFile file = new RandomAccessFile(indexFile, "r");
            HttpResponse response = new DefaultHttpResponse(
                    request.protocolVersion(),
                    HttpResponseStatus.OK);
            response.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8")
                    .set(HttpHeaderNames.CONTENT_LENGTH, file.length());

            ctx.write(response);
            ctx.write(new DefaultFileRegion(
                    file.getChannel(), 0, file.length()
            ));
            ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT)
                    .addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private URI getAbsoluteURI(String relativeURI) {
        try {
            URI basingURI = HttpRequestHandler.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI();
            return basingURI.resolve(relativeURI);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Is not URI", e);
        }
    }
}
