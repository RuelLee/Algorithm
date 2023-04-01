/*
 Author : Ruel
 Problem : Baekjoon 1241번 머리 톡톡
 Problem address : https://www.acmicpc.net/problem/1241
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1241_머리톡톡;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생이 원 모양으로 앉아있다.
        // 각 학생은 1,000,000 이하의 자연수 중 하나를 선택하였고,
        // 자신의 차례가 되면 한바퀴를 돌며 자신이 선택한 수의 약수를 선택한 학생들의 머리를 톡톡 친다.
        // 각 학생들이 다른 학생의 머리를 몇 번 톡톡 치는지 구하라
        //
        // 소수 판정
        // 을 활용한 문제
        // i라는 수가 소수인지 판별할 때,
        // i 이하의 수를 모두 검증하는 방법, 에라토스테네스의 체를 이용하는 방법도 있다.
        // 하지만 전자의 경우, 그 수를 좀 더 최적화하여 i의 제곱근 이하의 수들만 살펴보는 방법도 있다.
        // 소수를 판별할 때 두 수의 곱이 i인지 확인하게 되는데
        // i = a * b에서 a가 i의 제곱근보다 작은 수라면 b가 i가 제곱근보다 큰 수가 되어
        // 거를 수 있기 때문이다.
        // 이를 활용하여 문제를 풀었다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 주어지는 학생들이 선택한 n개의 수
        int[] nums = new int[n];
        // 각 수가 몇 개인지 세어둔다.
        int[] counts = new int[1_000_001];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(br.readLine());
            counts[nums[i]]++;
        }

        StringBuilder sb = new StringBuilder();
        // 순서대로 자신이 선택한 수의 약수를 선택한 학생들이 몇 명인지 구한다.
        for (int num : nums) {
            int count = 0;
            // num이 1인 경우.
            // 자기 자신을 제외한 1의 개수를 센다.
            if (num == 1)
                count += counts[1] - 1;

            else {      // 그 외의 경우
                // 1 * num = num 이므로
                // 1의 개수와 자기 자신을 제외한 num의 개수를 더한다.
                count += counts[1] + counts[num] - 1;
                // 그 후, 2부터 num의 제곱근까지 수들을 살펴보며
                // i가 num의 약수일 경우
                // i와 num / i의 개수를 더해준다.
                for (int i = 2; i < Math.sqrt(num); i++) {
                    if (num % i == 0)
                        count += counts[i] + counts[num / i];
                }

                // num의 제곱근이 자연수인 경우
                // 제곱근의 개수 또한 세어준다.
                if ((int) Math.sqrt(num) * (int) Math.sqrt(num) == num)
                    count += counts[(int) Math.sqrt(num)];
            }

            // 센 약수의 개수를 StringBuilder에 기록한다
            sb.append(count).append("\n");
        }
        // 전체 답안 출력.
        System.out.print(sb);
    }
}