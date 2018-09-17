package com.randominfo.bot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WeatherInfo {
    @Id
    private Integer userId;

    @Column
    private String cityName;

    @Column
    private Integer cityId;

    public WeatherInfo() {
    }

    public WeatherInfo(Integer userId, String cityName, Integer cityId) {
        this.userId = userId;
        this.cityName = cityName;
        this.cityId = cityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "userId=" + userId +
                ", cityName='" + cityName + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
