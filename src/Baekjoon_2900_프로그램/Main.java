/*
 Author : Ruel
 Problem : Baekjoon 2900번 프로그램
 Problem address : https://www.acmicpc.net/problem/2900
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2900_프로그램;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기가 n이고 0이 채워져있는 배열 a가 주어진다.
        // 해당 배열에 대해
        // void something(int jump) {
        //    int i = 0;
        //    while (i < N) {
        //        a[i] = a[i] + 1;
        //        i = i + jump;
        //    }
        // }
        // 다음과 같은 코드를 실행한다.
        // k번 함수를 호출하며, 인자로 넘기는 jump의 값은, x1, x2, ..., xk이다.
        // q번 확인을 하며,
        // l과 r이 주어질 때, a[l] + ... + a[r]의 값을 출력한다.
        //
        // 누적합 문제
        // n과 k과 최대 100만, 1<= xi < n
        // q도 최대 100만까지 주어지므로
        // 일일이 매번 구해서는 당연히 시간 초과
        // 따라서 x를 값에 따라 몇 번 들어왔는지 세어, 같은 jump에 대해서는 한번에 처리하며
        // 확인하는 동작 또한 누적합을 통해 한번에 처리해야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 크기 n인 배열과 함수 호출 횟수 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // jump 값들을 센다.
        int[] counts = new int[n];
        for (int i = 0; i < k; i++)
            counts[Integer.parseInt(st.nextToken())]++;

        long[] psums = new long[n];
        for (int i = 0; i < counts.length; i++) {
            // counts[i]가 0라면, i로 하는 점프는 없는 경우. 건너 뛴다.
            if (counts[i] == 0)
                continue;
            
            // 0번 idx부터 시작하여
            // 배열의 범위 내까지
            // 점프하며, counts[i] 만큼을 더해나간다.
            int idx = 0;
            while (idx < psums.length) {
                psums[idx] += counts[i];
                idx += i;
            }
        }
        // 함수 처리 끝
        
        // 누적합 처리
        for (int i = 1; i < psums.length; i++)
            psums[i] += psums[i - 1];
        
        // 확인 처리
        int q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            
            // l ~ r까지의 누적합을 구해 기록
            sb.append(psums[r] - (l == 0 ? 0 : psums[l - 1])).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}