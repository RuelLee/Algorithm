/*
 Author : Ruel
 Problem : Baekjoon 5014번 스타트링크
 Problem address : https://www.acmicpc.net/problem/5014
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5014_스타트링크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // f층으로 이루어진 고층 건물에, 현재 s층에 있으며, 최종적으로 g층에 가려한다.
        // 엘리베이터는 한번에 위로 u층, 아래로 d층씩만 이동이 가능하다.
        // s -> g층으로 가는데 총 몇번의 버튼을 눌러야하는가?
        // 만약 이동하는 것이 불가능하다면 use the stairs를 출력한다.
        //
        // BFS 문제
        // 시작 층부터 위, 아래 이동할 때, 눌러야하는 버튼의 수를 계속해서 계산해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력으로 주어지는 값.
        int f = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int g = Integer.parseInt(st.nextToken());
        int u = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        // 각 층에 도달하기 위해 눌러야하는 최소 버튼의 수.
        int[] floors = new int[f + 1];
        // 큰 값으로 초기화.
        Arrays.fill(floors, Integer.MAX_VALUE);
        // 시작층에서 눌러야하는 버튼의 횟수는 0.
        floors[s] = 0;
        // 큐를 통해 BFS를 진행한다.
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        while (!queue.isEmpty()) {
            // 현재 층.
            int current = queue.poll();

            // 위로 가는 버튼을 눌렀을 때
            // current + u 층에 도달하기 위해 눌러야하는 버튼의 횟수가
            // current + 1보다 클 경우.
            // current에서 current + u층으로 이동하는 것이 유리.
            if (current + u < floors.length && floors[current + u] > floors[current] + 1) {
                // 최소 버튼의 횟수 갱신.
                floors[current + u] = floors[current] + 1;
                // 큐 삽입.
                queue.offer(current + u);
            }

            // 아래로 가는 버튼을 눌렀을 때.
            // current - d 층에 도달하기 위해 눌러야하는 버튼의 횟수가
            // current + 1보다 클 경우.
            // current -> current - d층으로 이동하는 것이 유리.
            if (current - d > 0 && floors[current - d] > floors[current] + 1) {
                // 최소 버튼의 횟수 갱신.
                floors[current - d] = floors[current] + 1;
                // 큐 삽입.
                queue.offer(current - d);
            }
        }
        // 최종적으로 g층에 도달하기 위해 눌러야하는 버튼의 횟수가
        // 초기값 그대로라면 불가능한 경우. use the stairs 출력
        // 아니라면 해당 버튼의 횟수 출력.
        System.out.println(floors[g] == Integer.MAX_VALUE ? "use the stairs" : floors[g]);
    }
}