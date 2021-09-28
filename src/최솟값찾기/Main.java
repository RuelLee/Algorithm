/*
 Author : Ruel
 Problem : Baekjoon 11003번 최솟값 찾기
 Problem address : https://www.acmicpc.net/problem/11003
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 최솟값찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Number {      // 숫자의 순서와 값을 저장하는 클래스
    int loc;
    int value;

    public Number(int loc, int value) {
        this.loc = loc;
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 플래티넘 문제들 중에선 상당히 간단한 편인 것 같다
        // 우선순위큐를 사용해서 일정 범위 내에 있는 가장 작은 수를 지속적으로 출력해줘야한다
        // 다만 입출력 시간이 빡빡하므로, Scanner를 사용하면 시간 초과 -> BufferedReader를 사용해야했다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        PriorityQueue<Number> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.value, o2.value));     // 값에 따라 숫자들을 오름차순 정렬한다.
        StringBuilder sb = new StringBuilder();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            priorityQueue.offer(new Number(i, Integer.parseInt(st.nextToken())));

            while (!priorityQueue.isEmpty() && priorityQueue.peek().loc < i - l + 1)        // 만약 현재 가장 작은 수가 원하는 범위에서 벗어난 값이라면 우선순위큐에서 뽑아서 값을 버려주자.
                priorityQueue.poll();
            sb.append(priorityQueue.peek().value).append(" ");      // 범위에 맞는 가장 작은 값을 발견하면, StringBuilder 에 기록해주자.
        }
        System.out.println(sb);     // 모든 경우가 끝나면 한번에 출력.
    }
}