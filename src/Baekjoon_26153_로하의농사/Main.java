/*
 Author : Ruel
 Problem : Baekjoon 26153번 로하의 농사
 Problem address : https://www.acmicpc.net/problem/26153
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26153_로하의농사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[][] map;

    public static void main(String[] args) throws IOException {
        // n * m 크기 격자의 마을이 주어진다.
        // 각 격자에는 땅에서 나오는 물의 양이 주어진다.
        // 로하가 위치한 x y 위치로부터 p개의 파이프를 사용하여 각 격자에 나오는 물들을 끌어오려한다.
        // 직선으로 연결할 때는 하나의 파이프, 곡선으로 연결할 경우에는 두 개의 파이프가 소모된다.
        // 끌어올 수 있는 최대 물의 양은?
        //
        // 브루트 포스, DFS, 백트래킹 문제
        // 방문한 위치를 표시해나가며, DFS를 통해 백트래킹하며 답을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 격자
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 격자마다 물의 양
        map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        // 로하의 위치와 파이프 길이
        int startR = Integer.parseInt(st.nextToken());
        int startC = Integer.parseInt(st.nextToken());
        int length = Integer.parseInt(st.nextToken());
        
        // 로하가 위치한 곳
        int answer = map[startR][startC];
        boolean[][] visited = new boolean[n][m];
        visited[startR][startC] = true;
        // 사방탐색
        for (int d = 0; d < 4; d++) {
            int nextR = startR + dr[d];
            int nextC = startC + dc[d];
            
            // 맵 범위를 벗어나지 않으며, 파이프가 1이상 남았을 때
            if (checkArea(nextR, nextC) && length >= 1) {
                // 방문 체크
                visited[nextR][nextC] = true;
                // nextR, nextC로 탐색했을 때 얻을 수 있는 최대값이 함수의 리턴값으로 돌아옴
                // 그 때의 값과 로하가 위치한 곳의 물의 양을 합쳐 answer에 최대값 비교
                answer = Math.max(answer, bruteForce(nextR, nextC, visited, d, length - 1) + map[startR][startC]);
                visited[nextR][nextC] = false;
            }
        }
        // 답 출력
        System.out.println(answer);
    }
    
    // DFS, 백트래킹
    static int bruteForce(int r, int c, boolean[][] visited, int preDirection, int remain) {
        // 더 이상 연결할 파이프가 없다면 현재 위치의 물의 양 리턴
        if (remain == 0)
            return map[r][c];
        
        // r, c에서 추가로 탐색해서 얻을 수 있는 최대 물의 양 계산
        int max = 0;
        for (int d = 0; d < 4; d++) {
            int nextR = r + dr[d];
            int nextC = c + dc[d];
            // 직선 파이프일 경우 1, 곡선일 경우 2 소모
            int pipeRequired = (d == preDirection ? 1 : 2);

            if (checkArea(nextR, nextC) && !visited[nextR][nextC] && remain >= pipeRequired) {
                visited[nextR][nextC] = true;
                max = Math.max(max, bruteForce(nextR, nextC, visited, d, remain - pipeRequired));
                visited[nextR][nextC] = false;
            }
        }
        // 추가 탐색으로 얻은 물과 현재 위치의 물의 양을 합하여 리턴
        return max + map[r][c];
    }
    
    // 맵 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < map.length && c >= 0 && c < map[r].length;
    }
}