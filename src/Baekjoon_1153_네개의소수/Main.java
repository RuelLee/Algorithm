/*
 Author : Ruel
 Problem : Baekjoon 1153번 네 개의 소수
 Problem address : https://www.acmicpc.net/problem/1153
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1153_네개의소수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static boolean[] primeNumbers;

    public static void main(String[] args) throws IOException {
        // n이 주어진다.
        // n을 4개의 소수 합으로 나타내라.
        // 불가능하면 -1을 출력한다.
        //
        // 에라토스테네스의 체, 백트래킹
        // 먼저 n보다 작은 범위 내에서 소수들을 구한다.
        // 그 후에 큰 소수부터 선택하여 4개 수의 합으로 표현할 수 있는지 확인한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n
        int n = Integer.parseInt(br.readLine());

        // 에라토스테네스의 체
        primeNumbers = new boolean[n + 1];
        Arrays.fill(primeNumbers, true);
        primeNumbers[0] = primeNumbers[1] = false;
        for (int i = 2; i < primeNumbers.length; i++) {
            if (!primeNumbers[i])
                continue;
            for (int j = 2; i * j < primeNumbers.length; j++)
                primeNumbers[i * j] = false;
        }

        // 백트래킹을 사용하여 가능한 수들을 찾는다.
        int[] answer = findAnswer(n, 0, new int[4]);
        // 불가능한 경우
        if (answer[3] == 0)
            System.out.println(-1);
        else {      // 가능한 경우
            StringBuilder sb = new StringBuilder();
            for (int pn : answer)
                sb.append(pn).append(" ");
            sb.deleteCharAt(sb.length() - 1);
            System.out.println(sb);
        }
    }
    
    // 백트래킹
    static int[] findAnswer(int sum, int picked, int[] numbers) {
        // 4개의 수를 모두 뽑았고
        if (picked == 4) {
            // 아직 값이 남았다면 불가능한 경우
            // 마지막 칸을 0으로 채워서 보내고
            if (sum != 0)
                numbers[3] = 0;
            // 그렇지 합이 모두 채워졌다면 그대로 반환한다.
            return numbers;
        }
        
        int[] answer = new int[4];
        // sum보다 같거나 작은 값부터 확인해나간다.
        for (int i = sum; i > 1; i--) {
            // 소수일 때
            if (primeNumbers[i]) {
                // picked+1번째 수로 i를 선택하여
                numbers[picked] = i;
                // picked+2번째 수를 찾으러 보낸다.
                answer = findAnswer(sum - i, picked + 1, numbers);
                // 반환된 수열의 마지막 수가 0이 아니라면
                // 해당하는 경우를 찾은 경우.
                // 반복문 종료
                if (answer[3] != 0)
                    break;
            }
        }
        // 찾은 결과를 반환한다.
        return answer;
    }
}