/*
 Author : Ruel
 Problem : Baekjoon 24533번 결합
 Problem address : https://www.acmicpc.net/problem/24533
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24533_결합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 물질이 (a, b) 꼴로 주어진다.
        // (a, b), (c, d)라는 물질을 합치면 ad + bc의 에너지가 방출되고, (a+c, b+d)의 물질이 된다고 한다.
        // 모든 물질을 하나의 물질로 합칠 때, 얻는 에너지를 최대로 하고자할 때
        // 그 값은?
        //
        // 수학 문제
        // i번 물질을 (ai, bi)로 나타내면
        // 1번 물질과 2번 물질을 합치면
        // a1 * b2 + a2 * b1의 에너지와 (a1 + a2, b1 + b2)의 물질이 남게 된다.
        // 3번 물질과 4번 물질을 합치면
        // 마찬가지로
        // a3 * b4 + a4 * b3의 에너지와 (a3 + a4, b3 + b4)의 물질이 남게 된다.
        // 이렇게 만들어진 두 물질을 합치면
        // (a1 + a2) * (b3 + b4) + (a3 + a4) * (b1 + b2)의 에너지가 생기고
        // (a1 + .. a4, b1 + .. b4)의 물질이 남는다.
        // 따라서 어떻게 물질을 합치더라도
        // a1 * (b2 + ... + bn) + a2 * (b1 + ... bn)의 에너지와
        // (a1 + ... an, b1 + .. bn)의 물질이 남게된다.
        // 따라서 그냥 임의로 다 합쳐버리면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 물질
        int n = Integer.parseInt(br.readLine());
        int[][] materials = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < materials.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < materials[i].length; j++)
                materials[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 그냥 순서대로 다 합쳐버리자
        long sum = 0;
        for (int i = 1; i < materials.length; i++) {
            sum += (long) materials[i - 1][0] * materials[i][1] + (long) materials[i - 1][1] * materials[i][0];
            for (int j = 0; j < materials[i].length; j++)
                materials[i][j] += materials[i - 1][j];
        }
        // 방출된 에너지의 값
        System.out.println(sum);
    }
}