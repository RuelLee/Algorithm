/*
 Author : Ruel
 Problem : Baekjoon 14891번 톱니바퀴
 Problem address : https://www.acmicpc.net/problem/14891
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14891_톱니바퀴;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class State {
    int gear;
    int direction;

    public State(int gear, int direction) {
        this.gear = gear;
        this.direction = direction;
    }
}

public class Main {
    static String[] gears;
    static int[] fronts, rears;

    public static void main(String[] args) throws IOException {
        // 연이어 있는 4개의 톱니바퀴가 주어진다.
        // 각 톱니바퀴에는 8개의 톱니가 있다.
        // 한 톱니바퀴를 회전시킬 때, 양 옆에 맞닿은 톱니바퀴의 톱니가 서로 다른 극을 갖고 있을 경우
        // 양 옆의 톱니바퀴는 현재 톱니바퀴와 반대로 회전을 하게 된다.
        // 각 톱니 바퀴들의 상태에 따라 점수가 주어진다.
        // 1번 톱니바퀴의 12시방향이 S극이면 1점
        // 2번 톱니바퀴의 12시방향이 S극이면 2점
        // 3번 톱니바퀴의 12시방향이 S극이면 4점
        // 4번 톱니바퀴의 12시방향이 S극이면 8점
        // k번 톱니를 회전시킨 후, 점수를 출력하라
        //
        // 시뮬레이션 문제
        // 각 톱니바퀴의 앞부분(9시), 뒷부분(3시)에 대한 포인터를 갖고
        // 회전을 시켰을 때에 따라 포인터들을 이동시킨다.
        // 그 후, 최종 상태에 대한 점수를 계산하여 출력한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 톱니 바퀴들의 상태
        gears = new String[4];
        for (int i = 0; i < gears.length; i++)
            gears[i] = br.readLine();
        
        // 앞 쪽(9시)와 뒷 쪽(3시)를 가리키는 포인터들
        fronts = new int[4];
        Arrays.fill(fronts, 6);
        rears = new int[4];
        Arrays.fill(rears, 2);

        // k번 회전시킨다.
        int k = Integer.parseInt(br.readLine());
        for (int i = 0; i < k; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            // 회전시키는 톱니바퀴
            int gear = Integer.parseInt(st.nextToken()) - 1;
            // 방향
            int direction = Integer.parseInt(st.nextToken());
            rotate(gear, direction);
        }
        
        // 점수 계산
        int score = 0;
        for (int i = 0; i < gears.length; i++)
            score += (gears[i].charAt((fronts[i] + 2) % 8) == '1') ? (int) Math.pow(2, i) : 0;
        System.out.println(score);
    }

    // gear 톱니를 direction 방향으로 회전시킨다.
    static void rotate(int gear, int direction) {
        // 이번 회전에 회전되었는지 여부
        boolean[] rotated = new boolean[4];
        // BFS를 통해 양 옆의 톱니로 회전을 전달시킨다.
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(gear, direction));
        while (!queue.isEmpty()) {
            State current = queue.poll();
            // 이미 회전시킨 톱니라면 건너뛴다.
            if (rotated[current.gear])
                continue;

            // current.gear의 왼쪽 톱니를 살펴본다.
            // 회전이 아직 되지 않았고, 맞닿은 톱니가 서로 다른 극이라면
            // 회전시킨다.
            if (current.gear - 1 >= 0 && !rotated[current.gear - 1] &&
                    gears[current.gear].charAt(fronts[current.gear]) != gears[current.gear - 1].charAt(rears[current.gear - 1])) {
                queue.offer(new State(current.gear - 1, current.direction * -1));
            }

            // current.gear의 오른쪽 톱니를 살펴본다.
            if (current.gear + 1 < 4 && !rotated[current.gear + 1] &&
                    gears[current.gear].charAt(rears[current.gear]) != gears[current.gear + 1].charAt(fronts[current.gear + 1])) {
                queue.offer(new State(current.gear + 1, current.direction * -1));
            }

            // 회전 사항을 current.gear에 반영한다.
            fronts[current.gear] = (fronts[current.gear] + 8 + current.direction * -1) % 8;
            rears[current.gear] = (rears[current.gear] + 8 + current.direction * -1) % 8;
            rotated[current.gear] = true;
        }
    }
}