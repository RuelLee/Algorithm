/*
 Author : Ruel
 Problem : Baekjoon 2142번 정돈된 배열
 Problem address : https://www.acmicpc.net/problem/2142
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2142_정돈된배열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 2차원 배열이 주어진다.
        // 정돈되어있다는 말은
        // 1 <= i < k <= m, 1 <= j < l < n을 만족하는 모든 i, j, k, l에 대해
        // A[i][j] + A[k][l] ≤ A[i][l] + A[k][j]가 성립함을 말한다.
        // 주어지는 배열에 대해 정돈되어있는가를 출력하라
        //
        // 애드혹 문제
        // 대각선 끼리의 두 합이 서로 같거나 왼쪽아래에서 오른쪽 위로 가는 대각선의 합이 더 커야한다.
        // 하지만 식을 연속성을 갖도록 조금 바꿔보면
        // A[i][j] + A[i][l] ≤ A[k][j] - A[k][l]
        // 로 바꿀 수 있고, i열의 연속한 두 원소의 합이
        // k열의 같은 순서에 위치한 연속한 두 두 원소의 합보다 같거나 작아야함을 알 수 있다.
        // k에 i+1을 넣고, l에 j+1을 넣는다면
        // A[i][j] + A[i][j+1] ≤ A[i+1][j] - A[i+1][j+1]
        // 과 같이 쓸 수 있고, 범위 내 모든 i와 j에 성립하는지 확인하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 2차원 배열의 크기 n, m
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            
            // 각 배열의 원소
            int[][] map = new int[n][m];
            for (int i = 0; i < map.length; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < map[i].length; j++)
                    map[i][j] = Integer.parseInt(st.nextToken());
            }

            boolean answer = true;
            // 범위 내의 모든 i와 j에 대해
            // A[i][j] + A[i][j+1] ≤ A[i+1][j] - A[i+1][j+1]이 성립하는가 찾는다.
            for (int i = 0; i < map.length - 1; i++) {
                for (int j = 0; j < map[i].length - 1; j++) {
                    if (map[i][j] - map[i][j + 1] > map[i + 1][j] - map[i + 1][j + 1]) {
                        answer = false;
                        break;
                    }
                }
            }
            // 모두 성립한다면 YES 그렇지 않다면 NO를 기록
            sb.append(answer ? "YES" : "NO").append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}