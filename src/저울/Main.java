/*
 Author : Ruel
 Problem : Baekjoon 10159 저울
 Problem address : https://www.acmicpc.net/problem/10159
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 저울;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 트리에서의 다이나믹프로그래밍으로 풀려다가 틀렸다.
        // 당연히 트리 구조가 아니라 그래프 구조이기 때문에...
        // 전체적으로 관계를 모두 따져야한다. -> 플로이드-와샬
        // 자신보다 가벼운 물건보다 무거운 물건은 자신과의 관계를 알 수 없다
        // 계속 가볍거나, 계속 무겁거나 일방적인 관계에서만 확실한 관계를 알 수 있다.
        // 자신보다 무거운 물건 따로, 무거운 물건 따로 계산후, 전체에서 자신을 포함해서 제해주면, 관계를 알 수 없는 물건들의 개수가 나온다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] heavyMatrix = new int[n][n];        // a가 b보다 무겁다
        int[][] lightMatrix = new int[n][n];        // a가 b보다 가볍다

        for (int[] hm : heavyMatrix)
            Arrays.fill(hm, 1);
        for (int[] lm : lightMatrix)
            Arrays.fill(lm, 1);

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;

            heavyMatrix[a][b] = 0;
            lightMatrix[b][a] = 0;
        }

        for (int via = 0; via < n; via++) {
            for (int start = 0; start < n; start++) {
                if (start == via)
                    continue;
                for (int end = 0; end < n; end++) {
                    if (end == via || end == start)
                        continue;

                    // start와 end 간의 관계가 via를 거쳐서 명확해진다면
                    // 값의 합을 넣어준다 -> 사실상 1로 초기화되어있고, 관계가 명확한 대상끼리는 0값이 설정되어있으므로
                    // 관계가 명확해진다면 0 값이 들어간다.
                    if (heavyMatrix[start][end] > heavyMatrix[start][via] + heavyMatrix[via][end])
                        heavyMatrix[start][end] = heavyMatrix[start][via] + heavyMatrix[via][end];

                    if (lightMatrix[start][end] > lightMatrix[start][via] + lightMatrix[via][end])
                        lightMatrix[start][end] = lightMatrix[start][via] + lightMatrix[via][end];
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (i == j)
                    continue;

                if (heavyMatrix[i][j] == 0)     // i가 j보다 무겁다는게 명확한 것을 센다
                    count++;
                if (lightMatrix[i][j] == 0)     // i가 j보다 가볍다는게 명확한 것을 센다.
                    count++;
            }
            sb.append(n - count - 1).append("\n");
        }
        System.out.println(sb);
    }
}