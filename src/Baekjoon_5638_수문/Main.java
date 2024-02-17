/*
 Author : Ruel
 Problem : Baekjoon 5638번 수문
 Problem address : https://www.acmicpc.net/problem/5638
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5638_수문;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 댐에 n개의 수문이 있으며, 각 수문의 시간 당 유량과 피해 비용이 주어진다.
        // m개의 테스트케이스로
        // v만큼의 물을 t시간 이내에 비워야한다는 조건이 주어졌을 때
        // 각 조건을 만족하는 최소비용을 출력하라
        //
        // 비트마스크, 브루트포스 문제
        // n이 20, t개 최대 50으로 주어지므로
        // n개의 수문으로 가능한 모든 조합을 만든 후, 비용 순으로 정렬
        // 그 후 조건을 만족하는 최소 비용을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수문
        int n = Integer.parseInt(br.readLine());
        int[][] waterGates = new int[n][2];
        for (int i = 0; i < n; i++)
            waterGates[i] = Arrays.stream(br.readLine().trim().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 가능한 모든 조합을 비트마스킹을 통해 구분.
        long[][] combinations = new long[1 << n][2];
        findCombinations(0, waterGates, combinations);
        // 비용 순 정렬
        Arrays.sort(combinations, Comparator.comparingLong(o -> o[1]));

        // m개의 테스트케이스
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            sb.append("Case ").append((i + 1)).append(": ");
            boolean found = false;
            for (int j = 1; j < combinations.length; j++) {
                // j번째 조합으로 모든 물을 비워내는 것이 가능하다면
                if (combinations[j][0] * t >= v) {
                    // 해당 비용 기록
                    sb.append(combinations[j][1]);
                    found = true;
                    break;
                }
            }
            // 테스트케이스를 만족하는 조합이 존재하지 않는다면 IMPOSSIBLE 기록
            if (!found)
                sb.append("IMPOSSIBLE");
            sb.append("\n");
        }
        // 답안 출력
        System.out.print(sb);
    }

    // 비트마스킹을 통해 가능한 조합을 모두 구한다.
    static void findCombinations(int bitmask, int[][] waterGates, long[][] combinations) {
        if (bitmask >= combinations.length)
            return;

        // 현재 상태 bitmask에 i번째 수문을 추가적으로 여는 경우를 계산한다.
        for (int i = 0; i < waterGates.length; i++) {
            int next = bitmask | (1 << i);
            // next를 아직 계산한 적이 없다면
            if (combinations[next][0] == 0) {
                // 해당하는 유량과
                combinations[next][0] += combinations[bitmask][0] + waterGates[i][0];
                // 비용 계산
                combinations[next][1] += combinations[bitmask][1] + waterGates[i][1];
                // next로 파생되는 경우들도 계산.
                findCombinations(next, waterGates, combinations);
            }
        }
    }
}