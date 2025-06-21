/*
 Author : Ruel
 Problem : Baekjoon 2024번 선분 덮기
 Problem address : https://www.acmicpc.net/problem/2024
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2024_선분덮기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Line {
    int idx;
    int start;
    int end;

    public Line(int idx, int start, int end) {
        this.idx = idx;
        this.start = start;
        this.end = end;
    }
}

public class Main {
    // 선분이 -50_000 ~ 50_000까지 들어오므로
    // 양수로 표현하기 위해 모두 BIAS를 더해줌
    static final int BIAS = 50_000;

    public static void main(String[] args) throws IOException {
        // x축 위의 선분들이 주어진다.
        // 0 ~ m까지를 최소의 선분들로 모두 덮고자 한다.
        // 각 선분의 시작점과 끝 점이 주어질 때
        // 필요한 선분의 최소 개수는?
        //
        // 스위핑, 정렬 문제
        // 먼저 정렬을 통해, 선분들을 시작점이 이른 순서대로, 시작점이 같다면
        // 끝점이 이른 순서대로 정렬을 한다.
        // 그 후, 각 지점에서 필요한 최소 선분과 해당 선분이 무엇인지 표시하며
        // m까지의 최소 선분 개수를 세면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String input = br.readLine();

        // 해당 지점을 덮은 선분
        int[] line = new int[BIAS * 2 + 1];
        // 0 ~ 해당 지점까지 필요한 최소 선분의 개수
        int[] minLines = new int[BIAS * 2 + 1];

        // 정렬 대신, 우선순위큐를 사용
        PriorityQueue<Line> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if ((o1.start == o2.start))
                return Integer.compare(o1.end, o2.end);
            return Integer.compare(o1.start, o2.start);
        });
        while (input != null) {
            // 0 ~ m까지를 덮는다.
            int m = Integer.parseInt(input);
            // 배열 및 우선순위큐 초기화
            Arrays.fill(line, -1);
            Arrays.fill(minLines, Integer.MAX_VALUE);
            priorityQueue.clear();

            // 각 선분을 구분하기 위해 cnt를 통해 idx번호 추가
            int cnt = 0;
            while (true) {
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                if (start == 0 && end == 0)
                    break;

                priorityQueue.offer(new Line(cnt++, start + BIAS, end + BIAS));
            }

            // 선분이 하나 이상 들어온 경우.
            if (!priorityQueue.isEmpty()) {
                // 첫 선분의 시작점 부터 시작.
                for (int i = priorityQueue.peek().start; i <= BIAS + m; i++) {
                    // 우선순위큐의 최상단 선분의 끝점이 i보다 작은 경우는 우선순위큐에서 제거
                    while (!priorityQueue.isEmpty() && priorityQueue.peek().end < i)
                        priorityQueue.poll();
                    
                    // 선분이 더 이상 없다면 종료
                    if (priorityQueue.isEmpty())
                        break;
                    
                    // 선분의 시작점이 BIAS보다 같거나 작은 경우
                    // 해당 선분을 시작 선분으로 사용할 수 있음.
                    // 따라서 해당 i까지 하나의 선분으로 덮을 수 있음.
                    if (priorityQueue.peek().start <= BIAS) {
                        line[i] = priorityQueue.peek().idx;
                        minLines[i] = 1;
                    } else if (line[priorityQueue.peek().start] == -1)      // 시작 지점이 선분으로 덮이지 않은 경우. 더 이상 진행 불가.
                        break;
                    // i 지점을 덮을 선분과 현재 선분의 시작 지점을 덮은 선분이 같은 경우
                    // 하나의 선분으로 계속해서 덮고 있는 중이기 때문에, minLines[선분의 시작점]개로 덮을 수 있다.
                    // 최소값을 갱신하는지 확인
                    else if (line[priorityQueue.peek().start] == priorityQueue.peek().idx) {
                        if (minLines[i] > minLines[priorityQueue.peek().start]) {
                            minLines[i] = minLines[priorityQueue.peek().start];
                            line[i] = priorityQueue.peek().idx;
                        }
                    } else if (minLines[i] > minLines[priorityQueue.peek().start] + 1) {
                        // 현재 i지점을 덮을 선분의 시작 지점이 다른 선분으로 덮인 경우
                        // 다른 선분 위에 잇거나 덮어야 하는 경우이므로, 해당 선분의 시작점 +1개의 선분으로 계산.
                        minLines[i] = minLines[priorityQueue.peek().start] + 1;
                        line[i] = priorityQueue.peek().idx;
                    }
                }
            }
            // m지점의 값이 초기값이라면 불가능한 경우이므로 0 출력 
            // 그 외의 경우는 해당 지점을 덮는 최소 선분의 개수 출력
            System.out.println(minLines[BIAS + m] == Integer.MAX_VALUE ? 0 : minLines[BIAS + m]);
            // 다음 입력
            input = br.readLine();
        }
    }
}