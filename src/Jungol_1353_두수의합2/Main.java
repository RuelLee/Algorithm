/*
 Author : Ruel
 Problem : Jungol 1353번 두수의 합2
 Problem address : https://jungol.co.kr/problem/1353
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1353_두수의합2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 서로 다른 수가 주어진다.
        // 두 개의 수를 골라 합이 m이 되는 경우의 수를 구하라
        //
        // 정렬, 두 포인터 문제
        // 두 포인터를 통해, 양쪽 끝에서 시작하여 왼쪽 포인터는 순서대로, 오른쪽 포인터는 합이 m보다 큰 동안 계속 감소시키며
        // 두 포인터가 가르키는 값들의 합이 m이 되는 개수를 센다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수, 원하는 합 m
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 수들
        int[] array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(array);

        // 오른쪽 포인터
        int j = n - 1;
        int cnt = 0;
        // 왼쪽 포인터를 하나씩 증가시키며
        for (int i = 0; i < j; i++) {
            // 합이 m보다 큰 경우 오른쪽 포인터를 감소
            while (array[i] + array[j] > m)
                j--;
            // i가 j보다 작음이 유지되며 합이 m인 경우 cnt 증가
            if (i < j && array[i] + array[j] == m)
                cnt++;
        }
        // 답 출력
        System.out.println(cnt);
    }
}