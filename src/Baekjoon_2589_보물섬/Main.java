/*
 Author : Ruel
 Problem : Baekjoon 2589번 보물섬
 Problem address : https://www.acmicpc.net/problem/2589
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2589_보물섬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // h * w 크기의 지도가 주어진다.
        // 지도의 각 격자는 W, L로 주어지며, 바다 혹은 육지를 나타낸다.
        // 보물은 서로 간에 최단 거리로 이동하는데 있어 가장 긴 시간이 걸리는 육지 두 곳에 나뉘어 묻혀있다.
        // 보물 지도가 주어질 때, 보물이 묻혀있는 두 곳 간의 최단 거리를 이동하는 시간을 구하라
        //
        // 브루트 포스, BFS 문제
        // 모든 L에 대해서 도달할 수 있는 가장 먼 거리를 구해낸다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // h * w 크기의 보물 지도
        int h = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        
        // 지도 정보
        char[][] map = new char[h][];
        for (int i = 0; i < h; i++)
            map[i] = br.readLine().toCharArray();
        // 가장 먼 두 곳의 거리
        int maxLength = 0;
        int[][] distances = new int[h][w];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 육지일 경우
                if (map[i][j] == 'L') {
                    // 최소 거리 초기화
                    for (int[] d : distances)
                        Arrays.fill(d, Integer.MAX_VALUE);
                    distances[i][j] = 0;
                    
                    // BFS 탐색
                    Queue<Integer> queue = new LinkedList<>();
                    queue.offer(i * w + j);
                    while (!queue.isEmpty()) {
                        int current = queue.poll();
                        int row = current / w;
                        int col = current % w;
                        
                        // 사방탐색
                        for (int d = 0; d < 4; d++) {
                            int nextR = row + dr[d];
                            int nextC = col + dc[d];
                            
                            // 맵의 범위를 벗어나지 않고, 육지이며, 최단 거리를 갱신한다면
                            if (checkArea(nextR, nextC, h, w) && map[nextR][nextC] == 'L' &&
                                    distances[nextR][nextC] > distances[row][col] + 1) {
                                // 거리 계산
                                distances[nextR][nextC] = distances[row][col] + 1;
                                // 최단 거리가 여태까지 중 발견한 가장 먼 거리인지 확인
                                maxLength = Math.max(maxLength, distances[nextR][nextC]);
                                // 큐에 추가
                                queue.offer(nextR * w + nextC);
                            }
                        }
                    }
                }
            }
        }
        // 가장 먼 두 곳의 거리 출력
        System.out.println(maxLength);
    }
    
    // 맵 범위 체크
    static boolean checkArea(int r, int c, int h, int w) {
        return r >= 0 && r < h && c >= 0 && c < w;
    }
}