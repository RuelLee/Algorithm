/*
 Author : Ruel
 Problem : Baekjoon 1937번 욕심쟁이 판다
 Problem address : https://www.acmicpc.net/problem/1937
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1937_욕심쟁이판다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][] forest;
    static int[][] maxDistance;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 대나무숲이 주어진다
        // 판다가 존재하는데, 이전 칸보다 더 많은 대나무가 있는 곳으로 이동하면서 대나무를 먹는다고 한다
        // 판다가 가장 많이 이동할 수있는 거리는 얼마인가
        //
        // 메모이제이션 문제
        // 해당 위치로부터 최대로 얼마나 이동할 수 있는지를 메모이제이션으로 기록해둔다
        // 그 후, 해당 위치에 도달했을 때 메모이제이션 기록이 있다면 바로 참고해서 연산을 생략한다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        forest = new int[n][];
        maxDistance = new int[n][n];
        for (int i = 0; i < forest.length; i++)
            forest[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int maxDistance = 0;        // 최대 거리 기록
        // 모든 칸에서 시작해본다
        // 이미 메모이제이션으로 기록됐다면 바로 값 참고가 될 것이다.
        for (int i = 0; i < forest.length; i++) {
            for (int j = 0; j < forest[i].length; j++)
                maxDistance = Math.max(maxDistance, calcMaxDistance(i, j));
        }
        System.out.println(maxDistance);
    }

    static int calcMaxDistance(int r, int c) {      // r, c에서 이동할 수 있는 최대 거리를 계산한다.
        if (maxDistance[r][c] != 0)     // 메모이제이션 기록이 있다면 바로 참고.
            return maxDistance[r][c];

        int currentMaxDistance = 0;
        for (int d = 0; d < 4; d++) {       // 4방을 탐색하며
            int nextR = r + dr[d];
            int nextC = c + dc[d];

            // 더 많은 대나무가 있는 곳을 찾는다면
            // 해당 방향에서 다시 재귀적으로 해당 칸에서 최대 이동거리를 구해 최대값을 갱신한다.
            if (checkArea(nextR, nextC) && forest[r][c] < forest[nextR][nextC])
                currentMaxDistance = Math.max(currentMaxDistance, calcMaxDistance(nextR, nextC));
        }
        // 계산된 currentMaxDistance는 다음 칸으로부터 이동 가능한 최대 이동 거리
        // maxDistance[r][c]에는 현재칸을 포함한 최대 이동 거리로 currentMaxDistance + 1로 기록하고, 값을 리턴한다.
        return maxDistance[r][c] = currentMaxDistance + 1;
    }

    static boolean checkArea(int r, int c) {        // 범위 체크
        return r >= 0 && r < forest.length && c >= 0 && c < forest[r].length;
    }
}