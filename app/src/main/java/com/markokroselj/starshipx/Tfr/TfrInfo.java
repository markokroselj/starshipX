package com.markokroselj.starshipx.Tfr;

public class TfrInfo {
    private String date;
    private String issueDate;
    private String beginningDateAndTime;
    private String endDateAndTime;
    private String reason;
    private String type;
    private String location;
    private String altitude;
    private String imgUrl;

    public TfrInfo(String date, String issueDate, String beginningDateAndTime, String endDateAndTime, String reason, String type, String location, String altitude, String imgUrl) {
        this.date = date;
        this.issueDate = issueDate;
        this.beginningDateAndTime = beginningDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.reason = reason;
        this.type = type;
        this.location = location;
        this.altitude = altitude;
        this.imgUrl = imgUrl;
    }

    public TfrInfo() {
    }

    public String getReason() {
        return reason;
    }

    public TfrInfo setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public TfrInfo setIssueDate(String issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public String getDate() {
        return date;
    }

    public TfrInfo setDate(String date) {
        this.date = date;
        return this;
    }

    public String getBeginningDateAndTime() {
        return beginningDateAndTime;
    }

    public TfrInfo setBeginningDateAndTime(String beginningDateAndTime) {
        this.beginningDateAndTime = beginningDateAndTime;
        return this;
    }

    public String getEndDateAndTime() {
        return endDateAndTime;
    }

    public TfrInfo setEndDateAndTime(String endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
        return this;
    }

    public String getType() {
        return type;
    }

    public TfrInfo setType(String type) {
        this.type = type;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public TfrInfo setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getAltitude() {
        return altitude;
    }

    public TfrInfo setAltitude(String altitude) {
        this.altitude = altitude;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public TfrInfo setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    @Override
    public String toString() {
        return "TfrInfo{" +
                "date='" + date + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", beginningDateAndTime='" + beginningDateAndTime + '\'' +
                ", endDateAndTime='" + endDateAndTime + '\'' +
                ", reason='" + reason + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", altitude='" + altitude + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
