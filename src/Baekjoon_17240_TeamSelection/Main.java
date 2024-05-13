/*
 Author : Ruel
 Problem : Baekjoon 17240번 Team Selection
 Problem address : https://www.acmicpc.net/problem/17240
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17240_TeamSelection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static int[][] players;
    static List<List<Integer>> orders;

    public static void main(String[] args) throws IOException {
        // 5명이 5개의 역할군으로 진행하는 게임을 한다.
        // n명의 선수들에 대해 각 역할군에 대한 실력이 주어진다.
        // 5명을 뽑아 서로 다른 역할을 맡게하여 팀을 이루고자할 때
        // 팀에 속한 선수들의 각자 맡은 역할 실력 합의 최대 값은?
        //
        // 정렬, 브루트포스, 백트래킹 문제
        // 선수들을 각 역할에 따른 실력을 내림차순으로 정렬한다.
        // 그 후, 각 역할마다 최대 5위까지 살펴보며 백트래킹과 브루트포스를 활용하여
        // 가능한 모든 팀 조합을 살펴보고, 실력 합의 최대값을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 선수
        int n = Integer.parseInt(br.readLine());
        players = new int[n][];
        for (int i = 0; i < players.length; i++)
            players[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 역할에 따른 선수들의 실력
        orders = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int order = i;
            orders.add(new ArrayList<>());
            for (int j = 0; j < n; j++)
                orders.get(i).add(j);
            // 내림차순 정렬
            orders.get(i).sort((o1, o2) -> Integer.compare(players[o2][order], players[o1][order]));
        }
        // 가능한 조합에 따른 최대 실력 합 출력
        System.out.println(getMaxScore(0, new boolean[n], 0));
    }

    // 브루트 포스, 백트래킹을 활용하여 최대 실력 합을 구한다.
    static int getMaxScore(int idx, boolean[] selected, int sum) {
        // idx가 5까지 진행되었다면
        // 모든 역할에 선수들 배분이 끝났다.
        // 해당 점수를 반환.
        if (idx >= 5)
            return sum;

        // idx번째 역할에 선수를 할당한다.
        int max = 0;
        for (int i = 0; i < 5; i++) {
            // idx번째 역할에서 i번째로 실력이 좋은 선수의 번호
            int playerIdx = orders.get(idx).get(i);
            // 해당 선수가 아직 다른 역할에 선택되지 않았다면
            if (!selected[playerIdx]) {
                // 선택 표시
                selected[playerIdx] = true;
                // 그리고 해당 선수를 idx번째 역할에 채용하고
                // idx+1번째 역할 순서로 넘어간다.
                // 파생되는 경우를 모두 구해 그 때의 실력합이 max를 갱신하는지 확인.
                max = Math.max(max, getMaxScore(idx + 1, selected, sum + players[playerIdx][idx]));
                // 선택 표시 취소
                selected[playerIdx] = false;
            }
        }
        // 찾은 최대 실력 합 값을 반환한다.
        return max;
    }
}