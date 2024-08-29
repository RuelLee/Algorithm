/*
 Author : Ruel
 Problem : Baekjoon 31863번 내진 설계
 Problem address : https://www.acmicpc.net/problem/31863
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31863_내진설계;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 지도가 주어진다.
        // @ : 진원지
        // . : 일반 도로
        // * : 내진 설계가 안된 건물
        // # : 내진 설계가 된 건물
        // | : 방파제
        // 지진이 발생하여 진원지로부터 상하좌우 2칸으로 본진이 뻗어나간다.
        // 내진 설계가 되지 않은 건물은 한번의 지진에 무너지며, 상하좌우 한 칸으로 여진을 만들어낸다.
        // 내진 설계가 되어있는 건물은 두번째 지진에 무너지며, 마찬가지로 여진을 발생시킨다.
        // 지진은 방파제로 막혀있으면 더 이상 진행하지 못한다.
        // 최종적으로 무너진 건물의 수와 무너지지 않은 건물의 수를 공백으로 구분하여 출력한다
        //
        // 시뮬레이션, BFS 문제
        // 문제 그대로 주어진 조건에 따라 프로그래밍하면 된다.
        // 본진에 의해서는 내진 설계가 되지 않은 건물들이 무너지며
        // 여진에 의해서 내진 설계가 되지 않은 건물 + 내진 설계가 된 건물들도 무너질 수 있다.
        // 내진 설계가 된 건물들에 한해서 이번이 첫번째 지진인지, 두번째 지진인지를 구분할 수 있어야한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 지도
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        char[][] map = new char[n][m];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 진원지
        int start = 0;
        // 건물의 수
        int structures = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case '@' -> start = i * m + j;
                    case '*' -> structures++;
                    case '#' -> structures++;
                }
            }
        }
        
        // 건물이 무너지면 큐에 담아 여진을 발생
        Queue<Integer> queue = new LinkedList<>();
        // 내진 설계가 된 건물인 경우, 지진을 한차례 이미 겪었는지 표시한다.
        boolean[][] damaged = new boolean[n][m];
        // 붕괴한 건물의 수
        int collapsed = 0;
        
        // 본진
        // 네 방향으로
        for (int d = 0; d < 4; d++) {
            // 두칸씩까지 뻗어나갈 수 있다.
            for (int i = 1; i <= 2; i++) {
                int nextR = start / m + dr[d] * i;
                int nextC = start % m + dc[d] * i;

                // 맵 범위를 벗어나지 않으며
                if (checkArea(nextR, nextC, map)) {
                    // 방파제라면 더 이상 진행하지 않는다.
                    if (map[nextR][nextC] == '|')
                        break;

                    // 내진 설계가 안된 건물일 경우 바로 무너진다.
                    if (map[nextR][nextC] == '*') {
                        collapsed++;
                        map[nextR][nextC] = '.';
                        queue.offer(nextR * m + nextC);
                    } else if (map[nextR][nextC] == '#')        // 내진 설계 건물이라면 한차례 데미지만 받는다.
                        damaged[nextR][nextC] = true;
                }
            }
        }
        
        // 여진
        while (!queue.isEmpty()) {
            // 현재 위치
            int current = queue.poll();
            int row = current / m;
            int col = current % m;
            
            // 네 방향으로 한 칸씩
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];

                // 맵 범위를 벗어나지 않으며
                if (checkArea(nextR, nextC, map)) {
                    // 내진 설계가 안된 건물일 경우
                    // 무너진다.
                    if (map[nextR][nextC] == '*') {
                        collapsed++;
                        map[nextR][nextC] = '.';
                        queue.offer(nextR * m + nextC);
                    } else if (map[nextR][nextC] == '#') {      // 내진 설계가 된 건물일 경우
                        // 이미 한차례 데미지를 받았다면 무너지고
                        if (damaged[nextR][nextC]) {
                            collapsed++;
                            map[nextR][nextC] = '.';
                            queue.offer(nextR * m + nextC);
                        } else      // 그렇지 않으면 데미지만 받는다.
                            damaged[nextR][nextC] = true;
                    }
                }
            }
        }

        // 무너진 건물과 무너지지 않은 건물의 수를 출력한다.
        System.out.println(collapsed + " " + (structures - collapsed));
    }
    
    // 맵 범위 체크
    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}