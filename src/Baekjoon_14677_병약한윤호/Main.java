/*
 Author : Ruel
 Problem : Baekjoon 14677번 병약한 윤호
 Problem address : https://www.acmicpc.net/problem/14677
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14677_병약한윤호;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

class State {
    int time;
    int start;
    int end;

    public State(int time, int start, int end) {
        this.time = time;
        this.start = start;
        this.end = end;
    }
}

public class Main {
    static final char[] pillsByTime = {'B', 'L', 'D'};

    public static void main(String[] args) throws IOException {
        // n일치 약봉지는 3 * n개의 약이 연이어 붙어있다.
        // 각 약은 아침 / 점심 / 저녁 약으로 나뉘어져있다.
        // 첫번째 혹은 마지막 약만 먹을 수 있을 때,
        // 총 몇 개의 약을 제 시간에 먹을 수 있는가?
        //
        // DP, 너비 우선 탐색 문제
        // 각 약을 먹을 때, 맨 앞 혹은 맨 뒤의 약이 현재 시간에 맞는 약인지 확인하고 먹야아한다.
        // 만약 두 약 모두 먹을 수 있다면 두 경우 또한 모두 고려해야한다.
        // 따라서 현재 시간과, 남은 약의 범위를 갖고서 너비 우선 탐색을 진행한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // n일치의 약
        int n = Integer.parseInt(br.readLine());
        String pills = br.readLine();

        // 최대 먹을 수 있는 약의 개수
        int max = 0;
        // 방문 체크
        boolean[][] visited = new boolean[3 * n][3 * n];
        Queue<State> queue = new LinkedList<>();
        // 초기값
        // 계속 증가하는 시간과, 남은 약봉지의 범위
        queue.offer(new State(0, 0, 3 * n - 1));
        while (!queue.isEmpty()) {
            State current = queue.poll();
            
            // 모든 약을 다 먹은 경우 종료
            if (current.time >= 3 * n)
                break;
            // 이미 방문했다면 건너뛴다.
            else if (visited[current.start][current.end])
                continue;
            
            // 방문체크
            visited[current.start][current.end] = true;
            
            // 맨 앞의 약을 먹을 수 있는 경우
            if (pills.charAt(current.start) == pillsByTime[current.time % 3]) {
                // 시간 증가, 약봉지의 시작 범위 증가
                queue.offer(new State(current.time + 1, current.start + 1, current.end));
                // 최대 약의 개수 갱신
                max = Math.max(max, current.time + 1);
            }
            
            // 맨 뒤의 약을 먹을 수 있는 경우
            if (pills.charAt(current.end) == pillsByTime[current.time % 3]) {
                // 시간 증가, 약 봉지의 끝 범위 감소
                queue.offer(new State(current.time + 1, current.start, current.end - 1));
                // 최대 약의 개수 갱신
                max = Math.max(max, current.time + 1);
            }
        }

        // 먹을 수 있었던 최대 약의 개수 출력.
        System.out.println(max);
    }
}