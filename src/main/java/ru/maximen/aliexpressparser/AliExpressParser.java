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
package ru.maximen.aliexpressparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import ru.maximen.aliexpressparser.model.GpsProduct;
import ru.maximen.aliexpressparser.service.AliExpressParserService;
import ru.maximen.aliexpressparser.service.HttpAliExpressParserService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author maximen39
 */
public class AliExpressParser {
    private static final List<String> CVS_HEADER = Arrays.asList("id товара", "id продавца", "Ссылка на картинку",
            "Ссылка на товар", "Название товара", "Минимальная цена", "Максимальная цена", "Минимальная цена(без скидки)",
            "Максимальная цена(без скидки)", "Скидка", "Promotion id", "Было товаров", "Доступно товаров", "В наличии",
            "Покупок", "Название магазина", "Ссылка на магазин");

    private Gson gson;
    private HttpClient httpClient;
    private AliExpressParserService aliExpressParserService;

    public AliExpressParser() {
        initGson();
        initHttpClient();
        initService();
    }

    private void initGson() {
        gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
    }

    private void initHttpClient() {
        httpClient = HttpClientBuilder
                .create()
                .build();
    }

    private void initService() {
        aliExpressParserService = new HttpAliExpressParserService(gson, httpClient);
    }


    public AliExpressParserService getAliExpressParserService() {
        return aliExpressParserService;
    }

    public void saveGpsProductsInCSV(String path, Collection<GpsProduct> gpsProducts) {
        File parent = new File(path);
        parent.mkdirs();

        File csv = new File(parent, "Products.csv");

        try (FileWriter fileWriter = new FileWriter(csv)) {

            CsvUtils.writeLine(fileWriter, CVS_HEADER);
            for (GpsProduct gpsProduct : gpsProducts) {
                CsvUtils.writeLine(fileWriter, gpsProduct.toList());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
