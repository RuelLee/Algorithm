/*
 Author : Ruel
 Problem : Baekjoon 21772번 가희의 고구마 먹방
 Problem address : https://www.acmicpc.net/problem/21772
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21772_가희의고구마먹방;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int r, c;
    static char[][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // r * c 크기의 맵이 주어진다.
        // 맵에 가희의 위치는 'G', 고구마는 'S', 빈 칸은 '.', 장애물은 '#' 으로 주어진다.
        // 가희는 t시간 동안 한 칸씩 이동할 수 있으며, 최대한 많은 고구마를 얻고자 한다.
        // 얻을 수 있는 최대한의 고구마를 출력하라
        //
        // 브루트 포스, 백트래킹 문제
        // t가 최대 10으로 작게 주어지므로
        // G를 근처로 10 이내에 갈 수 있는 모든 방법에 대해 따져볼 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c크기의 맵, 주어진 시간 t
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        map = new char[r][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 가희의 위치를 찾아
        // bruteForce 진행
        int answer = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'G')
                    answer = bruteForce(i, j, t, 0);
            }
        }
        // 답 출력
        System.out.println(answer);
    }

    // currentR, currentC에서 현재 남은 시간 time, 얻은 고구마의 갯수 obtain
    // 으로 파생되는 경우들을 계산
    static int bruteForce(int currentR, int currentC, int time, int obtain) {
        // 현 위치가 고구마인지 여부
        boolean sweetPotato = map[currentR][currentC] == 'S';
        // 시간이 모두 끝났다면 현재 obtain 반환
        if (time == 0)
            return obtain + (sweetPotato ? 1 : 0);
        
        // 고구마는 한 번만 획득할 수 있으므로
        // 현재 위치의 고구마를 빈 칸으로 변경
        if (sweetPotato)
            map[currentR][currentC] = '.';

        // 현재 얻을 수 있는 최대 고구마의 갯수
        int max = 0;
        // 사방탐색하여, 갈 수 있는 곳들을 모두 계산
        for (int d = 0; d < 4; d++) {
            int nextR = currentR + dr[d];
            int nextC = currentC + dc[d];

            if (checkArea(nextR, nextC) && map[nextR][nextC] != '#')
                max = Math.max(max, bruteForce(nextR, nextC, time - 1, obtain + (sweetPotato ? 1 : 0)));
        }

        // 빈 칸을 다시 고구마로 되돌리고
        if (sweetPotato)
            map[currentR][currentC] = 'S';
        // 얻을 수 있는 최대 고구마의 갯수 반환
        return max;
    }
    
    // 범위 체크
    static boolean checkArea(int row, int col) {
        return row >= 0 && row < r && col >= 0 && col < c;
    }
}