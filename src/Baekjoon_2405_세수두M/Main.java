/*
 Author : Ruel
 Problem : Baekjoon 2405번 세 수, 두 M
 Problem address : https://www.acmicpc.net/problem/2405
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2405_세수두M;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // i, j , k번째 수를 정렬하여, a[i], a[j], a[k]라 할 때
        // 중위값은 a[j], 세 수의 평균값은 (a[i] + a[j] + a[k]) / 3이라 할 때
        // 중위값과 평균값의 차이를 최대로 하고자할 때
        // 그 차이를 3배한 값은?
        //
        // 정렬 문제
        // 차이를 3배하므로 이를 풀어써보면
        // a[i] + a[j] + a[k] - 3 * a[j] = a[i] - 2 * a[j] + a[k]가 된다.
        // 해당 값의 절대값이 가장 큰 값을 구해야한다.
        // 값이 양수라면 값 자체가 제일 커야하므로 a[k]는 가장 큰 값으로 고정, a[i] - 2 * a[j]가 최소인 값을 찾아야한다.
        // 값이 음수라면 가장 작은 값을 찾아야하므로 a[i]는 첫번째 고정, -2 * a[j] + a[k]가 최소인 값을 찾야아한다.
        // 정렬하여 두 번 반복문을 돌리면 끝.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 입력으로 주어지는 n개의 수
        int[] array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(br.readLine());
        Arrays.sort(array);

        int answer = 0;
        // a[k]를 가장 큰 값으로 고정. a[i] - 2 * a[j]가 최대인 값을 찾는다.
        for (int i = 0; i < n - 2; i++)
            answer = Math.max(answer, Math.abs(array[i] - array[i + 1] * 2 + array[n - 1]));
        // a[i]를 첫번째 수로 고정. -2 * a[j] + a[k]가 최소인 값을 찾는다.
        for (int i = n - 1; i >= 2; i--)
            answer = Math.max(answer, Math.abs(array[0] - array[i - 1] * 2 + array[i]));
        // 답 출력
        System.out.println(answer);
    }
}