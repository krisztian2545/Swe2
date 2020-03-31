package geolocator;

import java.net.URL;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.common.net.UrlEscapers;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Class for obtaining geolocation information about an IP adress
 * The class uses the <a href="http://ip-api.com/json/">IP api com</a> service.
 */
public class GeoLocator {

    static Logger logger;

    /**
     * URI of the geolocation service
     */
    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Gson GSON = new Gson();

    /**
     * Creates a <code>GeoLocator</code> object.
     */
    public GeoLocator() {}

    /**
     * Returns the geo. inf. about the JVM running this application.
     *
     * @return an object wrapping the geo. inf.
     * @throws IOException if any I/O error occurs
     */
    public GeoLocation getGeoLocation() throws IOException {
        return getGeoLocation(null);
    }

    /**
     * Returns geolocation information about the IP adress or host name specified.
     * If the argument is <code>null</code> the method returns the geo. inf. of the JVM running this application.
     *
     * @param ipAddrOrHost the IP adress or host name, may be {@code null}
     * @return an object wrapping the geo. inf.
     * @throws IOException if any I/O error occurs
     */
    public GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {
        URL url;
        logger.debug("ipAddrOrHost: {}", ipAddrOrHost);

        if (ipAddrOrHost != null) {
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
        } else {
            url = new URL(GEOLOCATOR_SERVICE_URI);
        }
        logger.debug("Url: {}", url);

        String s = IOUtils.toString(url, "UTF-8");
        return GSON.fromJson(s, GeoLocation.class);
    }

    public static void main(String[] args) throws IOException {

        logger = LoggerFactory.getLogger(GeoLocator.class);
        MDC.put("userId", "my user id");

        try {
            String arg = args.length > 0 ? args[0] : null;
            //System.out.println(new GeoLocator().getGeoLocation(arg));
            logger.info( "{}", new GeoLocator().getGeoLocation(arg) );
        } catch (IOException e) {
            //System.err.println(e.getMessage());
            logger.error(e.getMessage());
        }
    }

}












// annotációk a zhban, olyan metaadatok, amelyek kozvetett hatassal van a vegrehajtasra