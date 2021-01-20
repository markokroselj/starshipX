
package com.markokroselj.starshipx.labPadreCams.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YtVideo {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("pageInfo")
    @Expose
    private PageInfo pageInfo;

    /**
     * No args constructor for use in serialization
     */
    public YtVideo() {
    }

    /**
     * @param kind
     * @param pageInfo
     * @param etag
     * @param items
     */
    public YtVideo(String kind, String etag, List<Item> items, PageInfo pageInfo) {
        super();
        this.kind = kind;
        this.etag = etag;
        this.items = items;
        this.pageInfo = pageInfo;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public YtVideo withKind(String kind) {
        this.kind = kind;
        return this;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public YtVideo withEtag(String etag) {
        this.etag = etag;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public YtVideo withItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public YtVideo withPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }

}
