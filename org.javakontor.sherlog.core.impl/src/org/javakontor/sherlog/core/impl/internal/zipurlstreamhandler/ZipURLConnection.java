package org.javakontor.sherlog.core.impl.internal.zipurlstreamhandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * A <code>URLConnection</code> which loads its content from a specified zip
 * entry within a local file.
 */
public class ZipURLConnection extends URLConnection {


    /**
     * The zip file.
     */
    private final File file;


    /**
     * The name of the zip entry within the zip file.
     */
    private final String zipEntryName;


    /**
     * The <code>ZipFile</code> object for the zip file. Created when
     * <code>connect()</code> is called.
     */
    private ZipFile zipFile = null;


    /**
     * The <code>ZipEntry</code> object for the entry in the zip file. Created
     * when <code>connect()</code> is called.
     */
    private ZipEntry zipEntry = null;


    /**
     * Creates a new <code>ZipURLConnection</code> for the specified zip entry
     * within the specified <code>File</code>.
     */
    public ZipURLConnection(URL url, File file, String zipEntryName) {
        super(url);

        this.file = file;
        this.zipEntryName = zipEntryName;
    }


    /**
     * Attempts to open the zip entry.
     */
    public void connect() throws IOException {
        this.zipFile = new ZipFile(file);
        this.zipEntry = zipFile.getEntry(zipEntryName);
        if (zipEntry == null)
            throw new IOException("Entry " + zipEntryName + " not found in file " + file);
        this.connected = true;
    }


    /**
     * Returns the <code>InputStream</code> that reads from this connection.
     */
    public InputStream getInputStream() throws IOException {
        if (!connected)
            connect();

        return zipFile.getInputStream(zipEntry);
    }


    /**
     * Returns the length of the uncompressed zip entry.
     */
    public int getContentLength() {
        if (!connected)
            return -1;

        return (int) zipEntry.getSize();
    }
}
