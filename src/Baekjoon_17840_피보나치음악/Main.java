/*
 Author : Ruel
 Problem : Baekjoon 17840번 피보나치 음악
 Problem address : https://www.acmicpc.net/problem/17840
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17840_피보나치음악;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 0 ~ 10까지 11개의 음을 칠 수 있는 피아노가 있다.
        // 이를 1 1 2 3 5 8 1 3 2 1과 같이 피보나치 수열을 한 자리씩 뜯어 음을 치려고 한다.
        // 추가로 이를 조금 바꾸어, 피보나치 수를 m으로 나눈 나머지 값을 사용하기로 한다.
        // m이 10일 경우, 수열은 1 1 2 3 5 8 3 1 4 ... 가 된다.
        // q개의 쿼리가 m이 주어진다.
        // 각 쿼리는 10^15이하인 n이 주어지고, 해당 순서의 음을 구하라.
        //
        // 피사노 주기, dp 문제
        // dp[이전 수][현재 수] = 현재 수의 마지막 음의 순서로 채워나간다.
        // 그러면 순서가 0이 아닌 곳을 만나게 되고, 사이클이 발생함을 뜻한다.
        // 사이클 앞부분과 사이클 부분을 구분하여 쿼리를 처리하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // q개의 쿼리, 나머지값 m
        int q = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // dp[이전 수][현재 수] = 현재 수의 마지막 음의 순서
        int[][] dp = new int[m][m];
        // 실제 수열
        List<Integer> array = new ArrayList<>();
        // 첫번째 수
        array.add(1);
        // 이전 수와 현재 수
        int pre = 0;
        int current = 1;
        // 첫번째 수의 마지막 음 순서
        dp[0][1] = 1;
        Stack<Integer> stack = new Stack<>();
        // 사이클을 발견할 때까지
        while (dp[current][(pre + current) % m] == 0) {
            int next = (pre + current) % m;
            // 만약 다음 수가 0이라면 0 자체를 넣음
            if (next == 0)
                stack.push(0);
            // 그 외의 경우엔 하나씩 쪼개 스택에 담는다.
            while (next > 0) {
                stack.push(next % 10);
                next /= 10;
            }
            next = (pre + current) % m;
            // next의 길이 반영
            dp[current][next] = dp[pre][current] + stack.size();
            // 쪼갠 수를 리스트에 담는다.
            while (!stack.isEmpty())
                array.add(stack.pop());

            // 이전, 현재 수의 값 변경
            pre = current;
            current = next;
        }

        // 반복문을 나왔다면 사이클을 발견한 것
        // 사이클의 앞부분의 길이
        // pre -> current로 진행하는 수열이 다시 발견된 것이므로
        // 그 앞 부분인 pre까지의 길이를 짤라야한다.
        int head = dp[current][(pre + current) % m] - calcLength((pre + current) % m);
        // 현재 길이에서 head를 제외한 길이가 반복되는 주기
        int cycleLength = dp[pre][current] - head;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            // 0 based idx로 수정
            long n = Long.parseLong(br.readLine()) - 1;
            // 사이클이 아닌 head에 해당하는 경우, 그 순서를 바로 기록
            if (n < head)
                sb.append(array.get((int) n));
            else {
                // 사이클에 해당하는 경우
                // head만큼을 짜르고
                n -= head;
                // 나머지에서 반복을 돌린 후
                n %= cycleLength;
                // 해당하는 순서의 값을 기록
                sb.append(array.get(head + (int) n));
            }
            sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // n의 길이 반환
    static int calcLength(int n) {
        if (n == 0)
            return 1;

        int cnt = 0;
        while (n > 0) {
            cnt++;
            n /= 10;
        }
        return cnt;
    }
}