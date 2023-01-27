/*
 Author : Ruel
 Problem : Baekjoon 17822번 원판 돌리기
 Problem address : https://www.acmicpc.net/problem/17822
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17822_원판돌리기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 반지름이 1, 2, ..., n인 원판들이 크기가 큰 순으로 쌓여있다.
        // i번째 원판에 적힌 j번째 수를 (i, j)라 할 때
        // 인접한 원판의 같은 순서 혹은, 같은 원판의 좌우를 서로 인접해있다고 한다.
        // x d k를 활용하여 원판을 회전시키려고 한다.
        // x의 배수인 원판을, d방향(시계 0, 반시계 -1)으로 k칸 회전시킨다.
        // 그 후, 각 원판을 살펴보며 인접한 수들 사이에 중복된 수가 존재한다면 해당 수를 지우고
        // 인접한 수가 생기지 않는다면 원판들에 남아있는 수들 중 평균보다 큰 값은 -1, 작은 값은 +1을 한다.
        // t번 회전할 때 남아있는 수들의 합은?
        //
        // 구현 문제
        // 원판을 '회전' 시킨다고 하였는데, 실제로 배열을 회전시킬 필요는 없는 문제
        // 원판에 놓이는 수의 개수를 알고 있으므로, 원판을 회전시킬 경우 생기는 위치 변동값만 계산해두고
        // 실제로 배열의 수를 옮기지말고, 활용하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 각 원판들에 대한 입력
        int[][] circles = new int[n][];
        for (int i = 0; i < circles.length; i++)
            circles[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 원판이 몇 칸 움직였는지에 대한 변동값들.
        int[] diffs = new int[n];
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            
            // x 배수의 원판들에 대해
            // 시계 방향일 경우, m - k만큼
            // 반시계 방향일 경우 k 만큼 변동값을 주고, 모듈러 연산.
            // 시계 방향으로 회전할 경우, 원래 위치가 0인 값은 1로 이동을 한다.
            // 다시 말해, 회전 후의 1인 위치의 값에 참조하려면 1 + (-1)을 해줘야한다.
            // 따라서 시계방향일 경우, 변동값이 음으로, 반시계일 경우 양으로 적용한다.
            for (int j = 1; j * x - 1 < n; j++) {
                diffs[j * x - 1] += (d == 0 ? m - k : k);
                diffs[j * x - 1] %= m;
            }

            // 각 원판의 수들을 방문하며 인접한 같은 수가 존재하는지 확인한다.
            boolean[][] visited = new boolean[n][m];
            boolean foundSame = false;
            for (int r = 0; r < circles.length; r++) {
                for (int c = 0; c < circles[r].length; c++) {
                    // 방문하지 않았고, 수가 남아있을 경우에만
                    if (!visited[r][c] && circles[r][c] != 0) {
                        // 방문 체크
                        visited[r][c] = true;
                        // BFS를 통해 인접한 수들 중 같은 값을 갖는 수가 있는지 확인한다.
                        Queue<Integer> queue = new LinkedList<>();
                        queue.offer(r * m + c);
                        // 원래 값 복사.
                        int value = circles[r][c];
                        while (!queue.isEmpty()) {
                            int current = queue.poll();
                            // 현재 위치
                            int currentR = current / m;
                            int currentC = current % m;
                            
                            // 4방 탐색
                            for (int delta = 0; delta < 4; delta++) {
                                // 다음 원판.
                                int nextR = currentR + dr[delta];
                                // 존재하지 않는 원판이라면 건너뛴다.
                                if (nextR < 0 || nextR >= circles.length)
                                    continue;
                                // 수의 위치를 계산한다.
                                // 만약 동일한 원핀이라면 변동값에 변화를 줄 필요없다.
                                // 하지만 다른 원판이라면 서로 원판값의 차이만큼을 적용시켜줘야한다.
                                // 0을 기준으로, 현재 원판의 변동값을 빼주고, 다음 원판의 변동값을 더해주자.
                                // 물론 원판이므로 0과 m-1번째 수는 서로 맞닿아있음을 유의하면서 모듈러 연산을 해준다.
                                int nextC = (currentC + dc[delta] + (currentR == nextR ? 0 : diffs[nextR] - diffs[currentR]) + m) % m;
                                
                                // 다음 위치가 방문하지 않았고, 같은 값을 갖고 있다면
                                if (!visited[nextR][nextC] && value == circles[nextR][nextC]) {
                                    // 같은 값을 갖는 수를 발견했음 체크
                                    foundSame = true;
                                    // 방문 체크
                                    visited[nextR][nextC] = true;
                                    // 큐 추가
                                    queue.offer(nextR * m + nextC);
                                    // 수 제거
                                    circles[currentR][currentC] = circles[nextR][nextC] = 0;
                                }
                            }
                        }
                    }
                }
            }
            // 만약 회전 후, 같은 값을 갖는 수들을 발견하지 못했다면
            if (!foundSame) {
                int sum = 0;
                int count = 0;
                for (int[] circle : circles) {
                    sum += Arrays.stream(circle).sum();
                    count += Arrays.stream(circle).filter(value -> value != 0).count();
                }
                // 남아있는 수들의 평균
                double average = (double) sum / count;

                // 모든 수를 확인하며 평균보다 큰 값은 -1
                // 작은 값은 +1을 해준다.
                for (int r = 0; r < circles.length; r++) {
                    for (int c = 0; c < circles[r].length; c++) {
                        if (circles[r][c] != 0)
                            circles[r][c] += (circles[r][c] == average ? 0 : (circles[r][c] > average ? -1 : 1));
                    }
                }
            }
        }

        // 최종적으로 원판들에 남아있는 모든 수들의 합을 출력한다.
        int answer = 0;
        for (int[] ci : circles)
            answer += Arrays.stream(ci).sum();
        System.out.println(answer);
    }
}