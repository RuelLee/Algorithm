/*
 Author : Ruel
 Problem : Baekjoon 27440번 1로 만들기 3
 Problem address : https://www.acmicpc.net/problem/27440
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27440_1로만들기3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class State {
    long num;
    int operation;

    public State(long num, int operation) {
        this.num = num;
        this.operation = operation;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 정수에 x에 다음 3개의 연산을 사용할 수 있다.
        // 1. X가 3으로 나누어 떨어지면, 3으로 나눈다.
        // 2. X가 2로 나누어 떨어지면, 2로 나눈다.
        // 3. 1을 뺀다.
        // x가 주어질 때 1로 만드는 최소 연산 횟수를 구하라
        //
        // BFS 문제
        // x의 범위가 10^18까지 크게 주어지지만
        // 1,2번 연산에 의해 값이 크게 줄어드므로 BFS로 직접 탐색한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 처음에 주어지는 수 x
        long x = Long.parseLong(br.readLine());

        // 값의 범위가 크므로 HashSet을 통해 방문체크를 한다.
        HashSet<Long> visited = new HashSet<>();
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(x, 0));
        // 처음엔 0번 연산에 x에서 시작
        visited.add(x);
        int answer = 0;
        while (!queue.isEmpty()) {
            State current = queue.poll();
            // 1에 도착하면 답을 기록하고 종료
            if (current.num == 1) {
                answer = current.operation;
                break;
            }
            
            // 3의 배수이고, 방문한 적이 없을 경우
            // 1번 연산 적용
            if (current.num % 3 == 0 && !visited.contains(current.num / 3)) {
                queue.offer(new State(current.num / 3, current.operation + 1));
                visited.add(current.num / 3);
            }
            
            // 2의 배수이고 방문한 적이 없다면
            // 2번 연산 적용
            if (current.num % 2 == 0 && !visited.contains(current.num / 2)) {
                queue.offer(new State(current.num / 2, current.operation + 1));
                visited.add(current.num / 2);
            }
            
            // 0보다 크고 방문한 적이 없다면
            // 3번 연산 적용
            if (current.num > 0 && !visited.contains(current.num - 1)) {
                queue.offer(new State(current.num - 1, current.operation + 1));
                visited.add(current.num - 1);
            }
        }
        
        // 답안 출력
        System.out.println(answer);
    }
}