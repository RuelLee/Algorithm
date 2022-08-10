/*
 Author : Ruel
 Problem : Baekjoon 21939번 문제 추천 시스템 Version 1
 Problem address : https://www.acmicpc.net/problem/21939
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21939_문제추천시스템Version1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Problem {
    int num;
    int difficulty;

    public Problem(int num, int difficulty) {
        this.num = num;
        this.difficulty = difficulty;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 문제들과 각각의 난이도가 주어진다
        // 그리고 m개의 명령이 주어진다
        // recommend x -> x가 1이면 가장 어려운 문제 그러한 문제가 여러 개라면 문제 번호가 큰 것,
        // -1이면 가장 쉬운 문제 그러한 문제가 여러 개라면 문제 번호가 작은 것을 출력한다.
        // add p l -> 난이도 l인 p번 문제를 추가한다. 기존 문제 리스트에 없는 문제만 주어진다. 제거 후엔, 다시 들어올 수 있다.
        // solved p -> 문제 리스트에서 p번 문제를 제거한다.
        //
        // 두 개의 우선순위큐를 이용하여 최대힙, 최소힙으로 각각 구성하여 이용한다.
        // Collection에서 remove가 얼마나 비효율적인지 알 수 있었다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 리스트에 있는 문제의 난이도를 기록해둔다.
        int[] problems = new int[100_001];
        // 난이도에 따른 최대힙. 난이도 같다면 큰 번호 우선.
        PriorityQueue<Problem> desc = new PriorityQueue<>((o1, o2) -> {
            if (o1.difficulty == o2.difficulty)
                return Integer.compare(o2.num, o1.num);
            return Integer.compare(o2.difficulty, o1.difficulty);
        });
        // 난이도에 따른 최소힙. 난이도가 같다면 작은 번호 우선.
        PriorityQueue<Problem> asc = new PriorityQueue<>((o1, o2) -> {
            if (o1.difficulty == o2.difficulty)
                return Integer.compare(o1.num, o2.num);
            return Integer.compare(o1.difficulty, o2.difficulty);
        });
        // n개의 문제 추가.
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());

            // 문제에 난이도 기록.
            problems[p] = l;
            // 최대, 최소힙에 각각 삽입.
            asc.offer(new Problem(p, l));
            desc.offer(new Problem(p, l));
        }

        // m개의 명령 처리.
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String order = st.nextToken();
            switch (order) {
                // recommend인 경우.
                case "recommend" -> {
                    int type = Integer.parseInt(st.nextToken());
                    switch (type) {
                        // 가장 어려운 문제.
                        case 1 -> {
                            // 최대힙을 살펴보며, 꺼낸 문제와 problems에 기록된 난이도가 다를 경우
                            // 이미 제거됐거나, 제거된 후에 추가된 문제. 현재 순서와는 맞지 않으므로 그대로 제거.
                            while (!desc.isEmpty() && desc.peek().difficulty != problems[desc.peek().num])
                                desc.poll();
                            // 문제 조건 상 리스트에 최소 한 개 이상의 문제가 있을 때만 recommend 명령이 주어진다 하였다.
                            assert desc.peek() != null;
                            // 가장 어려우면서 번호가 큰 문제를 출력한다.
                            sb.append(desc.peek().num).append("\n");
                        }
                        // 가장 쉬운 문제.
                        case -1 -> {
                            // 최대힙과 마찬가지로 제거되거나, 난이도가 갱신된 문제를 제거한다.
                            while (!asc.isEmpty() && asc.peek().difficulty != problems[asc.peek().num])
                                asc.poll();
                            // 가장 쉬우면서 번호가 작은 문제 출력.
                            assert asc.peek() != null;
                            sb.append(asc.peek().num).append("\n");
                        }
                    }
                }
                // 문제 추가.
                case "add" -> {
                    int p = Integer.parseInt(st.nextToken());
                    int l = Integer.parseInt(st.nextToken());

                    // 난이도 기록.
                    problems[p] = l;
                    // 최대, 최소 힙에 삽입.
                    desc.offer(new Problem(p, l));
                    asc.offer(new Problem(p, l));
                }
                // 문제를 제거하는 경우.
                case "solved" -> {
                    int p = Integer.parseInt(st.nextToken());
                    // problems에 0 값을 넣어 문제가 제거됐다고 표시한다.
                    problems[p] = 0;
                }
            }
        }

        // 최종 답안 출력.
        System.out.print(sb);
    }
}