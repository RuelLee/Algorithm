/*
 Author : Ruel
 Problem : Jungol 8538번 괄호 합체
 Problem address : https://jungol.co.kr/problem/8538
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8538_괄호합체;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // (과 )으로만 이루어진 두 괄호 문자열이 주어진다.
        // 빈 문자열을 올바른 괄호 문자열이며, S가 올바른 문자열일 때, (S)도 올바른 문자열이다.
        // S와 T가 올바른 문자열이라면 S + T 또한 올바른 문자열이다.
        // 두 문자열에서 각각 접두사를 순서대로 이어붙였을 때, 올바른 괄호 문자열이 되는 경우의 수는?
        //
        // 누적합 문제
        // (와 )의 개수를 세어나간다.
        // (은 +1, )는 -1을 해 나가며 첫 문자열의 누적합 개수를 세어나간다.
        // 단 )의 개수가 더 많아져 누적합이 음수가 되는 경우, 접두사 자체가 올바른 문자열이 되지 않으므로 더 이상 세지 않는다.
        // 두번째 문자열 또한 개수를 세어나가는데
        // 두번째 문자열은 앞에서 (의 개수를 받기 때문에 음수가 되어도 된다.
        // 단, )))(과 같이 -3 이후로 -2가 되는 경우, 이 때는 접두사에서 ((인 2를 가져와 매칭시키면 안된다.
        // 이미 )))로 -3을 한번 거쳤기 때문에, 3이상의 값이 필요하기 때문.
        // 이럴 때는 거쳐선 누적합의 최솟값을 계속 따져가며, 누적합의 최솟값과 누적합의 합이 0 이상인 경우만 따져 올바른 문자열 경우의 수에 합산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 첫번째 문자열
        String input = br.readLine();
        // 누적합의 개수를 센다.
        int[] leftCounts = new int[200001];
        int sum = 0;
        for (int i = 0; i < input.length(); i++) {
            // (인 경우 +1
            if (input.charAt(i) == '(')
                sum++;
            else        // )인 경우 -1
                sum--;
            // 음수가 됐다면 더 이상 살펴보지 않는다.
            if (sum < 0)
                break;
            // 해당 누적합의 개수 추가
            leftCounts[sum]++;
        }

        sum = 0;
        // 왼쪽 문자열의 잉여 (의 개수가 cut보다 같거나 커야한다.
        int cut = 0;
        // 두번째 문자열
        input = br.readLine();
        long ans = 0;
        for (int i = 0; i < input.length(); i++) {
            // 마찬가지로 개수 누적
            if (input.charAt(i) == '(')
                sum++;
            else
                sum--;

            // 왼쪽 문자열에서 잉여 문자열의 최소 개수 컷
            cut = Math.max(cut, -sum);

            // sum과 cut의 합이 0보다 작으며, sum이 0보다 같거나 작은 경우
            // 매칭되는 개수만큼을 답에 합산
            if (-sum >= cut && sum <= 0)
                ans += leftCounts[-sum];
        }
        // 답 출력
        System.out.println(ans);
    }
}