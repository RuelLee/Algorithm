/*
 Author : Ruel
 Problem : Baekjoon 31623번 Room Temperature
 Problem address : https://www.acmicpc.net/problem/31623
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31623_RoomTemperature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사무원들이 각자 자신의 방에 있다.
        // 각 사무원의 적정 실내 온도가 주어진다.
        // 각 사무원은 k개의 자켓을 입어, 적정 실내 온도를 k * t 온도만큼 낮출 수 있다.
        // 불쾌함 수치는 자신의 적정 실내 온도와 방의 실제 온도 간의 차이만큼이 된다.
        // 모든 사무원들 중 최대 불쾌함 수치를 최소화하고자 할 때
        // 그 값은?
        //
        // 정렬 문제
        // 아이디어를 생각하는게 어려운 문제
        // 먼저, 자켓을 통해 적정 온도를 t도 낮출 수 있으므로,
        // 모든 사무원들의 적정 온도는 모듈러 연산으로 0 ~ t-1 값으로 나타낼 수 있다.
        // 그 뒤, 최솟값과 최댓값의 차이를 구하며, 해당하는 중간값으로 설정할 시, 불쾌감을 최소화할 수 있다.
        // 이 때, 온도는 정수값이므로, 소수자리가 발생할 경우 반올림 혹은 +1 값을 하여 /2를 한 값으로 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 사무원과 자켓을 입을 때마다 감소하는 적정 온도 t
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 각 사무원들의 적정 온도
        // 를 % t를 하여 최대한 자켓을 입은 경우를 만든다.
        int[] a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < a.length; i++)
            a[i] = (Integer.parseInt(st.nextToken())) % t;
        // 정렬
        Arrays.sort(a);

        // 적정 온도가 가장 낮은 사무원부터 자켓을 하나씩 벗으며
        // 적정 온도의 최솟값과 최댓값 차이가 최소인 값을 구한다.
        int minDIff = a[a.length - 1] - a[0];
        for (int i = 0; i < a.length - 1; i++)
            minDIff = Math.min(minDIff, a[i] + t - a[i + 1]);
        
        // 불쾌감 수치의 최대값은 
        // 적정 온도 차이의 / 2값을 반올림한 값이다.
        // 해당 값 출력
        System.out.println((minDIff + 1) / 2);
    }
}