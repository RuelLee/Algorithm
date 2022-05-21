/*
 Author : Ruel
 Problem : Baekjoon 18138번 리유나는 세일러복을 좋아해
 Problem address : https://www.acmicpc.net/problem/18138
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18138_리유나는세일러복을좋아해;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> connections;
    static int[] matched;

    public static void main(String[] args) throws IOException {
        // n개의 티셔츠 너비와 m개의 카라 너비가 주어진다
        // 이 둘을 이용하여 세일러복을 만드려고 하는데,
        // 카라 너비가 티셔츠 너비의 1/2이상 3/4이하이거나, 1이상 5/4이하인 경우에만 만들 수 있다고 한다
        // 만들 수 있는 가장 많은 세일러복의 개수는 몇개인가
        //
        // 이분매칭 문제
        // 티셔츠와 카라의 너비를 갖고서 서로 가능한 경우 이어줘, 이분 그래프를 만든다
        // 그 후, 그 이분 그래프를 토대로 이분 매칭을 진행한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 티셔츠의 너비
        int[] shirts = new int[n];
        for (int i = 0; i < shirts.length; i++)
            shirts[i] = Integer.parseInt(br.readLine());

        // 카라의 너비
        int[] collars = new int[m];
        for (int i = 0; i < collars.length; i++)
            collars[i] = Integer.parseInt(br.readLine());

        // 이분 그래프의 연결을 나타낼 리스트.
        connections = new ArrayList<>();
        for (int i = 0; i < shirts.length; i++)
            connections.add(new ArrayList<>());

        for (int i = 0; i < shirts.length; i++) {
            for (int j = 0; j < collars.length; j++) {
                // 티셔츠i와 카라j가 서로 조건이 맞을 때만 연결시켜준다.
                if ((collars[j] >= (double) shirts[i] / 2 && collars[j] <= (double) shirts[i] * 3 / 4) ||
                        (collars[j] >= shirts[i] && collars[j] <= (double) shirts[i] * 5 / 4))
                    connections.get(i).add(j);
            }
        }

        // 카라에 매치된 티셔츠를 저장할 공간.
        matched = new int[m];
        Arrays.fill(matched, -1);
        int sum = 0;
        for (int i = 0; i < shirts.length; i++) {
            // i번째 티셔츠로 세일러복을 만드는 것이 가능하다면
            // sum을 하나씩 증가시켜간다.
            if (bipartiteMatching(i, new boolean[n]))
                sum++;
        }
        // 최대 만들 수 있는 세일러복의 수 출력
        System.out.println(sum);
    }

    // 이분 매칭 메소드
    static boolean bipartiteMatching(int n, boolean[] visited) {
        // 방문 체크
        visited[n] = true;

        // n 티셔츠와 어울리는 카라들
        for (int collar : connections.get(n)) {
            // 아직 다른 티셔츠와 매칭되지 않았거나
            // 아님 매칭된 티셔츠를 다른 카라와 연결이 가능한 경우
            if (matched[collar] == -1 ||
                    (!visited[matched[collar]] && bipartiteMatching(matched[collar], visited))) {
                // collar 카라와 n 티셔츠를 연결시켜준다.
                matched[collar] = n;
                // true 리턴
                return true;
            }
        }
        // 매칭이 불가능한 경우 false 리턴.
        return false;
    }
}