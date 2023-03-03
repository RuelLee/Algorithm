/*
 Author : Ruel
 Problem : Baekjoon 16469번 소년 점프
 Problem address : https://www.acmicpc.net/problem/16469
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16469_소년점프;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static final int MAX = 100 * 100 + 1;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 마미손은 악당 무리에게서 도망치고 있다.
        // r * c 크기의 미로가 주어지며, 세 악당들의 위치가 주어진다.
        // 악당들은 상하좌우로만 움직일 수 있으며, 이동 속도는 같고, 칸 단위로만 이동 가능하다
        // 세 명이 한 지점에 모였을 때 걸린 시간이 최소가 되는 지점에 마미손이 숨어있다고 확신한다.
        // 그러한 지점에 모이는 시간과, 개수를 출력하라.
        // 없다면 -1을 출력한다
        //
        // BFS 문제
        // 각각 악당들에 대해 3개의 출발점을 갖고서 BFS를 돌린다.
        // 그 후, 세 개의 결과를 들고서 각 지점에 대해 모이는 시간이 가장 오래 걸린 사람의 시간이
        // 해당 지점에 세 명이 모이는데 걸리는 시간이 된다.
        // 이를 바탕으로 세 명이 모이는데 시간이 가장 적게 걸린 장소의 시간과 같은 시간을 갖는 지점의 개수를 세면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // r * c 크기의 미로
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        char[][] map = new char[r][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 각 악당들이 지점들을 방문하는데 걸리는 시간
        int[][][] villains = new int[3][][];
        for (int i = 0; i < villains.length; i++) {
            st = new StringTokenizer(br.readLine());
            // findMinTimes 메소드를 통해 결과를 받는다.
            villains[i] = findMinTimes(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, map);
        }

        // 한 지점에 모이는데 걸리는 시간.
        // 악당들 중 가장 늦게 모인 사람의 시간.
        int maxTime = Math.max(villains[0][0][0], Math.max(villains[1][0][0], villains[2][0][0]));
        // 그러한 지점의 개수
        int count = 1;
        // 모든 지점을 방문
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int time = 0;
                // 한 지점에서 세 명이 모이는데 걸리는 시간을 체크한다.
                for (int[][] villain : villains) time = Math.max(time, villain[i][j]);
                
                // 만약 이전 지점보다 모이는데 걸리는 시간이 더 적은 지점이 발견됐다면
                if (maxTime > time) {
                    // 카운트를 초기화하고 해당 시간 기록
                    count = 1;
                    maxTime = time;
                } else if (maxTime == time)     // 만약 같은 시간이라면 카운트 증가
                    count++;
            }
        }
        
        // 답안에 맞게 StringBuilder로 답안 작성
        StringBuilder sb = new StringBuilder();
        if (maxTime == MAX)
            sb.append(-1);
        else
            sb.append(maxTime).append("\n").append(count);
        // 답안 출력
        System.out.println(sb);
    }

    // BFS를 통해 해당 빌런이 각 지점들을 방문하는데 걸리는 시간을 계산한다.
    static int[][] findMinTimes(int r, int c, char[][] map) {
        // 지점들의 최소 시간
        int[][] minTimes = new int[map.length][map[0].length];
        // 초기화
        for (int[] mt : minTimes)
            Arrays.fill(mt, MAX);
        minTimes[r][c] = 0;
        
        // BFS 탐색
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(r * map[0].length + c);
        while (!queue.isEmpty()) {
            int row = queue.peek() / map[0].length;
            int col = queue.poll() % map[0].length;
            
            // 4방 탐색
            for (int d = 0; d < 4; d++) {
                int nextR = row + dr[d];
                int nextC = col + dc[d];
                
                // 미로 안이며
                // 벽이 아니고, 최소 시간을 갱신할 때만
                if (checkArea(nextR, nextC, map) &&
                        map[nextR][nextC] == '0' &&
                        minTimes[nextR][nextC] > minTimes[row][col] + 1) {
                    // 값 갱신 후, 큐 추가
                    minTimes[nextR][nextC] = minTimes[row][col] + 1;
                    queue.offer(nextR * map[0].length + nextC);
                }
            }
        }
        // 각 지점에 대한 최소 방문 시간 반환
        return minTimes;
    }
    
    // 미로 범위 체크
    static boolean checkArea(int r, int c, char[][] map) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}