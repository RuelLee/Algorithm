/*
 Author : Ruel
 Problem : Baekjoon 15730번 수영장 사장님
 Problem address : https://www.acmicpc.net/problem/15730
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15730_수영장사장님;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, m;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * m 크기의 격자가 주어진다.
        // 각 격자의 높이가 주어진다.
        // 사방이 현재 위치보다 높이가 높다면, 사방의 제일 낮은 높이만큼까지 해당 격자에 물을 채울 수 있다.
        // 전체 공간에 물을 얼마나 채울 수 있는가?
        //
        // BFS 문제
        // 안에서부터 세면 어려운 문제이므로
        // 밖에서부터 높이를 차근차근 높여가며 내부에 물이 들어오지 않은 공간의 수를 세어 누적시켜준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 공간
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n + 2][m + 2];
        // 최대 격자의 높이
        int maxHeight = 0;
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= m; j++)
                maxHeight = Math.max(maxHeight, map[i][j] = Integer.parseInt(st.nextToken()));
        }

        // 라이브러리에 정의된 큐를 사용할 경우, 메모리 초과가 나므로
        // int 배열을 통해 간단하게 큐를 만들어 사용
        int[] queue = new int[(n + 2) * (m + 2)];
        // 누적된 물의 양
        int sum = 0;
        // 행 하나의 길이
        int rowLength = m + 2;
        int left, right;
        // 1부터 maxHeight까지 수위를 바깥에서부터 높여나간다.
        for (int i = 1; i <= maxHeight; i++) {
            // 바깥 중 한 공간에 수위 i 지정
            map[0][0] = i;
            // 큐에 추가
            queue[0] = 0;
            left = 0;
            right = 1;
            // 큐가 빌 때까지
            while (left < right) {
                // 사방 탐색
                for (int d = 0; d < 4; d++) {
                    int nextR = queue[left] / rowLength + dr[d];
                    int nextC = queue[left] % rowLength + dc[d];

                    // 수위보다 낮은 격자를 발견하면, 해당 격자를 수위만큼까지 높이를 채운다.
                    // 그리고 큐에 추가
                    if (checkArea(nextR, nextC) && map[nextR][nextC] < i ) {
                        map[nextR][nextC] = i;
                        queue[right++] = nextR * rowLength + nextC;
                    }
                }
                // 다음으로 넘어감
                left++;
            }

            // 원래 n * m 공간에서 현재 수위보다 낮은 공간이 있는지 세어 누적시킨다.
            for (int r = 1; r <= n; r++) {
                for (int c = 1; c <= m; c++) {
                    if (map[r][c] < i)
                        sum++;
                }
            }
        }
        // 답 출력
        System.out.println(sum);
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n + 2 && c >= 0 && c < m + 2;
    }
}