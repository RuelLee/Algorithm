/*
 Author : Ruel
 Problem : Baekjoon 
 Problem address : 
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2253_점프;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Jump {
    int loc;
    int length;

    public Jump(int loc, int length) {
        this.loc = loc;
        this.length = length;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] dp = new int[n + 1][142];
        for (int[] d : dp)
            Arrays.fill(dp, Integer.MAX_VALUE);

    }
}