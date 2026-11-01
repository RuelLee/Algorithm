/*
 Author : Ruel
 Problem : Jungol 13656번 연속된 소수의 합
 Problem address : https://jungol.co.kr/problem/13656
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_13656_연속된소수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static final int MAX = 10_000;

    public static void main(String[] args) throws IOException {
        // 1만 이하의 수가 주어질 때,
        // 해당 수를 연속된 소수의 합으로 표현하는 경우의 수를 구하라
        //
        // 브루트 포스, 에라토스테네스의 체
        // 1만 이하의 소수를 구하면 약 2천개이다.
        // 모든 연속한 구간을 구하면, 2000C2 = 약 200만으로 충분히 작은 크기이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 에라토스테네스의 체로 범위 내 소수를 구한다.
        boolean[] notPrimeNum = new boolean[MAX + 1];
        notPrimeNum[1] = true;
        List<Integer> primeNums = new ArrayList<>();
        for (int i = 2; i < notPrimeNum.length; i++) {
            if (notPrimeNum[i])
                continue;

            primeNums.add(i);
            for (int j = 2; j * i < notPrimeNum.length; j++)
                notPrimeNum[i * j] = true;
        }

        // 각 수를 연속된 소수의 합으로 표현할 수 있는 개수를 구한다.
        int[] cnts = new int[MAX + 1];
        // i ~ j까지의 합을 구함
        for (int i = 0; i < primeNums.size(); i++) {
            int sum = 0;
            for (int j = i; j < primeNums.size(); j++) {
                sum += primeNums.get(j);
                // 만약 합이 범위를 벗어났다면 종료
                if (sum > MAX)
                    break;
                // 아니라면 경우의 수 하나 추가
                cnts[sum]++;
            }
        }

        // 반복되는 입력 처리
        int input = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (input != 0) {
            // 답 기록
            sb.append(cnts[input]).append("\n");
            input = Integer.parseInt(br.readLine());
        }
        // 전체 답 처리
        System.out.print(sb);
    }
}