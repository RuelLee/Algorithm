/*
 Author : Ruel
 Problem : Baekjoon 14395번 4연산
 Problem address : https://www.acmicpc.net/problem/14395
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14395_4연산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class minDistance {
    int distance;
    long pi;
    char operation;

    public minDistance(int distance, long pi, char operation) {
        this.distance = distance;
        this.pi = pi;
        this.operation = operation;
    }

    public minDistance(int distance, int pi, char operation) {
        this.distance = distance;
        this.pi = pi;
        this.operation = operation;
    }
}

public class Main {
    static final int LIMIT = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        // 정수 s와 t가 주어진다.
        // 아래 4개의 연산을 통해 s를 t로 바꾸는 최소 연산을 구하는 프로그램을 작성하라
        // s = s + s; (출력: +)
        // s = s - s; (출력: -)
        // s = s * s; (출력: *)
        // s = s / s; (출력: /) (s가 0이 아닐때만 사용 가능)
        //
        // 너비 우선 탐색 문제
        // s와 t가 최대 10억으로 주어지므로 배열로 탐색할 수는 없다.
        // 또 s와 t 사이의 모든 값을 탐색하는 것이 아니므로 해쉬맵을 통해 표시한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 해쉬맵을 통해 해당 수에 도달하는 최소 연산 횟수와
        // 이전 값과 시행한 연산에 대한 정보들을 같이 저장한다.
        HashMap<Long, minDistance> minDistances = new HashMap<>();
        // 시작점 초기화
        minDistances.put((long) s, new minDistance(0, -1, '#'));
        // 0도 s에서 바로 - 연산을 사용하여 도달하는 경우 말고는 없다.
        minDistances.put(0L, new minDistance(1, s, '-'));
        
        // 너비 우선 탐색
        Queue<Long> queue = new LinkedList<>();
        queue.offer((long) s);
        while (!queue.isEmpty()) {
            long current = queue.poll();
            
            // * 연산
            if (current * current <= LIMIT &&
                    !minDistances.containsKey(current * current)) {
                minDistances.put(current * current, new minDistance(minDistances.get(current).distance + 1, current, '*'));
                queue.offer(current * current);
            }
            
            // + 연산
            if (current * 2 <= LIMIT &&
                    !minDistances.containsKey(current * 2)) {
                minDistances.put(current * 2, new minDistance(minDistances.get(current).distance + 1, current, '+'));
                queue.offer(current * 2);
            }

            // / 연산
            // / 연산 또한 s가 바로 1로 되는 경우 말고는 다른 경우가 없을 것이다.
            if (!minDistances.containsKey(1L)) {
                minDistances.put(1L, new minDistance(minDistances.get(current).distance + 1, current, '/'));
                queue.offer(1L);
            }
        }

        // s와 t가 같다면 0
        if (s == t)
            System.out.println(0);
        // 만약 t에 도달하는 경우가 없다면 -1
        else if (!minDistances.containsKey((long) t))
            System.out.println(-1);
        else {
            // 그 외의 경우에는
            // t에서부터 s로 가는 경로를 저장해두었으므로
            // 시행한 연산을 거꾸로 추적하며 스택을 이용하여 담아둔다.
            Stack<Character> stack = new Stack<>();
            long loc = t;
            while (loc != s) {
                stack.push(minDistances.get(loc).operation);
                loc = minDistances.get(loc).pi;
            }

            // 모두 담았다면 스택에서 꺼내 답안을 작성하고
            StringBuilder sb = new StringBuilder();
            while (!stack.isEmpty())
                sb.append(stack.pop());
            // 출력한다.
            System.out.println(sb);
        }
    }
}