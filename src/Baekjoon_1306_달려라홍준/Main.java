/*
 Author : Ruel
 Problem : Baekjoon 1306번 달려라 홍준
 Problem address : https://www.acmicpc.net/problem/1306
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1306_달려라홍준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 광고판의 밝기와 시야 m이 주어진다
        // i의 위치에 있을 경우, i - (m - 1) ~ i + (m - 1)까지의 광고판이 보인다고 한다.
        // m번째 칸에서 시작해서, n - m + 1칸 까지 뛸 때(= 항상 2m - 1개의 광고판을 볼 때)
        // 각 지점에서의 시야에 들어오는 광고판들 중 가장 밝은 밝기들을 출력하라.
        //
        // 슬라이딩 윈도우 문제
        // 우선순위큐를 최대힙을 통해 2m - 1개의 광고판의 밝기들을 넣는다.
        // 그리고 달리면서 가장 밝은 밝기의 값을 기록하고, i - (m - 1)번째 광고판의 밝기는 제거,
        // i + m 번째 광고판의 밝기는 추가해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 광고판의 개수와 시야.
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 광고판의 밝기들.
        int[] ads = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 시야 내에 들어오는 광고판들을 우선순위큐에 넣는다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        // 특정 값을 하나 제거하기는 쉽지 않으므로, 큐 안에 들어있는 값들의 개수를 센다.
        int[] inQueue = new int[1_000_001];
        // m의 위치일 때 보이는 광고판들을 추가해준다.
        for (int i = 0; i < 2 * m - 1; i++) {
            priorityQueue.offer(ads[i]);
            inQueue[ads[i]]++;
        }

        StringBuilder sb = new StringBuilder();
        // m(여기선 시작이 0이므로 m - 1)부터 n - m + 1까지 달려나간다.
        for (int i = m - 1; i + m - 1 < ads.length - 1; i++) {
            // 우선순위큐의 peek 값의 개수가 0이라면
            // 이미 큐 안에 해당하는 광고판의 밝기는 없는 경우.
            // 해당 값을 제거해준다.
            while (inQueue[priorityQueue.peek()] == 0)
                priorityQueue.poll();

            // 가장 밝은 밝기의 값을 기록하고
            sb.append(priorityQueue.peek()).append(" ");
            // i - (m - 1)번째 광고판의 밝기 하나 제거.
            inQueue[ads[i - (m - 1)]]--;

            // i + m번째 광고판의 밝기 추가.
            priorityQueue.offer(ads[i + m]);
            // inQueue 배열에도 해당하는 밝기의 개수 하나 증가.
            inQueue[ads[i + m]]++;
        }
        // n - m + 1 위치에서 보이는 광고판들의 최대 밝기 기록.
        while (inQueue[priorityQueue.peek()] == 0)
            priorityQueue.poll();
        sb.append(priorityQueue.peek());
        // 기록된 값들 출력.
        System.out.println(sb);
    }
}