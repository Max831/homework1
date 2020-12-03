package ru.digitalhabbits.homework1.service;

import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WikipediaClient {
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) {
        final URI uri = prepareSearchUrl(searchString);
        // TODO: NotImplemented
        sendRequest(uri);
        return uri.toString();
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
            throw new RuntimeException(exception);
        }
    }

    private void sendRequest(URI uri) {
        HttpGet request = new HttpGet(uri);

        // add request headers
        request.addHeader("custom-key", "mkyong");
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
        try {

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(request)) {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                    var t = JsonParser.parseString(result);
                    System.out.println(t);
                }
//                JsonParser.parseString(r)
//                JsonPath.
//                JSONObject obj = new JSONObject(jsonString);
//                String pageName = obj.getJSONObject("pageInfo").getString("pageName");
//
//                JSONArray arr = obj.getJSONArray("posts");
//                for (int i = 0; i < arr.length(); i++)
//                {
//                    String post_id = arr.getJSONObject(i).getString("post_id");
//    ......
//                }
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
    }
}
