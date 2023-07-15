/*
 Author : Ruel
 Problem : Baekjoon 2411번 아이템 먹기
 Problem address : https://www.acmicpc.net/problem/2411
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2411_아이템먹기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자판이 주어지고,
        // a개의 아이템과 b개의 장애물이 주어진다.
        // 왼쪽 아래에서 오른쪽 위로 가려고한다.
        // 이동 방향은 항상 위 혹은 오른쪽이며, 모든 아이템은 먹어야하며
        // 장애물이 있는 곳은 지나갈 수 없다.
        // 도달하는 방법의 가짓수는?
        //
        // DP 문제
        // 생각을 해보면 모든 아이템을 먹기 위해서는
        // 오른쪽 위 대각선으로 진행하며 먹는 방법밖에는 존재하지 않는다.
        // 따라서 아이템들을 정렬시켜두고
        // 이전 위치로부터 다음 아이템까지 가는 방법의 수를 구해서 계속 곱해나가는 방법을 취했다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자판
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 아이템의 수 a, 장애물의 수 b
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 아이템들
        int[][] items = new int[a + 1][];
        for (int i = 0; i < items.length - 1; i++)
            items[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 과 마지막 도착 지점
        items[a] = new int[]{n, m};
        // 오른쪽 위로 오름차순 정렬
        Arrays.sort(items, (o1, o2) -> {
            if (o1[0] == o2[0])
                return Integer.compare(o1[1], o2[1]);
            return Integer.compare(o1[0], o2[0]);
        });
        
        // 장애물의 위치는 boolean 배열로 정리
        boolean[][] blocked = new boolean[n + 1][m + 1];
        for (int i = 0; i < b; i++) {
            st = new StringTokenizer(br.readLine());
            blocked[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = true;
        }
        
        // 처음 시작 위치
        int preR = 1;
        int preC = 1;
        // 현재 경우의 수
        int answer = 1;
        for (int[] item : items) {
            // DP
            int[][] dp = new int[n + 1][m + 1];
            // 시작점에서 경우의 수
            dp[preR][preC] = 1;
            // 시작점부터, 다음 아이템의 위치까지의
            // 작은 직사각형 모양만 계산한다.
            for (int i = preR; i <= item[0]; i++) {
                for (int j = preC; j <= item[1]; j++) {
                    // 위로 진행이 가능한 경우
                    if (i + 1 < dp.length && !blocked[i + 1][j])
                        dp[i + 1][j] += dp[i][j];
                    // 오른쪽으로 진행이 가능한 경우
                    if (j + 1 < dp[i].length && !blocked[i][j + 1])
                        dp[i][j + 1] += dp[i][j];
                }
            }
            // 구해진 경우의 수를 전체 경우의 수에 곱해준다.
            answer *= dp[item[0]][item[1]];
            // 이전 위치에 현 아이템의 위치들을 넣어준다.
            preR = item[0];
            preC = item[1];
        }

        // 계산된 결과 출력.
        System.out.println(answer);
    }
}