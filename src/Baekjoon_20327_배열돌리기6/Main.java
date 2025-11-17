/*
 Author : Ruel
 Problem : Baekjoon 20327번 배열 돌리기 6
 Problem address : https://www.acmicpc.net/problem/20327
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20327_배열돌리기6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][][] matrix;
    static int SIZE;

    public static void main(String[] args) throws IOException {
        // 2^n * 2^n 크기의 배열이 주어진다.
        // l은 0 ~ n까지 주어지며, 이는 2^l * 2^l의 작은 이차원 배열을 하나의 원소로 취급하여 하는 연산이다.
        // 1번 연산은 2^l * 2^l 내의 원소들을 상하 반전시킨다.
        // 2번 연산은 2^l * 2^l 내의 원소들을 좌우 반전시킨다.
        // 3번 연산은 2^l * 2^l 내의 원소들을 오른쪽으로 90도 회전시킨다.
        // 4번 연산은 2^l * 2^l 내의 원소들을 왼쪽으로 90도 회전시킨다.
        // 5번 연산은 2^l * 2^l의 부분 배열들을 상하 반전시킨다.
        // 6번 연산은 2^l * 2^l의 부분 배열들을 좌우 반전시킨다.
        // 7번 연산은 2^l * 2^l의 부분 배열들을 시계방향으로 90도 회전시킨다.
        // 8번 연산은 2^l * 2^l의 부분 배열들을 반시계방향으로 90도 회전시킨다.
        // 자세히는 문제 참고
        // r개의 연산이 주어질 때, 연산이 끝난 후 결과를 출력하라
        //
        // 구현 문제
        // 각 연산이 주어질 때, 해당 값이 어느 위치로 이동하는지를 잘 고려하여 구현한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 2^n * 2^n 크기의 배열, r번의 연산 횟수
        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        // 배열의 크기
        SIZE = 1 << n;
        // 2개 선언하여, 연산마다 번갈아가면서 값 계산
        matrix = new int[2][SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < SIZE; j++)
                matrix[0][i][j] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());

            // 각 연산 처리
            operation(i, l, k);
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE - 1; j++)
                sb.append(matrix[r % 2][i][j]).append(" ");
            sb.append(matrix[r % 2][i][SIZE - 1]).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }

    // seq번째 명령으로 2^level * 2^level 단위씩, type번 연산을 수행.
    static void operation(int seq, int level, int type) {
        // matrix[from]의 값을 바탕으로 matrix[to]에 기록
        int from = seq % 2;
        int to = (seq + 1) % 2;
        // 전체 배열을 2^level * 2^level의 배열로 분할하여 생각.
        // (i, j)번째 부분 이차원 배열
        for (int i = 0; i < SIZE >> level; i++) {
            for (int j = 0; j < SIZE >> level; j++) {
                // 해당 배열의 시작 x값과 종료 x값
                int startX = (1 << level) * i;
                int endX = (1 << level) * (i + 1) - 1;
                // 해당 배열의 시작 y값과 종료 y값
                int startY = (1 << level) * j;
                int endY = (1 << level) * (j + 1) - 1;
                
                // 해당 값을 모두 돌며
                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        switch (type) {
                            // 1번 연산은 2^level * 2^level 내에서 상하 반전
                            case 1 -> matrix[to][endX - (x - startX)][y] = matrix[from][x][y];
                            // 2번 연산은 2^level * 2^level 내에서 좌우 반전
                            case 2 -> matrix[to][x][endY - (y - startY)] = matrix[from][x][y];
                            // 3번 연산은 2^level * 2^level 내에서 시계 방향 90도 회전
                            case 3 -> matrix[to][startX + (y - startY)][endY - (x - startX)] = matrix[from][x][y];
                            // 4번 연산은 2^level * 2^level 내에서 반시계 방향 90도 회전
                            case 4 -> matrix[to][endX - (y - startY)][startY + (x - startX)] = matrix[from][x][y];
                            // 5 ~ 8번 연산은 2^level * 2^level의 이차원 배열들끼리의 연산이 일어난다.
                            // 5번은 2^level * 2^level의 이차원 배열들끼리의 상하 반전
                            case 5 -> {
                                int diff = SIZE - (1 << level) * (2 * i + 1);
                                matrix[to][x + diff][y] = matrix[from][x][y];
                            }
                            // 6번은 2^level * 2^level의 이차원 배열들끼리의 좌우 반전
                            case 6 -> {
                                int diff = SIZE - (1 << level) * (2 * j + 1);
                                matrix[to][x][y + diff] = matrix[from][x][y];
                            }
                            // 7번은 2^level * 2^level의 이차원 배열들끼리의 시계 방향 90도 회전
                            case 7 -> {
                                int xDIff = (1 << level) * j;
                                int yDiff = SIZE - (1 << level) * (i + 1);
                                matrix[to][(x - startX) + xDIff][(y - startY) + yDiff] = matrix[from][x][y];
                            }
                            // 8번은 2^level * 2^level의 이차원 배열들끼리의 반시계 방향 90도 회전
                            case 8 -> {
                                int xDiff = SIZE - (1 << level) * (j + 1);
                                int yDiff = (1 << level) * i;
                                matrix[to][(x - startX) + xDiff][(y - startY) + yDiff] = matrix[from][x][y];
                            }
                        }
                    }
                }
            }
        }
    }
}