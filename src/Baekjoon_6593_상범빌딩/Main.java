/*
 Author : Ruel
 Problem : Baekjoon 6593번 상범 빌딩
 Problem address : https://www.acmicpc.net/problem/6593
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6593_상범빌딩;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class State {
    int f;
    int r;
    int c;

    public State(int f, int r, int c) {
        this.f = f;
        this.r = r;
        this.c = c;
    }
}

public class Main {
    // 층 수도 고려해야하므로 변동량 delta값들이
    // 3개의 그룹이 필요하다.
    static int[] df = {0, 0, 0, 0, -1, 1};
    static int[] dr = {-1, 0, 1, 0, 0, 0};
    static int[] dc = {0, 1, 0, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        // L층, R행, C열의 빌딩이 주어진다.
        // 'S' 위치에서 'E'의 위치로 탈출하고자 한다.
        // 이동을 할 때, 각 위치에서 동 서 남 북 상 하로만 이동이 가능하다고 한다.
        // '#'은 벽, '.'은 빈공간일 때, 탈출하는데 걸리는 최소 시간은?
        //
        // 그래프 탐색 문제
        // 일반적인 BFS문제이나 좌표계가 3차원이라는 점만 다르다.
        // 층수를 고려하며 BFS를 돌려주자!
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        String input = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (input != null) {
            StringTokenizer st = new StringTokenizer(input);
            int L = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());
            
            // 주어지는 값이 모두 0이라면 종료
            if (L == 0 && R == 0 && C == 0)
                break;
            
            // 각 층에 따른 도면들이 주어진다.
            char[][][] building = new char[L][R][];
            for (int i = 0; i < building.length; i++) {
                for (int j = 0; j < building[i].length; j++)
                    building[i][j] = br.readLine().toCharArray();
                br.readLine();
            }
            
            // 출발 위치
            State start = null;
            // 도착 위치
            State end = null;
            for (int i = 0; i < building.length; i++) {
                for (int j = 0; j < building[i].length; j++) {
                    for (int k = 0; k < building[i][j].length; k++) {
                        if (building[i][j][k] == 'S')
                            start = new State(i, j, k);
                        else if (building[i][j][k] == 'E')
                            end = new State(i, j, k);
                    }
                }
            }
            
            // 각 지점에 도달하는데 드는 최소 시간
            int[][][] minTimes = new int[L][R][C];
            for (int[][] mt : minTimes) {
                for (int[] m : mt)
                    Arrays.fill(m, Integer.MAX_VALUE);
            }
            // 시작 위치의 초기값
            minTimes[start.f][start.r][start.c] = 0;
            
            // BFS 탐색
            Queue<State> queue = new LinkedList<>();
            queue.offer(start);
            while (!queue.isEmpty()) {
                State current = queue.poll();
                // 도착지점에 도달했다면 종료
                if (current.f == end.f && current.r == end.r && current.c == end.c)
                    break;

                // 6방향 탐색을 한다.
                for (int d = 0; d < 6; d++) {
                    int nextF = current.f + df[d];
                    int nextR = current.r + dr[d];
                    int nextC = current.c + dc[d];
                    
                    // 다음 위치가 빌딩의 범위를 벗어나지 않으며
                    // 벽이 아니고
                    // 도달하는 최소 시간을 갱신한다면
                    if (checkArea(nextF, nextR, nextC, building) &&
                            building[nextF][nextR][nextC] != '#' &&
                            minTimes[nextF][nextR][nextC] > minTimes[current.f][current.r][current.c] + 1) {
                        // 값 갱신 후, 큐에 추가
                        minTimes[nextF][nextR][nextC] = minTimes[current.f][current.r][current.c] + 1;
                        queue.offer(new State(nextF, nextR, nextC));
                    }
                }
            }

            // 도착 지점의 최소 도달 시간이 초기값이라면 불가능한 경우.
            if (minTimes[end.f][end.r][end.c] == Integer.MAX_VALUE)
                sb.append("Trapped!").append("\n");
            // 값이 바뀌었다면 해당 지점에 해당 시간에 도착한 경우.
            else
                sb.append("Escaped in ").append(minTimes[end.f][end.r][end.c]).append(" minute(s).").append("\n");

            // 다음 테스트케이스의 L, R, C 값의 문자열을 읽는다.
            input = br.readLine();
        }
        // 전체 답안 출력.
        System.out.println(sb);
    }

    static boolean checkArea(int f, int r, int c, char[][][] building) {
        return f >= 0 && f < building.length && r >= 0 && r < building[f].length && c >= 0 && c < building[f][r].length;
    }
}