package ru.digitalhabbits.homework1.service;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import static org.slf4j.LoggerFactory.getLogger;

public class WikipediaClient {
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";
    private static final Logger logger = getLogger(WikipediaClient.class);

    @Nonnull
    public String search(@Nonnull String searchString) {
        final URI uri = prepareSearchUrl(searchString);
        return sendRequest(uri);
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            logger.error(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    private String sendRequest(URI uri) {
        HttpGet request = new HttpGet(uri);
        request.addHeader("Accept", "application/json");
        request.addHeader("Accept-Charset", "utf-8");
        String result = "";
        try {

            try (CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.STANDARD).build())
                    .build();
                 CloseableHttpResponse response = httpClient.execute(request)) {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()))) {
                        String json = reader.readLine();
                        JSONArray jsonArray = JsonPath.parse(json).read("$.query.pages.*.extract");
                        if (!jsonArray.isEmpty()) {
                            result = jsonArray.get(0).toString();
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.fillInStackTrace() + e.getLocalizedMessage());
        }
        return result;
    }
}
