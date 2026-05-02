/*
 Author : Ruel
 Problem : Jungol 1134번 미로
 Problem address : https://jungol.co.kr/problem/1134
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1134_미로;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static char[][] map = new char[500][500];
    static int[][] minBreaks = new int[500][500];
    static int n, m;

    public static void main(String[] args) throws IOException {
        // m * n 크기의 격자칸이 주어진다
        // 시작 위치가 S, 도착 위치는 T로 주어지며, 빈 공간은 . 벽은 *으로 주어진다.
        // 최대 k번 벽을 뚫을 수 있을 때, S에서 T로 이동이 가능한가?
        //
        // 최단 거리 문제
        // dp[row][col] = 현재 위치까지 도달하기 위해 뚫은 최소 벽의 개수
        // 로 탐색을 해나가며 값을 채운다.
        // 최종 위치에서 이 값이 k이하이면 가능, 아니면 불가능

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int testCase = 0; testCase < t; testCase++) {
            int k = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            // m * n 크기의 격자
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());

            // 시작, 도달 위치
            int[] start = new int[2];
            int[] end = new int[2];
            // 맵 입력
            for (int i = 0; i < n; i++) {
                String input = br.readLine().replace(" ", "");
                for (int j = 0; j < m; j++) {
                    map[i][j] = input.charAt(j);
                    if (map[i][j] == 'S') {
                        start[0] = i;
                        start[1] = j;
                    } else if (map[i][j] == 'T') {
                        end[0] = i;
                        end[1] = j;
                    }
                }
            }

            // 초기화
            clear();
            minBreaks[start[0]][start[1]] = 0;
            // 시작 위치에서 탐색 시작
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
            pq.offer(new int[]{start[0], start[1], 0});
            while (!pq.isEmpty()) {
                int[] current = pq.poll();
                // 이미 더 적은 횟수로 도달한 경우, 건너뜀
                if (minBreaks[current[0]][current[1]] < current[2])
                    continue;

                // 사방탐색
                for (int d = 0; d < 4; d++) {
                    int nextR = current[0] + dr[d];
                    int nextC = current[1] + dc[d];

                    // 맵 범위 안이고
                    if (checkArea(nextR, nextC)) {
                        // 벽이라면 +1회, 빈 공간이면 그대로
                        int nextBreak = (map[nextR][nextC] == '*' ? 1 : 0) + minBreaks[current[0]][current[1]];
                        // 최소 벽 뚫기 횟수를 갱신하는지 확인
                        if (minBreaks[nextR][nextC] > nextBreak) {
                            // 그럴 경우 값 갱신 및 우선순위큐에 추가
                            minBreaks[nextR][nextC] = nextBreak;
                            pq.offer(new int[]{nextR, nextC, nextBreak});
                        }
                    }
                }
            }
            // 최종 위치에서 벽을 뚫은 횟수가 k번 이하인지 확인하고 기록
            sb.append(minBreaks[end[0]][end[1]] <= k ? "y" : "n").append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // 범위 체크
    static boolean checkArea(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m;
    }

    // minBreaks 값 초기화
    static void clear() {
        for (int[] mb : minBreaks)
            Arrays.fill(mb, 0, m, Integer.MAX_VALUE);
    }
}