/*
 Author : Ruel
 Problem : Baekjoon 4370번 곱셈 게임
 Problem address : https://www.acmicpc.net/problem/4370
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4370_곱셈게임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        // 게임마다 n을 정한다.
        // 턴마다 p에 2 ~ 9까지의 수를 곱해 먼저, n이상의 수를 만드는 사람이 승리한다.
        // 두 사람 모두 완벽하게 게임을 한다고 할 때,
        // 첫 턴을 시작하는 사람이 승리하는지, 패배하는지 출력하라
        //
        // 게임 이론 문제
        // 각 턴마다 2 ~ 9 까지를 수를 곱하는 경우의 수를 따져보고
        // 해당 수에 진입한 사람이 이기는 경우가 한 경우라도 있는지 계산한다.
        // 모두 지는 경우라면 이기는 방법이 없지만, 한 경우라도 존재한다면
        // 해당 사람은 항상 그 경우를 선택할 것이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 여러 개의 테스트케이스가 주어진다.
        String input = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (input != null) {
            // n
            long n = Long.parseLong(input);

            sb.append(findAnswer(1, new HashMap<>(), n) ? "Baekjoon" : "Donghyuk").append(" wins.").append("\n");
            // 다음 테스트케이스 입력
            input = br.readLine();
        }
        System.out.print(sb);
    }

    // 재귀로 답을 찾아나간다.
    static boolean findAnswer(long num, HashMap<Long, Boolean> hashMap, long n) {
        // num이 n을 넘을 경우, 이전 턴의 사람이 승리하는 것.
        // 현재 턴에 진입한 사람은 패배
        if (num >= n)
            return false;
        // 이미 계산된 결과가 있는지 확인한다.
        else if (hashMap.containsKey(num))
            return hashMap.get(num);

        // 이기는 경우가 하나라도 존재하는지 계산한다/
        boolean canWin = false;
        // 2 ~ 9까지의 수를 곱한다.
        for (int i = 2; i < 10; i++) {
            // 다음 턴 사람이 패배하는 경우밖에 없다면
            // 내가 승리할 수 있다.
            if (!findAnswer(num * i, hashMap, n)) {
                // 답 기록 후 종료.
                canWin = true;
                break;
            }
        }
        // 해쉬맵에 기록 추가 후, 결과 리턴.
        hashMap.put(num, canWin);
        return canWin;
    }
}