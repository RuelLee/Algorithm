/*
 Author : Ruel
 Problem : Baekjoon 23058번 뒤집기 게임
 Problem address : https://www.acmicpc.net/problem/23058
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23058_뒤집기게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] map;

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자 위에 양면이 검은색과 흰색으로 칠해진 돌이 있다.
        // 돌은 흰색 혹은 검은색이 보이도록 놓여있다.
        // 다음과 같은 행동을 할 수 있다.
        // 1. 한 행이나 열을 전부 뒤집는다.
        // 2. 하나의 돌을 뒤집는다.
        // 모든 돌을 같은 색으로 바꾸는데 걸리는 최소 행동의 수를 구하라
        // 
        // 브루트 포스, 비트마스킹 문제
        // 생각을 해보면 같은 행이나 열을 두 번 뒤집는 건 의미가 없다.
        // 뒤집거나, 뒤집지 않거나이다.
        // n이 최대 8로 주어지므로, 각 행과 열을 뒤집는 경우의 수는 최대 2^16 이다.
        // 직접 계산이 가능한 정도의 수이다.
        // 전체 행동의 수는, 한 줄을 뒤집은 횟수 + 검은 돌가 흰 돌중 적은 수의 개수

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 격자
        int n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        for (int i = 0; i < map.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        int answer = Integer.MAX_VALUE;
        // 모든 행과 열에 대한 경우의 수는 2 * n으로 볼 수 있다.
        // 해당 경우를 모두 계산.
        for (int i = 0; i < (1 << 2 * n); i++) {
            // 비트마스크가 i일 때, 현재 검은 돌의 수 계산.
            int stones = countStones(i);
            // 비트마스크가 i일 때, 줄을 뒤집은 횟수는 countLineFlip(i) 이고
            // 흰 돌과 검은 돌 중 더 적은 수는 Math.min(stones, n * n - stones) 이다.
            // 두 수를 합한 수가 행동의 수.
            answer = Math.min(answer, countLineFlip(i) + Math.min(stones, n * n - stones));
        }
        // 모든 돌을 같은 색으로 바꾸는데 필요한 최소 행동의 수 출력
        System.out.println(answer);
    }

    // bitmask가 주어졌을 때, 뒤집은 줄의 수를 구한다.
    // bitmask를 보고, 1의 개수를 세면 된다.
    static int countLineFlip(int bitmask) {
        int count = 0;
        while (bitmask > 0) {
            count += (bitmask % 2);
            bitmask /= 2;
        }
        return count;
    }

    // bitmask를 보고 현재 검은 돌의 수를 계산한다.
    static int countStones(int bitmask) {
        int count = 0;
        // bitmask를 보고, i번째 행을 뒤집었는지 여부와
        // j번째 열을 뒤집었는지 여부를 반영하여, 검은 돌의 개수를 센다.
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                count += (map[i][j] + ((bitmask & 1 << i) == 0 ? 0 : 1) + ((bitmask & 1 << map.length + j) == 0 ? 0 : 1)) % 2;
            }
        }
        return count;
    }
}