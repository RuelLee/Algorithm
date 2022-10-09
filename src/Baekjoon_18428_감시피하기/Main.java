/*
 Author : Ruel
 Problem : Baekjoon 18428 감시 피하기
 Problem address : https://www.acmicpc.net/problem/18428
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18428_감시피하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static char[][] map;
    static boolean possible = false;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 복도가 주어진다
        // 각 복도는 선생님, 학생, 빈 공간으로 주어진다.
        // 선생님은 상, 하, 좌, 우 일직선으로 학생이 있는지 감시할 수 있다.
        // 투과가 불가능한 3개의 장애물을 설치할 수 있다고 했을 때, 모든 학생들이
        // 선생님의 감시를 피할 수 있는지 출력하라.
        //
        // 모든 경우의 수를 계산하고 이를 하는데 백트래킹을 이용하는 문제
        // 먼저 빈 공간에 3개의 장애물을 설치하는 경우의 수들을 구하며
        // 해당 경우의 수에서 선생님이 한 명의 학생이라도 찾아내는 것이 가능한지 따져나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력 처리.
        int n = Integer.parseInt(br.readLine());
        map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().replace(" ", "").toCharArray();

        // 경우의 수를 모두 계산한다.
        calcCases(0, 0, new boolean[n][n]);
        // 기록된 답안 출력.
        System.out.println(possible ? "YES" : "NO");
    }

    // 장애물을 설치하는 경우의 수를 구한다.
    static void calcCases(int idx, int numOfObstacles, boolean[][] obstacles) {
        // 만약 이미 감시를 피하는 경우의 수를 구했다면 추가적으로 구할 필요 없다.
        if (possible)
            return;
        // 장애물 3개의 배치가 끝났다면
        else if (numOfObstacles == 3) {
            // 한 명의 학생이라도 발각되는지 확인하고.
            // 한 명의 학생도 찾지 못한다면
            // possible에 true를 기록하고 종료.
            if (!canSee(obstacles))
                possible = true;
            return;
            // 장애물 설치가 끝나지 않았는데 맵의 범위를 벗어났다면 종료.
        } else if (idx >= map.length * map.length)
            return;

        // idx를 row, col으로 변환.
        int row = idx / map.length;
        int col = idx % map.length;

        // 만약 row, col이 빈 공간이라면
        if (map[row][col] == 'X') {
            // 장애물 설치 후
            obstacles[row][col] = true;
            // 재귀적으로 함수를 불러 장애물 설치 경우의 수 계산을 계속한다.
            calcCases(idx + 1, numOfObstacles + 1, obstacles);
            // 해당 경우의 수 계산이 끝났다면 장애물 회수.
            obstacles[row][col] = false;
        }
        // 장애물을 설치하지 않고 다음 idx로 넘어가는 경우의 수 계산.
        calcCases(idx + 1, numOfObstacles, obstacles);
    }

    // 선생님의 학생을 발견할 수 있는지 확인한다.
    static boolean canSee(boolean[][] obstacles) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 학생의 위치에서
                if (map[i][j] == 'S') {
                    // 좌측에 선생님이 있는지 확인.
                    int row = i - 1;
                    while (row >= 0) {
                        // 장애물이 있다면 좌측은 더 이상 살펴보지 않는다.
                        if (obstacles[row][j])
                            break;
                        // 선생님이 있다면 발각된다.
                        else if (map[row][j] == 'T')
                            return true;
                        row--;
                    }

                    // 우측에 선생님이 있는지 확인.
                    row = i + 1;
                    while (row < map.length) {
                        if (obstacles[row][j])
                            break;
                        else if (map[row][j] == 'T')
                            return true;
                        row++;
                    }

                    // 위쪽에 선생님이 있는지 확인.
                    int col = j - 1;
                    while (col >= 0) {
                        if (obstacles[i][col])
                            break;
                        else if (map[i][col] == 'T')
                            return true;
                        col--;
                    }
                    // 아랫쪽에 선생님이 있는지 확인.
                    col = j + 1;
                    while (col < map[i].length) {
                        if (obstacles[i][col])
                            break;
                        else if (map[i][col] == 'T')
                            return true;
                        col++;
                    }
                }
            }
        }
        // 모든 학생들이 선생님께 발각되지 않았다면 false 리턴.
        return false;
    }
}