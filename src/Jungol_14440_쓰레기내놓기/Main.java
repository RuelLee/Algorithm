/*
 Author : Ruel
 Problem : Jungol 14440번 쓰레기 내놓기
 Problem address : https://jungol.co.kr/problem/14440
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_14440_쓰레기내놓기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 쓰레기봉투가 놓여있다. 양 손으로 무게 합이 m이하인 최대 2개의 쓰레기봉투를 들어 옮길 수 있다.
        // 모든 쓰레기봉투를 옮기는데 걸리는 왕복 횟수는?
        //
        // 두 포인터 문제
        // 두 포인터로 가장 무거운 쓰레기봉투부터 옮기되,
        // 남은 무게로 다른 쓰레기봉투를 하나 더 들 수 있는지 확인하며 옮긴다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 쓰레기봉투, 무게 합 m
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 쓰레기봉투
        int[] trashes = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            trashes[i] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(trashes);

        // 두 포인터
        int front = 0;
        int cnt = 0;
        for (int rear = n - 1; rear >= front; rear--) {
            // 만약 두 쓰레기봉투의 합이 m이하인 경우, 한 번에 옮긴다.
            // 그리고 front는 다음 쓰레기봉투를 가르킨다.
            if (trashes[front] + trashes[rear] <= m)
                front++;
            // 어쨌건 뒷 포인터가 가르키는 쓰레기봉투를 옮기며 1회 왕복한다.
            cnt++;
        }
        // 답 출력
        System.out.println(cnt);
    }
}