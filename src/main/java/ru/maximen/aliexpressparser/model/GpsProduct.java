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
package ru.maximen.aliexpressparser.model;

import java.util.Arrays;
import java.util.List;

/**
 * @author maximen39
 */
public class GpsProduct {

    private long productId;
    private long sellerId;
    private String productImage;
    private String productDetailUrl;
    private String productTitle;
    private String minPrice;
    private String maxPrice;
    private String oriMinPrice;
    private String oriMaxPrice;
    private int discount;
    private long promotionId;
    private long startTime;
    private long endTime;
    private int totalStock;
    private int stock;
    private boolean soldout;
    private int orders;
    private String shopName;
    private String shopUrl;

    public long getProductId() {
        return productId;
    }

    public GpsProduct setProductId(long productId) {
        this.productId = productId;
        return this;
    }

    public long getSellerId() {
        return sellerId;
    }

    public GpsProduct setSellerId(long sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public String getProductImage() {
        return productImage;
    }

    public GpsProduct setProductImage(String productImage) {
        this.productImage = productImage;
        return this;
    }

    public String getProductDetailUrl() {
        return productDetailUrl;
    }

    public GpsProduct setProductDetailUrl(String productDetailUrl) {
        this.productDetailUrl = productDetailUrl;
        return this;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public GpsProduct setProductTitle(String productTitle) {
        this.productTitle = productTitle;
        return this;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public GpsProduct setMinPrice(String minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public GpsProduct setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public String getOriMinPrice() {
        return oriMinPrice;
    }

    public GpsProduct setOriMinPrice(String oriMinPrice) {
        this.oriMinPrice = oriMinPrice;
        return this;
    }

    public String getOriMaxPrice() {
        return oriMaxPrice;
    }

    public GpsProduct setOriMaxPrice(String oriMaxPrice) {
        this.oriMaxPrice = oriMaxPrice;
        return this;
    }

    public int getDiscount() {
        return discount;
    }

    public GpsProduct setDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    public long getPromotionId() {
        return promotionId;
    }

    public GpsProduct setPromotionId(long promotionId) {
        this.promotionId = promotionId;
        return this;
    }

    public long getStartTime() {
        return startTime;
    }

    public GpsProduct setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long getEndTime() {
        return endTime;
    }

    public GpsProduct setEndTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public GpsProduct setTotalStock(int totalStock) {
        this.totalStock = totalStock;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public GpsProduct setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public boolean isSoldout() {
        return soldout;
    }

    public GpsProduct setSoldout(boolean soldout) {
        this.soldout = soldout;
        return this;
    }

    public int getOrders() {
        return orders;
    }

    public GpsProduct setOrders(int orders) {
        this.orders = orders;
        return this;
    }

    public String getShopName() {
        return shopName;
    }

    public GpsProduct setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public GpsProduct setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GpsProduct that = (GpsProduct) o;

        if (productId != that.productId) return false;
        return sellerId == that.sellerId;

    }

    @Override
    public int hashCode() {
        int result = (int) (productId ^ (productId >>> 32));
        result = 31 * result + (int) (sellerId ^ (sellerId >>> 32));
        return result;
    }

    public List<String> toList() {
        return Arrays.asList(
                String.valueOf(getProductId()),
                String.valueOf(getSellerId()),
                "https:" + getProductImage(),
                "https:" + getProductDetailUrl(),
                getProductTitle(),
                getMinPrice(),
                getMaxPrice(),
                getOriMinPrice(),
                getOriMaxPrice(),
                getDiscount() + "%",
                String.valueOf(getPromotionId()),
                getTotalStock() + " шт.",
                getStock() + " шт.",
                isSoldout() ? "Нет в наличии" : "В наличии",
                getOrders() + " шт.",
                getShopName(),
                "https:" + getShopUrl()
        );
    }
}
