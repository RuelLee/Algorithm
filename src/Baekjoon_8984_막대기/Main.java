/*
 Author : Ruel
 Problem : Baekjoon 8984번 막대기
 Problem address : https://www.acmicpc.net/problem/8984
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_8984_막대기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Stick {
    int top;
    int bottom;

    public Stick(int top, int bottom) {
        this.top = top;
        this.bottom = bottom;
    }
}

public class Main {
    static HashMap<Integer, Long> topDP;
    static HashMap<Integer, Long> bottomDP;

    public static void main(String[] args) throws IOException {
        // n개의 막대기가 위, 아래로 연결되어 있다.
        // 서로 교차하지 않으면서, 가장 긴 지그재그 막대기의 길이를 구하여라
        //
        // 정렬과 DP를 이용한 문제
        // 1. 먼저, 수의 개수는 최대 10만개인데, 좌표 값의 범위는 1억까지이므로 압축이 필요하다.
        // 압축을 해도 되지만 간단하게 DP를 해쉬맵으로 세팅하는 걸로 하자.
        // 2. 연결된 막대기들이 서로 교차해서는 안된다 -> 정렬을 통해 일정한 순서대로 처리해주자
        // 정렬을 통해 막대기들을 위 지점 좌표값의 오름차순으로, 위 지점의 좌표 값이 같다면 아래 지점 좌표값의 오름차순으로 정려해주자
        // 그리고 막대기를 순차적으로 살펴보며, 해당 막대기를 연결함으로서 위 좌표나 아래 좌표를 마지막으로 끝나는 지그재그 막대기의 최대값을 DP에 기록해주자
        // 최종적으로 DP에 저장된 값 중 가장 큰 값을 가져온다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        PriorityQueue<Stick> sticks = new PriorityQueue<>((o1, o2) -> {
            if (o1.top == o2.top)       // 위 좌표 값이 같다면, 아래 좌표의 오름차순으로
                return Integer.compare(o1.bottom, o2.bottom);
            // 기본적으로는 위 좌표 값을 오름차순으로 정렬한다.
            return Integer.compare(o1.top, o2.top);
        });
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            sticks.offer(new Stick(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        // 위 좌표에서 끝나는 지그재그 막대기 길이의 최대값.
        topDP = new HashMap<>();
        // 아래 좌표에서 끝나는 지그재그 막대기 길이의 최대값.
        bottomDP = new HashMap<>();

        while (!sticks.isEmpty()) {
            Stick current = sticks.poll();

            // 현재 막대기를 연결하기 전, 위, 아래 좌표의 지그재그 막대 최대 길이값을 가져온다.
            // current.top가 마지막인 막대의 최대 길이값. 저장된 값이 없다면 0.
            long maxLengthTopEnd = topDP.containsKey(current.top) ? topDP.get(current.top) : 0;
            // current.bottom이 마지막인 막대의 최대 길이값. 저장된 값이 없다면 0.
            long maxLengthBottomEnd = bottomDP.containsKey(current.bottom) ? bottomDP.get(current.bottom) : 0;

            // 현재 저장된 위 지점이 마지막으로 끝나는 막대 길이보다
            // 아래 지점의 최대 막대 길이값 + current 막대기를 통해 생기는 새로운 지그재그 막대기의 길이가 더 길다면 DP를 갱신해준다.
            if (maxLengthTopEnd < maxLengthBottomEnd + Math.abs(current.top - current.bottom) + l)
                topDP.put(current.top, maxLengthBottomEnd + Math.abs(current.top - current.bottom) + l);

            // 현재 저장된 아래 지점이 마지막으로 끝나는 막대 길이보다
            // 위 지점의 최대 막대 길이값 + current 막대기를 통해 생기는 새로운 지그재그 막대기의 길이가 더 길다면 DP를 갱신해준다.
            if (maxLengthBottomEnd < maxLengthTopEnd + Math.abs(current.top - current.bottom) + l)
                bottomDP.put(current.bottom, maxLengthTopEnd + Math.abs(current.top - current.bottom) + l);
        }

        // 최대 막대기 길이는 현재 저장된 DP 값중 가장 큰 값을 가져온다.
        long maxLength = Math.max(topDP.values().stream().mapToLong(Long::longValue).max().getAsLong(),
                bottomDP.values().stream().mapToLong(Long::longValue).max().getAsLong());
        System.out.println(maxLength);
    }
}