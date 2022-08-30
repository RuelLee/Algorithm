/*
 Author : Ruel
 Problem : Baekjoon 1344번 축구
 Problem address : https://www.acmicpc.net/problem/1344
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1344_축구;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] primeNumbers = {2, 3, 5, 7, 11, 13, 17};

    public static void main(String[] args) throws IOException {
        // 축구 경기를 90분간 진행하며, 5분 동안 각 팀이 득점할 확률이 주어진다
        // 경기가 끝났을 때, 적어도 한 팀이 소수개의 골을 기록할 확률은 얼마인가?
        //
        // 조합을 이용한 문제
        // 먼저 한 팀이 5분마다 골을 기록할 확률이 p라 할 때 n개의 골을 기록할 확률은
        // 18Cn * (p^n) * ((1 - p)^(18 - n)) 으로 나타낼 수 있다.
        // 따라서 각 팀에 대해 소수 개의 골의 확률을 모두 구해 더해준 뒤
        // 각 확률은 상대팀 또한 소수 개의 골을 득점할 확률이 중복으로 계산되어있으므로
        // 두 확률을 곱한 값을 빼준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        double aPercent = Integer.parseInt(br.readLine()) / (double) 100;
        double bPercent = Integer.parseInt(br.readLine()) / (double) 100;

        // 파스칼의 삼각형을 이용하여 combination 값을 구해주자.
        int[][] combinations = new int[19][19];
        combinations[0][0] = 1;
        for (int i = 1; i < combinations.length; i++) {
            combinations[i][0] = combinations[i - 1][0];
            for (int j = 1; j <= i; j++)
                combinations[i][j] = combinations[i - 1][j - 1] + combinations[i - 1][j];
        }
        
        // 각 팀이 소수 개의 골을 득점할 확률
        double pa = 0;
        double pb = 0;
        for (int primeNumber : primeNumbers) {
            // 18∏primeNumber * p^primbrNumer * (1 - p)^(18 - primeNumber)
            // 각 팀이 primeNumber 개의 골을 기록할 확률을 더해준다.
            pa += combinations[18][primeNumber] * Math.pow(aPercent, primeNumber) * Math.pow((1 - aPercent), 18 - primeNumber);
            pb += combinations[18][primeNumber] * Math.pow(bPercent, primeNumber) * Math.pow((1 - bPercent), 18 - primeNumber);
        }

        // 최종적으로 구해진 확률에서 교집합으로 중복 계산된 두 팀 모두 소수개의 골을 득점할 확률을
        // 한번 빼준다.
        System.out.println(pa + pb - pa * pb);
    }
}