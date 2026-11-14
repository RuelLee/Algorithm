/*
 Author : Ruel
 Problem : Jungol 8083번 Player-based Team Distribution
 Problem address : https://jungol.co.kr/problem/8083
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8083_PlayerBasedTeamDistribution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 선수와 점수가 주어진다.
        // 선수들을 1개 이상의 팀으로 나누며, 모든 선수들은 팀에 속해야한다.
        // 각 팀의 점수 합은 팀에 속한 인원 수 * 선수들의 점수로 구해진다.
        // 모든 팀의 점수 합계가 최대가 되게끔 팀을 배정하고자 할 때, 그 값은?
        //
        // 정렬, 그리디 문제
        // 선수의 점수가 양수 혹은 0인 경우, 당연히 한 팀으로 몰아 팀의 인원수를 늘려 곱하는 값을 크게 할수록 좋다.
        // 그럼 음수인 경우는 무조건 별개의 팀으로 하나씩 구분하는게 좋느냐하면 그건 또 아니다.
        // 음수인 선수가 팀에 포함되어 합산 점수를 까먹는 값보다 인원이 +1 되며 늘어나는게 더 유리할 수도 있다.
        // 또는 별개의 팀으로써의 점수 합이 계산되는 것보다 팀으로 배정되는게 감소되는 점수가 더 적은 경우도 있다.
        // 따라서 정렬한 뒤, 선수들의 점수를 큰 값에서부터 포함시켜나가며
        // 현재 팀 내 선수들의 점수 합과 팀 점수를 가지고 가면서,
        // 새로운 선수가 팀에 포함됐을 때의 점수와 새로운 선수가 별개의 팀일 때의 점수를 비교하여 더 큰 쪽을 택한다.
        // 그렇게 한 팀이 정해지고 나면, 나머지 선수들을 각각 별개의 팀으로 배정한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 선수
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 선수들의 점수
        int[] players = new int[n];
        for (int i = 0; i < n; i++)
            players[i] = Integer.parseInt(st.nextToken());
        // 정렬
        Arrays.sort(players);

        int idx = n - 1;
        long sum = 0;
        long score = 0;
        // 선수들을 점수에 대해 내림차순으로 살펴보며, idx번 선수를 한 팀으로 묶었을 때가 별개의 팀으로 분리했을 때보다
        // 점수 합산이 큰 경우에 한해 계산 추가시켜나간다.
        while (idx >= 0 && score + players[idx] <= (sum + players[idx]) * (n - idx)) {
            sum += players[idx];
            score = sum * (n - 1 - idx + 1);
            idx--;
        }

        // 나머지 선수들을 별개의 팀으로 배정한다.
        while (idx >= 0)
            score += players[idx--];
        // 답 출력
        System.out.println(score);
    }
}