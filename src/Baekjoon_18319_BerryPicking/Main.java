/*
 Author : Ruel
 Problem : Baekjoon 18319번 Berry Picking
 Problem address : https://www.acmicpc.net/problem/18319
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18319_BerryPicking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int k;
    static int[] berries;

    public static void main(String[] args) throws IOException {
        // n그루의 나무와 각각에 열린 베리의 수가 주어진다.
        // k개의 바구니가 주어진다.
        // 베시는 k개의 바구니를 채우되, 한 바구니에는 한 나무에서 딴 베리들만 담아야한다.
        // 바구니 중 베리들이 가장 많은 k / 2개는 엘시에게 준다고 할 때
        // 베시가 얻을 수있는 베리의 최대 개수는?
        //
        // 그리디, 브루트 포스 문제
        // 모든 x에 대해, x개의 베리가 담는 바구니를 최대한 많이 만든 후,
        // 이 개수가 k보다 같거나 많다면 엘시 또한 k / 2 * x개의 베리를 갖게 되고
        // k보다 적다면, 나머지 바구니에는 현재 담을 수 있는 최대한의 많은 베리들을 담아 그 개수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n그루의 나무
        int n = Integer.parseInt(st.nextToken());
        // k개의 바구니
        k = Integer.parseInt(st.nextToken());

        berries = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            berries[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(berries);

        int answer = 0;
        // 우선순위큐를 통해, 한 나무에서 x개씩 담은 바구니를 만들고, 남은 베리들을 담아, 최대 개수를 나머지 개수를 괸리한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 1; i <= berries[n - 1]; i++) {
            // 담은 바구니의 수
            int cnt = 0;
            // 살펴보는 나무의 idx
            int idx = n - 1;
            priorityQueue.clear();
            // 가장 많이 열린 나무들부터 내림차순으로 살펴보며
            while (idx >= 0 && cnt < k && berries[idx] >= i) {
                // 최대한 i개의 베리를 담는 바구니를 많이 만든 뒤
                cnt += berries[idx] / i;
                // 나머지를 우선순위큐에 담는다.
                priorityQueue.offer(berries[idx--] % i);
            }

            // k개보다 같거나 많이 바구니들이 만들어졌다면
            // 베시 또한 i * k / 2개의 베리를 갖고, 담으로 넘어간다.
            if (cnt >= k) {
                answer = Math.max(answer, i * k / 2);
                continue;
            }

            // 그 외의 경우
            int sum = 0;
            // 엘시에게 줄 바구니는 모두 채운 경우
            // cnt - (k / 2)개의 엘시가 갖는 바구니 또한 i개씩 담겨있다.
            if (cnt > k / 2)
                sum += (cnt - k / 2) * i;
            // 나머지 바구들을 채운다.
            while (cnt < k && (!priorityQueue.isEmpty() || idx >= 0)) {
                int currentMax = 0;
                // 우선순위큐와 현재 남은 가장 베리가 많은 나무를 비교하여 더 많은 쪽을 담는다.
                if (!priorityQueue.isEmpty() && (idx < 0 || priorityQueue.peek() >= berries[idx]))
                    currentMax = priorityQueue.poll();
                else
                    currentMax = berries[idx--];
                currentMax = Math.min(currentMax, i);

                // 베시에게 주는 경우는 누적
                // 엘시에게 주는 경우는 셀 필요 없이 건너뜀.
                if (cnt >= k / 2)
                    sum += currentMax;
                cnt++;
            }
            // 최대값 여부 체크
            answer = Math.max(answer, sum);
        }
        // 답 출력
        System.out.println(answer);
    }
}