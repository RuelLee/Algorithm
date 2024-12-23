/*
 Author : Ruel
 Problem : Baekjoon 17965번 Absolute Game
 Problem address : https://www.acmicpc.net/problem/17965
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17965_AbsoluteGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // Alice 와 Bob은 각각 n개의 수를 갖고 있다.
        // 두 사람은 게임을 하는데
        // Alice부터 시작하여, 번갈아가며 하나의 수를 지워나가며
        // 하나의 수만 남았을 때, Alice는 두 수의 차이를 최대화하고자 하며
        // Bob은 두 수의 차이를 최소화하고자 한다.
        // 두 사람 모두 최선의 선택을 한다고 할 때
        // 남은 두 수의 차이는?
        //
        // 게임 이론, 그리디 문제
        // Alice는 가장 차이가 적은 수를 지우며, 차이의 최소값을 늘려나갈 것이다.
        // 결국 n개의 수 중에서 n-1개를 지우게 되며, 각각 한 개의 수만 남게 된다.
        // 따라서 Alice는 각 수에 대해, Bob의 수열과의 최소 차이를 구하고
        // 이 값이 최대인 수 하나만 남게 될 것이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Alice와 Bob이 갖고 있는 수의 개수 n
        int n = Integer.parseInt(br.readLine());
        
        // 각각의 수
        int[][] arrays = new int[2][n];
        for (int i = 0; i < arrays.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < arrays[i].length; j++)
                arrays[i][j] = Integer.parseInt(st.nextToken());
        }

        // 현재까지 계산한 
        // 최소 차이들 중 최댓값
        int answer = 0;
        for (int i = 0; i < arrays[0].length; i++) {
            // Alice의 i번째 수와 Bob 배열의 수들과의 최소 차이
            int diff = Integer.MAX_VALUE;
            for (int j = 0; j < arrays[1].length; j++)
                diff = Math.min(diff, Math.abs(arrays[0][i] - arrays[1][j]));
            // diff가 answer의 최댓값을 갱신하는지 확인
            answer = Math.max(answer, diff);
        }
        // 답 출력
        System.out.println(answer);
    }
}