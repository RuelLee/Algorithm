/*
 Author : Ruel
 Problem : Baekjoon 2660번 회장뽑기
 Problem address : https://www.acmicpc.net/problem/2660
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2660_회장뽑기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 인원을 가진 모임이 주어진다
        // 각 회원의 다른 회원들과 가까운 정도를 수치화해서 점수가 가장 좋은 사람을 회장직으로 뽑으려한다.
        // 한 회원과 다른 회원이 친구 사이라면 1점.
        // 한 회원과 다른 회원이 친구의 친구 사이라면 2점.
        // 이렇게 한 다리를 건널 때마다 1점이 추가되며, 각 회원의 최종적인 점수는
        // 자신의 가장 먼 친구 사이의 점수로 책정되며, 가장 낮은 점수를 갖는 회원을 회장직으로 뽑으려한다.
        // 이 때 회장 후보의 점수와 후보 수, 그리고 후보들을 출력하라.
        // 단, 회원 사이에는 서로 모르는 사람도 있지만, 몇 사람을 통하면 모두가 서로를 알 수 있다.
        //
        // 플로이드-와샬 문제
        // 서로 간의 먼 친구 사이라도 다른 친구를 통해 더 가까운 친구가 될 수 있다면 점수를 낮출 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int members = Integer.parseInt(br.readLine());

        // 인접 행렬
        int[][] adjMatrix = new int[members][members];
        // 친구 사이 입력.
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (a == -1 && b == -1)
                break;

            adjMatrix[a - 1][b - 1] = adjMatrix[b - 1][a - 1] = 1;
        }

        // 경유 친구
        for (int via = 0; via < adjMatrix.length; via++) {
            // 회원 A
            for (int start = 0; start < adjMatrix.length; start++) {
                // A와 경유 친구가 같은 사람이거나, 서로 모르는 사이라면 건너 뛴다.
                if (start == via ||
                        adjMatrix[start][via] == 0)
                    continue;
                // 회원 B
                for (int end = 0; end < adjMatrix.length; end++) {
                    // 회원 B와 경유 친구, B와 A가 서로 같은 사람이거나
                    // 경유 친구와 B가 서로 모르는 사이일 경우 건너 뛴다.
                    if (end == via || end == start ||
                            adjMatrix[via][end] == 0)
                        continue;

                    // A와 B가 모르는 사이이거나
                    // A와 B의 사이가 via를 경유하면 더 가까운 친구 사이가 될 경우
                    // 해당 값으로 갱신해준다.
                    if (adjMatrix[start][end] == 0 || adjMatrix[start][end] > adjMatrix[start][via] + adjMatrix[via][end])
                        adjMatrix[start][end] = adjMatrix[start][via] + adjMatrix[via][end];
                }
            }
        }

        List<Integer> candidates = new ArrayList<>();
        // 후보자 점수 초기화.
        int candidatePoint = Integer.MAX_VALUE;
        for (int i = 0; i < adjMatrix.length; i++) {
            // 각 회원의 가장 먼 친구 사이의 점수.
            int score = Arrays.stream(adjMatrix[i]).max().getAsInt();
            // 만약 후보자 점수보다 이번 회원의 score가 더 낮을 경우.
            // 후보자 점수를 score로 바꾸고, 저장되어있던 후보자 목록을 비우고, 이번 후보자를 등록한다.
            if (score < candidatePoint) {
                candidatePoint = score;
                candidates.clear();
                candidates.add(i + 1);
                // 만약 후보자 점수와 score가 같은 점수를 같는다면, 해당 회원을 후보자 등록만 한다.
            } else if (score == candidatePoint)
                candidates.add(i + 1);
        }

        // 최종적으로 후보자 점수, 후보자 수
        // 그리고 후보자들을 출력한다.
        StringBuilder sb = new StringBuilder();
        sb.append(candidatePoint).append(" ").append(candidates.size()).append("\n");
        for (int candidate : candidates)
            sb.append(candidate).append(" ");
        System.out.println(sb);
    }
}