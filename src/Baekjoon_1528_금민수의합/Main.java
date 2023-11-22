/*
 Author : Ruel
 Problem : Baekjoon 1528번 금민수의 합
 Problem address : https://www.acmicpc.net/problem/1528
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1528_금민수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<Integer> list;

    public static void main(String[] args) throws IOException {
        // 금민수는 4와 7만으로 이루어진 수이다.
        // n이 주어지면 이 수를 금민수의 합으로 표현하고 싶다.
        // 그러한 방법이 여러개라면 사전순으로 앞서는 것을 출력하고, 없다면 -1을 출력한다.
        //
        // DP, BFS 문제
        // 먼저 4와 7로 만들 수 있는 금민수들을 모두 구한다.
        // 그 후, 0부터 BFS를 통해 금민수 합으로 만들 수 있는 수들을 계산하며
        // 이전 수를 기록해둔다.
        // n으로부터 기록된 수를 거꾸로 찾아가며 답안을 작성한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 금민수의 합으로 표현할 n
        int n = Integer.parseInt(br.readLine());

        // 초기값으로 -1 세팅
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        // 금민수들을 모두 찾고, 정렬
        list = new ArrayList<>();
        setList(0);
        Collections.sort(list);

        // 0부터 BFS를 통해 금민수의 합으로 표현가능한 수들을 찾는다.
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == n)
                break;

            // current에 plus(금민수)를 더하는 경우
            // current + plus는 금민수의 합으로 표현되는 수
            for (int plus : list) {
                // 범위를 넘지 않고, 아직 계산된 적 없다면
                if (current + plus < dp.length && dp[current + plus] == -1) {
                    // current + plus는 current에서 금민수를 더한 값임을 표시하고
                    dp[current + plus] = current;
                    // 큐 추가
                    queue.offer(current + plus);
                }
            }
        }

        // 사전순으로 가장 앞서야하므로, 금민수의 합 내에서도 정렬이 필요하다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        // n부터 거꾸로 추적해나간다.
        int idx = n;
        // 이전 수가 -1이 될 때까지
        while (dp[idx] != -1) {
            // 큐에 current와 dp[idx]의 차(금민수)를 추가하고
            priorityQueue.offer(idx - dp[idx]);
            // idx를 이전수로 바꿔준다.
            idx = dp[idx];
        }

        StringBuilder sb = new StringBuilder();
        // 만약 비어있다면 금민수의 합으로 표현이 불가능한 경우이므로
        // -1 기록
        if (priorityQueue.isEmpty())
            sb.append(-1);
        else {
            // 우선순위큐에 들어있는 수들을 꺼내 답안 작성
            while (!priorityQueue.isEmpty())
                sb.append(priorityQueue.poll()).append(" ");
            sb.deleteCharAt(sb.length() - 1);
        }
        
        // 답안 출력
        System.out.println(sb);
    }

    // 금민수들을 모두 구한다.
    static void setList(int num) {
        // num에서 뒤에 4를 붙이거나
        int next = num * 10 + 4;
        if (next <= 1_000_000) {
            list.add(next);
            setList(next);
        }
        
        // 7을 붙인 수 또한 금민수이므로 해당 수들을
        // list에 추가
        next = num * 10 + 7;
        if (next <= 1_000_000) {
            list.add(next);
            setList(next);
        }
    }
}