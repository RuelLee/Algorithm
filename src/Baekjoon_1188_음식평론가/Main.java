/*
 Author : Ruel
 Problem : Baekjoon 1188번 음식 평론가
 Problem address : https://www.acmicpc.net/problem/1188
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1188_음식평론가;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 소시지, m명의 평론가가 주어진다
        // m명의 평론가에게 모두 같은 양의 소시지를 최소한의 커팅으로 나눠주고 싶다.
        // 예를 들어 소시지가 2명, 평론가가 6명 있는 경우에는
        // 각 소시지를 1/3 조각으로 잘라, 6명에 나눠주면 되고, 이 때 필요한 커팅은 4번이다.
        // 소시지가 3개 평론가가 4명 있는 경우에는, 각 소시지를 3/4 조각으로 잘라 나눠주면 되고,
        // 한 명은 1/4 조각 3개를 받게 된다. 이 때 필요한 커팅은 3번이다.
        // n, m이 주어질 때 필요한 최소 커팅 수를 구하라
        //
        // 최소 공배수를 구하는 문제
        // n개의 소시지를 m명이 나눠먹어야하므로
        // 각 소시지를 n / m 조각으로 나눈 뒤, 해당 조각이 몇 조각인지 센다
        // 세며, 정수가 될 때마다의 개수는 이전 커팅을 통해 해당 조각이 완성되어 커팅이 필요하지 않은 경우이므로
        // 세지 않는다.
        // 이를 소수를 사용하지 않고, 정수로 계산하기 위해서
        // (n * m)개의 조각을 n개씩 m명에게 나눠준다고 생각하자
        // 나눠주며, n과의 m의 최소공배수의 배수에 해당하는 수를 지날 때마다 해당 수는 세지 않는다
        // (= 소수로 계산할 때 정수를 지나는 것과 같다)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // n * m개의 조각을 n명에게 나눠주는데는 m번의 커팅이 필요하다
        // 하지만 n과 m의 최소공배수의 배수들은 이전 커팅을 통해 이미 나눠진 조각이므로
        // 커팅이 필요하지 않다. 따라서 (n * m) / LCM(n, m)만큼을 빼준다.
        int answer = (n * m) / n - (n * m) / getLCM(n, m);
        // 정답 출력.
        System.out.println(answer);
    }

    // 최소공배수를 구한다.
    static int getLCM(int a, int b) {
        // 먼저 최대공약수를 유클리드 호제법을 통해 구한다.
        int max = Math.max(a, b);
        int min = Math.min(a, b);

        while (min > 0) {
            max %= min;
            int temp = max;
            max = min;
            min = temp;
        }
        // 최대공약수는 max이다
        // 최소공배수는 a * b에 최대공약수만큼이 중복되어 곱해져있는 형태이므로
        // a * b / max가 최소공배수가 된다.
        return a * b / max;
    }
}