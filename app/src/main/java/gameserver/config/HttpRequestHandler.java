package gameserver.config;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsURI;

    public HttpRequestHandler(String wsURI) { this.wsURI = wsURI; }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        if (request.uri().equalsIgnoreCase(wsURI)) {

            System.out.println("Upgrade requested");

            ctx.fireChannelRead(request.retain());
        } else {

            //final File indexFile = new File(getClass().getResource("/static/index.html").toURI());
            final InputStream in = getClass().getResourceAsStream("/static/index.html");

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

            tempFile.delete();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
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