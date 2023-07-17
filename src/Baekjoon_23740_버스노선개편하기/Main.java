/*
 Author : Ruel
 Problem : Baekjoon 23740번 버스 노선 개편하기
 Problem address : https://www.acmicpc.net/problem/23740
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23740_버스노선개편하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

class Bus {
    int start;
    int end;
    int fare;

    public Bus(int start, int end, int fare) {
        this.start = start;
        this.end = end;
        this.fare = fare;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 버스 노선은 S E C로 나타낼 수 있으며
        // 구간 [S, E]를 C의 요금으로 운행하는 버스라는 의미이다.
        // 버스들을 통폐합하며, 한 정거장 이상 겹친다면 하나의 버스로 합치며
        // 운행 요금은 더 낮은 쪽은 따른다고 한다.
        // 통폐합이 끝난 버스 노선의 수와
        // 각 노선을 출력하라
        //
        // 정렬 문제
        // 정거장 하나라도 겹친다면 합쳐지므로
        // 기존 버스들은 시작 지점에 대해 오름차순 정렬을 한 후, 하나씩 살펴본다.
        // 그러면서, 지금 신설하고 있는 노선과 구간이 겹친다면 합쳐서 생각하며
        // 시작 정거장이 현재 신설하는 노선과 겹치지 않는다면 분리 후, 새로운 노선으로 생성하는 과정을 반복한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 버스 노선의 수
        int n = Integer.parseInt(br.readLine());
        Bus[] buses = new Bus[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            buses[i] = new Bus(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        // 시작 정거장으로 오름차순 정렬
        Arrays.sort(buses, (o1, o2) -> Integer.compare(o1.start, o2.start));

        // 새로운 버스 노선을 리스트로 저장.
        List<Bus> reorganized = new ArrayList<>();
        
        // 첫번째 버스를 기준으로 새로운 노선 생성 시작
        int currentStart = buses[0].start;
        int currentEnd = buses[0].end;
        int currentFare = buses[0].fare;
        for (int i = 1; i < buses.length; i++) {
            // 만약 i번째 버스의 기점이, 신설하고 있는 노선의 종점보다 더 크다면
            // 노선이 분리됨
            if (currentEnd < buses[i].start) {
                // 기존 신설 노선을 하나의 노선으로 추가
                reorganized.add(new Bus(currentStart, currentEnd, currentFare));
                // 그 후, i번째 버스를 기준으로 다시 생성
                currentStart = buses[i].start;
                currentEnd = buses[i].end;
                currentFare = buses[i].fare;
            } else {        // 정류장이 겹친다면 통합
                // 종점은 신설하고 있는 노선과 i노선 중 더 먼 곳으로
                currentEnd = Math.max(currentEnd, buses[i].end);
                // 요금은 더 싼 노선으로
                currentFare = Math.min(currentFare, buses[i].fare);
            }
        }
        // 마지막 버스가 포함된 노선도 추가
        reorganized.add(new Bus(currentStart, currentEnd, currentFare));

        StringBuilder sb = new StringBuilder();
        // 신설된 노선의 총 수
        sb.append(reorganized.size()).append("\n");
        // 각각의 노선 정보
        for (Bus b : reorganized)
            sb.append(b.start).append(" ").append(b.end).append(" ").append(b.fare).append("\n");
        // 전체 답안 출력
        System.out.print(sb);
    }
}