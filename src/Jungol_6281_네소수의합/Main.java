/*
 Author : Ruel
 Problem : Jungol 6281번 네 소수의 합
 Problem address : https://jungol.co.kr/problem/6281
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_6281_네소수의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // 어떤 네 개의 소수를 더한 합이 n이라고 한다
        // 가능한 네 개의 소수를 출력하라
        // 불가능하다면 NONE를 출력한다
        //
        // 에라토스테네스의 체, 백트래킹 문제
        // 에레토스테네스의 체로 소수들을 구한 뒤, 큰 소수부터 빼나가며, n이 네 개의 소수의 합으로 이루어졌는지 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어진 값 n
        int n = Integer.parseInt(br.readLine());

        // 에라토스테네스의 체
        boolean[] notPrimeNums = new boolean[n + 1];
        // 소수를 리스트로 정리
        List<Integer> primeNums = new ArrayList<>();
        for (int i = 2; i < notPrimeNums.length; i++) {
            if (notPrimeNums[i])
                continue;
            primeNums.add(i);

            for (int j = 2; i * j < notPrimeNums.length; j++)
                notPrimeNums[i * j] = true;
        }
        // 답을 배열로 받음
        int[] answer = new int[4];
        // 만약 불가능하다면 NONE 출력
        if (!backTracking(0, n, primeNums, answer))
            System.out.println("NONE");
        else {
            // 가능하다면 answer 배열로 답을 작성
            StringBuilder sb = new StringBuilder();
            sb.append(answer[3]);
            for (int i = 2; i >= 0; i--)
                sb.append(" ").append(answer[i]);
            // 출력
            System.out.println(sb);
        }
    }

    // 현재 idx+1번째 수를 고를 차례이고, 남은 합이 remain.
    // 소수 배열 primeNums, 답안 배열 array
    static boolean backTracking(int idx, int remain, List<Integer> primeNums, int[] array) {
        // 마지막 수까지 고른 후
        if (idx == 4) {
            // 남은 합이 0인 경우, 찾음!
            if (remain == 0)
                return true;
            // 그 외의 경우 못 찾음
            return false;
        }

        // 소수를 n보다 작지만 가장 큰 소수부터 빼 나간다.
        for (int i = primeNums.size() - 1; i >= 0; i--) {
            if (remain < primeNums.get(i))
                continue;

            // idx번째에 수를 채우고
            array[idx] = primeNums.get(i);
            // 재귀
            if (backTracking(idx + 1, remain - primeNums.get(i), primeNums, array))
                return true;
        }
        // 위에서 못 찾은 경우 false 반환
        return false;
    }
}