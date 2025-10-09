/*
 Author : Ruel
 Problem : Baekjoon 
 Problem address : https://www.acmicpc.net/problem/
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14938_서강그라운드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        int[] items = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < items.length; i++)
            items[i] = Integer.parseInt(st.nextToken());

        int[][] adjMatrix = new int[n][n];
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int l = Integer.parseInt(st.nextToken());

            adjMatrix[a][b] = adjMatrix[b][a] = l;
        }

        for (int via = 0; via < n; via++) {
            for (int start = 0; start < n; start++) {
                if (start == via || adjMatrix[start][via] == Integer.MAX_VALUE)
                    continue;

                for (int end = 0; end < n; end++) {
                    if (end == via || end == start || adjMatrix[via][end] == Integer.MAX_VALUE)
                        continue;

                    adjMatrix[start][end] = Math.min(adjMatrix[start][end], adjMatrix[start][via] + adjMatrix[via][end]);
                }
            }
        }

        int answer = 0;
        for (int i = 0; i < n; i++) {
            int sum = items[i];
            for (int j = 0; j < n; j++) {
                if (i == j)
                    continue;

                if (adjMatrix[i][j] <= m)
                    sum += items[j];
            }
            answer = Math.max(answer, sum);
        }
        System.out.println(answer);
    }
}