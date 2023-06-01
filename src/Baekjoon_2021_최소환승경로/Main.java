/*
 Author : Ruel
 Problem : Baekjoon 2021번 최소 환승 경로
 Problem address : https://www.acmicpc.net/problem/2021
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2021_최소환승경로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 역, l개의 노선이 주어진다.
        // 시작역부터 도착역까지 도달하는 최소 환승 수를 구하라.
        //
        // BFS 문제
        // n과 l의 값이 꽤 크기 때문에 중복 연산을 최대한 줄여주어야했다.
        // 일단 같은 노선의 경우 환승을 하지 않아도 되므로, 같은 환승 횟수를 가진다.
        // 따라서 BFS를 하되, 노선을 기준으로 생각한다.
        // 여기서 이미 계산된 노선과, 역을 체크해 중복 계산이 발생하지 않도록 주의한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 역과 노선의 개수
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        
        // 노선에 포함된 역들
        List<List<Integer>> lines = new ArrayList<>();
        for (int i = 0; i < l; i++)
            lines.add(new ArrayList<>());
        // 각 역에 연결된 노선
        List<HashSet<Integer>> connectedLines = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connectedLines.add(new HashSet<>());
        // 노선 정보를 읽으며, 각 역에 연결된 노선에 대한 정보도 기록한다.
        for (int i = 0; i < l; i++) {
            st = new StringTokenizer(br.readLine());
            while (st.hasMoreTokens()) {
                int station = Integer.parseInt(st.nextToken());
                if (station == -1)
                    break;

                lines.get(i).add(station);
                connectedLines.get(station).add(i);
            }
        }
        
        // 시작역과 도착역
        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        Queue<Integer> queue = new LinkedList<>();
        // 각 노선에 최소 환승 횟수
        int[] minTransfers = new int[l];
        Arrays.fill(minTransfers, Integer.MAX_VALUE);
        // 시작역에 연결된 노선들에 대해
        // 환승횟수는 0으로 처리한 후, 큐에 담는다.
        for (int line : connectedLines.get(start)) {
            queue.offer(line);
            minTransfers[line] = 0;
        }

        // 이미 계산된 역에 대해서도 추가적인 탐색을 하지 않도록 한다.
        boolean[] visitedStations = new boolean[n + 1];
        while (!queue.isEmpty()) {
            // 현재 노선
            int currentLine = queue.poll();

            // 현재 노선에 연결된 역들을 살펴본다.
            for (int station : lines.get(currentLine)) {
                // 만약 이미 계산됐던 역이라면 살펴보지 않는다.
                if (visitedStations[station])
                    continue;
                
                // station에 연결된 다른 노선들에 대해
                for (int line : connectedLines.get(station)) {
                    // 최소 환승 횟수가 갱신되는 경우에 값을 갱신하고 큐에 추가한다.
                    if (minTransfers[line] > minTransfers[currentLine] + 1) {
                        minTransfers[line] = minTransfers[currentLine] + 1;
                        queue.offer(line);
                    }
                }
                // 해당 역에 대한 노선들을 모두 살펴봤으므로
                // 해당 역에 대해 방문 체크를 해준다.
                visitedStations[station] = true;
            }
        }

        // 도착역에 연결된 노선들 중 가장 적은 환승 횟수를 갖는 노선을 찾아
        int answer = Integer.MAX_VALUE;
        for (int line : connectedLines.get(end))
            answer = Math.min(answer, minTransfers[line]);

        // 해당 환승 횟수를 출력한다.
        // 만약 초기값 그대로라면 불가능한 경우이므로 -1을 출력한다.
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}