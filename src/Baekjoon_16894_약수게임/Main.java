/*
 Author : Ruel
 Problem : Baekjoon 16894번 약수 게임
 Problem address : https://www.acmicpc.net/problem/16894
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16894_약수게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static HashMap<Long, Boolean> memo;

    public static void main(String[] args) throws IOException {
        // 처음에 정수 n이 적혀있다.
        // 그 수를 지우고 1과 n이 아닌 n의 약수 중 하나를 적어나간다.
        // 더 이상 적을 수 없는 사람이 게임을 이긴다.
        // 두 사람이 최적의 방법으로 게임을 했을 때, 이기는 사람을 구하라
        // koosaga와 cubelover가 게임에 참여하며, koosaga가 먼저 게임을 진행한다.
        //
        // 게임 이론, 소수 판정 문제
        // 게임 이론에 따라 해당 수를 맞딱뜨린 사람이 이기는지 지는지 여부를 판별하여
        // 해쉬맵에 저장하고 자신이 이기는 경우가 한 가지라도 존재하는지 찾는다.
        // 그러한 경우가 존재하지 않는 경우에만 지고, 한가지라도 존재하는 경우에는 이긴다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 수 n
        long n = Long.parseLong(br.readLine());
        memo = new HashMap<>();
        
        // 처음 n을 접한 사람이 이기는 경우 koosaga 출력
        // 아닐 경우 cubelover 출력
        System.out.println(findAnswer(n) ? "koosaga" : "cubelover");
    }

    // n을 접한 사람이 이기는지 지는지 반환한다.
    static boolean findAnswer(long n) {
        // 이미 기록된 결과라면 값 반환
        if (memo.containsKey(n))
            return memo.get(n);

        // 이길 수 있는지
        boolean canWin = false;
        // 소수 여부
        // 소수일 경우 승리한다.
        boolean isPrimeNumber = true;
        // n이 i로 나누어떨어질 경우, i와 n / i를 모두 살펴본다.
        // 따라서 i는 제곱이 n보다 같거나 작은 시점까지만 살펴보면 된다.
        for (int i = 2; (long) i * i <= n; i++) {
            // 나누어 떨어지지 않는다면 건너뛴다.
            if (n % i != 0)
                continue;

            // 약수가 존재한다면 소수가 아니다.
            isPrimeNumber = false;
            // i나 n/i로 상대방이 무조건 지는 경우가 존재하는지 찾는다.
            // 그러한 경우가 존재한다면 n을 접한 사람이 이긴다.
            if (!findAnswer(i) || !findAnswer(n / i)) {
                canWin = true;
                break;
            }
        }

        // 소수라면 n을 접한 사람이 무조건 이기며
        if (isPrimeNumber)
            memo.put(n, true);
        else        // 아니라면 canWin에 저장된 결과를 해쉬맵에 저장한다.
            memo.put(n, canWin);
        // 결과값 반환.
        return memo.get(n);
    }
}