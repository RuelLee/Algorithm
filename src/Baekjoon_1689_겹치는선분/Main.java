/*
 Author : Ruel
 Problem : Baekjoon 1689번 겹치는 선분
 Problem address : https://www.acmicpc.net/problem/1689
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1689_겹치는선분;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1차원 좌표계의 n개의 선분이 주어진다.
        // 선분이 최대로 겹쳐 있는 부분의 겹친 선분의 개수를 출력하라.
        // 선분이 끝 점에서 겹치는 것은 겹치는 것으로 세지 않는다.
        //
        // 스위핑 문제
        // 일단 선분들을 시작점에 대해 정렬한다.
        // 선분들을 하나씩 차례대로 살펴보며, 해당 선분의 시작점보다
        // 작은 끝점을 갖고 있는 선분들을 모두 제해나가며 겹치는 선분들의 개수를 찾는다.
        // 이 때 겹치는 선분들은 끝점을 기준으로 최소힙 우선순위큐에 담아 관리해나가자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        // 주어지는 선분들
        int[][] lines = new int[n][2];
        for (int i = 0; i < lines.length; i++)
            lines[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 시작점에 대해 정렬.
        Arrays.sort(lines, (o1, o2) -> Integer.compare(o1[0], o2[0]));

        // 최대 겹치는 선분들의 개수.
        int max = 0;
        // 우선순위큐로 겹치는 선분들의 끝점을 관리한다.
        PriorityQueue<Integer> overlapped = new PriorityQueue<>();
        for (int[] line : lines) {
            // i번째의 선분의 시작점보다 작은 끝점을 갖고 있는 경우
            // 우선순위큐에서 꺼낸다.
            while (!overlapped.isEmpty() &&
                    overlapped.peek() <= line[0])
                overlapped.poll();
            // 마지막으로 i번째 선분의 끝점을 우선순위큐에 담는다.
            overlapped.offer(line[1]);
            // 우선순위큐에는 겹치는 선분들의 끝점들이 담겨있다.
            // 이 개수가 현재 겹치는 선분의 개수.
            // max에 최대값인지 확인하고 값 갱신.
            max = Math.max(max, overlapped.size());
        }
        // 최종 답 출력.
        System.out.println(max);
    }
}