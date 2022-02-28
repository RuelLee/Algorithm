/*
 Author : Ruel
 Problem : Baekjoon 1915번 가장 큰 정사각형
 Problem address : https://www.acmicpc.net/problem/1915
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1915_가장큰정사각형;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, -1, 0};
    static int[] dc = {-1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n, m크기의 판에 숫자 1과 0이 주어진다
        // 1로 이루어진 정사각형 중 가장 큰 정사각형의 넓이를 구하는 문제
        // 간단한 DP문제
        // 1이 발견이 되면, 자신으로부터 왼쪽, 왼쪽위, 위 세칸을 살펴보고, 해당하는 가장 숫자를 찾아 그 값 + 1을 자신의 값으로 넣어준다
        // 여기서 dp값이 의미하는 것은 왼쪽은 왼쪽에서부터 이어져온 1의 개수이고,
        // 왼쪽 위는 왼쪽 위 칸을 왼쪽 아래 끝점으로 갖는 정사각형의 한 변의 길이다.
        // 윗 칸은 위에서 이어져온 1의 개수이다
        // 가령
        // 0 1 1
        // 1 1 0
        // 1 1 0 이라고 주어진다면
        // DP는
        // 0 1 1
        // 1 1 0
        // 1 2 0 이 된다. 여기서 2가 나오는 배경을 보면
        // 왼쪽에 1개의 1이 있고, 위쪽에도 1개의 1이 있으며, 왼쪽 위에 길이 1의 정사각형이 있으므로, 길이 2의 정사각형이 완성되는 식이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        char[][] board = new char[n][m];
        for (int i = 0; i < n; i++)
            board[i] = br.readLine().toCharArray();

        int[][] checkSize = new int[n][m];      // 계산되는 정사각형의 길이를 저장할 DP
        int maxLength = 0;      // 최대 길이를 동시에 계산해두자.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '1') {       // 만약 해당 값이 1이라면
                    if (i > 0 && j > 0) {       // 행과 열 모두 0보다 크다면(왼쪽 위를 살펴볼 수 있다면)
                        checkSize[i][j] = Integer.MAX_VALUE;
                        for (int d = 0; d < 3; d++)     // 세 곳을 살펴보고, 가장 작은 값에 + 1한 값을 가져오자.
                            checkSize[i][j] = Math.min(checkSize[i][j], checkSize[i + dr[d]][j + dc[d]] + 1);
                    } else      // 만약 행과 열 중 하나라도 1보다 작다면(왼쪽 위 칸이 없다면)
                        checkSize[i][j] = 1;        // 1값을 넣어준다.
                    // 그리고 전체 정사각형들의 최대 길이를 계산해두자
                    maxLength = Math.max(maxLength, checkSize[i][j]);
                }
            }
        }
        // 최종적으로 완성되는 maxLength의 제곱이 정답.
        System.out.println(maxLength * maxLength);
    }
}