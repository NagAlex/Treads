package org.nag;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.*;

/**
 * Utils class that contains useful method to interact with URLConnection
 */
public class ConnectionUtils {

    /**
     * Downloads content for specified URL and returns it as a byte array.
     * Should be used for small files only. Don't use it to download big files it's dangerous.
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] getData(URL url) throws IOException {
        InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName("proxy.fcbank.com.ua"), 3128);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, isa);
        StringBuilder content = new StringBuilder();
        try(BufferedInputStream bis = new BufferedInputStream(url.openConnection(proxy).getInputStream())) {
            int b;
            while((b = bis.read()) != -1) {
                content.append((char) b);
            }
        } catch (IOException e) {
            System.out.println("Error opening Input Stream");
        }
        //System.out.println(content);
        //implement me
        return content.toString().getBytes();
    }
}
