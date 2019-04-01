/*
 * Copyright (C) 2019 maximen39
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.maximen.aliexpressparser.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import ru.maximen.aliexpressparser.exception.AliExpressException;
import ru.maximen.aliexpressparser.model.GpsProduct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author maximen39
 */
public class HttpAliExpressParserService implements AliExpressParserService {
    private static final String ALIEXPRESS_GPSFRONT_URL = "https://gpsfront.aliexpress.com";

    private Gson gson;
    private HttpClient httpClient;
    private HttpClientContext context;

    public HttpAliExpressParserService(Gson gson, HttpClient httpClient) {
        this.gson = gson;
        this.httpClient = httpClient;
        this.context = new HttpClientContext();
    }

    @Override
    public Collection<GpsProduct> getGpsProducts(int widgetId, int limit, int offset) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("widget_id", String.valueOf(widgetId)));
        nameValuePairs.add(new BasicNameValuePair("limit", String.valueOf(limit)));
        nameValuePairs.add(new BasicNameValuePair("offset", String.valueOf(offset)));
        nameValuePairs.add(new BasicNameValuePair("postback", "1"));
        nameValuePairs.add(new BasicNameValuePair("_", String.valueOf(System.currentTimeMillis())));

        Optional<JsonObject> optional = sendRequest(
                ALIEXPRESS_GPSFRONT_URL + "/queryGpsProductAjax.do",
                nameValuePairs
        );

        if (optional.isPresent()) {
            JsonObject jsonObject = optional.get();
            if (jsonObject.has("success") && !jsonObject.get("success").getAsBoolean()) {
                throw new AliExpressException(jsonObject.get("message").getAsString());
            }

            if (jsonObject.has("gpsProductDetails")) {
                JsonArray jsonArray = jsonObject.getAsJsonArray("gpsProductDetails");
                return gson.fromJson(jsonArray, new TypeToken<Collection<GpsProduct>>() {
                }.getType());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<GpsProduct> getGpsProducts(int widgetId, int min) {
        if (min <= 12) {
            return getGpsProducts(widgetId, min, 0);
        }
        Set<GpsProduct> gpsProducts = new HashSet<>();

        int i = 0;
        while (gpsProducts.size() < min && i <= min / 12) {
            gpsProducts.addAll(getGpsProducts(widgetId, 12, 12 * i++));
        }
        return gpsProducts;
    }

    private Optional<JsonObject> sendRequest(String query, List<NameValuePair> nameValuePair) {
        try {
            URIBuilder uriBuilder = new URIBuilder(query);
            uriBuilder.setParameters(nameValuePair);
            URI uri = uriBuilder.build().normalize();

            HttpGet request = new HttpGet(uri);
            HttpResponse response = httpClient.execute(request, context);

            try {
                String contentType = response.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();

                if (contentType.contains("application/javascript") || contentType.contains("application/json")) {
                    BufferedReader rd = new BufferedReader(
                            new InputStreamReader(response.getEntity().getContent()));
                    String line = rd.lines().collect(Collectors.joining());
                    return Optional.of(gson.fromJson(line, JsonObject.class));
                }
            } finally {
                EntityUtils.consume(response.getEntity());
            }
        } catch (IOException | URISyntaxException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
