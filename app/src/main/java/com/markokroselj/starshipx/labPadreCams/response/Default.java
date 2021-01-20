
package com.markokroselj.starshipx.labPadreCams.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Default {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;

    /**
     * No args constructor for use in serialization
     */
    public Default() {
    }

    /**
     * @param width
     * @param url
     * @param height
     */
    public Default(String url, Integer width, Integer height) {
        super();
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Default withUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Default withWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Default withHeight(Integer height) {
        this.height = height;
        return this;
    }

}
