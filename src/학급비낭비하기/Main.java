/*
 Author : Ruel
 Problem : Baekjoon 14498번 학급비 낭비하기
 Problem address : https://www.acmicpc.net/problem/14498
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 학급비낭비하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<HashSet<Integer>> conflicts;
    static int[] matched;

    public static void main(String[] args) throws IOException {
        // 고양이와 개(https://www.acmicpc.net/problem/3683)와 유사한 문제
        // 각 인원은 뽁뽁이와 꼭꼭이 중 좋아하는 물품을 하나 선택하고, 물품 중 원하는 번호와 선택하지 않은 물품 중 하나의 번호를 선택한다
        // 그리고 두 요구가 모두 반영된 경우만 해당 인원이 만족했다고 한다.
        // 만족하지 않은 인원의 수의 최소값은?
        // 뽁뽁이를 좋아하는 사람들 간, 꼭꼭이를 좋아하는 사람들 간에는 충돌이 발생하지 않는다
        // 의견 충돌이 발생해 불만족하는 사람이 생기는 경우는 뽁뽁이 - 꼭꼭이를 선택한 사람들 간에 발생한다
        // 충돌이 발생한 사람들끼리 연결을 한다.
        // 서로 매칭을 시켜가며 수를 세면, 해당 충돌에 찬성하거나, 반대하는 사람들 중 적은 수가 세어진다
        // 그리면 이 수를 불만족하는 사람들의 경우로 세면 최소값이 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] people = new int[k][3];
        List<List<Integer>> bbokBBokCons = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            bbokBBokCons.add(new ArrayList<>());
        List<List<Integer>> ggokGGokCons = new ArrayList<>();
        for (int i = 0; i < m + 1; i++)
            ggokGGokCons.add(new ArrayList<>());
        for (int i = 0; i < people.length; i++) {
            st = new StringTokenizer(br.readLine());
            people[i][0] = Integer.parseInt(st.nextToken());
            people[i][1] = Integer.parseInt(st.nextToken());
            people[i][2] = Integer.parseInt(st.nextToken());
            if (people[i][2] == 0)      // 뽁뽁이에 찬성하는 사람
                bbokBBokCons.get(people[i][0]).add(i);
            else        // 꼭꼭이에 찬성하는 사람
                ggokGGokCons.get(people[i][1]).add(i);
        }

        conflicts = new ArrayList<>();      // n번 사람과 충돌이 발생하는 m을 기록해둔다.
        for (int i = 0; i < k; i++)
            conflicts.add(new HashSet<>());

        for (int i = 0; i < people.length; i++) {
            if (people[i][2] == 0)      // 뽁뽁이를 선택한 사람은 people[i][1]에 찬성한 사람들과 충돌이 발생한다.
                conflicts.get(i).addAll(ggokGGokCons.get(people[i][1]));
            else {      // 꼭꼭이를 선택한 사람은 뽁뽁이 people[i][0]을 선택한 사람들과 충돌이 발생한다.
                for (int p : bbokBBokCons.get(people[i][0]))
                    conflicts.get(p).add(i);
            }
        }
        matched = new int[k];       // 매칭되었는지, 누구와 매칭되었는가
        Arrays.fill(matched, -1);
        int count = 0;
        for (int i = 0; i < k; i++) {
            if (matching(i, new boolean[k]))
                count++;
        }
        System.out.println(count);
    }

    static boolean matching(int a, boolean[] visited) {
        if (visited[a])         // 사이클이 발생해 다시 a번 사람으로 돌아온 경우
            return false;       // 불가능
        visited[a] = true;      // 방문 체크

        for (int cons : conflicts.get(a)) {     // 충돌이 발생한 반대 입장의 사람
            // 매칭이 안되었거나, 매칭된 사람을 다른 사람과 매칭으로 돌릴 수 있다면
            if (matched[cons] == -1 || matching(matched[cons], visited)) {
                matched[cons] = a;      // cons와 a를 매칭하고 true 반환
                return true;
            }
        }
        // 못 찾았다면 false 리턴.
        return false;
    }
}