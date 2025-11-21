/*
 Author : Ruel
 Problem : Baekjoon 2593번 엘리베이터
 Problem address : https://www.acmicpc.net/problem/2593
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2593_엘리베이터;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n층으로 이루어진 아파트에 m개의 엘리베이터가 있다.
        // 각 엘리베이터는 xi층부터 위로 운행을 하며, yi층 마다 멈춘다고 한다.
        // a층에서 b층으로 가려고하면 최소 몇 대의 엘리베이터를 타야하고
        // 그 때 타야하는 엘리베이터를 순서대로 출력하라
        //
        // BFS 문제
        // 각 층에 가는데 몇 번을 갈아타야하는지를 계산하는 것이 아니라
        // a층에서 시작해서 각 엘리베이터에 타려면 몇 번을 갈아타야하는지가 중요하다.
        // 여러 엘리베이터가 동시에 도달하는 층에서 서로 갈아탈 수 있다.
        // 먼저 a층에 도달하는 엘리베이터들은 그냥 탈 수 있고,
        // 이제 해당 엘리베이터들이 도달하는 층마다, 아직 탑승한 적이 없는 엘리베이터들 중에
        // 같은 층에 멈추는 엘리베이터가 있는지 확인하며 계산한다.
        // 이 때, 이전에 탑승했던 엘리베이터를 기록해둔다.
        // 최종적으로 b층에 거쳐간 엘리베이터의 수가 최소인 엘리베이터를 찾고, 탑승했던 엘리베이터들을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n층의 아파트, m개의 엘리베이터
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 엘리베이터 정보
        int[][] elevators = new int[m][2];
        for (int i = 0; i < elevators.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < elevators[i].length; j++)
                elevators[i][j] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        // a층에서 b층으로 가고 싶다.
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        
        // 각 엘리베이터까지 탑승한 엘리베이터의 수
        int[] minTransfer = new int[m];
        // 이전에 탑승한 엘리베이터
        int[] before = new int[m];
        // 여태 탑승이 가능했던 엘리베이터 표시
        boolean[] enqueued = new boolean[m];
        for (int i = 1; i < before.length; i++)
            before[i] = i;
        Arrays.fill(minTransfer, Integer.MAX_VALUE);
        Queue<Integer> queue = new LinkedList<>();
        // a층에서 바로 탑승 가능한 엘리베이터들
        for (int i = 0; i < elevators.length; i++) {
            if (a >= elevators[i][0] && (a - elevators[i][0]) % elevators[i][1] == 0) {
                queue.offer(i);
                minTransfer[i] = 1;
                enqueued[i] = true;
            }
        }
        
        // BFS
        while (!queue.isEmpty()) {
            // current 엘리베이터에서 탐색
            int current = queue.poll();
            
            // 멈추는 층들
            for (int floor = elevators[current][0]; floor <= n; floor += elevators[current][1]) {
                // floor에서 환승 가능한 엘리베이터들
                for (int j = 0; j < elevators.length; j++) {
                    if (enqueued[j])
                        continue;
                    // 환승 가능하고, 최소 탑승 횟수를 갱신한다면
                    else if (floor >= elevators[j][0] && (floor - elevators[j][0]) % elevators[j][1] == 0 &&
                            minTransfer[j] > minTransfer[current] + 1) {
                        minTransfer[j] = minTransfer[current] + 1;
                        before[j] = current;
                        queue.offer(j);
                        enqueued[j] = true;
                    }
                }
            }
        }
        
        // b층에서 멈추는 최소 탑승 횟수의 엘리베이터 찾기
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < elevators.length; i++) {
            if (b >= elevators[i][0] && (b - elevators[i][0]) % elevators[i][1] == 0 &&
                    (stack.isEmpty() || minTransfer[stack.peek()] > minTransfer[i])) {
                stack.clear();
                stack.push(i);
            }
        }

        StringBuilder sb = new StringBuilder();
        // 그러한 엘리베이터가 없다면 -1 불가능 기록
        if (stack.isEmpty())
            sb.append(-1).append("\n");
        else {
            // 그 외의 경우, 탑승 기록을 거꾸로 찾아가며 답안 작성
            sb.append(minTransfer[stack.peek()]).append("\n");
            while (before[stack.peek()] != stack.peek())
                stack.push(before[stack.peek()]);
            while (!stack.isEmpty())
                sb.append(stack.pop() + 1).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}