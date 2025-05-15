/*
 Author : Ruel
 Problem : Baekjoon 21233번 Year of the Cow
 Problem address : https://www.acmicpc.net/problem/21233
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21233_YearoftheCow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 12년 주기의 년으로 시간 이동을 할 수 있다.
        // n명의 조상과 해당 조상을 만날 수 있는 년이 주어질 때, 총 몇 년의 시간이 걸려야
        // 모든 조상을 만들 수 있는지 계산하라
        // 시간 이동은 총 K번 할 수 있다.
        //
        // 그리디, 정렬, 우선순위큐 문제
        // 먼저, 정렬을 통해 조상들을 순서대로 나열하자.
        // 최소 한 번은 가장 오래된 조상보다 같거나 큰 12배수 해로 이동해야한다.
        // 그리고 시간 이동을 하지 않고서 그냥 쭉 시간을 보내도 된다.
        // 하지만 k-1번의 시간 이동을 함으로써, 시간을 줄일 수 있다.
        // 이 때, 시간 이동을 하는 기준은, 다음 조상보다 같거나 큰 12배수 해로 이동했을 때
        // 줄어드는 시간이 큰 순서대로 k-1개를 뽑아 사용하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 조상, 시간 이동 제한 횟수 k번
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 조상을 만날 수 있는 해
        // 정렬
        int[] years = new int[n + 1];
        for (int i = 1; i < years.length; i++)
            years[i] = Integer.parseInt(br.readLine());
        Arrays.sort(years);
        
        // 우선 순위큐를 통해 시간 이동을 할 경우, 다음 조상을 만나는데 드는 시간이 많이 줄어드는 순으로 살펴본다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        // 마지막으로 원래 시간으로 돌아와야하므로, 가장 최근의 조상과의 시간 이동 시간을 계산하여 넣어둠.
        priorityQueue.offer(years[1] / 12 * 12);
        for (int i = 2; i < years.length; i++) {
            // i번째 조상에서 가장 빠르게 시간 이동을 할 수 있는 해
            int from = years[i] / 12 * 12;
            // i-1번째 조상을 만나는데 가장 빠른 이동을 할 수 있는 해
            int to = (years[i - 1] / 12 + 1) * 12;
            
            // 줄어드는 시간
            priorityQueue.offer(from - to);
        }
        
        // 가장 오래된 조상을 만나기 위해 이동해야하는 해
        int sum = (years[n] / 12 + 1) * 12;
        // 에서 k-1번의 시간 이동을 통해 줄어드는 햇수를 제외한다.
        for (int i = 0; i < k - 1; i++)
            sum -= priorityQueue.poll();
        // 답 출력
        System.out.println(sum);
    }
}