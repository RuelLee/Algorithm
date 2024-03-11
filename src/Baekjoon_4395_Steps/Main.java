/*
 Author : Ruel
 Problem : Baekjoon 4395번 Steps
 Problem address : https://www.acmicpc.net/problem/4395
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4395_Steps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 수가 주어진다.
        // 작은 수부터 큰 수까지 도달하려는데
        // 첫 걸음은 1, 두번째 걸음부터는 이전 걸음보다 하나 작거나, 같거나, 하나 큰 걸음으로 갈 수 있으며
        // 마지막 걸음은 1이여야한다.
        // 최소 걸음으로 도달하고자할 때, 그 걸음 수는?
        //
        // 브루트포스
        // x와 y가 0부터 최대 2^31까지 주어지므로
        // 일일이 계산한다하더라도 10만번이 채 안된다.
        // 첫 걸음도 1, 마지막 걸음도 1이므로
        // 걸음 수를 그래프로 나타냈을 때, 피라미드 형태를 띠되
        // 사이에 부족한 걸음을 끼워넣는다고 생각하면 편하다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 테스트케이스
        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int testCase = 0; testCase < n; testCase++) {
            int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            // 두 수의 차이
            int diff = nums[1] - nums[0];

            // 1부터 걸어나간다.
            int subtract = 1;
            int count = 0;
            while (diff > 0) {
                // 남은 차이가 걸음의 2배보다 같거나 크다면
                if (diff >= subtract * 2) {
                    // 두배만큼을 빼고, 두 걸음을 센다
                    diff -= subtract++ * 2;
                    count += 2;
                } else if (diff <= subtract) {
                    // 피라미드 형태에서의 꼭지점 혹은
                    // 사이에 한 걸음을 끼워넣음으로써 diff가 0이 된다면
                    // 해당 한 걸음을 채운다.
                    diff = 0;
                    count++;
                } else {
                    // 그 외의 경우는 현 걸음보다는 크지만
                    // 현 걸음 * 2보다는 작은 경우만 있다.
                    // 그 경우도 두 걸음을 세주고, diff를 0으로 만든다.
                    diff = 0;
                    count += 2;
                }
            }
            // 센 걸음 수 기록
            sb.append(count).append("\n");
        }
        // 답안 출력
        System.out.print(sb);
    }
}