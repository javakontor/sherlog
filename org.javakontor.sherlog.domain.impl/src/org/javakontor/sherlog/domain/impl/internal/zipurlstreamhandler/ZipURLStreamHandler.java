package org.javakontor.sherlog.domain.impl.internal.zipurlstreamhandler;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.osgi.service.url.AbstractURLStreamHandlerService;

/**
 * <p>
 * A URLStreamHandler which allows accessing specific zip entries within a zip file. The format of the file part of the
 * URL is <code>[full path to the zip file]|[name of zip entry]</code>, but you are encouraged to use the
 * <code>createURL(String fileName, String entryName)</code> method instead of creating a URL manually. The host and
 * port part of the URL should be <code>null</code>. An application is responsible for setting a
 * <code>URLStreamHandlerFactory</code> that will return a ZipURLStreamHandler when appropriate.
 */
public class ZipURLStreamHandler extends AbstractURLStreamHandlerService {

  public static final Object URL_HANDLER_PROTOCOL = new String[] { "zip" };

  /**
   * Returns a ZipURLConnection for the specified URL.
   */

  public URLConnection openConnection(URL url) throws IOException {
    String urlFile = url.getFile();
    int barIndex = urlFile.indexOf("|");
    if (barIndex == -1)
      throw new MalformedURLException("Missing '|'");

    String fileName = urlFile.substring(0, barIndex);
    String entryName = urlFile.substring(barIndex + 1);
    return new ZipURLConnection(url, new File(fileName), entryName);
  }

  /**
   * Parses the specified URL string.
   */
  @SuppressWarnings("deprecation")
  protected void parseURL(URL url, String spec, int start, int limit) {
    String urlFile = url.getFile();
    int barIndex = urlFile.indexOf("|");

    String fileName = barIndex == -1 ? urlFile : urlFile.substring(0, barIndex);
    String entryName = barIndex == -1 ? "" : urlFile.substring(barIndex + 1);

    int lastSlashIndex = entryName.lastIndexOf("/");
    String newEntryName = entryName.substring(0, lastSlashIndex + 1) + spec.substring(start, limit);

    setURL(url, url.getProtocol(), "", -1, fileName + "|" + newEntryName, null);
  }

  /**
   * Creates a <code>URL</code> that points to the specified entry within the specified zip file. The <code>URL</code>
   * will have "zip" as the protocol name. To use the resulting <code>URL</code>, an application must set a
   * <code>URLStreamHandlerFactory</code> (via the <code>URL</code> class) which will return a
   * <code>ZipURLStreamHandler</code> for the "zip" protocol. Returns <code>null</code> if the "zip: protocol is
   * unrecognized.
   */

  public static URL createURL(String fileName, String entryName) {
    return createURL("zip", fileName, entryName);
  }

  /**
   * Creates a <code>URL</code> that points to the specified entry within the specified zip file and has the specified
   * protocol name. To use the resulting <code>URL</code>, an application must set a
   * <code>URLStreamHandlerFactory</code> (via the <code>URL</code> class) which will return a
   * <code>ZipURLStreamHandler</code> for the protocol name given to this method. Returns <code>null</code> if the
   * specified protocol is unrecognized.
   */

  public static URL createURL(String protocol, String fileName, String entryName) {
    try {
      return new URL(protocol, "", -1, fileName + "|" + entryName);
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }

}
