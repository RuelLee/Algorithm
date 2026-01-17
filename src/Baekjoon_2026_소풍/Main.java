/*
 Author : Ruel
 Problem : Baekjoon 
 Problem address : https://www.acmicpc.net/problem/
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2026_소풍;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int k;
    static int[][] friends;

    public static void main(String[] args) throws IOException {
        // n명의 학생이 주어진다. 이 중 k명을 소풍에 보내고자 한다.
        // f개의 친구 관계가 주어질 때, 소풍에 보내는 모든 인원을 서로 친구이게 하고 싶다.
        // 불가능하다면 -1을, 가능하다면, 낮은 번호의 학생부터 출력한다.
        // 그러한 경우가 여러개라면 사전순으로 먼저오는 것을 출력한다.
        //
        // 백트래킹, 비트마스킹 문제
        // 비트마스킹을 꼭 써야하는 건 아니지만, 쓴다면 교집합을 구할 때 편한 문제.
        // 그룹을 구성할 때, 현재 구성 인원과 추가 가능한 후보를 갖고 있어야한다.
        // 후보는 현재 모든 구성원의 친구 교집합이며 추가하려는 후보 또한 현재 그룹의 모든 인원과 친구여야한다.
        // 최종적으로 모두 살펴봤을 때, k명 이상이 되는 그룹을 발견한다면 해당 그룹을 출력한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // k명의 그룹을 소풍에 보낸다.
        // 전체 학생의 수 n, 친구 관계의 수 f
        k = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        int f = Integer.parseInt(st.nextToken());

        // 각 학생의 친구 관계
        // friends[학생 번호][n개의 비트 공간을 담기 위한 배열]
        friends = new int[n + 1][n / 32 + 1];
        for (int i = 0; i < f; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // a와 b에 서로를 친구로 담아둔다.
            friends[a][b / 32] |= (1 << (b % 32));
            friends[b][a / 32] |= (1 << (a % 32));
        }

        // 그룹 구성 인원
        int[] group = new int[n / 32 + 1];
        // 가능한 후보 목록
        int[] candidates = new int[n / 32 + 1];
        boolean found = false;
        for (int i = 1; i <= n; i++) {
            Arrays.fill(group, 0);
            // 현재 그룹 구성원 i 혼자.
            group[i / 32] |= (1 << (i % 32));
            // 후보 목록 i의 친구들
            for (int j = 0; j < friends[i].length; j++)
                candidates[j] = friends[i][j];
            // 해당 경우로 k명의 그룹을 구성하는 것이 가능하다면
            // 반복문 종료
            if (findAnswer(i, 1, group, candidates)) {
                found = true;
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        // 찾지 못한 경우, -1을 출력
        if (!found)
            sb.append(-1).append("\n");
        else {
            // 그 외의 경우 친구들 목록을 답안에 기록
            for (int i = 1; i <= n; i++) {
                if ((group[i / 32] & (1 << (i % 32))) != 0)
                    sb.append(i).append("\n");
            }
        }
        // 답 출력
        System.out.print(sb);
    }

    // 백트래킹
    // idx 현재 학생 번호, cnt 그룹 구성원이 수, group 구성원의 bit 정보
    // candidates 후보의 bit 정보
    static boolean findAnswer(int idx, int cnt, int[] group, int[] candidates) {
        // k명의 그룹이 탄생했다면 바로 true 반환
        if (cnt >= k)
            return true;
            // 마지막 학생까지 도달했는데 k명의 그룹을 구성하지 못했다면
            // false 반환
        else if (idx == friends.length)
            return false;

        // 새로운 후보 목록
        int[] temp = new int[candidates.length];
        // idx보다 큰 값에 대해서만 찾는다
        for (int i = idx + 1; i < friends.length; i++) {
            // 후보 목록에 있으며
            // 현재 구성원 모두를 친구로 갖는다면
            if ((candidates[i / 32] & (1 << (i % 32))) != 0
                    && canMakeGroup(group, i)) {
                // i를 그룹에 구성
                group[i / 32] |= (1 << (i % 32));
                // 후보 목록 중 i의 친구들만 모두 추려 새로운 후보 목록을 작성
                for (int j = 0; j < temp.length; j++)
                    temp[j] = (candidates[j] & friends[i][j]);
                // 해당 경우로 답을 찾은 경우 true 반환
                if (findAnswer(i, cnt + 1, group, temp))
                    return true;

                // 그렇지 못한 경우, i를 그룹에 제외
                group[i / 32] -= (1 << (i % 32));
            }
        }
        // 못 찾은 경우 false 반환
        return false;
    }

    // group의 모든 구성원들이 candidate와 친구인지 확인
    static boolean canMakeGroup(int[] group, int candidate) {
        for (int i = 0; i < friends[candidate].length; i++) {
            // 친구가 아닌 구성원이 있다면 false 반환
            if ((group[i] & friends[candidate][i]) != group[i])
                return false;
        }
        // 맞다면 true 반환
        return true;
    }
}