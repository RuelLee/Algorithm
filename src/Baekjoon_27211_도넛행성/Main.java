/*
 Author : Ruel
 Problem : Baekjoon 27211번 도넛 행성
 Problem address : https://www.acmicpc.net/problem/27211
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27211_도넛행성;

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
        // n * m 칸으로 이루어진 도넛 모양 행성이 주어진다.
        // 도넛 모양이므로, 1행과 n행은 서로 이어져있고, 1열과 m열이 이어져있다.
        // 숲은 1, 비어있는 칸은 0으로 주어질 때, 탐험할 수 있는 구역의 개수는 몇 개인가?
        //
        // 그래프 탐색 문제
        // 행과 열의 끝이 각각 맞닿아있음을 유의하면서 그래프 풀면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n행 m열
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 행성에 숲으로 막힌 공간과 빈 공간.
        int[][] donut = new int[n][];
        for (int i = 0; i < donut.length; i++)
            donut[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 탐험할 수 있는 구역의 개수
        int count = 0;
        int[][] group = new int[n][m];
        for (int i = 0; i < donut.length; i++) {
            for (int j = 0; j < donut[i].length; j++) {
                // i, j구역이 숲이거나 이미 구역으로 편성이 되어있다면 건너뛴다.
                if (donut[i][j] == 1 || group[i][j] > 0)
                    continue;

                // 그렇지 않다면 새로운 구역을 발견한 것.
                // 해당 칸에서 탐색을 한다.
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(i * m + j);
                // 새로운 그룹으로 편성.
                group[i][j] = ++count;
                while (!queue.isEmpty()) {
                    int row = queue.peek() / m;
                    int col = queue.poll() % m;
                    
                    // 4방 탐색
                    for (int d = 0; d < 4; d++) {
                        // 행의 끝이 맞닿아있으므로, -1일 경우, n - 1과 같은 곳이다.
                        // 이 점을 보정해주기 위해 n을 더한 후, 모듈러 n을 통해 위치를 교정해준다.
                        int nextR = (row + dr[d] + n) % n;
                        // 열도 마찬가지.
                        int nextC = (col + dc[d] + m) % m;
                        
                        // 인근 칸이 숲이 아니고, 그룹 배정이 되지 않은 곳이라면
                        if (donut[nextR][nextC] == 0 && group[nextR][nextC] == 0) {
                            // 그룹 배정
                            group[nextR][nextC] = count;
                            // 큐 추가
                            queue.offer(nextR * m + nextC);
                        }
                    }
                }
            }
        }
        
        // 탐험할 수 있는 구역의 수 출력.
        System.out.println(count);
    }
}