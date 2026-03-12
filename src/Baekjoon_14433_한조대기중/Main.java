/*
 Author : Ruel
 Problem : Baekjoon 14433번 한조 대기 중
 Problem address : https://www.acmicpc.net/problem/14433
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14433_한조대기중;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> lists;

    public static void main(String[] args) throws IOException {
        // 두 팀이 각각 n명의 선수로 이루어져있다.
        // m개의 트롤픽이 주어진다.
        // 첫 팀이 원하는 트롤픽이 k1개, 두 번째 팀이 원하는 트롤픽이 k2개 주어진다.
        // 각 팀에 최대한 원하는 트롤픽을 많이 시켜준다.
        // 첫번째 팀의 트롤픽의 수가 두번째 팀보다 적을 경우 "네 다음 힐딱이"
        // 그 외의 경우 "그만 알아보자"를 출력한다.
        //
        // 이분 매칭 문제
        // 각 팀에 대해 트롤픽을 이분 매칭을 통해 최대한 많이 매칭시켜준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 선수, m개의 트롤픽, 각 팀의 선수들이 원하는 트롤픽의 합 k1, k2
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k1 = Integer.parseInt(st.nextToken());
        int k2 = Integer.parseInt(st.nextToken());

        // 각 선수가 원하는 트롤픽
        lists = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            lists.add(new ArrayList<>());

        for (int i = 0; i < k1; i++) {
            st = new StringTokenizer(br.readLine());
            lists.get(Integer.parseInt(st.nextToken())).add(Integer.parseInt(st.nextToken()));
        }
        // 이분 매칭
        int[] matched = new int[m + 1];
        boolean[] visited = new boolean[n + 1];
        int[] counts = new int[2];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(visited, false);
            // i번 선수부터 n번 선수까지 트롤픽을 매칭
            // 된다면 count 증가
            if (bipartiteMatching(i, matched, visited))
                counts[0]++;
        }

        // 두번째 팀에 대해서도 마찬가지로 진행
        for (int i = 1; i <= n; i++)
            lists.get(i).clear();
        for (int i = 0; i < k2; i++) {
            st = new StringTokenizer(br.readLine());
            lists.get(Integer.parseInt(st.nextToken())).add(Integer.parseInt(st.nextToken()));
        }
        Arrays.fill(matched, 0);
        for (int i = 1; i <= n; i++) {
            Arrays.fill(visited, false);
            if (bipartiteMatching(i, matched, visited))
                counts[1]++;
        }

        // 두 팀의 트롤픽의 수 비교
        System.out.println(counts[0] < counts[1] ? "네 다음 힐딱이" : "그만 알아보자");
    }

    // 이분 매칭
    static boolean bipartiteMatching(int n, int[] matched, boolean[] visited) {
        // 이미 방문한 선수를 또 돌아왔다면
        // 불가능한 경우. false 반환
        if (visited[n])
            return false;

        // 방문 체크
        visited[n] = true;
        // 해당 선수의 원하는 트롤픽을 살펴보며
        for (int p : lists.get(n)) {
            // 해당 트롤픽이 아직 선택되지 않았거나, 선택한 선수를 다른 픽으로 옮길 수 있다면
            // 해당 트롤픽을 n번 선수가 가져간다.
            if (matched[p] == 0 || bipartiteMatching(matched[p], matched, visited)) {
                matched[p] = n;
                return true;
            }
        }
        // 매칭시키지 못했다면 false 반환
        return false;
    }
}