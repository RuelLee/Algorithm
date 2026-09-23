/*
 Author : Ruel
 Problem : Jungol 5937번 두 상단
 Problem address : https://jungol.co.kr/problem/5937
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5937_두상단;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 마을 인구와 마을 간의 연결 정보가 주어진다.
        // 마을들을 두 개의 영역으로 나누고자 한다.
        // 영역 내의 마을들을 서로 직간접적으로 연결되어있어야한다.
        // 두 영역의 인구 합의 차이를 최소화하고자할 때, 그 값은?
        //
        // BFS, 비트마스킹 문제
        // n이 최대 10으로 그리 크지 않다.
        // 따라서, 각 단일 마을부터 BFS를 통해
        // 마을을 하나씩 연결해나가며 직간접적으로 연결된 마을을 비트마스킹으로 처리한다.
        // 그 후, 두 개의 비트마스킹 값을 통해, 마을이 중복되지 않는 두 영역으로 나눌 수 있는지를 확인하고 인구수 차를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 마을
        int n = Integer.parseInt(br.readLine());
        // 각 마을의 인구
        int[] pop = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 전체 인구
        int total = 0;
        for (int i = 0; i < n; i++)
            total += (pop[i] = Integer.parseInt(st.nextToken()));

        // 마을 간 연결 상태
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n; i++)
            connections.add(new ArrayList<>());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            for (int j = 0; j < num; j++)
                connections.get(i).add(Integer.parseInt(st.nextToken()) - 1);
        }

        // 모든 비트가 채워진 값
        int fullBitmask = (1 << n) - 1;
        // 방문 체크
        boolean[] visited = new boolean[1 << n];
        // 초기값
        // 하나의 마을이 영역인 경우.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            int bit = (1 << i);
            queue.offer(bit);
            visited[bit] = true;
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            for (int i = 0; i < n; i++) {
                // i번재 마을이 포함된 상태라면
                if ((cur & (1 << i)) != 0) {
                    // i번째 마을과 연결된 마을을 추가하는 경우를 살펴본다.
                    for (int next : connections.get(i)) {
                        int nextBit = cur | (1 << next);
                        // 미방문인 경우
                        if (!visited[nextBit]) {
                            // 방문 체크 후, 큐 추가
                            visited[nextBit] = true;
                            queue.offer(nextBit);
                        }
                    }
                }
            }
        }


        int ans = Integer.MAX_VALUE;
        // 모든 비트를 살펴가며
        for (int i = 1; i < visited.length; i++) {
            if (!visited[i] || !visited[fullBitmask - i])
                continue;

            // i비트와 반전된 비트가 서로 모두 가능한 경우
            // = 두 영역으로 나눌 수 있는 경우
            // 인구 수 차이를 계산
            int sum = 0;
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0)
                    sum += pop[j];
            }
            ans = Math.min(ans, Math.abs(total - 2 * sum));
        }
        // 불가능한 경우일 경우 -1
        // 그 외의 경우 최소 차이를 출력
        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }
}