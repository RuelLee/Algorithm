/*
 Author : Ruel
 Problem : Baekjoon 1956번 운동
 Problem address : https://www.acmicpc.net/problem/1956
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 운동;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 모든 경유 경로를 판단해서 출발지와 도착지가 같은 최소 비용 경로를 선택해야한다.
        // 모든 경로 -> 플로이드-와샬
        Scanner sc = new Scanner(System.in);
        int v = sc.nextInt();
        int e = sc.nextInt();

        int[][] roads = new int[v][v];
        for (int[] rd : roads)
            Arrays.fill(rd, 4000000);   // 충분히 큰 값으로 미리 비용을 채워준다.

        for (int i = 0; i < e; i++)
            roads[sc.nextInt() - 1][sc.nextInt() - 1] = sc.nextInt();

        for (int via = 0; via < roads.length; via++) {
            for (int start = 0; start < roads.length; start++) {
                if (via == start)
                    continue;
                for (int end = 0; end < roads.length; end++) {
                    if (via == end)     // 단일 경로로서 출발지와 도착지가 같은 곳만 제외하면 된다.
                        continue;

                    if (roads[start][end] > roads[start][via] + roads[via][end])
                        roads[start][end] = roads[start][via] + roads[via][end];
                }
            }
        }
        int min = 4000000;
        for (int i = 0; i < roads.length; i++)
            min = Math.min(min, roads[i][i]);
        System.out.println(min != 4000000 ? min : -1);
    }
}