/*
 Author : Ruel
 Problem : Baekjoon 14939번 불 끄기
 Problem address : https://www.acmicpc.net/problem/14939
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14939_불끄기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static char[][] bulbs;
    static int[] dr = {0, -1, 0, 1, 0};
    static int[] dc = {0, 0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 10 * 10 개의 전구들이 주어지고,
        // 해당 전구의 스위치를 누르면, 자신을 포함한 십자범위 전구들의 상태가 반전된다고 한다
        // 모든 전구를 끄는데 최소한으로 눌러야하는 스위치의 개수를 출력하라
        //
        // 가만히 생각을 해보면, 2행의 차례인데, 1행의 전구가 켜져있다면, 1행의 전구를 끌 기회는 이번이 마지막이다
        // 따라서 n행의 스위치를 끌 때의 기준은 n-1행의 전구가 켜져있는지이다.
        // 따라서 첫행은 순열을 이용하여 모든 가짓수를 구한 후(2^10으로 모든 경우를 계산한더라도 문제 없다) 두번째 행부터는 윗행을 살펴보며 정한다
        // 그리하여 마지막 행의 모든 전구를 끌 수 있다면 가능한 경우이고, 이 때의 스위치를 누른 횟수를 반환한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        bulbs = new char[10][];
        for (int i = 0; i < bulbs.length; i++)
            bulbs[i] = br.readLine().toCharArray();

        int minPush = firstLinePermutation(0, 0);
        System.out.println(minPush == Integer.MAX_VALUE ? -1 : minPush);
    }

    // 첫 행은 순열을 통해 모든 가짓수를 구한다
    static int firstLinePermutation(int col, int pushed) {
        // 첫행의 마지막 전구까지 모든 가짓수를 구했다면
        if (col == bulbs.length) {
            // 현재 전구들의 상태를 clone에 복사한 후
            char[][] clone = new char[10][10];
            for (int i = 0; i < clone.length; i++) {
                for (int j = 0; j < clone[i].length; j++)
                    clone[i][j] = bulbs[i][j];
            }

            // 0행은 계산이 끝났으므로, 1행부터 살펴본다.
            for (int i = 1; i < clone.length; i++) {
                for (int j = 0; j < clone[i].length; j++) {
                    // 자신의 윗 전구가 켜져있다면 이번에 반드시 꺼야한다.
                    if (clone[i - 1][j] == 'O') {
                        buttonPush(i, j, clone);
                        pushed++;
                    }
                }
            }

            boolean allTurnOff = true;
            // 아랫행이 반드시 전구를 꺼주므로, 마지막 행만 켜져있는 전구가 있는지 찾아본다.
            for (int i = 0; i < bulbs[9].length; i++) {
                if (clone[9][i] == 'O') {       // 켜져있는 전구가 있다면
                    // false
                    allTurnOff = false;
                    break;
                }
            }
            // 모든 전구가 꺼져있다면, 스위치를 누른 횟수를 반환하고, 그렇지 않다면, Integer.MAX_VALUE를 반환한다.
            return allTurnOff ? pushed : Integer.MAX_VALUE;
        }

        // 첫 행에서는 스위치를 눌렀을 때와 그렇지 않을 때, 두 가지의 경우에 대해 최소 스위치 누른 횟수를 구한다.
        int minPush = Integer.MAX_VALUE;

        // 스위치를 누르고,
        buttonPush(0, col, bulbs);
        // 스위치를 누른 상태에서의 최소 스위치 누른 횟수를 구한다
        minPush = Math.min(minPush, firstLinePermutation(col + 1, pushed + 1));
        // 스위치를 다시 한번 눌러 원래 상태로 복구하고, 최소 스위치 누른 횟수를 구한다.
        buttonPush(0, col, bulbs);
        minPush = Math.min(minPush, firstLinePermutation(col + 1, pushed));

        return minPush;
    }

    // 해당 전구의 스위치를 누른다.
    static void buttonPush(int row, int col, char[][] clone) {
        // 자신과 네 방향의 전구 5개의 상태를 반전시킨다.
        for (int d = 0; d < dr.length; d++) {
            int aroundR = row + dr[d];
            int aroundC = col + dc[d];

            if (checkArea(aroundR, aroundC)) {
                if (clone[aroundR][aroundC] == '#')
                    clone[aroundR][aroundC] = 'O';
                else
                    clone[aroundR][aroundC] = '#';
            }
        }
    }

    static boolean checkArea(int r, int c) {
        return r >= 0 && r < bulbs.length && c >= 0 && c < bulbs[r].length;
    }
}