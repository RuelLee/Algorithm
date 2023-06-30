/*
 Author : Ruel
 Problem : Baekjoon 14712번 넴모넴모 (Easy)
 Problem address : https://www.acmicpc.net/problem/14712
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14712_넴모넴모_Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static boolean[][] filled;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 게임판이 주어진다.
        // 2 * 2 크기에 넴모들이 올라간다면 해당 넴모들을 삭제할 수 있다.
        // 2 * 2 넴모가 없는 상태에 게임을 그만두려고 한다.
        // 주어진 게임판에서 넴모가 없는 상태의 가짓수는?
        //
        // 브루트 포스 문제
        // n과 m의 제한이 각각 25 이하이며, 둘의 곱 또한 25 이하이다
        // 따라서 모든 경우의 수를 따져 계산하더라도 시간 초과가 나지 않는다.
        // 브루트 포스를 통해 모든 칸에 넴모를 놓고나 놓지 않았을 때의 경우를 센다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 주어지는 게임판의 크기
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 게임 판
        filled = new boolean[n][m];
        // 센 경우의 수를 출력
        System.out.println(countCases(0));
    }

    // 넴모가 없는 경우의 수를 센다.
    static int countCases(int idx) {
        // idx로 현재 row, col을 계산
        int row = idx / filled[0].length;
        int col = idx % filled[0].length;

        // row가 크기를 벗어났다면, 모든 칸에 넴모 배치가 끝난 것.
        // 현재 2 * 2 크기의 넴모가 존재한다면 0, 존재하지 않는다면 1을 반환한다.
        if (row >= filled.length)
            return findNemmo() ? 0 : 1;

        int sum = 0;
        // 현재 칸에 넴모를 두었을 때
        filled[row][col] = true;
        sum += countCases(idx + 1);
        // 두지 않았을 때
        filled[row][col] = false;
        sum += countCases(idx + 1);
        // 의 합을 구해 반환한다.
        return sum;
    }

    // 주어진 게임판에 2*2 넴모가 존재하는지 찾는다.
    static boolean findNemmo() {
        // 2 * 2 크기의 넴모를 찾으므로
        // row와 col을 전체 크기보다 하나씩 작은 상태까지 탐색한다.
        for (int i = 0; i < filled.length - 1; i++) {
            for (int j = 0; j < filled[i].length - 1; j++) {
                // (i, j)를 왼쪽 위의 끝점으로 갖는 2*2 넴모가 존재한다면
                // true를 반환한다.
                if (filled[i][j] && filled[i + 1][j] &&
                        filled[i][j + 1] && filled[i + 1][j + 1])
                    return true;
            }
        }
        // 찾지 못했다면 false 반환.
        return false;
    }
}