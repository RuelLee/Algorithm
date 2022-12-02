/*
 Author : Ruel
 Problem : Baekjoon 3190번 뱀
 Problem address : https://www.acmicpc.net/problem/3190
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3190_뱀;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Order {
    int time;
    char direction;

    public Order(int time, char direction) {
        this.time = time;
        this.direction = direction;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // 'Dummy' 라는 도스게임이 있다. 이 게임에는 뱀이 나와서 기어다니는데, 사과를 먹으면 뱀 길이가 늘어난다. 뱀이 이리저리 기어다니다가 벽 또는 자기자신의 몸과 부딪히면 게임이 끝난다.
        // 가로 세로 n 크기의 정사각 보드 위에서 진행된다.
        // k개의 사과 위치가 주어지며, l개의 방향 변환 정보가 주어질 때
        // 게임이 끝나는 시간을 출력하라
        //
        // 간단한 구현 문제
        // 사과를 먹으면 뱀의 길이가 길어지기 때문에 이를 처리하기 위해서 큐를 사용했다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 입력 정보
        int n = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());
        
        // 사과의 위치
        boolean[] apples = new boolean[n * n];
        for (int i = 0; i < k; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            apples[(Integer.parseInt(st.nextToken()) - 1) * n + Integer.parseInt(st.nextToken()) - 1] = true;
        }

        // l개의 방향 변환 정보.
        int l = Integer.parseInt(br.readLine());
        Queue<Order> orders = new LinkedList<>();
        for (int i = 0; i < l; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            orders.offer(new Order(Integer.parseInt(st.nextToken()), st.nextToken().charAt(0)));
        }

        // 시간
        int time = 0;
        // 현재 뱀이 보드에 위치한 정보.
        Queue<Integer> snake = new LinkedList<>();
        snake.offer(0);
        // 초기 방향은 동쪽.
        int direction = 1;
        // 머리의 위치.
        int headR = 0;
        int headC = 0;
        while (true) {
            // 시간 증가.
            time++;
            // 현재 바라보는 방향을 토대로 다음 위치 계산.
            int nextR = headR + dr[direction];
            int nextC = headC + dc[direction];
            int idx = nextR * n + nextC;

            // 다음 위치가 맵을 벗어나거나 자신의 몸통을 만난다면 종료.
            if (!checkArea(nextR, nextC, n) || snake.contains(idx))
                break;

            // 아니라면 머리를 다음 위치로 이동.
            headR = nextR;
            headC = nextC;
            // snake 큐에 추가.
            snake.offer(idx);
            // 만약 다음 위치에 사과가 있다면 먹고
            if (apples[idx])
                apples[idx] = false;
            // 없다면 길이가 증가하는 게 아닌 이동해야하므로, 큐에서 값을 하나 뺀다.
            else
                snake.poll();
            
            // 아직 남은 방향 전환 정보가 있고
            // 그게 지금 시간이라면 
            if (!orders.isEmpty() && time == orders.peek().time) {
                // L일 경우 반시계 방향으로 회전
                if (orders.peek().direction == 'L')
                    direction = (direction + 3) % 4;
                // D일 경우 시계 방향으로 회전
                else
                    direction = (direction + 1) % 4;
                // 방향 전환 정보 제거
                orders.poll();
            }
        }
        // 최종 종료 시간 출력.
        System.out.println(time);
    }

    // 뱀이 맵을 벗어나는지 확인
    static boolean checkArea(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}