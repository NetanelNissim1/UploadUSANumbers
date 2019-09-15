package com.uploadUsaNumbers.model;

import com.opencsv.bean.CsvBindByName;

public class YellowPagesCSV {

    @CsvBindByName
    private String url;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String address;

    @CsvBindByName
    private String phone;

    @CsvBindByName
    private String rating;

    @CsvBindByName
    private String website;

    @CsvBindByName
    private String categories;

    @CsvBindByName
    private String email;

    @CsvBindByName
    private String extra_phones;

    @CsvBindByName
    private String hours;

    @CsvBindByName
    private String logo;
    @CsvBindByName
    private String images;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExtra_phones() {
        return extra_phones;
    }

    public void setExtra_phones(String extra_phones) {
        this.extra_phones = extra_phones;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

}