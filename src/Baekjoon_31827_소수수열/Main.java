/*
 Author : Ruel
 Problem : Baekjoon 31827번 소수 수열
 Problem address : https://www.acmicpc.net/problem/31827
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31827_소수수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 다음 두 조건을 만족하는 크기 n인 수열을 구하라
        // 1. 모든 원소는 100만 이하의 서로 다른 소수로 이루어져 있다.
        // 2. 길이가 k인 임의의 부분 수열의 합 ai + ai+1 + ... + ai+k-1은 k의 배수이다.
        //
        // 에라토스테네스의 체 문제
        // 길이가 k인 임의의 부분 수열의 합이 k가 되기 위해선
        // k로 나눈 나머지가 1인 소수들로 채워버리면 편하다.
        // 다행히 100만 이하의 소수들을 k의 최대값인 9를 통해 나머지에 따라 분류하면
        // 나머지가 1인 소수가 n의 최대 크기 1만을 넘어 모든 경우에 가능하다.
        // 따라서 에라토스테네스의 체를 통해 소수를 구하되, k로 나눈 나머지가 1인 소수들을 따로 분류하여
        // 그 후, 해당 소수들 n개를 일렬로 나열해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 전체 수열의 길이 n, 임의의 연속된 부분 수열의 길이 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // k로 나눈 나머지가 1인 소수들을 큐에 담는다.
        Queue<Integer> queue = new LinkedList<>();
        // 에라토스테네스의 체
        boolean[] eratosthenes = new boolean[1_000_001];
        for (int i = 2; i < eratosthenes.length; i++) {
            if (eratosthenes[i])
                continue;

            // 나눈 나머지가 1인 경우 큐에 담는다.
            if (i % k == 1)
                queue.offer(i);
            for (int j = 2; i * j < eratosthenes.length; j++)
                eratosthenes[i * j] = true;
        }

        StringBuilder sb = new StringBuilder();
        // n개의 소수를 꺼내 답안 작성
        for (int i = 0; i < n; i++)
            sb.append(queue.poll()).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        // 답 출력
        System.out.println(sb);
    }
}