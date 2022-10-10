/*
 Author : Ruel
 Problem : Baekjoon 156601번 링크와 스타트
 Problem address : https://www.acmicpc.net/problem/15661
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15661_링크와스타트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int n;
    static int[][] synergies;
    static int[] teamDiffs;

    public static void main(String[] args) throws IOException {
        // n명의 선수가 주어진다.
        // 각 선수는 다른 선수와 한 팀에 속할 경우 시너지를 낸다.
        // n명의 선수를 두 팀으로 나누어(한 팀에는 최소 한명의 선수가 포함) 시너지 합의 차이가 최소가 되도록 만들고자 한다.
        // 이 때 최소 시너지 합의 차이는?
        //
        // 비트마스킹을 활용한 브루트포스 문제
        // n이 20까지 주어지므로, 조합을 통해 모든 경우의 수를 따져볼 수 있다.
        // 하지만 만약 4명의 선수가 있는데, 1, 4번이 한 팀이 되는 경우와 2, 3번이 한 팀이 되는 경우는
        // 결국 같은 경우라고 볼 수 있으므로 이 점을 생각하면 연산을 반으로 줄일 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 선수의 수
        n = Integer.parseInt(br.readLine());
        // 각 선수가 다른 선수와 함께 뛸 때 내는 시너지.
        synergies = new int[n][];
        Arrays.fill(teamDiffs, -1);
        for (int i = 0; i < synergies.length; i++)
            synergies[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 팀 구성에 따른 두 팀 간의 시너지 차이.
        teamDiffs = new int[1 << n];

        // 최소 시너지 차이를 출력한다.
        System.out.println(findMinGapTeams(0, 0));
    }

    // 선수들을 두 팀으로 나누는 모든 경우의 수를 따져보고 최소 시너지 합의 차이를 구한다.
    static int findMinGapTeams(int idx, int aTeamBitmask) {
        // 마지막 선수까지 배정이 끝났다면
        if (idx == n) {
            // 혹시 한쪽 팀에 모든 선수가 몰려있을 경우
            // 큰 값을 리턴.
            if (aTeamBitmask + 1 == Math.pow(2, n) ||
                    aTeamBitmask == 0)
                return Integer.MAX_VALUE;
            // 이미 해당 경우를 구한 적이 있다면 값만 리턴.
            else if (teamDiffs[aTeamBitmask] != -1)
                return teamDiffs[aTeamBitmask];

            // 위 두 경우가 아니라면 비트마스크를 보고 각 팀의 시너지 합을 구한다.
            int[] teamSum = new int[2];
            for (int i = 0; i < synergies.length; i++) {
                // i가 속한 팀
                int teamOfI = (aTeamBitmask & (1 << i)) == 0 ? 0 : 1;
                for (int j = 0; j < synergies[i].length; j++) {
                    if (i == j)
                        continue;
                    // j가 속한 팀
                    int teamOfJ = (aTeamBitmask & (1 << j)) == 0 ? 0 : 1;
                    // i와 j의 팀이 같다면 i의 시너지를 더해준다.
                    if (teamOfI == teamOfJ)
                        teamSum[teamOfI] += synergies[i][j];
                }
            }
            // 각 팀의 시너지 합 계산이 끝났다.
            // A팀에 속한 선수들을 나타내는 비트마스크 aTeamBitmask와
            // B팀에 속한 선수들을 나타내는 비트마스크 (1 << n) - 1 - aTeamBitmask는
            // 결국 같은 선수들로 두 팀을 나눈 것이므로
            // 두 비트마스크에 모두 값을 저장해준다.
            return teamDiffs[aTeamBitmask] = teamDiffs[(1 << n) - 1 - aTeamBitmask] = Math.abs(teamSum[0] - teamSum[1]);
        }

        // idx 선수를 aTeam에 배정했을 때와, idx 선수를 bTeam에 배정했을 때, 두 가지 경우를 모두 계산하여
        // 더 적은 시너지 차이를 갖는 경우를 반환한다.
        return Math.min(findMinGapTeams(idx + 1, aTeamBitmask | (1 << idx)),
                findMinGapTeams(idx + 1, aTeamBitmask));
    }
}