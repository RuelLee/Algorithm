/*
 Author : Ruel
 Problem : Baekjoon 3075번 Astromeeting
 Problem address : https://www.acmicpc.net/problem/3075
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3075_Astromeeting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // p개의 은하가 주어지며, n명의 사람이 서로 모여 만남을 갖고자 한다.
        // q개의 은하길로 통해 은하들은 서로 연결되어있다.
        // 이 때 이동 비용은, 총 이동 거리의 제곱으로 계산한다.
        // 모든 사람이 최소 비용으로 모일 수 있는 은하의 번호와 그 때의 비용을 출력하라
        //
        // 플로이드-워셜 문제
        // 결국에는 각각의 은하에서 다른 은하로 이동하는 최소 거리를 모두 구해야하며
        // 각 인원들이 모일 때의 총 비용의 합 또한 구해야한다.
        // 따라서 플로이드 워셜 알고리즘을 사용한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // T개의 테스트케이스
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < T; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // n명의 사람, p개의 은하, 주어지는 은하길의 개수 q
            int n = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            // 각 사람이 위치한 은하의 번호.
            int[] galaxies = new int[n];
            for (int i = 0; i < galaxies.length; i++)
                galaxies[i] = Integer.parseInt(br.readLine()) - 1;
            
            // 각 은하에서 다른 은하로 가는 최소 거리를 구한다.
            // 초기값으로는 은하길만 설정.
            int[][] distances = new int[p][p];
            for (int i = 0; i < distances.length; i++) {
                Arrays.fill(distances[i], Integer.MAX_VALUE);
                distances[i][i] = 0;
            }
            for (int i = 0; i < q; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()) - 1;
                int b = Integer.parseInt(st.nextToken()) - 1;
                int d = Integer.parseInt(st.nextToken());
                distances[a][b] = distances[b][a] = Math.min(distances[a][b], d);
            }

            // 플로이드 워셜
            for (int via = 0; via < distances.length; via++) {
                for (int start = 0; start < distances.length; start++) {
                    if (start == via || distances[start][via] == Integer.MAX_VALUE)
                        continue;

                    for (int end = 0; end < distances.length; end++) {
                        if (end == via || end == start || distances[via][end] == Integer.MAX_VALUE)
                            continue;

                        distances[start][end] = Math.min(distances[start][end], distances[start][via] + distances[via][end]);
                    }
                }
            }

            // 각 은하에 모일 때, 비용 합을 구해 최소가 되는 은하를 찾는다.
            int idx = 0;
            // 비용이 Integer 범위를 넘어갈 수 있음에 주의
            long min = Long.MAX_VALUE;
            for (int i = 0; i < p; i++) {
                long sum = 0;
                for (int g : galaxies)
                    sum += Math.pow(distances[i][g], 2);

                if (sum < min) {
                    min = sum;
                    idx = i;
                }
            }
            // 해당 은하의 번호와 비용.
            sb.append(idx + 1).append(" ").append(min).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}