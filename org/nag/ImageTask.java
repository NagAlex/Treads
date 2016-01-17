package org.nag;

import java.io.InputStream;
import java.io.IOException;
import java.net.*;
import java.nio.file.*;

/**
 * Represents worker that downloads image from URL to specified folder.<br/>
 * Name of the image will be constructed based on URL. Names for the same URL will be the same.
 */
public class ImageTask implements Runnable {
    private URL url;
    private String folder;

    public ImageTask(URL url, String folder) {
        this.url = url;
        this.folder = folder;
    }

    /**
     * Inherited method that do main job - downloads the image and stores it at specified location
     */
    @Override
    public void run() {
        try {
            InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName("proxy.fcbank.com.ua"), 3128);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, isa);
            Path path = Paths.get(folder, buildFileName(url));
            try ( InputStream is = url.openConnection(proxy).getInputStream()) {
                Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Can't create connection to url: " + url);
            }
        } catch (IOException e) {
            System.out.println("Can't connect through proxy");
        }

       //implement me
    }

    //converts URL to unique file name
    private String buildFileName(URL url) {
        return url.toString().replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }
}
