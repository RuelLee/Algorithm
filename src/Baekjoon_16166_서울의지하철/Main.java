/*
 Author : Ruel
 Problem : Baekjoon 16166번 서울의 지하철
 Problem address : https://www.acmicpc.net/problem/16166
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16166_서울의지하철;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 지하철 호선의 개수 n과 각 호선에 포함된 역들이 주어진다.
        // 0번 역에서 출발하여 도착역에 최소 환승으로 도달하고자할 때
        // 환승 횟수를 구하라
        //
        // 그래프 탐색 문제
        // 같은 호선에 속해있다면 환승 없이 모두 도달할 수 있다.
        // 따라서 호선 단위로 그래프 탐색을 진행하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 호선
        int n = Integer.parseInt(br.readLine());
        
        // 각 호선에 속한 역들
        List<HashSet<Integer>> lines = new ArrayList<>();
        // 각 역에 연결된 호선들
        HashMap<Integer, HashSet<Integer>> connectedLines = new HashMap<>();
        for (int i = 0; i < n; i++) {
            lines.add(new HashSet<>());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int length = Integer.parseInt(st.nextToken());
            for (int j = 0; j < length; j++) {
                int station = Integer.parseInt(st.nextToken());
                // i호선에 station 역이 존재하며
                lines.get(i).add(station);
                // 반대로 station역이 i호선에 연결되어있음을 각각 기록한다.
                if (!connectedLines.containsKey(station))
                    connectedLines.put(station, new HashSet<>());
                connectedLines.get(station).add(i);
            }
        }
        // 도착역
        int end = Integer.parseInt(br.readLine());
        
        // 최소 환승 횟수
        int[] minTransfer = new int[n];
        Arrays.fill(minTransfer, Integer.MAX_VALUE);
        Queue<Integer> queue = new LinkedList<>();
        // 0번 역이 속한 호선은 모두 환승 횟수가 0
        // 이고 해당 호선부터 탐색이 가능하므로 큐에 추가
        for (int line : connectedLines.get(0)) {
            minTransfer[line] = 0;
            queue.offer(line);
        }
        
        // 호선 별 방문 체크
        boolean[] visited = new boolean[n];
        while (!queue.isEmpty()) {
            // 이번 호선
            int line = queue.poll();
            // 이미 계산했다면 건너뛴다.
            if (visited[line])
                continue;
            
            // line에 연결된 모든 역들
            for (int station : lines.get(line)) {
                // 해당 역에서 연결된 다른 호선
                for (int newLine : connectedLines.get(station)) {
                    // 다른 호선까지 도달하는데 최소 환승 횟수가 갱신이 된다면
                    if (minTransfer[newLine] > minTransfer[line] + 1) {
                        // 값 갱신 후, 큐에 추가.
                        minTransfer[newLine] = minTransfer[line] + 1;
                        queue.offer(newLine);
                    }
                }
            }
            visited[line] = true;
        }

        // 답
        int answer = Integer.MAX_VALUE;
        // 도착역이 속한 모든 호선을 살펴보고, 최소 환승 횟수 계산
        for (int line : connectedLines.get(end))
            answer = Math.min(answer, minTransfer[line]);
        // 초기값이 그대로라면 불가능한 경우이므로 -1 출력
        // 그렇지 않다면 answer 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}