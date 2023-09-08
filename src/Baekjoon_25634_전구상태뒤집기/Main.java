/*
 Author : Ruel
 Problem : Baekjoon 25634번 전구 상태 뒤집기
 Problem address : https://www.acmicpc.net/problem/25634
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25634_전구상태뒤집기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 전구와 각 밝기와, 켜짐 혹은 꺼짐에 대한 상태들이 주어진다.
        // '뒤집는다'라는 행동을 하는데 이는
        // 연속한 한 개 이상의 전구들의 상태를 반전시키는 것이다.
        // 한 번의 뒤집기를 통해 밝기 합을 최대화하고자 할 때, 밝기의 합은?
        //
        // 누적합 문제
        // 일정 때문에 새벽에 풀어서인지 많이 헤맸다.
        // 먼저 현재 상태의 밝기 합을 구한다.
        // 그리고 첫번째 전구부터, 해당 전구까지를 뒤집었을 때의 밝기 변화를 누적합으로 나타낸다.
        // 그리고 차례대로 살펴나가며
        // 이전에 등장한 최소 밝기 변화량(0 ~ min)과 이번 밝기 변화량(0 ~ i)까지의 차를 구한다.
        // 이는 0 ~ min까지의 전구들은 건들이지 않고, min + 1 ~ i까지의 전구들을 
        // 뒤집는 행위를 했을 때의 변화량을 나타낸다.
        // 그리고 이는 i번째까지 중 뒤집는다는 행위를 통해 만들 수 있는 가장 큰 밝기의 변동합을 나타낸다.
        // 이를 모두 수행한다면, 가장 큰 밝기 변화 차를 알 수 있고
        // 이를 초기 상태의 밝기에 더해준 값이 답이 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 전구
        int n = Integer.parseInt(br.readLine());
        // 각 전구의 밝기
        int[] bulbs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 초기 상태
        int[] onOff = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // switchDiffs[i] = 0 ~ i번째까지의 전구를 뒤집었을 때의 밝기 변화량
        int[] switchDiffs = new int[n];
        // 첫 전구가 꺼져있다면 켜지면서 bulbs[0] 만큼 밝기가 더해질 것이고
        // 켜져있다면, 꺼지면서 해당 값만큼 밝기가 줄어들 것이다.
        switchDiffs[0] = (onOff[0] == 0 ? bulbs[0] : -bulbs[0]);
        // 초기 상태에서의 밝기 누적 합
        int currentSum = onOff[0] * bulbs[0];
        // 한 번의 반복문을 통해
        // 초기 상태의 밝기 합과 0 ~ i번째까지 전구들을 뒤집었을 때의 밝기 변화를 같이 계산한다.
        for (int i = 1; i < switchDiffs.length; i++) {
            // 초기 상태 밝기
            currentSum += onOff[i] * bulbs[i];
            // 0 ~ i번째 전구를 뒤집었을 때의 밝기 변화
            switchDiffs[i] = switchDiffs[i - 1] + (onOff[i] == 0 ? bulbs[i] : -bulbs[i]);
        }

        // minDiff로는 0 ~ 해당 전구까지 뒤집었을 경우
        // 오히려 밝기 합이 -가 되어 손해가 되는 구간을 찾을 것이다.
        // 밝기 합이 더 큰 손해가 될 수록 해당 구간을 제외하는 것이 유리해진다.
        int minDIff = 0;
        // 최대 밝기 변화량
        int maxDIff = Integer.MIN_VALUE;
        for (int i = 0; i < switchDiffs.length; i++) {
            // 현재 계산된 뒤집었을 때의 최대 밝기 변화량과
            // 0 ~ i번째까지의 전구들 중 손해가 되는 왼쪽 구간을 제외하고 뒤집었을 때의
            // 밝기 변화량을 비교하여 더 큰 값을 남겨둔다.
            maxDIff = Math.max(maxDIff, switchDiffs[i] - minDIff);
            // 0 ~ i까지의 전구들을 뒤집었을 때의 변화량이
            // 여태 등장했던 구간들 중 가장 큰 손해가 되는지 따져본다.
            // minDiff는 이후 구간에서 뒤집었을 때 가장 큰 손해로 제외되는 구간이 될 것이다.
            minDIff = Math.min(minDIff, switchDiffs[i]);
        }

        // 최대 밝기 변화량과 현재 밝기를 더해 얻을 수 있는 최대 밝기 합을 출력한다.
        System.out.println(maxDIff + currentSum);
    }
}