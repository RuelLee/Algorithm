/*
 Author : Ruel
 Problem : Baekjoon 25711번 인경산
 Problem address : https://www.acmicpc.net/problem/25711
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25711_인경산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 산장들의 x좌표와 y좌표가 주어진다.
        // 산장들의 순서는 x좌표가 증가하는 형태로 주어진다.
        // 산장에서 다음 산장으로 가는데 필요한 에너지는
        // 오르막일 경우 거리 * 3
        // 평지일 경우 거리 * 2
        // 내리막길일 경우 거리 * 1 만큼 소모된다고 한다.
        // 다음 형태의 q개의 질의에 대해 답하라
        // i j : i번 산장에서 j번 산장으로 이동하는데 드는 체력 소모량
        //
        // 누적합 문제
        // i ~ j형태로 연속된 구간의 체력소모량을 구해야하기 때문에
        // 누적합을 통해 구하면 된다.
        // 다만 출발지가 도착지보다 더 크다면, 오르막, 내리막이 바뀌기 때문에
        // 앞에서 시작하는 형태, 뒤에서 시작하는 형태의 누적합을 두 종류를 구해둔다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 산장, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 산장 위치
        int[][] villas = new int[n][2];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < villas.length; i++)
            villas[i][0] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < villas.length; i++)
            villas[i][1] = Integer.parseInt(st.nextToken());

        // 앞에서 시작할 때의 누적합과
        double[] ascPsums = new double[n];
        // 뒤에서 시작하는 누적합.
        double[] descPsums = new double[n];
        for (int i = 1; i < ascPsums.length; i++) {
            // 오르막, 평지, 내리막에 따라 다르게 거리 계산하여 합한다.
            ascPsums[i] = ascPsums[i - 1] + calcDistances(i - 1, i, villas) *
                    (villas[i][1] > villas[i - 1][1] ? 3 : (villas[i][1] == villas[i - 1][1] ? 2 : 1));
            descPsums[n - 1 - i] = descPsums[n - i] + calcDistances(n - 1 - i, n - i, villas) *
                    (villas[n - 1 - i][1] > villas[n - i][1] ? 3 : (villas[n - 1 - i][1] == villas[n - i][1] ? 2 : 1));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            // u -> v로 이동한다.
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            // 정방향일 경우, ascPsums[v] - ascPsums[u]
            // 역방향일 경우, descPsums[v] - descPsums[u]
            sb.append(u < v ? ascPsums[v] - ascPsums[u] : descPsums[v] - descPsums[u]).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    static double calcDistances(int a, int b, int[][] villas) {
        return Math.sqrt(Math.pow(villas[a][0] - villas[b][0], 2) +
                Math.pow(villas[a][1] - villas[b][1], 2));
    }
}