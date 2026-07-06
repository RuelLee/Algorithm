/*
 Author : Ruel
 Problem : Jungol 8111번  헤어스타일이 비슷한 고슴도치
 Problem address : https://jungol.co.kr/problem/8111
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8111_헤어스타일이비슷한고슴도치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 고슴도치가 주어지고, i번째 고슴도치의 바늘의 수는 ai이다.
        // 자신 혹은 자신이 왼편의 고슴도치들에 대해 ai - k ~ ai개의 바늘을 가진 가장 먼 고슴도치를 찾아
        // 그 거리를 모두 합산한 값을 출력하라
        //
        // 우선순위큐 문제
        // 먼저, 각 바늘의 수 별로 가장 작은 idx를 구한다.
        // 그 후, k - x ~ x까지의 범위에 대해 가장 작은 idx를 모두 구해둔다.
        // 고슴도치를 살펴보며, 해당하는 idx와의 차를 누적시킨다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        // 각 고슴도치
        int[] hedgehogs = new int[200000];
        // 바늘 수에 따른 최소 idx
        int[] minIdxNeedles = new int[500001];
        // 범위가 k일 때, 최소 idx
        int[] rangeK = new int[500001];
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        // 우선순위큐로 범위 내의 가장 작은 idx를 갖는 바늘의 수를 찾는다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(x -> minIdxNeedles[x]));
        for (int testCase = 0; testCase < t; testCase++) {
            st = new StringTokenizer(br.readLine());
            // n마리의 고슴도치, 범위 k
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            Arrays.fill(minIdxNeedles, Integer.MAX_VALUE);
            st = new StringTokenizer(br.readLine());
            // 등장한 바늘의 최대 개수
            int max = 0;
            // 고슴도치의 바늘을 입력 받고, 각 바늘 수에 따른 최소 idx 값을 채운다.
            for (int i = 0; i < n; i++) {
                max = Math.max(max, hedgehogs[i] = Integer.parseInt(st.nextToken()));
                minIdxNeedles[hedgehogs[i]] = Math.min(minIdxNeedles[hedgehogs[i]], i);
            }

            Arrays.fill(rangeK, Integer.MAX_VALUE);
            priorityQueue.clear();
            // 0개부터 max까지 해당하는 k범위 내에서 가장 작은 idx를 기록한다.
            for (int i = 0; i <= max; i++) {
                priorityQueue.offer(i);
                while (i - priorityQueue.peek() > k)
                    priorityQueue.poll();
                rangeK[i] = minIdxNeedles[priorityQueue.peek()];
            }

            long answer = 0;
            // 모든 고슴도치를 살펴보며, 해당하는 idx 차이를 합산한다.
            for (int i = 0; i < n; i++)
                answer += (i - rangeK[hedgehogs[i]]);
            // 답 기록
            sb.append("Case #").append(testCase + 1).append("\n").append(answer).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}