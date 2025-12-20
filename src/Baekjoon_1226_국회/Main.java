/*
 Author : Ruel
 Problem : Baekjoon 1226번 국회
 Problem address : https://www.acmicpc.net/problem/1226
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1226_국회;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 정당이 주어진다.
        // 각 정당과 전체 의석의 수는 10만을 넘지 않는다.
        // 정당끼리 연합하려 한다.
        // 연합은 속한 당들의 전체 의석 수가 과반을 넘어야 한다.
        // 또한 연합의 어느 정당을 제외하더라도 연합이 성립하면 깔끔하지 못하다고 한다.
        // 깔끔한 연합 중 의석의 수가 가장 많은 것을 찾고, 포함된 정당의 수와 각 번호를 출력하라
        //
        // 냅색, 정렬 문제
        // 어느 한 정당을 제외시키더라도 연합이 성립해야한다.
        // 따라서 정당을 의석 수로 내림차순 정렬하여 살펴보면
        // 연합에는 현재 살펴보는 정당보다 같거나 많은 수의 정당만 포함되어있으므로
        // 현재 정당을 연합에서 제외시켜, 연합이 아니게 된다면 속한 어느 정당도 제외되서는 안되는 깔끔한 상태가 유지된다.
        // 따라서, 냅색을 풀되, 정당들을 의석 수로 정렬하여, 내림차순으로 계산하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 정당
        int n = Integer.parseInt(br.readLine());
        // 총 의석 수
        int sum = 0;
        int[][] parties = new int[n][2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            parties[i][0] = i + 1;
            sum += (parties[i][1] = Integer.parseInt(st.nextToken()));
        }
        // 내림차순 정렬
        Arrays.sort(parties, Comparator.comparingInt(x -> x[1]));

        int[][] dp = new int[sum + 1][2];
        // 초기값 세팅
        for (int i = 0; i < dp.length; i++)
            dp[i][0] = -1;
        // 연합에 0개의 정당일 때를 초기값으로
        dp[0][0] = 0;

        for (int i = parties.length - 1; i >= 0; i--) {
            // 정당에 속한 의원이 0이 되는 시점엔 반복문 종료
            if (parties[i][1] == 0)
                break;

            // 연합이 성립된 경우에는 현재 정당을 포함시키면 깔끔하지 않은 연합이 되므로 살펴볼 필요가 없다.
            // 현재 연합이 성립되지 않는 최대 인원부터 체크
            for (int j = sum / 2; j >= 0; j--) {
                if (dp[j][0] == -1)
                    continue;

                // 전체 의원의 수
                int currentSum = j + parties[i][1];
                // 연합의 총 의석 수가 아직 계산되지 않은 수일 경우.
                if (dp[currentSum][0] == -1) {
                    // j명의 연합에서 parties[i][0]번 정당을 추가할 때,
                    // currentSum명의 연합이 됨을 표시
                    dp[currentSum][0] = j;
                    dp[currentSum][1] = parties[i][0];
                }
            }
        }

        // 연합 중 가장 수가 많은 연합을 찾는다.
        int idx = -1;
        for (int i = dp.length - 1; i > sum / 2; i--) {
            if (dp[i][0] != -1) {
                idx = i;
                break;
            }
        }

        // 역추적하며, 포함된 정당의 번호를 찾는다.
        Stack<Integer> stack = new Stack<>();
        // 현재 idx명인 정당
        stack.push(idx);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        // 아직 더 찾을 정당이 있는 경우
        while (dp[stack.peek()][0] != 0) {
            // stack.peek()명 연합이 되며 포함한 정당을 우선순위큐에 담음
            priorityQueue.offer(dp[stack.peek()][1]);
            // stack.peek()에서 dp[stack.peek()][1]번의 정당을 제외시킨
            // dp[stack.peek()][0]명을 스택에 상단에 담음.
            stack.push(dp[stack.peek()][0]);
        }
        // 마지막 정당의 의석 수를 담음
        priorityQueue.offer(dp[stack.peek()][1]);

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        // 연합에 속한 정당의 수 기록
        sb.append(priorityQueue.size()).append("\n");
        sb.append(priorityQueue.poll());
        // 각 정당의 의석 수 기록
        while (!priorityQueue.isEmpty())
            sb.append(" ").append(priorityQueue.poll());
        // 답 출력
        System.out.print(sb);
    }
}