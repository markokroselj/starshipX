package com.markokroselj.starshipx.Tfr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Tfr {
    Document document = Jsoup.connect("https://tfr.faa.gov/tfr2/list.html").get();

    public Tfr() throws IOException {
    }

    public ArrayList<String> getDates() {
        Elements tfrs = document.getElementsByTag("table");
        ArrayList<String> dates = new ArrayList<>();
        for (int i = 4; i < tfrs.get(4).getElementsByTag("tbody").get(0).getElementsByTag("tr").size() - 3; i++) {
            Elements tds = tfrs.get(4).getElementsByTag("tbody").get(0).getElementsByTag("tr").get(i).getElementsByTag("td");
            if (tds.get(4).text().trim().equals("SPACE OPERATIONS") && tds.get(5).text().split(",")[0].trim().equals("BROWNSVILLE") && tds.get(5).text().split(",")[1].trim().equals("TX")) {
                if (tds.get(5).text().contains("through")) {
                    String[] tdArr = tds.get(5).text().split("(?<=,)");
                    String startDate = tdArr[3].trim() + " " + tdArr[4].trim().split("\\s+")[0];
                    String endDate = tdArr[5].trim() + " " + tdArr[6].trim().split("\\s+")[0];
                    dates.add(startDate + " - " + endDate);
                } else {
                    String[] tdArr = tds.get(5).text().split("(?<=,)");
                    String date = tdArr[3].trim() + " " + tdArr[4].trim().split("\\s+")[0];
                    dates.add(date);
                }
            }
        }
        return dates;
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        return df.format(c);
    }

    public boolean isToday() {
        ArrayList<String> tfrDates = getDates();
        for (String tfrDate : tfrDates) {
            if (tfrDate.equals(getCurrentDate()))
                return true;
        }
        return false;
    }

    private ArrayList<String> getLinks() {
        Elements tfrs = document.getElementsByTag("table");
        ArrayList<String> links = new ArrayList<>();
        for (int i = 4; i < tfrs.get(4).getElementsByTag("tbody").get(0).getElementsByTag("tr").size() - 3; i++) {
            Elements tds = tfrs.get(4).getElementsByTag("tbody").get(0).getElementsByTag("tr").get(i).getElementsByTag("td");
            if (tds.get(4).text().trim().equals("SPACE OPERATIONS") && tds.get(5).text().split(",")[0].trim().equals("BROWNSVILLE") && tds.get(5).text().split(",")[1].trim().equals("TX")) {
                Element link = tds.get(4).getElementsByTag("a").get(0);
                String url = link.attr("href");
                links.add("https://tfr.faa.gov/" + url.substring(2));
            }
        }
        return links;
    }

    public TfrInfo getTFrInfo(Integer index) throws IOException {
        TfrInfo tfrInfo = new TfrInfo();
        if (index == null) index = 0;
        String url = getLinks().get(index);
        Document document = Jsoup.connect(url).get();
        Element element = document.getElementById("meas");
        Elements trs = element.select(" td:nth-of-type(1) > table:nth-of-type(1) > tbody > tr");
        tfrInfo.setDate(trs.get(3).getElementsByTag("td").get(1).text().split("at")[0]);
        tfrInfo.setIssueDate(trs.get(1).getElementsByTag("td").get(1).text());
        tfrInfo.setLocation(trs.get(2).getElementsByTag("td").get(1).text());
        tfrInfo.setBeginningDateAndTime(trs.get(3).getElementsByTag("td").get(1).text());
        tfrInfo.setEndDateAndTime(trs.get(4).getElementsByTag("td").get(1).text());
        tfrInfo.setReason(trs.get(5).getElementsByTag("td").get(1).text());
        tfrInfo.setType(trs.get(6).getElementsByTag("td").get(1).text());
        tfrInfo.setAltitude(element.select("table:nth-of-type(2) > tbody > tr:nth-of-type(6) > td:nth-of-type(3) > font").text());
        tfrInfo.setImgUrl("https://tfr.faa.gov/" + element.select("table > tbody > tr:nth-of-type(1) > td:nth-of-type(2) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(1) > a > img").attr("src"));
        return tfrInfo;
    }
}
