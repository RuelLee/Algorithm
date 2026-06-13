/*
 Author : Ruel
 Problem : Jungol 1581번 두배열의 합
 Problem address : https://jungol.co.kr/problem/1581
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1581_두배열의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기 n의 배열과 크기 m의 배열이 주어진다.
        // 각 배열의 연속한 부분 합의 합이 t인 경우의 수를 구하라
        //
        // 브루트 포스
        // 각각의 모든 부분 합의 경우의 수를 구한다.
        // n과 m의 최대 크기가 1000이므로 1000C2 * 2가 최대 연산의 횟수
        // 그 후, 합의 경우의 수를 살펴보며 두 부분 배열의 합이 t인 경우의 수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 부분 배열의 합 t
        int t = Integer.parseInt(br.readLine());

        // 크기 n인 배열
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arrayA = new int[n];
        for (int i = 0; i < n; i++)
            arrayA[i] = Integer.parseInt(st.nextToken());

        // 크기 m인 배열
        int m = Integer.parseInt(br.readLine());
        int[] arrayB = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++)
            arrayB[i] = Integer.parseInt(st.nextToken());

        // 첫 배열의 부분 배열의 합의 경우의 수
        int[] aCounts = new int[t + 1];
        for (int i = 0; i < arrayA.length; i++) {
            int sum = 0;
            for (int j = i; j < arrayA.length; j++) {
                sum += arrayA[j];
                if (sum > t)
                    break;
                aCounts[sum]++;
            }
        }

        // 두번째 배열의 부분 배열의 합의 경우의 수
        int[] bCounts = new int[t + 1];
        for (int i = 0; i < arrayB.length; i++) {
            int sum = 0;
            for (int j = i; j < arrayB.length; j++) {
                sum += arrayB[j];
                if (sum > t)
                    break;
                bCounts[sum]++;
            }
        }

        // 두 배열의 부분 합이 t인 경우를 계산.
        int ans = 0;
        for (int i = 0; i <= t; i++)
            ans += aCounts[i] * bCounts[t - i];
        // 답 출력
        System.out.println(ans);
    }
}