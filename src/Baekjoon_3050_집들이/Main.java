/*
 Author : Ruel
 Problem : Baekjoon 3050번 집들이
 Problem address : https://www.acmicpc.net/problem/3050
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3050_집들이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // r * c 크기의 아파트 평면도가 주어진다.
        // 빈 칸은 '.', 막힌 공간은 'X'로 주어진다.
        // 빈 칸들 위에 하나의 직사각형 식탁을 놓고자 한다.
        // 식탁에 앉을 수 있는 사람의 수는 식탁의 둘레의 길이와 같다.
        // 식탁은 항상 아파트의 변에 평행하게 놓는다.
        // 되도록 많은 사람을 초대하고자 할 때, 초대할 수 있는 사람의 수는?
        // 사람의 크기는 매우 작다.
        //
        // 누적합 문제
        // 세로 방향으로 연속하여 놓인 빈 칸의 수를 누적합으로 구한다.
        // 그 후, 빈 칸들을 방문하여, 오른쪽으로 진행하며,
        // 만들 수 있는 최대 직사각형을 구해가며 답을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c 크기의 평면도
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 빈 칸과 막힌 칸이 주어진다.
        char[][] map = new char[r][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();

        // 위로 연속된 빈칸의 수를 누적합으로 구한다.
        int[][] colPsums = new int[r][c];
        for (int j = 0; j < map[0].length; j++)
            if (map[0][j] == '.')
                colPsums[0][j] = 1;
        for (int i = 1; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '.')
                    colPsums[i][j] = colPsums[i - 1][j] + 1;
            }
        }

        int answer = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 막힌 칸일 경우 건너뜀.
                if (map[i][j] == 'X')
                    continue;
                
                // 막히지 않았을 경우
                // 세로의 길이
                int maxHeight = colPsums[i][j];
                // 가로의 길이
                int width = 1;
                // 식탁에 앉을 수 있는 사람의 수
                answer = Math.max(answer, maxHeight * 2 + width * 2 - 1);
                // 오른쪽 칸이 빈 칸일 경우, 연속하여 탐색한다.
                while (j + width < map[i].length && map[i][j + width] == '.') {
                    // 식탁이 직사각형 모양이므로 가장 작은 세로 길이가 세로가 된다.
                    maxHeight = Math.min(maxHeight, colPsums[i][j + width]);
                    // 가로 증가.
                    width++;
                    // 해당 식탁에 앉을 수 있는 사람의 수 계산
                    answer = Math.max(answer, maxHeight * 2 + width * 2 - 1);
                }
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}