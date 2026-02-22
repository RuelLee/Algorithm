/*
 Author : Ruel
 Problem : Baekjoon 
 Problem address : https://www.acmicpc.net/problem/
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14746_ClosestPair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int c1 = Integer.parseInt(st.nextToken());
        int c2 = Integer.parseInt(st.nextToken());

        int[] p = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            p[i] = Integer.parseInt(st.nextToken());

        int[] q = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++)
            q[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(p);
        Arrays.sort(q);

        int i = 0, j = 0;
        int minDistance = Integer.MAX_VALUE;
        int same = 1;
        while (i < n && j < m) {
            int distance = Math.abs(p[i] - q[j]);
            if (distance < minDistance) {
                minDistance = distance;
                same = 1;
            } else if (distance == minDistance)
                same++;

            if (p[i] <= q[j])
                i++;
            else
                j++;
        }
        System.out.println((minDistance + Math.abs(c1 - c2)) + " " + same);
    }
}