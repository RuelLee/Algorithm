/*
 Author : Ruel
 Problem : Baekjoon 32633번 두더지 찾기
 Problem address : https://www.acmicpc.net/problem/32633
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32633_두더지찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 두더지 구멍과 관찰하는 시간 l이 주어진다.
        // 각 구멍마다 A와 B 값이 주어진다.
        // 시각 T가 Ai의 배수라면 해당 두더지가 나타난다.
        // Bi의 값이 1이라면 i번 구멍의 두더지가 등장, 0이라면 그렇지 않기를 바란다.
        // B를 만족하는 시각 T가 관찰 시간 l 내에 등장하는 최소 시각을 출력하라
        //
        // 유클리드 호제법
        // 먼저 B가 1인 값들에 대해 Ai를 최소공배수 처리한다.
        // 그러는 도중 최소공배수가 l을 초과할 경우, 불가능하므로 반복문 종료
        // 반복문을 끝까지 마쳤다면, 이제 Bi가 0인 값들에 대해 Ai와 약수 관계가 성립하는지 따진다.
        // 만약 해당 값이 최소공배수의 약수가 된다면 해당 구멍의 두더지가 등장하기 때문에 불가능하기 때문

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 구멍, 관찰 시간 l
        int n = Integer.parseInt(st.nextToken());
        long l = Long.parseLong(st.nextToken());

        // 각 두더지들의 등장 주기
        int[] a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < a.length; i++)
            a[i] = Integer.parseInt(st.nextToken());

        // 원하는 두더지들의 상태
        int[] b = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < b.length; i++)
            b[i] = Integer.parseInt(st.nextToken());

        // Bi가 1인 값들에 대해 최소공배수 처리
        long answer = 1;
        for (int i = 0; i < a.length; i++) {
            if (b[i] == 1)
                answer = answer / getGCD(a[i], answer) * a[i];

            // 만약 관찰 시간을 초과한다면 답은 -1 종료
            if (answer > l) {
                answer = -1;
                break;
            }
        }

        // 최소공배수가 l을 초과하지 않는다면
        // 이제 Bi가 0인 값의 배수인지 여부를 체크한다.
        if (answer != -1) {
            for (int i = 0; i < b.length; i++) {
                if (b[i] == 0 && answer % a[i] == 0) {
                    answer = -1;
                    break;
                }
            }
        }
        // Bi가 1인 값들에 대해 최소공배수를 구하고, Bi가 0인 값들에 대해
        // 배수가 아닌 경우 answer 출력
        // 그 외의 경우 -1 출력
        System.out.println(answer);
    }

    // a와 b의 최대공약수를 구한다.
    static long getGCD(long a, long b) {
        long max = Math.max(a, b);
        long min = Math.min(a, b);

        while (min > 0) {
            long temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}