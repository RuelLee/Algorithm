/*
 Author : Ruel
 Problem : Baekjoon 3671번 산업 스파이의 편지
 Problem address : https://www.acmicpc.net/problem/3671
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3671_산업스파이의편지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static boolean[] eratosthenes;

    public static void main(String[] args) throws IOException {
        // c개의 테스트케이스가 주어진다
        // 각 테스트케이스에는 수열이 주어진다.
        // 수들을 적절히 배치하거나 제외해서 소수를 만드는 경우는 모두 몇 개인지 출력하라
        // 최대 수열의 길이는 7이다.
        // ex) 17 -> 7, 17, 71로 3가지.
        //
        // 에라토스테네스의 체와 수들을 조합, 브루트 포스 문제
        // 에라토스테네스의 체를 통해 전체 범위에 대해 소수들을 모두 구해둔다.
        // 그 후 수들을 배치하거나 제외하는 경우를 모두 따져 소수을 해, 소수 경우의 수를 센다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 에라토스테네스의 체.
        eratosthenes = new boolean[10000000];
        eratosthenes[0] = eratosthenes[1] = true;
        for (int i = 2; i < eratosthenes.length; i++) {
            for (int j = 2; i * j < eratosthenes.length; j++)
                eratosthenes[i * j] = true;
        }

        int testCase = Integer.parseInt(br.readLine());
        StringBuffer sb = new StringBuffer();
        for (int t = 0; t < testCase; t++) {
            String input = br.readLine();
            // 같은 수가 같은 위치 배치되는데, 다른 경우로 세지 않기 위해서
            // 각 수의 개수를 센 후, 수의 종류에 따라 배치를 한다.
            int[] nums = new int[10];
            for (int i = 0; i < input.length(); i++)
                nums[input.charAt(i) - '0']++;

            sb.append(findAnswer(nums, input.length(), 0)).append("\n");
        }
        System.out.println(sb);
    }

    // 남은 수들, 남은 수들, 현재 만들어진 수를 매개변수로 넘겨가며
    // 소수가 만들어지는 경우를 세어나간다.
    static int findAnswer(int[] nums, int remain, int current) {
        // current가 소수라면, 1 아니면 0.
        int sum = !eratosthenes[current] ? 1 : 0;
        // 만약 남은 수가 있다면
        if (remain != 0) {
            // 혹시 current 0 이라면, 0을 다시 배치하더라도 새로운 수가 만들어지지 않고
            // 소수가 되지도 않는다.
            // 따라서 current가 0인 경우에는 1부터 시작
            // current가 0이 아닌 경우에는 뒤에 0을 붙일 경우 새로운 수가 되므로, 0부터 시작.
            for (int i = (current == 0 ? 1 : 0); i < nums.length; i++) {
                // 남은 수가 있다면
                if (nums[i] > 0) {
                    // 개수 차감.
                    nums[i]--;
                    // 재귀적으로 메소드를 호출해 다음 수들을 만들어나가며 소수의 개수를 센다.
                    // 최종적으로 만들어진 소수의 개수들을 sum에 추가.
                    sum += findAnswer(nums, remain - 1, current * 10 + i);
                    // 메소드의 재귀 호출이 끝났다면, 다른 수를 뒤에 붙여 새로운 수를 만들기 위해
                    // 차감했던 개수를 복구.
                    nums[i]++;
                }
            }
        }
        // 최종적으로 current에 뒤에 수들을 붙여 만든 소수의 개수를 반환한다.
        return sum;
    }
}