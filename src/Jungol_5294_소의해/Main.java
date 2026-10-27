/*
 Author : Ruel
 Problem : Jungol 5294번 소의 해
 Problem address : https://jungol.co.kr/problem/5294
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5294_소의해;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 현재 소의 해이고, 각 n마리의 조상이 존재하는 과거의 시점이 주어진다.
        // 소의 해에서 소의 해로 과거 혹은 미래로 여행이 가능하다고 한다.
        // 시간 여행은 최대 k번만 하려고 할 때, 모든 조상을 만나고 오는데 걸리는 시간은?
        //
        // 정렬, 우선순위큐 문제
        // 먼저 한 번의 시간 여행을 통해, 가장 먼 조상의 소보다 같거나 이른 시간의 소의 해로 이동한다.
        // 여기서부터 시간을 보내 현재까지 돌아온다.
        // 이제 남은 k - 1번의 시간 여행을 통해 이 과정을 최대한 단축해야한다.
        // 각 조상 사이의 간격 중 소의 해에 해당하는 것을 계산하고, 단축할 수 있는 길이가 가장 긴 시간을 골라 줄인다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 조상, k번의 시간 여행
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 각 소가 태어난 시점
        int[] cows = new int[n + 1];
        for (int i = 0; i < n; i++)
            cows[i] = Integer.parseInt(br.readLine());
        // 정렬
        Arrays.sort(cows);

        // 우선순위큐에 담아 소들 사이의 간격이 큰 순서대로 뽑는다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        // i번 소에서 -> i-1번 소로 이동하는데 시간 여행을 할 경우 단축시킬 수 있는 시간을 계산.
        for (int i = 1; i < cows.length; i++)
            priorityQueue.offer(cows[i] / 12 * 12 - (cows[i - 1] + 11) / 12 * 12);

        // 가장 이른 조상보다 같거나 이른 시점의 소의 해로 이동
        int sum = (cows[n] + 11) / 12 * 12;
        // 우선순위큐에서 0보다 큰 k-1개의 시간 여행을 골라 sum에서 빼준다.
        for (int i = 0; i < k - 1 && priorityQueue.peek() > 0; i++)
            sum -= priorityQueue.poll();

        // 답 출력
        System.out.println(sum);
    }
}