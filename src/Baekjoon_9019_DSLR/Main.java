/*
 Author : Ruel
 Problem : Baekjoon 9019번 DSLR
 Problem address : https://www.acmicpc.net/problem/9019
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9019_DSLR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static char[] order = {'D', 'S', 'L', 'R'};

    public static void main(String[] args) throws IOException {
        // D, S, L, R 연산을 할 수 있는 계산기가 있다
        // D. 값을 2배로 하고 9999보다 크다면 모듈러 10000을 한다.
        // S. 값에서 1을 뺀다. n이 0이라면 9999가 대신 저장된다
        // L. 각 자릿수를 왼편으로 회전시킨다. 1234 -> 2341
        // R. 각 자릿수를 오른편으로 회전시킨다. 1234 -> 4123
        // 테스트케이스 t와 각각의 테스트케이스에 원래 수 a, 원하는 수 b가 주어진다
        // 최소한의 연산으로 a를 b로 만들고자하며, 그 때의 연산들을 출력하라
        //
        // BFS 문제
        // 직접 연산을 해나가며 해당 값에 도달한 최소 연산의 수와 그 때의 연산들을 기록해두자
        // 그리고 최종적으로 b에 도달했을 때 BFS를 마치고 해당 연산들을 출력한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder answer = new StringBuilder();
        int testCase = Integer.parseInt(br.readLine());
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // 값은 0 ~ 9999까지 총 1만개이다
            int[] minOrders = new int[10000];
            // Integer.MAX_VALUE 값으로 초기화
            Arrays.fill(minOrders, Integer.MAX_VALUE);
            // a값에 도달하는 최소 명령 수는 0
            minOrders[a] = 0;
            // 그 때 각각의 수에 도달하는 명령들을 저장한다
            StringBuilder[] stringBuilders = new StringBuilder[10000];
            for (int i = 0; i < stringBuilders.length; i++)
                stringBuilders[i] = new StringBuilder();
            // queue에 a 값을 넣어 BFS를 시작한다.
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(a);
            while (!queue.isEmpty()) {
                int current = queue.poll();
                // b에 도달했다면 종료
                if (current == b)
                    break;

                // DSLR 네가지 연산을 모두 시행한다.
                int[] values = new int[4];
                // 곱하기 2를 한경우
                values[0] = (current * 2) % 10000;
                // 1값을 뺀 경우.
                values[1] = (current + 9999) % 10000;
                // 왼쪽으로 회전한 경우
                values[2] = (current * 10) % 10000 + (current * 10) / 10000;
                // 오른쪽으로 회전한 경우
                values[3] = (current % 10) * 1000 + (current / 10);

                for (int i = 0; i < values.length; i++) {
                    // 각각의 값이 최소 명령 수를 갱신하는지 확인하고 그렇다면 값 갱신과
                    // 명령어 갱신을 같이 해준다
                    // 그리고 갱신된 수를 queue에 넣어준다.
                    if (minOrders[values[i]] > minOrders[current] + 1) {
                        minOrders[values[i]] = minOrders[current] + 1;
                        stringBuilders[values[i]] = new StringBuilder(stringBuilders[current]);
                        stringBuilders[values[i]].append(order[i]);
                        queue.offer(values[i]);
                    }
                }
            }
            // 최종적으로 b값에 도달하는데 필요한 최소 명령들을 출력한다.
            answer.append(stringBuilders[b]).append("\n");
        }
        System.out.print(answer);
    }
}