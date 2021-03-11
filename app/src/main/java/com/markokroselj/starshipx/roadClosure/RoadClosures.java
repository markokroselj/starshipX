package com.markokroselj.starshipx.roadClosure;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class RoadClosures {

    Document document = Jsoup.connect("https://www.cameroncounty.us/spacex/").get();
    Elements closures = document.getElementsByClass("gem-table");

    public RoadClosures() throws IOException {
    }

    public ArrayList<String> getClosureDate() {

        ArrayList<String> dates = new ArrayList<>();
        for (int i = 1; i < closures.get(0).getElementsByTag("table").get(0).getElementsByTag("tr").size(); i++) {
            dates.add(closures.get(0).getElementsByTag("table").get(0).getElementsByTag("tr").get(i).getElementsByTag("td").get(1).text());
        }
        return dates;
    }

    private ArrayList<String> getClosureStatus() {
        ArrayList<String> status = new ArrayList<>();
        for (int i = 1; i < closures.get(0).getElementsByTag("table").get(0).getElementsByTag("tr").size(); i++) {
            status.add(closures.get(0).getElementsByTag("table").get(0).getElementsByTag("tr").get(i).getElementsByTag("td").get(3).text());
        }
        return status;
    }

    private static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.ENGLISH);
        return df.format(c);
    }


    public ArrayList<String> getScheduledClosuresDates() {
        ArrayList<String> scheduledDates = new ArrayList<>();
        for (int i = 0; i < getClosureDate().size(); i++) {
            Date dateOfClosure;
            Date currentDate;
            try {
                dateOfClosure = new SimpleDateFormat("EEEE, MMM d, yyyy", Locale.ENGLISH).parse(getClosureDate().get(i));
                currentDate = new SimpleDateFormat("EEEE, MMM d, yyyy", Locale.ENGLISH).parse(getCurrentDate());
            } catch (ParseException parseException) {
                parseException.printStackTrace();
                continue;
            }
            if (getClosureStatus().get(i).equals("Closure Scheduled") && (Objects.requireNonNull(dateOfClosure).compareTo(currentDate) >= 0)) {
                scheduledDates.add(getClosureDate().get(i));
            }
        }
        return scheduledDates;
    }

    public boolean isClosureScheduledForToday() {
        boolean isClosure = false;
        for (int i = 0; i < getClosureDate().size(); i++) {
            isClosure = getClosureDate().get(i).equals(getCurrentDate()) && getClosureStatus().get(i).equals("Closure Scheduled");
            if (isClosure) return true;
        }
        return isClosure;
    }
}
