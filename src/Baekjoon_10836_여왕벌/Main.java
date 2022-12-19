/*
 Author : Ruel
 Problem : Baekjoon 10836번 여왕벌
 Problem address : https://www.acmicpc.net/problem/10836
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10836_여왕벌;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 가로 세로 m크기의 벌집이 주어진다.
        // 처음 애벌레의 크기는 1이며
        // n일 동안 애벌레의 성장량이 주어진다.
        // 1열과 1행에 해당하는 좌측 최하단부터, 좌측 최상단 그리고 우측 최상단까지의 애벌레 성장량이 주어진다.
        // 그리고 그 외의 애벌레들은 자신보다 좌측, 좌상단, 상단의 애벌레 성장량을 비교해 가장 많이 자란 애벌레의 성장량만큼 자란다.
        // 성장량들은 감소하지 않는 형태이다.
        // n일 뒤의 애벌레의 크기를 출력하라.
        //
        // 시뮬레이션 문제...완전 탐색으로 모두 처리할 경우,
        // m이 최대 700, n이 최대 100만으로 주어지므로 시간초과가 난다.
        // 따라서 문제 조건을 살펴보고 계산을 줄일 수 있는지 확인해야한다.
        // 성장량은 줄어들지 않는 형태라 했으므로
        // 2 2 3
        // 1
        // 1        과 같이 증가하는 형태로 주어진다.
        // 조금 더 생각해보면 각 애벌레의 성장량들은
        // 1열을 제외하고서는 1행의 애벌레 성장값을 따라가게 된다.
        // 증가하는 형태이므로 1행의 값이 1열의 값보다 항상 같거나 클 것이기 때문이다.
        // 따라서 n일 동안 성장량을 누적시키고
        // 1열에 한해서는 해당값 출력 후, 나머지 열들은 자신의 최상단 행에 해당하는 값을 출력한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 가로 세로 크기 m, 진행 시일 n
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // 1열, 1행에 해당하는 값만 필요하므로 그 개수는 2 * m  - 1개
        int[] growths = new int[2 * m - 1];
        Arrays.fill(growths, 1);

        // n일
        for (int i = 0; i < n; i++) {
            // 값이 3개 주어지는데,
            st = new StringTokenizer(br.readLine());
            // 0 성장하는 애벌레의 수
            int zero = Integer.parseInt(st.nextToken());
            // 1 성장하는 애벌레의 수
            int one = Integer.parseInt(st.nextToken());
            // 2 성장하는 애벌레의 수
            int two = Integer.parseInt(st.nextToken());

            // 따라서 0 성장하는 애벌레는 건너뛰고
            // 1 성장하는 애벌레들 성장.
            for (int j = zero; j < zero + one; j++)
                growths[j] += 1;
            // 2 성장하는 애벌레들 성장.
            for (int j = zero + one; j < growths.length; j++)
                growths[j] += 2;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            // 1열에 대해서는 해당하는 growth 값 출력.
            sb.append(growths[m - i - 1]).append(" ");
            // 나머지 열에 대해서는 해당하는 1행에 해당하는 값 출력.
            for (int j = 1; j < m; j++)
                sb.append(growths[m + j - 1]).append(" ");
            sb.deleteCharAt(sb.length() - 1).append("\n");
        }
        // 전체 결과 출력.
        System.out.print(sb);
    }
}