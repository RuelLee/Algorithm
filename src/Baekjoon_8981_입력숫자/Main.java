/*
 Author : Ruel
 Problem : Baekjoon 8981번 입력숫자
 Problem address : https://www.acmicpc.net/problem/8981
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_8981_입력숫자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 주어진 코드의 출력 결과가 주어질 때, 입력을 출력하라
        //
        // 구현 문제
        // c언어로 작성되어있지만 대략 살펴보면, n개의 수로 이루어진 수열이 주어지고
        // 시작 위치는 0 현재 위치에 해당하는 수를 기록하고
        // (다음 위치는 현재 위치 + 현재 위치에 해당하는 수) % n의 위치로 이동
        // 해당 위치의 방문한 적이 있다면 한 칸 앞으로 이동
        // 을 반복한다.
        // 따라서 입력에 따라 원래 수열을 찾으려면
        // 원래 수열의 위치를 idx, 원래 수열을 original, 출력을 array라 할 때
        // 첫 수는 그대로.
        // 두번째 수부터, idx + original[idx]의 위치에 현재 수를 기록. 만약 이미 값이 들어있다면
        // 다음 위치로 이동을 반복한다.
        // 만족하는 수열이 없는 경우 -1을 출력하라 되어있으나, 이미 방문했다면 한 칸 앞으로 라는 조건 때문에
        // -1을 출력해야하는 경우는 없다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수.
        int n = Integer.parseInt(br.readLine());
        int[] array = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(st.nextToken());

        // 원래 수열
        int[] original = new int[n];
        // 첫 수는 그대로
        original[0] = array[0];
        // 위치
        int idx = 0;
        for (int i = 1; i < n; i++) {
            // i번째 출력된 수는
            // idx + 이전 수의 값의 위치에 기록한다.
            // 해당 값이 이미 차있다면 다음 위치로
            idx = (idx + original[idx]) % n;
            while (original[idx] != 0)
                idx = (idx + 1) % n;
            original[idx] = array[i];
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n").append(original[0]);
        for (int i = 1; i < n; i++)
            sb.append(" ").append(original[i]);
        // 전체 답 출력
        System.out.println(sb);
    }
}