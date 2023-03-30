/*
 Author : Ruel
 Problem : Baekjoon 15558번 점프 게임
 Problem address : https://www.acmicpc.net/problem/15558
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15558_점프게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개 크기의 두 개의 사다리가 주어진다. 여기서 플레이어는 사다리의 n보다 큰 칸으로 이동해야한다.
        // 사다리의 각 칸은 1 혹은 0으로 주어지며, 1인 곳은 밟을 수 있으며 0인 곳은 밟을 수 없다.
        // 각 초마다 플레이어는 한 칸 앞으로 이동하거나, 한 칸 뒤로 이동하거나, 옆 사다리에서 +k 칸으로 이동해야한다.
        // i초마다 i칸 미만의 칸들은 사라져 밟을 수 없다고 할 때,
        // 해당 게임을 클리어할 수 있다면 1 아니라면 0을 출력한다
        //
        // BFS 문제
        // 초라는 제한이 있기 때문에 각 턴마다 각 시간에 할 수 있는 행동들을 하고 다음 초로 넘긴다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 사다리의 크기와 옆 사다리로 이동할 때 앞으로 전진해야하는 값 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        // 사다리의 상태
        String[] ladders = new String[2];
        for (int i = 0; i < ladders.length; i++)
            ladders[i] = br.readLine();
        
        // 게임 클리어 여부
        boolean possible = false;
        // 이번 턴에 진행할 위치들
        Queue<Integer> queue;
        // 다음 턴에 진행할 위치들
        Queue<Integer> nextQueue = new LinkedList<>();
        nextQueue.offer(0);
        // 방문 체크
        boolean[][] visited = new boolean[2][n];
        visited[0][0] = true;
        // 현재 턴
        int turn = 0;
        // 다음 턴이 비어있지 않다면
        while (!nextQueue.isEmpty()) {
            // 이번에 진행할 큐로 옮겨주고
            queue = nextQueue;
            // nextQueue는 다음 턴을 위한 위치들을 받을 준비를 한다.
            nextQueue = new LinkedList<>();
            // 이번 턴에 진행할 위치들을 탐색한다.
            while (!queue.isEmpty()) {
                // 어떤 사다리인지와
                int ladder = queue.peek() / n;
                // 위치
                int loc = queue.poll() % n;
                
                // 앞으로 한 칸 진행한다.
                // 만약 n이상이 된다면 가능 체크 후 종료
                if (loc + 1 >= n) {
                    possible = true;
                    break;
                } else if (ladders[ladder].charAt(loc + 1) == '1' &&
                        !visited[ladder][loc + 1]) {
                    // 그 외의 경우, 앞 칸이 1인지, 방문했는지 체크 후
                    // 다음 턴에 진행할 위치에 추가하고 방문 체크
                    nextQueue.offer(ladder * n + loc + 1);
                    visited[ladder][loc + 1] = true;
                }
                
                // 뒤로 한 칸 진행한다.
                // 시간 상 가능한지와 1인지 여부, 그리고 방문 여부 체크
                if (loc - 1 > turn && ladders[ladder].charAt(loc - 1) == '1' &&
                        !visited[ladder][loc - 1]) {
                    nextQueue.offer(ladder * n + loc - 1);
                    visited[ladder][loc - 1] = true;
                }
                
                // 옆 사다리로 이동하는 경우
                int nextLadder = (ladder + 1) % 2;
                // +k한 칸이 n이상인지 체크
                // 맞다면 가능 체크 후 종료
                if (loc + k >= n) {
                    possible = true;
                    break;
                } else if (ladders[nextLadder].charAt(loc + k) == '1' &&
                        !visited[nextLadder][loc + k]) {
                    // 아닐 경우, 1인지 여부와 방문 여부 체크 후, 다음 턴 탐색에 추가한다.
                    nextQueue.offer(nextLadder * n + loc + k);
                    visited[nextLadder][loc + k] = true;
                }
            }

            // 만약 이번 턴에 게임 클리어가 가능했다면 다음 턴 탐색을 종료한다.
            if (possible)
                break;
            // 아니라면 턴 증가 후 진행
            turn++;
        }

        // 계산된 결과에 따른 결과 출력.
        System.out.println(possible ? 1 : 0);
    }
}