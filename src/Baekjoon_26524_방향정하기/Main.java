/*
 Author : Ruel
 Problem : Baekjoon 26524번 방향 정하기
 Problem address : https://www.acmicpc.net/problem/26524
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26524_방향정하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int LIMIT = 1_000_000_000 + 7;

    public static void main(String[] args) throws IOException {
        // n개의 점이 주어지고, 어떤 두 점을 잡더라도 하나의 간선으로 이어지도록
        // 총 n * (n - 1) / 2개의 간선이 있다.
        // 어떤 점에서 출발하더라도 출발한 점으로 되돌아올 수 없다는 규칙에 따라
        // 간선들의 방향을 정하고자할 때, 경우의 수는?
        //
        // 경우의 수 문제
        // 간단하게 생각해서
        // 앞에 뒤로 가는 방향으로 n개의 점들을 나열한다고 생각하면 된다.
        // n개의 점을 나열하는 경우의 수는 n! 이므로
        // 해당 값을 계산하며, LIMIT 값으로 나머지 연산을 취해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 점
        int n = Integer.parseInt(br.readLine());

        long answer = 1;
        // n까지 곱해나가며, LIMIT로 나머지 연산을 해 나간다.
        for (int i = 2; i <= n; i++)
            answer = (answer * i) % LIMIT;
        
        // 답 출력
        System.out.println(answer);
    }
}