package org.nag;

import java.io.*;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ImageCrawler downloads all images to specified folder from specified resource.
 * It uses multi threading to make process faster. To start download images you should call downloadImages(String urlToPage) method with URL.
 * To shutdown the service you should call stop() method
 */
public class ImageCrawler {

    //number of threads to download images simultaneously
    public static final int NUMBER_OF_THREADS = 15;

    private ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private String folder;

    public ImageCrawler(String folder) /*throws MalformedURLException*/ {
        this.folder = folder;
    }

    /**
     * Call this method to start download images from specified URL.
     * @param urlToPage
     * @throws IOException
     */
    public void downloadImages(String urlToPage) throws IOException {
        //URL url = new URL(urlToPage);
        Page page = new Page(new URL(urlToPage));
        Collection<URL> imgCollection = page.getImageLinks();
        for(URL imageLink: imgCollection) {
            if (isImageURL(imageLink)) executorService.execute(new ImageTask(imageLink, folder));
        }
    }

    /**
     * Call this method before shutdown an application
     */
    public void stop() {
        executorService.shutdown();
    }

    //detects is current url is an image. Checking for popular extensions should be enough
    private boolean isImageURL(URL url) {
        Pattern img = Pattern.compile("jpg|JPG|bmp|BMP|png|PNG|gif|GIF");
        Matcher imgMatch = img.matcher(url.toString());
        if(imgMatch.find()) return true;
        return false;
    }



}
