package services;

import models.QueryLine;
import models.WaitingTimeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ParsingService {

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    public static void dataParse(BufferedReader bufferedReader,
                                 List<WaitingTimeline> waitingTimelineList,
                                 List<QueryLine> queryLineList) throws IOException, ParseException {

        String line;

        while ((line = bufferedReader.readLine()) != null) {

            String[] splitLine = line.split(" ");

            if (splitLine[0].equals("C")) {
                waitingTimelineList.add(waitingTimelineParse(splitLine));
            } else if(splitLine[0].equals("D")){
                queryLineList.add(queryLineParse(splitLine));
            }
        }

    }

    private static WaitingTimeline waitingTimelineParse(String[] splitLine) throws ParseException {

        WaitingTimeline waitingTimeline = new WaitingTimeline();

        waitingTimeline.setServiceId(splitLine[1]);
        waitingTimeline.setQuestionTypeId(splitLine[2]);
        waitingTimeline.setPN(splitLine[3]);
        waitingTimeline.setDate(formatter.parse(splitLine[4]));
        waitingTimeline.setTime(Integer.parseInt(splitLine[5]));

        return waitingTimeline;
    }

    private static QueryLine queryLineParse(String[] splitLine) throws ParseException {

        QueryLine queryLine = new QueryLine();

        queryLine.setServiceId(splitLine[1]);
        queryLine.setQuestionTypeId(splitLine[2]);
        queryLine.setPN(splitLine[3]);

        if (isTimeInterval(splitLine[4])) {
            queryLine.setFrom(formatter.parse(splitLine[4].split("-")[0]));
            queryLine.setTo(formatter.parse(splitLine[4].split("-")[1]));
        }else {
            queryLine.setFrom(formatter.parse(splitLine[4]));
        }

        return queryLine;
    }

    private static boolean isTimeInterval(String time) {
        return time.split("-").length > 1;
    }
}
