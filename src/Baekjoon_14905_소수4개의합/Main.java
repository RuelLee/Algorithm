/*
 Author : Ruel
 Problem : Baekjoon 14905번 소수 4개의 합
 Problem address : https://www.acmicpc.net/problem/14905
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14905_소수4개의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static boolean[] notPrimeNumbers;

    public static void main(String[] args) throws IOException {
        // n이 주어질 때, n을 4개의 소수의 합으로 표현하라
        // 불가능하다면 “Impossible.”이라고 출력한다.
        //
        // 에라토스테네스의 체
        // 먼저 모든 소수를 구하고
        // 백트래킹을 통해 4개의 소수의 합과 일치하는 경우를 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 에라토스테네스의 체
        notPrimeNumbers = new boolean[100_000_001];
        for (int i = 2; i < notPrimeNumbers.length; i++) {
            if (notPrimeNumbers[i])
                continue;
            for (int j = 2; i * j < notPrimeNumbers.length; j++)
                notPrimeNumbers[i * j] = true;
        }
        
        String input = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (input != null) {
            // 수
            int num = Integer.parseInt(input);
            // 네 개의 소수 합과 일치하는 경우를 찾는다.
            int[] answer = findAnswer(0, num, new int[4]);
            
            // 불가능한 경우
            if (answer[3] == 0)
                sb.append("Impossible.");
            else {
                // 찾은 경우
                // 네 개의 수를 기록
                for (int n : answer)
                    sb.append(n).append(" ");
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("\n");

            input = br.readLine();
        }
        // 전체 답안 출력
        System.out.println(sb);
    }

    // 백트래킹을 통해 4개의 소수 합을 찾는다.
    static int[] findAnswer(int idx, int remain, int[] nums) {
        if (idx == 4) {
            // 수를 모두 고른 경우
            // 남은 값이 있다면 불가능한 경우.
            if (remain != 0)
                nums[3] = 0;
            // 만약 remain이 0이라면 해당하는 경우를 찾았으므로 해당 수열 반환
            return nums;
        }

        // idx번째 수를 정한다.
        int[] answer = new int[4];
        // remain보다 같거나 작은 수들 중
        for (int i = remain; i > 1; i--) {
            // 소수를 찾아
            if (!notPrimeNumbers[i]) {
                // idx번째에 i가 올 경우
                // 4개의 소수 합으로 표현할 수 있는지 찾는다.
                nums[idx] = i;
                answer = findAnswer(idx + 1, remain - i, nums);
                // 만약 해당하는 경우를 찾은 경우 더 이상 찾지 말고 반복문 종료
                if (answer[3] != 0)
                    break;
            }
        }
        // 해당 수열 반환.
        return answer;
    }
}