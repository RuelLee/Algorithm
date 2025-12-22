/*
 Author : Ruel
 Problem : Baekjoon 19576번 약수
 Problem address : https://www.acmicpc.net/problem/
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19576_약수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 지하수에 포함된 n개의 성분이 주어진다.
        // 포함된 성분들이 모두 약수-배수의 관계를 갖는다면 약수라고 칭한다.
        // 와우 매직을 통해 하나의 성분을 원하는 값으로 바꿀 수 있다.
        // 최소한의 와우매직을 사용하여, 약수로 만들고자할 때, 그 횟수는?
        //
        // 정렬, dp문제
        // 최대 길이의 배수 체인을 찾는 문제이다.
        // dp[i] = i 값을 마지막으로 갖는 배수 체인 중 가장 긴 값으로 정의한다.
        // 일단 정렬한 뒤, 순서대로 살펴보며, 자신보다 작은 값들 중 약수이며, 배수 체인이 가장 큰 값 +1을 갖도록 세운다.
        // 그러면 와우 매직을 사용하지 않았을 때, 이미 서로 약수-배수인 성분의 최대 수를 구할 수 있다.
        // 그 외의 나머지 성분들에 대해 모두 와우 매직을 사용하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 성분
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(array);

        // dp[i] = i를 마지막으로 갖는 배수 체인의 최대 길이
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int max = 1;
        // 오름차순으로 살펴보며
        for (int i = 1; i < dp.length; i++) {
            // 자신보다 작은 값들 중, 약수이며 배수 체인이 가장 긴 값을 가져와 자신과 연결했을 때의 길이를 기록한다.
            // 그리고 그 때의 최댓값 max를 구한다.
            for (int j = i - 1; j >= 0; j--) {
                if (array[i] % array[j] == 0)
                    max = Math.max(max, dp[i] = Math.max(dp[i], dp[j] + 1));
            }
        }
        // n - max 만큼의 성분들에게 와우 매직을 사용하여, 약수로 만든다.
        System.out.println(n - max);
    }
}