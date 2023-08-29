/*
 Author : Ruel
 Problem : Baekjoon 23889번 돌 굴러가유
 Problem address : https://www.acmicpc.net/problem/23889
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23889_돌굴러가유;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 각 마을에는 모래성들이 쌓여있다.
        // 왼쪽으로부터 k개의 돌이 오른쪽으로 굴러가며 모래성들을 부순다.
        // 마을에 m개의 벽을 설치해 돌이 더 이상 오른쪽으로 굴러가지 못하게 할 수 있다.
        // k개의 돌이 굴러가기 시작하는 마을의 위치들이 주어질 때
        // 가장 많은 모래성을 지키려면 어느 마을들에 벽을 설치해야하는가?
        // 지킬 수 있는 모래성의 수가 같다면 사전순으로 이른 쪽을 출력한다.
        //
        // 그리디 문제
        // 돌이 굴러가기 시작하는 마을에서, 다음 돌이 굴러가기 시작하는 마을까지의 모래성 합이
        // 해당 마을에 벽을 설치했을 때 지킬 수 있는 모래성의 수이다.
        // 따라서 위 조건에 따라 지킬 수 있는 모래성의 수를 구하고
        // 가장 많이 지킬 수 있는 마을 m개를 선택한다.
        // 이 때, 사전순으로 이른 순을 출력해야하므로,
        // 같은 모래성의 수라면 왼쪽 마을을 우선적으로 벽을 설치한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 마을, m개의 벽, k개의 돌
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 모래성의 수
        int[] sandCastles = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 돌이 굴러가기 시작하는 마을의 위치
        int[] stones = Arrays.stream(br.readLine().split(" ")).mapToInt(value -> Integer.parseInt(value) - 1).toArray();

        // 해당 돌을 막았을 때
        // 지킬 수 있는 모래성의 수
        int[] saved = new int[k];
        int stoneIdx = stones.length - 1;
        for (int i = sandCastles.length - 1; i >= 0; i--) {
            saved[stoneIdx] += sandCastles[i];
            if (i == stones[stoneIdx]) {
                if (--stoneIdx < 0)
                    break;
            }
        }

        // m개의 벽을 지킬 수 있는 모래성의 수로 선택한다.
        // 상위 m개의 마을의 위치를 남겨둔다.
        // 같은 수라면 오른쪽 마을을 우선적으로 제거한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (saved[o1] == saved[o2])
                return Integer.compare(o2, o1);
            return Integer.compare(saved[o1], saved[o2]);
        });
        for (int i = 0; i < saved.length; i++) {
            // m개보다 적은 수의 벽이라면 일단 추가
            if (priorityQueue.size() < m)
                priorityQueue.offer(i);
            // m개의 벽이 모두 설치되었으나
            // 설치된 벽보다 i번째 돌을 막는 것이 더 많은 수의 모래성을 살릴 수 있는 경우
            else if (saved[priorityQueue.peek()] < saved[i]) {
                priorityQueue.poll();
                priorityQueue.offer(i);
            }
        }

        // m개의 마을 위치를 오름차순으로 정렬
        PriorityQueue<Integer> asc = new PriorityQueue<>();
        while (!priorityQueue.isEmpty())
            asc.offer(stones[priorityQueue.poll()]);
        
        // 답안 출력
        StringBuilder sb = new StringBuilder();
        while (!asc.isEmpty())
            sb.append(asc.poll() + 1).append("\n");
        System.out.print(sb);
    }
}