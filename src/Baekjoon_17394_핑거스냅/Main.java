/*
 Author : Ruel
 Problem : Baekjoon 17394번 핑거 스냅
 Problem address : https://www.acmicpc.net/problem/17394
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17394_핑거스냅;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 인피니티 건틀릿을 갖고서 핑거 스냅을 할 경우
        // 1. 생명체를 절반으로 한다
        // 2. 생명체를 1/3으로 한다.
        // 3. 생명체를 하나 늘린다.
        // 4. 생명체를 하나 줄인다.
        // 1, 2번 경우의 나누어떨어지지 않으면 나머지는 버린다.
        // 4번의 경우 생명체가 0이라면 할 수 없다
        // 현재 n개의 생명체가 있으며, 이를 A이상 B이하의 소수개로 만들고자할 때
        // 필요한 최소 핑거 스냅은?
        //
        // 에라토스테네스의 체와 BFS 문제
        // 먼저 에라토스테네세의 체로 n이하의 소수들을 모두 찾는다.
        // 그 후, 핑거 스냅을 통해, a 이상 b이하의 소수를 탐색할 때까지 진행한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 에라토스테네스의 체
        boolean[] primeNumbers = new boolean[1_000_001];
        Arrays.fill(primeNumbers, true);
        primeNumbers[0] = primeNumbers[1] = false;
        // 2부터
        for (int i = 2; i < primeNumbers.length; i++) {
            // i가 소수가 아닐 경우 이미 계산이 된 경우. 건너 뛴다.
            if (!primeNumbers[i])
                continue;
            // i가 소수인 경우, 배수들을 따져가며 소수가 아니라고 체크해준다.
            for (int j = 2; i * j < primeNumbers.length; j++)
                primeNumbers[i * j] = false;
        }

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 테스트케이스 별 입력
            int n = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a, b와 n과의 연관 조건이 없으므로
            // b의 3배보다 n이 더 작을 수 있다.
            // 2번 핑거스냅을 통해 1/3로 한번에 줄이는 방법이 있을 수 있으므로
            // n+1과 b의 3배 중 더 큰 값까지 배열로 세우고 탐색해주자.
            int[] minFingers = new int[Math.max(n + 1, b * 3 + 1)];
            Arrays.fill(minFingers, Integer.MAX_VALUE);
            minFingers[n] = 0;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(n);
            int answer = -1;
            // BFS
            while (!queue.isEmpty()) {
                int current = queue.poll();
                // A이상 B이하의 범위이며 소수일 경우, 핑거 스냅 수를 기록하고 종료.
                if (current >= a && current <= b && primeNumbers[current]) {
                    answer = minFingers[current];
                    break;
                }
                // 1번 스냅
                if (minFingers[current / 2] > minFingers[current] + 1) {
                    minFingers[current / 2] = minFingers[current] + 1;
                    queue.offer(current / 2);
                }
                // 2번 스냅
                if (minFingers[current / 3] > minFingers[current] + 1) {
                    minFingers[current / 3] = minFingers[current] + 1;
                    queue.offer(current / 3);
                }
                // 3번 스냅
                if (current + 1 < minFingers.length &&
                        minFingers[current + 1] > minFingers[current] + 1) {
                    minFingers[current + 1] = minFingers[current] + 1;
                    queue.offer(current + 1);
                }
                // 4번 스냅
                if (current - 1 >= 0 &&
                        minFingers[current - 1] > minFingers[current] + 1) {
                    minFingers[current - 1] = minFingers[current] + 1;
                    queue.offer(current - 1);
                }
            }
            // 이번 테스트케이스의 답을 StringBuilder에 기록한다.
            sb.append(answer).append("\n");
        }
        // 전체 답안을 출력한다.
        System.out.print(sb);
    }
}