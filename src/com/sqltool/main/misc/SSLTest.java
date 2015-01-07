package com.abttsupport.main.misc;

import javax.net.ssl.*;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class SSLTest {

    public static void main(String[] args) throws Exception {
        // configure the SSLContext with a TrustManager
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);

        URL url = new URL(args[1]);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        System.out.println("Response Code: " + conn.getResponseCode());
        System.out.println("Cipher Suite: " + conn.getCipherSuite());

         Certificate[] certs = conn.getServerCertificates();
        for (Certificate certificate : certs) {
            System.out.println("Cert Type: " + certificate.getType());
            System.out.println("Cert Hash Code: " + certificate.hashCode());
            System.out.println("Cert Public Key Algorithm: " + certificate.getPublicKey().getAlgorithm());
            System.out.println("Cert Public Key Format: " + certificate.getPublicKey().getFormat());
        }
        conn.disconnect();
    }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}