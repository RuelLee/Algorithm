/*
 Author : Ruel
 Problem : Baekjoon 14890번 경사로
 Problem address : https://www.acmicpc.net/problem/14890
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14890_경사로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * n 크기의 지도가 주어지고 각 칸의 높이가 주어진다.
        // 동일한 높이의 칸을 지나갈 수 있다.
        // l길이의 동일한 높이의 칸이 있다면 경사로를 놓아, 해당 높이 +1 칸으로 이동할 수 있다.
        // 하나의 행 혹은 열을 따라 이동을 한다 할 때, 이동 가능한 행과 열의 수는?
        //
        // 구현 문제
        // 모든 행에 대해
        // 동일한 높이의 칸이 연속된다면 그 개수를 센다.
        // 높이가 +1인 칸을 만날 경우, 연속했던 칸의 개수와 비교하여 경사로를 세울 수 있는지 판단한다.
        // 높이가 -1인 칸을 만난 경우, 해당 칸으로부터 l개의 칸이 동일 한 높이가 되어 반대방향으로 경사로를 만들 수 있는지 확인한다.
        // 그 외의 경우는 해당 경로는 불가능하다.
        // 를 기준으로 계산 후, 열로도 마찬가지로 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * n 크기의 맵, 경사로를 놓기 위한 동일한 높이 칸의 개수 l
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        // 맵 입력
        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        // 경로의 수
        int answer = 0;
        for (int i = 0; i < n; i++) {
            // 모든 행에 대해
            int cnt = 1;
            boolean possible = true;
            for (int j = 1; j < n && possible; j++) {
                // 이전 칸과 동일한 높이를 갖고 있다면 cnt 증가
                if (map[i][j] == map[i][j - 1])
                    cnt++;
                // 1 증가한다면, 경사로를 놓을 수 있는지 확인. 놓을 수 있다면, 카운터는 다시 1부터 계산
                else if (map[i][j] == map[i][j - 1] + 1 && cnt >= l)
                    cnt = 1;
                // 1 감소한다면, 앞으로의 l개의 칸이 같은 높이인지 확인해야한다.
                else if (map[i][j] == map[i][j - 1] - 1) {
                    if (j + l - 1 < n) {
                        for (int k = j + 1; k <= j + l - 1; k++) {
                            if (map[i][k] != map[i][j]) {
                                possible = false;
                                break;
                            }
                        }
                        // 가능하다면 j의 위치를 해당 경사로의 마지막 위치로 옮긴다.
                        // 해당 경사로가 끝나는 시점이 다시 경사로의 시작점이 될 수는 없으므로
                        // cnt는 0으로 초기화
                        if (possible) {
                            cnt = 0;
                            j = j + l - 1;
                        }
                    } else  // 남은 길이가 l 자체가 안되는 경우 불가능
                        possible = false;
                } else  // 그 외의 경우 불가능
                    possible = false;
            }
            // 가능한 경우라면 경로의 개수 1개 추가
            if (possible)
                answer++;
        }

        // 행으로도 체크
        for (int i = 0; i < n; i++) {
            int cnt = 1;
            boolean possible = true;
            for (int j = 1; j < n && possible; j++) {
                if (map[j][i] == map[j - 1][i])
                    cnt++;
                else if (map[j][i] == map[j - 1][i] + 1 && cnt >= l)
                    cnt = 1;
                else if (map[j][i] == map[j - 1][i] - 1) {
                    if (j + l - 1 < n) {
                        for (int k = j + 1; k <= j + l - 1; k++) {
                            if (map[k][i] != map[j][i]) {
                                possible = false;
                                break;
                            }
                        }
                        if (possible) {
                            cnt = 0;
                            j = j + l - 1;
                        }
                    } else possible = false;
                } else
                    possible = false;
            }
            if (possible)
                answer++;
        }
        // 전체 개수 출력
        System.out.println(answer);
    }
}