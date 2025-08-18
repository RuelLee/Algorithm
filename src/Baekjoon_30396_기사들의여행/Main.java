/*
 Author : Ruel
 Problem : Baekjoon 30396번 기사들의 여행
 Problem address : https://www.acmicpc.net/problem/30396
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_30396_기사들의여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    // 4 * 4 크기의 체스 판이 주어진다.
    // 말은 나이트 뿐이고, 현재 배치와 원하는 배치가 주어진다.
    // 최소 몇 번의 움직임을 통해 원하는 배치로 바꿀 수 있는가?
    //
    // 비트마스킹, BFS
    // 4 * 4로 별로 크지 않은 크기이므로 비트마스크를 통해 상태를 하나의 숫자로 표현할 수 있다.
    
    // 현재 배치 상태
    static int[][] map;
    // 나이트들의 움직임
    static int[] dr = {-2, -2, -1, -1, 1, 1, 2, 2};
    static int[] dc = {-1, 1, -2, 2, -2, 2, -1, 1};
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 처음 배치와 원하는 배치를 bit로 저장
        int[] startEnd = new int[2];
        for (int i = 0; i < startEnd.length; i++) {
            for (int j = 0; j < 4; j++) {
                String input = br.readLine();
                for (int k = 0; k < 4; k++) {
                    if (input.charAt(k) == '1')
                        startEnd[i] |= (1 << (j * 4 + k));
                }
            }
        }
        map = new int[4][4];
        
        // dp[비트] = 말을 움직인 최소 횟수
        int[] dp = new int[1 << 17];
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 처음 상태
        dp[startEnd[0]] = 0;

        // BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startEnd[0]);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            // 현재 상태를 맵에 표시
            bitToMap(current);
            
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    // 나이트가 존재하는 칸인 경우
                    if (map[i][j] == 1) {
                        // 해당 나이트를 지우고, 다른 움직일 수 있는 칸을 찾는다.
                        map[i][j] = 0;
                        for (int d = 0; d < 8; d++) {
                            int nextR = i + dr[d];
                            int nextC = j + dc[d];
                            
                            // 맵 범위를 벗어나지 않고, 빈 칸인 경우
                            if (checkArea(nextR, nextC) && map[nextR][nextC] == 0) {
                                // 해당 칸에 나이트 배치
                                map[nextR][nextC] = 1;
                                // 그 때의 상태를 비트로 변환
                                int bit = mapToBit();
                                // dp[bit]의 값이 dp[current] + 1보다 크다면
                                // 현재 상태에서 나이트를 이동하는 것이 최소 이동인 경우
                                // 값 갱신 후, 큐에 추가
                                if (dp[bit] > dp[current] + 1) {
                                    dp[bit] = dp[current] + 1;
                                    queue.offer(bit);
                                }
                                // 배치했던 나이트 회수
                                map[nextR][nextC] = 0;
                            }
                        }
                        // 원래 상태의 나이트 복구
                        map[i][j] = 1;
                    }
                }
            }
        }
        // 원하는 상태일 때의 최소 이동 횟수 출력
        System.out.println(dp[startEnd[1]]);
    }
    
    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < 4 && c >= 0 && c < 4;
    }
    
    // 현재 체스 판의 상태를 비트로 변환
    static int mapToBit() {
        int bit = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++)
                bit |= (map[i][j] << (i * 4 + j));
        }
        return bit;
    }
    
    // 비트를 통해 체스 판의 나이트 배치
    static void bitToMap(int bitmask) {
        for (int i = 0; i < 16; i++)
            map[i / 4][i % 4] = ((bitmask & (1 << i)) != 0 ? 1 : 0);
    }
}