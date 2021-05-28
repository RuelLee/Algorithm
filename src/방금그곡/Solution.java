package 방금그곡;

import java.util.PriorityQueue;

class Music implements Comparable<Music> {
    int startTime;
    int endTime;
    String name;
    String melody;

    public Music(int startTime, int endTime, String name, String melody) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.melody = melody;
    }

    @Override
    public int compareTo(Music o) {     // 플레이 타임이 긴 순, startTime 이 먼저인 것으로 정렬한다.
        if (this.endTime - this.startTime == o.endTime - o.startTime)
            return Integer.compare(this.startTime, o.startTime);
        return Integer.compare(o.endTime - o.startTime, this.endTime - this.startTime);
    }
}

public class Solution {
    public static void main(String[] args) {
        // 시작 시간, 종료 시간, 제목, 멜로디가 하나의 노래로 주어진다.
        // 특정 멜로디와 같은 구간이 있는 노래를 구하여라(여러 개라면, 플레이 타임이 긴 것. 플레이 타임이 같다면 순서가 이른 것)

        String m = "CC#BCC#BCC#BCC#B";
        String[] musicinfos = {"03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"};

        PriorityQueue<Music> priorityQueue = new PriorityQueue<>();
        for (String s : musicinfos)
            priorityQueue.add(stringParsing(s));

        String target = convertMelody(m);
        String answer = "";
        while (!priorityQueue.isEmpty()) {
            Music current = priorityQueue.poll();
            if (current.melody.contains(target)) {
                answer = current.name;
                break;
            }
        }
        System.out.println(answer.equals("") ? "(None)" : answer);
    }

    static Music stringParsing(String s) {
        String[] split = s.split(",");
        int startTime = Integer.parseInt(split[0].substring(0, 2)) * 60 + Integer.parseInt(split[0].substring(3, 5));
        int endTime = Integer.parseInt(split[1].substring(0, 2)) * 60 + Integer.parseInt(split[1].substring(3, 5));
        int duration = endTime - startTime;
        String name = split[2];
        String melody = convertMelody(split[3]);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < duration; i++)
            sb.append(melody.charAt(i % melody.length()));

        return new Music(startTime, endTime, name, sb.toString());
    }

    static String convertMelody(String s) {     // C# 같이 두 문자로 이루어진 하나의 음을 c와 같은 소문자로 바꾸어준다.
        StringBuilder sb = new StringBuilder();

        int lastIdx = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '#') {
                sb.append(s.substring(lastIdx, i - 1));
                sb.append(s.substring(i - 1, i).toLowerCase());
                lastIdx = ++i;
            }
        }
        sb.append(s.substring(lastIdx));
        return sb.toString();
    }
}