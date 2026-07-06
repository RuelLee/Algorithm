/*
 Author : Ruel
 Problem : Jungol 
 Problem address : https://jungol.co.kr/problem/
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_5393_도시와주;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int SIZE = 26 * 26;
        int[][] counts = new int[SIZE][SIZE];
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int city = NameToInt(st.nextToken());
            int state = NameToInt(st.nextToken());
            counts[state][city]++;
        }

        long sum = 0;
        for (int i = 0; i < counts.length; i++) {
            for (int j = i + 1; j < counts[i].length; j++)
                sum += (long) counts[i][j] * counts[j][i];
        }
        System.out.println(sum);
    }

    static int NameToInt(String s) {
        return (s.charAt(0) - 'A') * 26 + s.charAt(1) - 'A';
    }
}