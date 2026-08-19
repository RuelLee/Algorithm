/*
 Author : Ruel
 Problem : Jungol 3428번 등수 찾기(ranking)
 Problem address : https://jungol.co.kr/problem/3428
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3428_등수찾기_ranking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생과 두 학생의 점수 관계가 주어진다.
        // x번 학생의 가능한 등수 범위를 출력하라
        //
        // DFS, 그래프 탐색 문제
        // x보다 직간접적으로 점수가 더 좋은 학생의 수, 더 나쁜 학생의 수를 구한 뒤
        // 이를 반영한 예상 등수의 범위를 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 학생, m개의 관계, 등수 범위를 알고 싶은 학생의 번호 x
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        // 점수 관계
        List<List<Integer>> better = new ArrayList<>();
        List<List<Integer>> worse = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            better.add(new ArrayList<>());
            worse.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            worse.get(a).add(b);
            better.get(b).add(a);
        }

        // 해당 학생의 계산되었는지 여부
        boolean[] check = new boolean[n + 1];
        // 가능한 최대 등수는 1등에서부터, 자신보다 성적이 좋은 학생의 수만큼을 더한다.
        int max = 1 + count(x, better, check) - 1;
        Arrays.fill(check, false);
        // 가장 낮은 등수는 n등에서부터 자신보다 못 본 학생의 수만큼을 뺀다.
        int min = n - (count(x, worse, check) - 1);
        System.out.println(max + " " + min);
    }

    // list를 받아, idx로부터 단방향으로 직간접적으로 이어진 노드의 개수를 센다.
    static int count(int idx, List<List<Integer>> list, boolean[] checked) {
        checked[idx] = true;
        int cnt = 1;
        for (int next : list.get(idx)) {
            if (!checked[next])
                cnt += count(next, list, checked);
        }
        return cnt;
    }
}