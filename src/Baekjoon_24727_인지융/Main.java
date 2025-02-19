/*
 Author : Ruel
 Problem : Baekjoon 24727번 인지융~
 Problem address : https://www.acmicpc.net/problem/24727
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24727_인지융;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Seek {
    int r;
    int c;
    int area;

    public Seek(int r, int c, int area) {
        this.r = r;
        this.c = c;
        this.area = area;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 공간이 주어진다.
        // 위 공간을 컴퓨터과학과 다른 공과대학이 각각 공간을 갖고자 한다.
        // 컴퓨터과학은 c개, 다른 공과대학은 e개의 공간을 할당받으며, 각 공간들은 서로 인접해있다.
        // 컴퓨터과학과 다른 공과대학의 공간은 바리케이드로 분리되어있다.
        // 다시 말해, 컴퓨터과학과 공과대학의 공간은 각각은 서로 상하좌우로 인접해있되,
        // 두 영역은 서로 인접하지 않는다.
        // 그렇게 배치하는 것이 가능하다면 1 출력 후, 그러한 경우중 하나를 출력
        // 불가능하다면 -1을 출력한다
        //
        // BFS 문제
        // 조금 생각할 것이 있지만 구현은 어렵지 않은 문제
        // 바리케이드로 두 구역을 분할하는 바리케이드가 차지하는 영역은 최소가 되는 것이 좋다.
        // 최대한으로 컴퓨터과학과 공과대학의 영역이 배치될 때, 바리케이드가 차지하는 형태는 대각선 형태가 된다.
        // 따라서 각각의 영역을 대각선 끝점으로 설정한 후, BFS를 해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n * n 크기의 영역
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 컴퓨터과학과 공과대학의 영역 c, e
        int c = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        // c와 e
        int[] nums = new int[]{0, c, e};
        // 현재 배치된 공간의 개수
        int[] counts = new int[]{0, 0, 0};
        int[][] map = new int[n][n];
        // BFS
        Queue<Seek> queue = new LinkedList<>();
        queue.offer(new Seek(0, 0, 1));
        queue.offer(new Seek(n - 1, n - 1, 2));
        while (!queue.isEmpty()) {
            Seek current = queue.poll();
            // 현 위치가 0이 아니거나, 할당된 영역의 개수를 모두 채웠다면 건너뛴다.
            if (map[current.r][current.c] != 0 || counts[current.area] == nums[current.area])
                continue;

            boolean canAllocate = true;
            // 사방을 살펴보고, 현 위치를 current.area 공간으로 설정할 수 있는지 살펴본다.
            for (int d = 0; d < 4; d++) {
                int nearR = current.r + dr[d];
                int nearC = current.c + dc[d];

                if (checkArea(nearR, nearC, n) && map[nearR][nearC] != 0 && map[nearR][nearC] != current.area) {
                    canAllocate = false;
                    break;
                }
            }

            // 설정할 수 있는 경우
            if (canAllocate) {
                // 설정하고, 카운터 증가.
                map[current.r][current.c] = current.area;
                counts[current.area]++;

                // 아직 할당해야할 공간이 더 남았다면 사방을 탐색하여
                // 0인 공간을 후보로 BFS에 담는다.
                if (counts[current.area] < nums[current.area]) {
                    for (int d = 0; d < 4; d++) {
                        int nextR = current.r + dr[d];
                        int nextC = current.c + dc[d];

                        if (checkArea(nextR, nextC, n) && map[nextR][nextC] == 0)
                            queue.offer(new Seek(nextR, nextC, current.area));
                    }
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        // 할당된 공간을 모두 채웠을 경우 가능한 경우
        if (counts[1] == nums[1] && counts[2] == nums[2]) {
            // 1 기록 후
            sb.append(1).append("\n");
            // 해당하는 공간의 형태 기록
            for (int[] m : map) {
                for (int g : m)
                    sb.append(g);
                sb.append("\n");
            }
        } else      // 불가능한 경우, -1 기록
            sb.append(-1).append("\n");
        // 답 출력
        System.out.print(sb);
    }

    static boolean checkArea(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}