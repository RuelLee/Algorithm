/*
 Author : Ruel
 Problem : Baekjoon 25395번 커넥티드 카 실험
 Problem address : https://www.acmicpc.net/problem/25395
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25395_커넥티드카실험;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 차와 연료가 주어진다.
        // 사물 인터넷에 연결된 차를 원격 조종할 수 있으며
        // 사물 인터넷에 연결되지 않은 차와 연결된 차가 동일한 위치에 있다면
        // 연결되지 않은 차를 사물 인터넷에 연결할 수 있다.
        // s번째 자동차가 처음부터 사물 인터넷에 연결이 되어있다고 할 때,
        // 사물 인터넷에 연결될 가능성이 있는 모든 차를 구하라
        //
        // 두 포인터, 그래프 탐색 문제
        // s부터 이동할 수 있는 범위를 두 포인터를 이용해 나타내고
        // 범위 내에 차들에 대해 탐색하여 이동할 수 있는 범위를 확장해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        int[] xs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] hs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 처음 s번째 차만으로 이동 가능한 범위
        int left = xs[s - 1] - hs[s - 1];
        int right = xs[s - 1] + hs[s - 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s - 1);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            int idx = current;
            // current부터 오른쪽으로 탐색한다.
            // currnet에서 닿을 수 있는 범위 차를 모두 방문
            while (idx + 1 < n && xs[idx + 1] <= xs[current] + hs[current]) {
                idx++;
                // 범위가 확장될 때만 큐에 추가
                if (xs[idx] - hs[idx] < left ||
                        xs[idx] + hs[idx] > right) {
                    left = Math.min(left, xs[idx] - hs[idx]);
                    right = Math.max(right, xs[idx] + hs[idx]);
                    queue.offer(idx);
                }
            }

            // current부터 왼쪽으로 탐색한다.
            idx = current;
            while (idx - 1 >= 0 && xs[idx - 1] >= xs[current] - hs[current]) {
                idx--;
                // 마찬가지로 범위가 확장될 때만 큐에 추가.
                if (xs[idx] - hs[idx] < left ||
                        xs[idx] + hs[idx] > right) {
                    left = Math.min(left, xs[idx] - hs[idx]);
                    right = Math.max(right, xs[idx] + hs[idx]);
                    queue.offer(idx);
                }
            }
        }
        
        // 범위에 속하는 모든 자동차가 사물 인터넷에 연결될 가능성이 있는 자동차들
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < xs.length; i++)
            if (xs[i] >= left && xs[i] <= right)
                sb.append(i + 1).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 출력
        System.out.println(sb);
    }
}