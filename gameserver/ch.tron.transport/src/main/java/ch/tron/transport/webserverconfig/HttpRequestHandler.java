package ch.tron.transport.webserverconfig;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Handles HttpRequests. Forwards request to
 * {@link io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler}
 * if upgrade to websocket is requested.
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);

    private final String wsURI;

    /**
     * Constructs a {@code HttpRequestHandler} object listening on
     * the given {@code URI} for requests to upgrade to a websocket
     * connection.
     *
     * @param wsURI The {@code URI} to upgrade to a websocket
     *              connection
     */
    public HttpRequestHandler(String wsURI) { this.wsURI = wsURI; }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        if (request.uri().equalsIgnoreCase(wsURI)) {

            logger.info("Upgrade requested");

            ctx.fireChannelRead(request.retain());
        } else {

            final InputStream in = getClass().getResourceAsStream("/static/index_battleRoyale_follow.html");

            // Workaround:
            // Creating a temporary file as param for RandomAccessFile-Instance
            // Stays empty and will be deleted after file was sent.
            final File tempFile = File.createTempFile("tempFile", ".txt");

            final String fileString = inputStreamToString(in);

            RandomAccessFile file = new RandomAccessFile(tempFile, "rw");

            file.writeUTF(fileString);

            HttpResponse response = new DefaultHttpResponse(
                    request.protocolVersion(),
                    HttpResponseStatus.OK);
            response.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8")
                    .set(HttpHeaderNames.CONTENT_LENGTH, file.length()-2);

            ctx.write(response);

            // First two Bytes in a RandomAccessFile where writeUTF is used are used for file length
            ctx.write(new DefaultFileRegion(file.getChannel(), 2, file.length()));

            ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT)
                    .addListener(ChannelFutureListener.CLOSE);

            if (!tempFile.delete()) {
                logger.info("tempFile NOT deleted");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("", cause.getMessage());
        ctx.close();
    }

    private String inputStreamToString(InputStream in) throws IOException {
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (in, StandardCharsets.UTF_8.name()))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        return textBuilder.toString();
    }
}
