/*
 Author : Ruel
 Problem : Jungol 4695번 벽보 2 (벽 칠하기)
 Problem address : https://jungol.co.kr/problem/4695
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4695_벽보2_벽칠하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 n의 벽이 주어지고, m개의 포스터가 주어진다.
        // 각 포스터는 s e c 로 주어지고, s ~ e까지의 벽을 c 포스터로 채운다는 뜻이다.
        // 포스터는 벽 위에 덧대어 붙이므로 마지막 포스터만 보인다.
        // 모든 포스터가 붙고 난 이후, 각 벽마다 보이는 포스터의 번호를 출력하라
        //
        // 정렬, 우선순위큐, 그리디 문제
        // 포스터를 시작 벽의 위치에 따라 오름차순 정렬한다.
        // 그리고 우선순위큐를 포스터가 붙은 시간에 대해 내림차순으로 살펴보며 관리한다.
        // 1번 벽부터 시작하여, 현재 해당 벽부터 붙이기 시작하는 포스터가 있는지 살펴보고 우선순위큐에 추가한다.
        // 그리고 우선순위큐에서는 최상단에 이미 붙이기가 끝난 포스터가 있는지 확인하고 제거한다.
        // 최종적으로 우선순위큐가 비어있다면, 그 벽엔 포스터가 붙지 않은 것이고, 있다면 해당 포스터가 붙어있는 것이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 n의 벽, m개의 포스터
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 포스터들
        int[][] posters = new int[m][4];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++)
                posters[i][j] = Integer.parseInt(st.nextToken());
            posters[i][3] = i;
        }
        // 시작 벽에 대해 오름차순 정렬
        Arrays.sort(posters, Comparator.comparingInt(p -> p[0]));

        StringBuilder sb = new StringBuilder();
        // 살펴보는 포스터의 인덱스
        int idx = 0;
        // 우선순위큐. 더 늦게 붙은 포스터를 우선적으로 살펴본다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(posters[o2][3], posters[o1][3]));
        // 1번 벽부터 n번 벽까지
        for (int i = 1; i <= n; i++) {
            // idx번 포스터가 현재 벽부터 붙이기 시작한다면 우선순위큐에 추가
            while (idx < m && posters[idx][0] == i)
                priorityQueue.offer(idx++);
            // 우선순위큐 최상단에 이미 붙이기가 끝난 포스터가 있다면 제거
            while (!priorityQueue.isEmpty() && posters[priorityQueue.peek()][1] < i)
                priorityQueue.poll();

            // 우선순위큐가 비어있다면 0, 아니라면 해당 포스터의 번호를 기록
            sb.append(priorityQueue.isEmpty() ? 0 : posters[priorityQueue.peek()][2]).append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}