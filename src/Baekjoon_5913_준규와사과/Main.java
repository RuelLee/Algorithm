/*
 Author : Ruel
 Problem : Baekjoon 5913번 준규와 사과
 Problem address : https://www.acmicpc.net/problem/5913
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5913_준규와사과;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int count = 0;

    public static void main(String[] args) throws IOException {
        // 5 * 5 크기의 과수원이 주어진다.
        // 한 사람은 (1, 1), 다른 한 사람은 (5, 5)에서 수확을 시작하여
        // 모든 과실을 수확하고 같은 지점에서 만나고자 한다.
        // k개의 지역에서는 돌이 있으며, 돌이 있는 지역이나 이미 수확을 진행한 구역으로는 지나가지 못한다.
        // 위와 같은 방법으로 수확을 하는 방법의 수는?
        //
        // 백트래킹 문제
        // 두 명이 동시에 수확을 진행한다고 했지만, 사실 (1, 1)에서 (4, 4)로 모든 과실을 수확하는 경우의 수와 같다.
        // 따라서 백트래킹을 이용하여 모든 경우의 수를 세어준다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // k개의 돌이 있는 지역
        int k = Integer.parseInt(br.readLine());
        boolean[][] stones = new boolean[5][5];
        for (int i = 0; i < k; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            stones[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1] = true;
        }

        // 백트래킹을 활용하여 (0, 0) -> (4, 4)로 진행할 수 있는 경우의 수를 센다.
        findAnswer(0, 0, 0, new boolean[5][5], stones, k);
        System.out.println(count);
    }

    static void findAnswer(int r, int c, int visitedArea, boolean[][] visited, boolean[][] stones, int k) {
        // 마지막 지점에 도착했고
        // 수확한 지역의 수가 돌을 제외한 모든 지역이라면
        // 경우의 수 하나 증가 후 종료
        if (r == 4 && c == 4 && visitedArea == 24 - k) {
            count++;
            return;
        }

        // 마지막 지점이 아니라면
        // 방문 체크
        visited[r][c] = true;
        // 방문 지역 개수 증가
        visitedArea++;
        // 다음 지역 탐색
        for (int d = 0; d < 4; d++) {
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            // 다음 지역이 맵 구역 안이고, 돌이 있는 지역이 아니며
            // 이미 방문하지 않았다면 메소드 재귀 호출
            if (checkArea(nextR, nextC) && !stones[nextR][nextC] && !visited[nextR][nextC])
                findAnswer(nextR, nextC, visitedArea, visited, stones, k);
        }

        // 이전 지역으로 돌아가므로
        // 방문 체크 해제
        visited[r][c] = false;
        // 방문 지역 개수 감수
        visitedArea--;
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < 5 && c >= 0 && c < 5;
    }
}