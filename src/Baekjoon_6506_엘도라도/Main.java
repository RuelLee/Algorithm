/*
 Author : Ruel
 Problem : Baekjoon 6506번 엘 도라도
 Problem address : https://www.acmicpc.net/problem/6506
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6506_엘도라도;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static long[][] fenwickTree;

    public static void main(String[] args) throws IOException {
        // 길이 n의 수열이 주어진다.
        // 해당 수열의 부분 수열 중 길이가 k인 증가하는 부분 수열의 개수를 찾고자 한다.
        // 해당하는 부분 수열의 개수는?
        //
        // DP, 누적합 문제
        // dp[길이][마지막의 가장 큰 수] = 경우의 수
        // 로 dp를 정하고, 누적합을 통해서 푼다.
        // 누적합을 빨리 처리하기 위해 펜윅 트리를 사용하였다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 최대 길이 100, 값의 범위 -10000 ~ 10000이므로 0 ~ 20001까지의 공간
        fenwickTree = new long[101][20002];
        StringBuilder sb = new StringBuilder();
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 원래 수열의 길이 n, 부분 수열의 길이 k
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            if (n == 0 && k == 0)
                break;
            
            // 펜윅 트리 초기화
            for (int i = 1; i <= k; i++)
                Arrays.fill(fenwickTree[i], 0);

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                // 이번 수
                int num = Integer.parseInt(st.nextToken()) + 10001;
                
                // 길이가 a-1이고 num보다 작은 수로 끝나는 모든 경우에 대해
                // 가장 마지막에 num을 붙여 길이가 a이고 num으로 끝나는 경우로 만들 수 있다.
                // 해당 경우 처리
                for (int a = k - 1; a > 0; a--)
                    addValue(a + 1, num, countBelowNum(a, num - 1));
                // 첫 부분 수열의 수로 num을 선택할 수도 있다.
                addValue(1, num, 1);
            }
            // 길이가 k이고 20001보다 같거나 작은 수로 끝나는 경우를 모두 센다.
            sb.append(countBelowNum(k, 20001)).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 길이가 length이고, num보다 같거나 작은 수로 끝나는 부분 수열의 개수를 계산한다.
    static long countBelowNum(int length, int num) {
        long count = 0;
        while (num > 0) {
            count += fenwickTree[length][num];
            num -= (num & -num);
        }
        return count;
    }

    // 길이가 length이고 maxNum으로 끝나는 경우의 수를
    // value개 추가한다.
    static void addValue(int length, int maxNum, long value) {
        while (maxNum < fenwickTree[length].length) {
            fenwickTree[length][maxNum] += value;
            maxNum += (maxNum & -maxNum);
        }
    }
}