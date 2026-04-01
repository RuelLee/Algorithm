/*
 Author : Ruel
 Problem : Baekjoon 17085번 십자가 2개 놓기
 Problem address : https://www.acmicpc.net/problem/17085
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17085_십자가2개놓기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // r * c 크기의 격자가 주어진다.
        // 격자는 # 혹은 .으로 이루어져있다.
        // #으로 주어진 위치에 십자가를 놓을 수 있다.
        // 두 개의 십자가를 겹치지 않게 # 위에 놓을 때,
        // 두 십자가의 넓이의 최대 곱은?
        //
        // 브루트 포스 문제
        // #의 위치와 해당 위치에 놓을 수 있는 최대 십자가의 크기를 구해둔다.
        // 두 개의 # 위치를 골라
        // 각각의 최소 십자가 크기부터 최대 십자가 크기까지 비교해나가면서
        // 서로 겹치는 점이 발생하는지 여부를 판별해나가며 두 십자가의 넓이의 최대 곱을 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 맵 정보
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();

        // 각 위치에 놓을 수 있는 십자가의 최대 길이
        int[][] maxLength = new int[n][m];
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '.')
                    continue;

                // 해당 위치를 리스트에 추가
                list.add(i * m + j);
                // 네 방향으로 탐색하여 최대 길이를 구한다.
                maxLength[i][j] = 1;
                for (int length = 1; length < (Math.min(n, m) + 1) / 2; length++) {
                    // 네 방향 모두 통과해야 가능.
                    boolean allPass = true;
                    for (int d = 0; d < 4; d++) {
                        int nextR = i + dr[d] * length;
                        int nextC = j + dc[d] * length;

                        if (!checkArea(nextR, nextC) || map[nextR][nextC] == '.') {
                            allPass = false;
                            break;
                        }
                    }
                    if (!allPass)
                        break;
                    maxLength[i][j] = length + 1;
                }
            }
        }

        int max = 0;
        // 두 개의 #을 골라 서로 십자가를 키워가며
        // 넓이의 곱을 구한다.
        for (int i = 0; i < list.size(); i++) {
            int r1 = list.get(i) / m;
            int c1 = list.get(i) % m;
            for (int j = i + 1; j < list.size(); j++) {
                int r2 = list.get(j) / m;
                int c2 = list.get(j) % m;

                for (int a = 0; a < maxLength[r1][c1]; a++) {
                    for (int b = 0; b < maxLength[r2][c2]; b++) {
                        // b가 하나씩 길이를 증가시킬 때마다
                        // 추가되는 네 개의 점이 모두 첫번째 십자가에 포함되지 않아야한다.
                        boolean allPass = true;
                        for (int d = 0; d < 4; d++) {
                            int checkR = r2 + dr[d] * b;
                            int checkC = c2 + dc[d] * b;

                            if ((r1 == checkR && (checkC >= c1 - a && checkC <= c1 + a)) ||
                                    (c1 == checkC && (checkR >= r1 - a && checkR <= r1 + a))) {
                                allPass = false;
                                break;
                            }
                        }
                        if (!allPass)
                            break;
                        // 넓이의 곱
                        max = Math.max(max, ((a + 1) * 4 - 3) * ((b + 1) * 4 - 3));
                    }
                }
            }
        }
        // 답 출력
        System.out.println(max);
    }

    // 맵 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}