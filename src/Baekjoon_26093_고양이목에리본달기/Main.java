/*
 Author : Ruel
 Problem : Baekjoon 26093번 고양이 목에 리본 달기
 Problem address : https://www.acmicpc.net/problem/26093
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26093_고양이목에리본달기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n마리의 고양이, k 종류의 리본이 주어진다.
        // 각 고양이는 각 종류의 리본마다 만족도가 다르며
        // 자신의 좌우에 위치한 고양이와 같은 색의 리본을 달고 싶어하지 않는다.
        // 모든 고양이에게 리본을 달 때, 만족도의 합이 최대가 되고자한다.
        // 그 때, 만족도의 합은?
        //
        // DP 문제
        // dp[현재리본을달고양이][고양이가단리본의색] = 만족도의 합
        // 으로 dp를 세우고, 마지막 고양이가 단 리본의 색을 가져간다.
        // 단 해당 순서의 고양이에서 만족의 합이 가장 높은 두 색의 idx를 따로 빼 두어
        // 다음 순서의 고양이가 리본을 달 때, 활용하여 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 고양이, k 종류의 리본
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 고양이의 리본 선호도
        int[][] satisfactions = new int[n][];
        for (int i = 0; i < satisfactions.length; i++)
            satisfactions[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // dp
        int[][] dp = new int[n][k];
        // 가장 만족도 합이 높은 두 idx를 계산한다.
        int[][] idxes = new int[2][2];
        Arrays.fill(idxes[0], -1);
        // 첫번째 고양이가 모든 리본을 달 때의 만족도
        for (int i = 0; i < satisfactions[0].length; i++) {
            dp[0][i] = satisfactions[0][i];
            if (idxes[0][0] == -1 || dp[0][i] > dp[0][idxes[0][0]]) {
                idxes[0][1] = idxes[0][0];
                idxes[0][0] = i;
            } else if (idxes[0][1] == -1 || dp[0][i] > dp[0][idxes[0][1]])
                idxes[0][1] = i;
        }

        for (int i = 1; i < dp.length; i++) {
            // 이번 순서의 만족도 합이 가장 높은 두 리본색을 저장할 공간
            Arrays.fill(idxes[i % 2], -1);
            for (int j = 0; j < dp[i].length; j++) {
                // 이전의 만족도 합이 가장 높았던 idx와 j가 서로 다르다면 해당 값을 반영하여
                // 이번 고양이에게 j번 리본을 달고
                // 같다면 두번째로 만족도 합이 높았던 idx의 만족도 합과 이번 고양이에게 j번 리본을 달 때의 만족도 합을 구한다.
                dp[i][j] = dp[i - 1][idxes[(i - 1) % 2][idxes[(i - 1) % 2][0] != j ? 0 : 1]] + satisfactions[i][j];

                // 이번 순서의 만족도 합이 가장 높은 두 리본의 idx를 찾는다.
                if (idxes[i % 2][0] == -1 || dp[i][j] > dp[i][idxes[i % 2][0]]) {
                    idxes[i % 2][1] = idxes[i % 2][0];
                    idxes[i % 2][0] = j;
                } else if (idxes[i % 2][1] == -1 || dp[i][j] > dp[i][idxes[i % 2][1]])
                    idxes[i % 2][1] = j;
            }
        }

        // 모든 고양이에게 리본을 달았을 때, 최대 만족도 합을 출력한다.
        System.out.println(Arrays.stream(dp[n - 1]).max().getAsInt());
    }
}