/*
 Author : Ruel
 Problem : Jungol 8443번 공주 파티
 Problem address : https://jungol.co.kr/problem/8443
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8443_공주파티;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 공주가 xi 위치에 있다.
        // 파티장에 가기 전에 ti만큼의 시간을 들여 화장을 하고, 파티장으로 이동한다.
        // 모든 공주들이 도착했을 때, 파티가 시작한다고 했을 때, 어느 위치에서 파티를 하는 것이
        // 가장 빨리 파티를 시작할 수 있는가?
        //
        // 수학
        // 화장이 없다고 한다면, 가장 왼쪽과 가장 오른쪽에 있는 공주들의 위치 평균을 내면 된다.
        // 가장 오래 걸리는 사람
        // k라는 위치에서 파티를 한다고 한다면, ti + |k - xi| 값이 각 공주들의 파티장 도착 시간이 된다.
        // k가 xi보다 같거나 오른쪽인 경우, ti + k - xi
        // 왼쪽인 경우 ti - k + xi가 된다.
        // 각 공주들의 두 경우 값을 모두 구해, 가장 큰 두 값을 고르면
        // ta + k - xa, tb - k + xb가 될텐데 이 두 값이 같아지는 경우가 가장 작은 이동 시간 (= 가장 이른 파티 시작 시간)이 된다.
        // 따라서 ti - xi 중 가장 큰 값과 ti + xi 중 가장 큰 값의 평균이 파티 시작 시간이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 공주
        int n = Integer.parseInt(br.readLine());

        // 각 공주들의 위치와 화장 시간
        int[][] arrays = new int[2][n];
        StringTokenizer st;
        for (int i = 0; i < arrays.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                arrays[i][j] = Integer.parseInt(st.nextToken());
        }

        int[] max = new int[2];
        max[0] = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            // ti - xi
            max[0] = Math.max(max[0], arrays[1][i] - arrays[0][i]);
            // ti + xi
            max[1] = Math.max(max[1], arrays[1][i] + arrays[0][i]);
        }

        // 두 값의 평균
        double answer = ((double) max[1] - max[0]) / 2;
        System.out.printf("%.1f%n", answer);
    }
}