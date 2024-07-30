/*
 Author : Ruel
 Problem : Baekjoon 32069번 가로등
 Problem address : https://www.acmicpc.net/problem/32069
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32069_가로등;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수직선 도로 위에 n개의 가로등이 켜져 있으며
        // 각 위치에서 어둠의 정도는 가장 가까운 가로등까지의 거리라고 한다.
        // 0 ~ l까지 l+1 위치들 중 어두운 정도가 가장 낮은 값부터 k번째 값까지를 차례대로 출력하라
        //
        // BFS 문제
        // l이 최대 10^18까지 매우 크게 주어지지만
        // 해쉬셋을 이용하여 visited 체크를 해주면서 BFS 탐색을 해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 0 ~ l까지의 범위에 어두운 정도가 k번째인 수를 차례대로 출력한다.
        long l = Long.parseLong(st.nextToken());
        // n개의 가로등
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        long[] streetLamps = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();

        // 가로등의 위치를 큐에 담는다.
        Queue<Long> queue = new LinkedList<>();
        HashSet<Long> hashSet = new HashSet<>();
        for (long sl : streetLamps) {
            queue.offer(sl);
            hashSet.add(sl);
        }
        
        // 현재 기록한 어두운 정도의 개수
        int count = 0;
        // 현재 거리
        int distance = 0;
        StringBuilder sb = new StringBuilder();
        // 큐가 비지 않았고, k번 미만으로 탐색했을 때
        while (!queue.isEmpty() && count < k) {
            // 다음 거리에 해당하는 위치를 담을 큐
            Queue<Long> nextQueue = new LinkedList<>();
            while (!queue.isEmpty() && count < k) {
                // 현재 위치
                long current = queue.poll();
                // 어두움 정도는 distance
                sb.append(distance).append("\n");
                // 개수 증가
                count++;
                // current - 1이 범위를 벗어나지 않고, 방문하지 않았다면
                // 큐에 추가
                if (current - 1 >= 0 && !hashSet.contains(current - 1)) {
                    nextQueue.offer(current - 1);
                    hashSet.add(current - 1);
                }
                // current + 1에 대해서도 탐색
                if (current + 1 <= l && !hashSet.contains(current + 1)) {
                    nextQueue.offer(current + 1);
                    hashSet.add(current + 1);
                }
            }
            // queue가 빌 때까지 모두 했다면
            // 다음 순서는 nextQueue
            queue = nextQueue;
            // 거리는 1 증가한다.
            distance++;
        }
        // 계산한 결과 출력
        System.out.print(sb);
    }
}