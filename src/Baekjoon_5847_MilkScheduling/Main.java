/*
 Author : Ruel
 Problem : Baekjoon 5847번 Milk Scheduling
 Problem address : https://www.acmicpc.net/problem/5847
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5847_MilkScheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 젖소가 주어지며, 각 젖소의 젖을 짜는데 걸리는 시간이 주어진다.
        // 젖소로부터 젖을 짜는데, 몇몇의 젖소는 다른 젖소의 젖부터 짜야한다.
        // 모든 젖소를 동시에 젖을 짤 수 있는 충분한 노동자가 있다고 할 때
        // 모든 젖소의 젖을 짜는데 걸리는 최소 시간은?
        //
        // 위상 정렬 문제
        // 모든 젖소의 젖을 동시에 짤 수 있으나, 선후관계가 있어, 동시에 짤 수 없다.
        // 따라서 위상 정렬을 통해, 선후 관계를 계산해나가며 최소 시간을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 젖소, m개의 선후관계
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 젖소의 젖을 짜는데 걸리는 시간
        int[] times = new int[n + 1];
        for (int i = 1; i < times.length; i++)
            times[i] = Integer.parseInt(br.readLine());
        
        // 선후 관계
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        int[] inDegrees = new int[n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            connections.get(a).add(b);
            inDegrees[b]++;
        }
        
        // 해당 젖소의 젖을 짜는데 필요한 가장 이른 시각
        long[] endTime = new long[n + 1];
        // 선후관계가 없는 젖소는 먼저 큐에 담아 짠다.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < inDegrees.length; i++) {
            if (inDegrees[i] == 0) {
                queue.offer(i);
                endTime[i] += times[i];
            }
        }

        long totalEndTime = 0;
        // 먼저 젖을 짜낸 젖소를 꺼내, 다음 가능한 젖소들을 살펴본다.
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // current - > next
            for (int next : connections.get(current)) {
                // next가 시작되기 전까지 endTime을 시작 시각으로 사용한다.
                // next보다 먼저 젖을 짜야하는 모든 젖소들을 짜낸 후에, next가 가능하므로
                // 가능한 시각을 구해나간다.
                endTime[next] = Math.max(endTime[next], endTime[current]);
                
                // 만약 진입차수가 0이 됐다면
                // 드디어 next 젖소의 젖을 짤 수 있다.
                if (--inDegrees[next] == 0) {
                    // 종료 시각은 시작 시간 + next의 젖을 짜는데 걸리는 시간
                    endTime[next] += times[next];
                    // 전체 종료 시간에 영향을 미치는지 확인
                    totalEndTime = Math.max(totalEndTime, endTime[next]);
                    // 큐에 추가
                    queue.offer(next);
                }
            }
        }
        // 전체 젖소의 젖을 짜는데 걸린 시간을 출력
        System.out.println(totalEndTime);
    }
}