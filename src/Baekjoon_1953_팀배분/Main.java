/*
 Author : Ruel
 Problem : Baekjoon 1953번 팀배분
 Problem address : https://www.acmicpc.net/problem/1953
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1953_팀배분;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생과 각 학생들이 싫어하는 학생이 주어진다.
        // 싫어하는 관계는 서로가 서로를 싫어한다.
        // 학생들을 두 팀으로 나누되, 서로 싫어하는 관계를 가진 학생들을 한 팀으로 넣지 않는다고 할 때
        // 각 팀에 속한 학생의 수와 학생의 번호를 오름차순으로 출력하라
        //
        // 그래프 탐색 문제. BFS, DFS
        // 학생들은 싫어하는 것이 서로 양방향이므로, 학생을 차례대로 탐색하며
        // 서로 싫어하는 학생들은 다른 팀에 배분시키는 과정을 반복한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 학생의 수
        int n = Integer.parseInt(br.readLine());
        // 서로 싫어하는 관계를 리스트로 정리
        List<List<Integer>> dislikes = new ArrayList<>(n + 1);
        for (int i = 0; i < n + 1; i++)
            dislikes.add(new ArrayList<>());
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            for (int j = 0; j < num; j++)
                dislikes.get(i + 1).add(Integer.parseInt(st.nextToken()));
        }

        // 두 팀으로 나눈다.
        List<PriorityQueue<Integer>> teams = new ArrayList<>();
        for (int i = 0; i < 2; i++)
            teams.add(new PriorityQueue<>());

        for (int i = 0; i < n; i++) {
            // 만약 학생 i + 1이 팀이 속해있다면, 다른 학생과의 싫어하는 관계를 통해
            // 팀 배분이 끝난 경우. 건너 뛴다.
            if (teams.get(0).contains(i + 1) || teams.get(1).contains(i + 1))
                continue;
            // 그렇지 않을 경우, 탐색이 되지 않은 경우이므로
            // 임의로 0번 팀에 포함시키고 싫어하는 관계 탐색을 시작한다.
            teams.get(0).offer(i + 1);
            splitTeams(i + 1, 0, dislikes, teams);
        }

        // 각 팀의 팀원 수와 팀원들을 출력한다.
        StringBuilder sb = new StringBuilder();
        for (PriorityQueue<Integer> team : teams) {
            sb.append(team.size()).append("\n");
            while (!team.isEmpty())
                sb.append(team.poll()).append(" ");
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 팀을 나눈다.
    static void splitTeams(int n, int team, List<List<Integer>> dislikes, List<PriorityQueue<Integer>> teams) {
        teams.get(team).add(n);

        // 싫어하는 사람들
        for (int dislike : dislikes.get(n)) {
            // 아직 팀 배분이 끝나지 않은 학생이라면
            if (!teams.get((team + 1) % 2).contains(dislike)) {
                // n과 다른 팀에 배분하고
                teams.get((team + 1) % 2).add(dislike);
                // 재귀적으로 탐색을 한다.
                splitTeams(dislike, (team + 1) % 2, dislikes, teams);
            }
        }
    }
}