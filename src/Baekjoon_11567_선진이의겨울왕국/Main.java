/*
 Author : Ruel
 Problem : Baekjoon 11567번 선진이의 겨울 왕국
 Problem address : https://www.acmicpc.net/problem/11567
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11567_선진이의겨울왕국;

import java.io.*;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int n, m;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 빙판길이 주어진다.
        // 각 칸은 갈 수 없는 X와 갈 수 있는 .으로 이루어져있다.
        // .은 한 번 밟으면 X로 바뀐다.
        // 탈출구는 빙판 아래에 있어 .이라면 한 번 밟은 후 다시 해당 위치에 도달해야한다.
        // X라면 그대로 탈출할 수 있다.
        // 시작 위치와 탈출 위치가 주어진다.
        // 시작 위치는 X로 주어진다.
        // 탈출 할 수 있는가를 출력하라
        //
        // BFS 문제
        // 인데 탈출구에 대한 조건을 조금 생각해야하는 문제
        // 일반적인 칸이라면 X에 도달할 수 없지만, 탈출구인 경우에는 도달할 수 있다.
        // 또한 .이 탈출구인 경우, 탈출구에 도달한 경로의 .과는 다른 .이 하나 더 있어야 탈출할 수 있다.
        // 시작점 옆에 바로 탈출구인 경우도 고려해야한다. 이 경우 도달한 경로의 .이 아닌 X가 되게 된다.
        // 여러 예외 조건들이 있어 한번에 맞추긴 힘든 문제
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 공간
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        char[][] map = new char[n][];
        for (int i = 0; i < map.length; i++)
            map[i] = br.readLine().toCharArray();
        
        // 시작점과 도착점
        st = new StringTokenizer(br.readLine());
        int start = (Integer.parseInt(st.nextToken()) - 1) * m + Integer.parseInt(st.nextToken()) - 1;
        st = new StringTokenizer(br.readLine());
        int end = (Integer.parseInt(st.nextToken()) - 1) * m + Integer.parseInt(st.nextToken()) - 1;
        
        // 방문 체크
        boolean[][] visited = new boolean[n][m];
        Queue<Integer> queue = new LinkedList<>();
        // 시작점
        queue.offer(start);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            // 4방 탐색
            for (int d = 0; d < 4; d++) {
                int nextR = current / m + dr[d];
                int nextC = current % m + dc[d];
                
                // 다음 칸이 범위 내이고, 미방문이고, .이거나 X인 탈출구인 경우
                // 방문
                if (checkArea(nextR, nextC) && !visited[nextR][nextC] &&
                        (map[nextR][nextC] == '.' || (nextR == end / m && nextC == end % m))) {
                    queue.offer(nextR * m + nextC);
                    visited[nextR][nextC] = true;
                }
            }
        }

        // 탈출구 인근의 '.'을 센다.
        // 예외적으로 시작점 X가 탈출구에 붙어있을 경우 센다.
        int count = 0;
        for (int d = 0; d < 4; d++) {
            int nearR = end / m + dr[d];
            int nearC = end % m + dc[d];

            if (checkArea(nearR, nearC) && (map[nearR][nearC] == '.' || (nearR == start / m && nearC == start % m)))
                count++;
        }
        
        // 탈출구까지 도달이 가능하고
        // 탈출구가 X이거나 경로를 제외한 인근에 .이 하나 더 있는 경우
        // 탈출 가능
        if (visited[end / m][end % m] && (map[end / m][end % m] == 'X' || count > 1))
            System.out.println("YES");
        else        // 그 외는 탈출 불가
            System.out.println("NO");
    }
    
    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}