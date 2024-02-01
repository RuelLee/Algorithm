/*
 Author : Ruel
 Problem : Baekjoon 29792번 규칙적인 보스돌이
 Problem address : https://www.acmicpc.net/problem/29792
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_29792_규칙적인보스돌이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 케릭터가 주어진다. k개의 보스가 주어진다.
        // 이 중 m개의 케릭터를 이용하여 15분씩만 보스를 사냥하여 최대 금액을 벌고자한다.
        // 케릭터들은 각 초당 데미지, 보스들은 체력과 보상금액이 주어진다.
        // 얻을 수 있는 최대 금액을 구하라
        //
        // 배낭 문제
        // 케릭터들 별로 모든 보스에 대해 얻을 수 있는 최대 금액을 배낭 문제와 같이 구하고
        // 그 중 최대 금액인 k개의 케릭터의 보상금을 합산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 케릭터 중 m개의 케릭터만 사냥에 활용하며
        // k개의 보스가 주어진다.
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 케릭터들의 DPS
        long[] characters = new long[n];
        for (int i = 0; i < characters.length; i++)
            characters[i] = Long.parseLong(br.readLine());
        
        // 보스들의 체력과 보상금
        long[][] bosses = new long[k][];
        for (int i = 0; i < bosses.length; i++)
            bosses[i] = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        // 모든 케릭터들을 살펴본다.
        for (long character : characters) {
            // 배낭 문제
            int[] dp = new int[901];
            // 모든 보스들을 살펴보며 15분 동안 얻을 수 있는 최대 금액을 구한다.
            for (long[] boss : bosses) {
                // 해당 보스를 잡는데 걸리는 시간
                int time = (boss[0] / character > 900 ? -1 : (int) (boss[0] / character) + (boss[0] % character == 0 ? 0 : 1));
                if (time == -1)
                    continue;

                // dp를 역순으로 채운다.
                for (int j = 900; j - time >= 0; j--)
                    dp[j] = Math.max(dp[j], dp[j - time] + (int) boss[1]);
            }
            // i번째 케릭터로 얻을 수 있는 금액을 최대 힙 우선순위큐에 추가.
            priorityQueue.offer(dp[900]);
        }

        // 가장 높은 금액 k개를 꺼내 더한다.
        int sum = 0;
        for (int i = 0; i < m; i++)
            sum += priorityQueue.poll();
        // 답안 출력.
        System.out.println(sum);
    }
}