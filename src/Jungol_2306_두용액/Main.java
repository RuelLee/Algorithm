/*
 Author : Ruel
 Problem : Jungol 2306번 두 용액
 Problem address : https://jungol.co.kr/problem/2306
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2306_두용액;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] solutions = new int[n];
        for (int i = 0; i < n; i++)
            solutions[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(solutions);

        int j = n - 1;
        int diff = Math.abs(solutions[0] + solutions[n - 1]);
        int[] ans = new int[]{0, n - 1};
        for (int i = 0; i < n && i < j; i++) {
            while (i < j - 1 && solutions[i] + solutions[j] > 0) {
                if (Math.abs(solutions[i] + solutions[j]) < diff) {
                    diff = Math.abs(solutions[i] + solutions[j]);
                    ans[0] = i;
                    ans[1] = j;
                }
                j--;
            }
            if (Math.abs(solutions[i] + solutions[j]) < diff) {
                diff = Math.abs(solutions[i] + solutions[j]);
                ans[0] = i;
                ans[1] = j;
            }
        }
        System.out.println(solutions[ans[0]] + " " + solutions[ans[1]]);
    }
}