/*
 Author : Ruel
 Problem : Baekjoon 7662번 이중 우선순위 큐
 Problem address : https://www.acmicpc.net/problem/7662
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7662_이중우선순위큐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // T개의 테스트케이스, K개의 입력이 주어진다.
        // I n 은 n을 추가한다는 의미이고
        // D 1 은 최대값을 하나 제거
        // D -1 은 최소값을 하나 제거를 의미한다. 수가 존재하지 않는데, D 명령이 주어진다면 무시한다.
        // 모든 입력이 끝난 후, 최대값과 최소값을 출력한다.
        // 만약 값이 없다면 EMPTY를 출력한다.
        //
        // 문제 이름 그대로 우선순위큐를 두개 사용하는 문제.
        // 각 우선순위큐에 수를 담되, 각 수들의 개수를 센다.
        // 그리고 D 입력이 주어질 때마다, 해당하는 우선순위큐에서만 수를 제거하고, 대신 개수를 줄여준다.
        // 그 후 하나의 명령일 처리할 때마다, 각 우선순위큐들에서 peek 값의 개수가 0개인지 확인하고 0 이라면 제거해준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int k = Integer.parseInt(br.readLine());
            // 각 수의 개수를 센다.
            HashMap<Integer, Integer> counts = new HashMap<>();
            // 오름차순
            PriorityQueue<Integer> asc = new PriorityQueue<>();
            // 내림차순
            PriorityQueue<Integer> desc = new PriorityQueue<>(Comparator.reverseOrder());

            for (int i = 0; i < k; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                // 명령의 종류
                char order = st.nextToken().charAt(0);
                // 값
                int value = Integer.parseInt(st.nextToken());

                // 입력이라면
                if (order == 'I') {
                    // 우선순위큐에 모두 추가하고
                    asc.offer(value);
                    desc.offer(value);
                    // counts에도 기록해준다.
                    if (counts.containsKey(value))
                        counts.put(value, counts.get(value) + 1);
                    else
                        counts.put(value, 1);
                    // 제거라면
                } else if (!asc.isEmpty()) {
                    // 내림차순 우선순위큐에서 제거 및 개수 카운팅.
                    if (value > 0)
                        counts.put(desc.peek(), counts.get(desc.poll()) - 1);
                    // 오름차순 우선순위큐에서 제거 및 개수 카운팅.
                    else
                        counts.put(asc.peek(), counts.get(asc.poll()) - 1);
                }

                // 하나의 명령을 처리하고는 각 우선순위큐에서 더 이상 존재하지 않는 값을
                // 최댓값, 최솟값으로 가르키고 있지 않은지 확인 후 제거.
                while (!desc.isEmpty() && counts.get(desc.peek()) == 0)
                    desc.poll();
                while (!asc.isEmpty() && counts.get(asc.peek()) == 0)
                    asc.poll();
            }

            // 만약 우선순위큐가 비어있다면 답이 존재 X
            if (asc.isEmpty())
                sb.append("EMPTY").append("\n");
            // 존재한다면 최댓값, 최솟값을 출력한다.
            else
                sb.append(desc.peek()).append(" ").append(asc.peek()).append("\n");
        }
        System.out.print(sb);
    }
}