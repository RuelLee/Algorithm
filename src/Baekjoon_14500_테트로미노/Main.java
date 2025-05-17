/*
 Author : Ruel
 Problem : Baekjoon 14500번 테트로미노
 Problem address : https://www.acmicpc.net/problem/14500
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14500_테트로미노;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m;

    public static void main(String[] args) throws IOException {
        // 다음과 같은 모양의 테트로미노이 주어진다.
        // □□□□     □□
        //              □□
        // □    □
        // □    □□    □□□
        // □□    □      □
        // 이 테트로미노 중 하나를 90도씩 회전시키거나, 대칭시켜 모양을 만든 후
        // n * m 크기의 판 위에 올려 각 칸의 점수 합을 최대화시키고 싶다.
        // 이 때 얻을 수 있는 점수의 최댓값은?
        //
        // 브루트 포스 문제
        // 모든 테트로미노들에 대해 회전시키거나 대칭시킨 것들이 포함하는 좌표들을 미리 구해두고
        // 모든 좌표에 대해 생기는 모든 경우의 수를 계산하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 판
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        // 판 위 각 격자의 점수
        int[][] map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        int[][][] tetrominoes = new int[][][]{
                // 첫번째 긴 막대 모양. 두 가지 경우 밖에 없다.
                {{0, 0}, {0, 1}, {0, 2}, {0, 3}}, {{0, 0}, {1, 0}, {2, 0}, {3, 0}},
                // 두번째 정사각형 모양. 한 가지 경우 밖에 없다.
                {{0, 0}, {0, 1}, {1, 0}, {1, 1}},
                // 세번째 모양. 먼저 90도씩 회전시킨 네 경우
                // 와 대칭 시켜 90도씩 회전 시킨 네 경우.
                {{0, 0}, {1, 0}, {2, 0}, {2, 1}}, {{0, 0}, {0, 1}, {0, 2}, {1, 0}}, {{0, 0}, {0, 1}, {1, 1}, {2, 1}}, {{0, 2}, {1, 0}, {1, 1}, {1, 2}},
                {{0, 1}, {1, 1}, {2, 0}, {2, 1}}, {{0, 0}, {1, 0}, {1, 1}, {1, 2}}, {{0, 0}, {0, 1}, {1, 0}, {2, 0}}, {{0, 0}, {0, 1}, {0, 2}, {1, 2}},
                // 네번재 모양. 90도 회전 시킨 두 경우와, 대칭시켜 회전시킨 두 경우
                {{0, 0}, {1, 0}, {1, 1}, {2, 1}}, {{0, 1}, {0, 2}, {1, 0}, {1, 1}},
                {{0, 1}, {1, 0}, {1, 1}, {2, 0}}, {{0, 0}, {0, 1}, {1, 1}, {1, 2}},
                // 다섯번째 모양. 90도씩 회전 시킨 네 경우
                {{0, 0}, {0, 1}, {0, 2}, {1, 1}}, {{0, 1}, {1, 0}, {1, 1}, {2, 1}}, {{0, 1}, {1, 0}, {1, 1}, {1, 2}}, {{0, 0}, {1, 0}, {1, 1}, {2, 0}}
        };

        int answer = 0;
        // 모든 좌표에 대해
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 모든 테트로미노의 경우의 수를 대입시킨다.
                for (int[][] tetromino : tetrominoes) {
                    int sum = 0;
                    // 현재 모양이 포함하는 좌표들의 점수를 계산
                    for (int[] shape : tetromino) {
                        int r = i + shape[0];
                        int c = j + shape[1];

                        // 하나라도 범위를 벗어난다면 해당 경우는 버린다.
                        if (!checkArea(r, c)) {
                            sum = -1;
                            break;
                        }
                        // 점수 누적
                        sum += map[r][c];
                    }
                    // 해당 모양의 점수가 최댓값을 갱신하는지 확인.
                    answer = Math.max(answer, sum);
                }
            }
        }
        // 얻을 수 있는 점수의 최댓값 출력
        System.out.println(answer);
    }

    // 해당 좌표가 판을 벗어나는지 확인한다.
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}