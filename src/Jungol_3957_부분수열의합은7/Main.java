/*
 Author : Ruel
 Problem : Jungol 
 Problem address : https://jungol.co.kr/problem/
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_3957_부분수열의합은7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] presents = new int[n];
        for (int i = 0; i < n; i++)
            presents[i] = Integer.parseInt(br.readLine().trim());

        int[][] minMax = new int[7][2];
        for (int i = 0; i < 7; i++)
            minMax[i][0] = n + 1;

        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += presents[i];
            int mod = (int) (sum % 7);
            minMax[mod][0] = Math.min(minMax[mod][0], i);
            minMax[mod][1] = Math.max(minMax[mod][1], i);
        }

        int answer = 0;
        answer = Math.max(answer, minMax[0][1] - (minMax[0][0] == 0 ? -1 : minMax[0][0]));
        for (int i = 1; i < minMax.length; i++)
            answer = Math.max(answer, minMax[i][1] - minMax[i][0]);
        System.out.println(answer);
    }
}