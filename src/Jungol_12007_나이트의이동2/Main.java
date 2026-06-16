/*
 Author : Ruel
 Problem : Jungol 12007 나이트의 이동2
 Problem address : https://jungol.co.kr/problem/12007
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_12007_나이트의이동2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int[] dr = {-2, -2, -1, 1, 2, 2, -1, 1};
    static int[] dc = {-1, 1, 2, 2, -1, 1, -2, -2};
    static int n, m;

    public static void main(String[] args) throws IOException {
        // n * m 크기의 체스판이 주어질 때
        // x, y에 위치한 나이트가 정확히 k번 이동하여 도달할 수 없는 칸들의 개수를 출력하라
        //
        // BFS 문제
        // BFS로 풀되, 조금 트릭이 들어간다.
        // 나이트는 가로나 세로로 2칸, 그 뒤에 세로나 가로로 1칸 이동하는 것이 하나의 움직임이다.
        // 따라서 한 방향으로 길게 나아가더라도 k(최대 100)턴 동안 이동하는 건 최대 200칸이다.
        // 따라서 n, m이 아무리 크게 주어지더라도 x, y 근처 x-200 ~ x+200, y-200 ~ y+200 내에서 계산을 마칠 수 있다.
        // 따라서 판의 크기를 적절히 선언하고, 값을 보정해주면 된다.
        // 또한 이동의 특성 상 갔다면 반드시 돌아올 수도 있다.
        // 따라서 홀짝을 따져, 방문 체크를 초기화하지 않고 이전 결과를 사용해나갈 수도 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 판
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 시작 위치 0 indexed로 보정
        int x = Integer.parseInt(st.nextToken()) - 1;
        int y = Integer.parseInt(st.nextToken()) - 1;
        // 방문 체크 배열. 좌우상하 최대 이동할 수 있는 크기는 200
        boolean[][][] visited = new boolean[2][Math.min(200, x) + Math.min(200, n - x - 1) + 1][Math.min(200, y) + Math.min(200, m - y - 1) + 1];
        // x, y 좌표 보정
        x = Math.min(200, x);
        y = Math.min(200, y);
        // 원래 n, m값 저장
        int originalN = n;
        int originalM = m;
        // n, m의 크기도 보정
        n = visited[0].length;
        m = visited[0][0].length;

        // 최대 움직이는 턴의 수
        int k = Integer.parseInt(br.readLine());

        // 큐도 두 개 선언하여 홀짝으로 왔다갔다 거리며 BFS 탐색한다.
        Queue<Integer>[] queues = new Queue[2];
        queues[0] = new LinkedList<>();
        queues[1] = new LinkedList<>();

        // 처음 위치
        queues[0].offer(x * m + y);
        // turn만큼 진행
        for (int turn = 0; turn < k; turn++) {
            // 만약 더 이상 움직일 새로운 칸이 없다면 종료
            if (queues[turn % 2].isEmpty())
                break;

            // 큐가 빌 때까지
            while (!queues[turn % 2].isEmpty()) {
                int cur = queues[turn % 2].poll();
                // 현재 위치 r, c
                int r = cur / m;
                int c = cur % m;

                // 나이트의 움직임으로 가능한 다음 위치
                for (int d = 0; d < 8; d++) {
                    int nextR = r + dr[d];
                    int nextC = c + dc[d];

                    // 맵 범위를 안 벗어나고, 미방문 구역이라면
                    if (checkArea(nextR, nextC) && !visited[(turn + 1) % 2][nextR][nextC]) {
                        // 방문 체크 및 큐에 추가
                        visited[(turn + 1) % 2][nextR][nextC] = true;
                        queues[(turn + 1) % 2].offer(nextR * m + nextC);
                    }
                }
            }
        }

        // 정확히 k턴에 이동가능한 칸의 개수를 센다.
        int cnt = 0;
        for (int i = 0; i < visited[k % 2].length; i++) {
            for (int j = 0; j < visited[k % 2][i].length; j++) {
                if (visited[k % 2][i][j])
                    cnt++;
            }
        }
        // 원래 체스 판의 크기에서 이동 가능한 칸의 개수를 빼준 값을 출력
        System.out.println((long) originalN * originalM - cnt);
    }

    // 범위 체크
    static boolean checkArea(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < m;
    }
}