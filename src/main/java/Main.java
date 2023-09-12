import models.QueryLine;
import models.WaitingTimeline;
import services.ParsingService;
import services.TimeService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
//    .stream().mapToInt(WaitingTimeline::getTime).sum()/ matchedTimelineList.size();
    public static void main(String[] args) {
        try {

            FileReader reader = new FileReader("src/main/input.txt");
            FileWriter writer = new FileWriter("src/main/output.txt", true);

            BufferedReader bufferedReader = new BufferedReader(reader);

            List<WaitingTimeline> waitingTimelineList = new ArrayList<>();
            List<QueryLine> queryLineList = new ArrayList<>();

            ParsingService.dataParse(bufferedReader, waitingTimelineList, queryLineList);

            for(QueryLine queryLine : queryLineList){

                List<WaitingTimeline> matchedTimelineList = TimeService.findTheTimeForQueryLine(queryLine, waitingTimelineList);

                if(matchedTimelineList.size() > 0){
                    writer.write(matchedTimelineList.stream().mapToInt(WaitingTimeline::getTime).sum()/ matchedTimelineList.size() + "\n");
                }else {
                    writer.write("-\n");
                }

            }

            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
