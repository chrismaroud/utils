package com.bitsfromspace.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.bitsfromspace.utils.ExceptionUtils.unchecked;
import static com.bitsfromspace.utils.IoUtils.readFully;

/**
 * @author chris
 * @since 29/09/2016.
 */
public interface IoUtils {

    static String httpGet(String url) {

        return unchecked(() -> {

            final HttpURLConnection httpConnection = (HttpURLConnection) new URL(url).openConnection();
            try {
                if (httpConnection.getResponseCode() < 200 || httpConnection.getResponseCode() > 299) {
                    throw new IOException(httpConnection.getResponseCode() + ": " + httpConnection.getResponseMessage());
                }


                try (InputStream in = httpConnection.getInputStream()) {
                    return readFully(in);
                }
            } finally {
                httpConnection.disconnect();
            }
        });
    }


    static String readFully(final InputStream in) {

        final StringBuilder stringBuilder = new StringBuilder();
        final byte[] buffer = new byte[128 * 1024];
        unchecked(() -> {
            for (int read = in.read(buffer); read != -1; read = in.read(buffer)) {
                stringBuilder.append(new String(buffer, 0, read));
            }
        });
        return stringBuilder.toString();


    }
}
