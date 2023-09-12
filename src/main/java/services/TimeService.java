package services;

import models.QueryLine;
import models.WaitingTimeline;

import java.util.ArrayList;
import java.util.List;

public class TimeService {

    public static List<WaitingTimeline> findTheTimeForQueryLine(QueryLine queryLine, List<WaitingTimeline> waitingTimelineList) {

        List<WaitingTimeline> matchedTimelineList = new ArrayList<>();

        for (WaitingTimeline waitingTimeline : waitingTimelineList) {
            if(isWaitingTimelineMatched(queryLine, waitingTimeline)) matchedTimelineList.add(waitingTimeline);
        }

        return matchedTimelineList;
    }

    private static boolean isWaitingTimelineMatched(QueryLine queryLine, WaitingTimeline waitingTimeline) {

        boolean isMatched = true;

        // check for serviceId match
        if (!isIdMatched(queryLine.getServiceId(), waitingTimeline.getServiceId())) isMatched = false;

        // check for questionTypeId match
        if (!isIdMatched(queryLine.getQuestionTypeId(), waitingTimeline.getQuestionTypeId())) isMatched = false;

        if (!queryLine.getPN().equals(waitingTimeline.getPN())) isMatched = false;

        if (!isDateMatched(queryLine, waitingTimeline)) isMatched = false;

        return isMatched;
    }

    private static boolean isIdMatched(String queryLineId, String waitingTimelineId) {

        if (queryLineId.equals("*")) return true;

        String[] parsedQueryLineId = queryLineId.split("\\.");
        String[] parsedWaitingTimelineId = waitingTimelineId.split("\\.");

        for (int i = 0; i < parsedQueryLineId.length && i < parsedWaitingTimelineId.length; i++) {
            if (!parsedQueryLineId[i].equals(parsedWaitingTimelineId[i])) return false;
        }

        return true;
    }

    private static boolean isDateMatched(QueryLine queryLine, WaitingTimeline waitingTimeline) {

        if (queryLine.getTo() != null) {
            return waitingTimeline.getDate().after(queryLine.getFrom()) &&
                    waitingTimeline.getDate().before(queryLine.getTo());
        } else {
            return waitingTimeline.getDate().after(queryLine.getFrom());
        }

    }

}
