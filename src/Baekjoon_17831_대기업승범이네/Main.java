/*
 Author : Ruel
 Problem : Baekjoon 17831번 대기업 승범이네
 Problem address : https://www.acmicpc.net/problem/17831
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17831_대기업승범이네;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> child;
    static int[] skill;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        // 트리가 주어지고, 각 노드마다의 실력이 주어진다. 루트는 1번.
        // 부모 - 자식 간에만 멘토링 관계를 형성할 수 있으며, 하나의 멘토링 관계만 참여할 수 있다.
        // 이 때 시너지 값은 멘토, 멘티 실력의 곱이라고 한다
        // 시너지의 합이 최대로 할 때, 이 때 시너지의 합은?
        // 트리에서 다이나믹 프로그래밍! DP를 활용해보자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        child = new ArrayList<>();      // 각 노드의 자식 노드들을 저장한다.
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 2; i < n + 1; i++)     // 주어진 값이 부모, 순서인 i는 자식.
            child.get(Integer.parseInt(st.nextToken())).add(i);

        st = new StringTokenizer(br.readLine());
        skill = new int[n + 1];     // 각 노드의 실력
        for (int i = 1; i < n + 1; i++)
            skill[i] = Integer.parseInt(st.nextToken());

        // dp[n][0] -> n번 노드가 멘토링 관계에 포함되지 않았을 때의 서브 트리의 시너지 합의 최대값
        // dp[n][1] -> n번 노드가 자식 노드 중 하나와 멘토링 관계를 형성할 때의 시너지 합의 최대값.
        dp = new int[n + 1][2];
        for (int[] d : dp)
            Arrays.fill(d, -1);

        System.out.println(findMentoringSet(1, false));
    }

    static int findMentoringSet(int n, boolean isNMentee) {
        if (isNMentee && dp[n][0] != -1)        // n이 멘티이고, 이미 계산된 결과가 있다면 바로 반환.
            return dp[n][0];
        else if (!isNMentee && dp[n][1] != -1)      // n이 멘티가 아닐 때, 기계산 결과가 있다면 반환.
            return dp[n][1];

        dp[n][0] = dp[n][1] = 0;        // 0으로 초기화. -1이 아님으로 방문했다는 사실을 알 수 있다.
        int maxValueWhenNMentorC = 0;       // n의 자식 노드들 중 자식 노드가 멘토가 되는 것보다, n의 멘티가 될 때 시너지 상승이 클 때 그 값을 차이값을 저장한다.
        for (int c : child.get(n)) {
            int whenNisNotMentor = findMentoringSet(c, false);      // n-c과 아무 관계가 아닐 때, c이하 서브트리의 시너지 합의 최대값
            int whenNIsMentor = findMentoringSet(c, true);      // n-c가 멘토링 관계일 때 c이하 서브 트리의 시너지 합의 최대값.
            dp[n][0] += whenNisNotMentor;       // n과 c가 아무 관계가 아닐 때의 시너지 합의 최대값은 dp[n][0]에 누적시켜간다.
            // n - c가 멘토링 관계일 때, 시너지 합이 n - c가 아무 관계도 아닐 때보다 크다면, 그 차이값을 저장한다.
            maxValueWhenNMentorC = Math.max(maxValueWhenNMentorC, whenNIsMentor + skill[n] * skill[c] - whenNisNotMentor);
        }
        // dp[n][1]은 n과 자식노드들 중 하나가 멘토링 관계일 때의 최대값이다
        // 이는 dp[n][0](n - c가 아무 관계가 아닐 때)에서 가장 상승이 큰 자식 노드와 멘토링 관계를 갖으면 된다
        // 이 때의 차이값은 maxValueWhenNMentorC으로 계산됐을 것이므로, 이를 dp[n][0]에 더한 값을 dp[n][1]로 한다.
        dp[n][1] = dp[n][0] + maxValueWhenNMentorC;

        // 계산이 끝났다.
        // n이 멘티였다면, n과 자식노드들은 관계를 형성하면 안되므로, dp[n][0]값을 리턴하고
        // n이 멘티가 아니었다면, 자식 노드와 멘토링 관계를 가져도 상관없으므로, dp[n][1]을 리턴한다.
        return isNMentee ? dp[n][0] : dp[n][1];
    }
}