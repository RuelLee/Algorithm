/*
 Author : Ruel
 Problem : Baekjoon 7453번 합이 0인 네 정수
 Problem address : https://www.acmicpc.net/problem/7453
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7453_합이0인네정수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기가 같은 네 배열이 주어진다.
        // 각 배열에서 하나의 수를 뽑아 모두 더했을 때, 그 합이 0인 경우의 개수를 구하라
        //
        // 정렬, 두 포인터
        // 크기가 최대 4000으로 주어지므로 4개를 모두 각각으로 뽑으면 4000 ^ 4으로 계산할 수 없다.
        // 따라서 두 배열씩 나누어, 각각 합으로 가능한 값을 최대 4000 ^ 2개씩 만들고
        // 그 후, 두 포인터를 사용하여 합이 0이 되는 경우의 수를 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 크기가 n인 네 배열
        int n = Integer.parseInt(br.readLine());
        int[][] arrays = new int[4][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++)
                arrays[j][i] = Integer.parseInt(st.nextToken());
        }

        // ab의 합으로 가능한 값
        int[] ab = new int[n * n];
        // cd의 합으로 가능한 값
        int[] cd = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ab[i * n + j] = arrays[0][i] + arrays[1][j];
                cd[i * n + j] = arrays[2][i] + arrays[3][j];
            }
        }
        // 각각 정렬
        Arrays.sort(ab);
        Arrays.sort(cd);

        long count = 0;
        int j = cd.length - 1;
        // ab 배열에서는 작은 값부터 살펴보며
        for (int i = 0; i < ab.length; i++) {
            // cd 배열에서는 큰 값부터 살펴보며
            // 합이 0이 되는 지점을 찾는다.
            while (j > 0 && ab[i] + cd[j] > 0)
                j--;
            
            // 합이 0이 되는 지점을 찾았다면
            if (ab[i] + cd[j] == 0) {
                // 중복된 값들이 존재할 수 있으므로
                // ab에서 중복된 값의 개수 계산
                long left = 1;
                while (i < ab.length - 1 && ab[i] == ab[i + 1]) {
                    left++;
                    i++;
                }
                
                // cd에서 중복된 값의 개수 계산
                long right = 1;
                while (j > 0 && cd[j] == cd[j - 1]) {
                    right++;
                    j--;
                }
                // 두 수를 곱해 더해준다.
                count += left * right;
            }
        }
        // 최종적으로 찾은 네 배열에서 한 값씩을 뽑아
        // 그 합이 0인 경우의 개수 출력
        System.out.println(count);
    }
}