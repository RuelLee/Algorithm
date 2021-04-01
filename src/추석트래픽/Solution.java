package 추석트래픽;

import java.util.*;

class Log {
    int startTime;
    int endTime;

    public Log(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

public class Solution {
    public static void main(String[] args) {
        String[] lines = {"2016-09-15 20:59:57.421 0.351s",
                "2016-09-15 20:59:58.233 1.181s",
                "2016-09-15 20:59:58.299 0.8s",
                "2016-09-15 20:59:58.688 1.041s",
                "2016-09-15 20:59:59.591 1.412s",
                "2016-09-15 21:00:00.464 1.466s",
                "2016-09-15 21:00:00.741 1.581s",
                "2016-09-15 21:00:00.748 2.31s",
                "2016-09-15 21:00:00.966 0.381s",
                "2016-09-15 21:00:02.066 2.62s"};

        // 로그를 시작 시간 순서대로 담을 priorityQueue
        Queue<Log> logQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.startTime, o2.startTime));

        for (String st : lines)
            logQueue.add(stringToLog(st));

        // 로그를 종료시간에 따라 확인할 priorityQueue.
        Queue<Log> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.endTime, o2.endTime));

        int count = 0;

        while (!logQueue.isEmpty()) {   // 로그를 시작 시간 순서대로 queue에 담음.
            Log l = logQueue.poll();
            queue.add(l);
            while (l.startTime - queue.peek().endTime >= 1000)  // 로그들 중 방금 담긴 로그의 시작 시간 1초 이전에 끝난 로그들은 뺌.
                queue.poll();

            if (count < queue.size())   // queue.size()는 현재 담긴 로그의 시작 시간 순간에 처리되고 있는 로그의 개수. 그 값을 비교하여 최대동시처리개수 갱신.
                count = queue.size();

        }
        System.out.println(count);
    }

    static Log stringToLog(String st) {
        String[] split = st.split(" ");
        int endTime = parsingTime(split[1]);
        int duration = (int) (Double.parseDouble(split[2].substring(0, split[2].length() - 1)) * 1000);
        int startTime = endTime - duration + 1;

        return new Log(startTime, endTime);
    }

    static int parsingTime(String time) {
        String[] split = time.split(":");

        return Integer.parseInt(split[0]) * 60 * 60 * 1000 +
                Integer.parseInt(split[1]) * 60 * 1000 +
                (int) (Double.parseDouble(split[2]) * 1000);
    }
}