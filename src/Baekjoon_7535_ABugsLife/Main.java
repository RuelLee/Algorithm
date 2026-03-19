/*
 Author : Ruel
 Problem : Baekjoon 7535번 A Bug’s Life
 Problem address : https://www.acmicpc.net/problem/7535
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7535_ABugsLife;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // s개의 테스트 케이스가 주어진다.
        // 각 테스트 케이스마다 벌레의 수와 서로 상호작용하는 두 벌레가 주어진다.
        // 상호작용은 서로 이성인 벌레만 한다고 가정한다.
        // 만약 동성애 성향을 가진 벌레가 발견되는지 여부를 출력하라
        //
        // 그래프 탐색 문제
        // 상호 작용에 대해 리스트로 정리하고
        // 벌레들을 1번 팀 혹은 2번 팀에 배정하는 것을 계속해나간다.
        // 서로 상호작용을 하는 벌레들은 다른 팀에 배정되야한다.
        // 만약 상호작용을 하는데 같은 팀인 경우, 이상 현상이 발견된 것이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 상호 작용을 담을 리스트
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < 2001; i++)
            connections.add(new ArrayList<>());

        // 테스트 케이스의 수
        int s = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        Queue<Integer> queue = new LinkedList<>();
        int[] teams = new int[2001];
        for (int testCase = 0; testCase < s; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 벌레의 수
            int bugs = Integer.parseInt(st.nextToken());
            for (int i = 1; i <= bugs; i++)
                connections.get(i).clear();
            Arrays.fill(teams, 1, bugs + 1, -1);
            queue.clear();

            // 상호 작용의 수
            int interactions = Integer.parseInt(st.nextToken());
            for (int i = 0; i < interactions; i++) {
                st = new StringTokenizer(br.readLine());
                // 상호 작용을 하는 두 벌레
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                connections.get(a).add(b);
                connections.get(b).add(a);
            }

            boolean answer = true;
            for (int i = 1; i < teams.length && answer; i++) {
                // 아직 i번 벌레가 팀 배정이 안됐다면
                if (teams[i] == -1) {
                    // 0번 팀에 배정 후, 탐색
                    queue.offer(i);
                    teams[i] = 0;
                    while (!queue.isEmpty() && answer) {
                        int current = queue.poll();

                        // 상호 작용을 하는 벌레
                        for (int next : connections.get(current)) {
                            // 아직 팀 배정이 안됐다면 current와 다른 팀에 배정
                            if (teams[next] == -1) {
                                teams[next] = (teams[current] + 1) % 2;
                                queue.offer(next);
                            } else if (teams[next] == teams[current]) { // current와 같은 팀이라면 이상 현상
                                answer = false;
                                break;
                            }
                        }
                    }
                }
            }
            // 결과 기록
            sb.append("Scenario #").append(testCase + 1).append(":\n");
            if (answer)
                sb.append("No s");
            else
                sb.append("S");
            sb.append("uspicious bugs found!").append("\n").append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}