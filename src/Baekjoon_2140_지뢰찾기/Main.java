/*
 Author : Ruel
 Problem : Baekjoon 2140번 지뢰찾기
 Problem address : https://www.acmicpc.net/problem/2140
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2140_지뢰찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int n;
    static int[] dr = {-1, -1, -1, 0, 1, 1, 1, 0};
    static int[] dc = {-1, 0, 1, 1, 1, 0, -1, -1};

    public static void main(String[] args) throws IOException {
        // n * n 칸에 0 ~ 8의 숫자 혹은 #로 값이 주어진다.
        // 각 숫자는 해당 칸 인근에 묻힌 지뢰의 숫자이고, #은 지뢰가 묻혀있을지도 모르는 곳이다.
        // 총 몇 개의 #에서 지뢰를 발견할 수 있는지 계산하라
        //
        // 그리디 문제
        // 정확히 어느 위치에 지뢰가 묻혀있는지를 찾진 않아도 된다.
        // 발견 가능한 지뢰가 총 몇 개인지 세면 된다.
        // 따라서 # 칸마다 자기 주위에 0가 있는지 체크한다. 0가 있다면 그 위치에는 지뢰가 있을 수 없기 때문.
        // 그 외의 경우, 해당 칸에 지뢰가 있다고 가정하고, 인근의 0보다 큰 수들을 모두 1씩 낮춰준다.
        // 이렇게 하면, 지뢰의 정확한 위치는 알 수 없지만, 발견할 수 있는 지뢰의 수를 셀 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 맵
        n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            String input = br.readLine();
            for (int j = 0; j < n; j++) {
                if (input.charAt(j) == '#')
                    map[i][j] = -1;
                else
                    map[i][j] = input.charAt(j) - '0';
            }
        }

        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 지뢰가 묻혀있을지도 모르는 칸
                if (map[i][j] == -1) {
                    boolean foundZero = false;

                    // 인근에 0이 있는지 찾는다.
                    for (int d = 0; d < 8 && !foundZero; d++) {
                        int nearR = i + dr[d];
                        int nearC = j + dc[d];

                        if (checkArea(nearR, nearC) && map[nearR][nearC] == 0)
                            foundZero = true;
                    }

                    // 0이 없다면
                    if (!foundZero) {
                        // 인근에 0보다 큰 값들을 모두 1씩 낮춰준다.
                        for (int d = 0; d < 8; d++) {
                            int nearR = i + dr[d];
                            int nearC = j + dc[d];

                            if (checkArea(nearR, nearC) && map[nearR][nearC] > 0)
                                map[nearR][nearC]--;
                        }
                        // 카운터 증가
                        count++;
                    }
                }
            }
        }
        // 발견 가능한 지뢰의 수 출력
        System.out.println(count);
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}