/*
 Author : Ruel
 Problem : Baekjoon 1469번 숌 사이 수열
 Problem address : https://www.acmicpc.net/problem/1469
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1469_숌사이수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 8보다 같거나 작은 자연수 n이 주어진다.
        // 서로 다른 자연수 n쌍으로 이루어진 수열을 만들고자 한다.
        // 그 조건으로, 
        // 등장하는 수가 i라면 다음 i와 사이에 다른 수가 i개 있어야한다.
        // 예를 들어 1, 2, 3이 주어진다면, 2 3 1 2 1 3 처럼
        // 2 사이에는 2개의 다른 수, 1 사이에는 1개의 다른 수, 3 사이에는 3개의 다른 수가 들어가야한다.
        // 해당 수열이 여러개라면 사전순으로 가장 이른 것을 출력하며
        // 그러한 수열이 없다면 -1을 출력한다.
        //
        // 브루트포스, 백트래킹 문제
        // n이 8이하로 매우 작다.
        // 따라서 모든 경우에 대해 시도해보는 것이 가능하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        int n = Integer.parseInt(br.readLine());
        // 해당 수
        int[] x = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 사전순으로 가장 이른 것을 우선적으로 계산하기 위해 오름차순 정렬한다.
        Arrays.sort(x);

        // 만들어진 수열
        int[] output = new int[n * 2];
        Arrays.fill(output, -1);
        // 결과
        boolean answer = findAnswer(0, 0, new boolean[n], output, x);
        StringBuilder sb = new StringBuilder();
        // 찾았다면 해당 수열의 답안 작성
        if (answer) {
            for (int num : output)
                sb.append(num).append(" ");
            sb.deleteCharAt(sb.length() - 1);
        } else      // 그렇지 않다면 -1 기록
            sb.append(-1);

        // 답안 출력
        System.out.println(sb);
    }

    // 백트래킹
    static boolean findAnswer(int idx, int pick, boolean[] selected, int[] output, int[] x) {
        // 마지막 위치까지 살펴보는 것이 끝났다면
        // 모든 수를 배치했다면 true 반환
        if (idx == output.length)
            return pick == x.length;

        // 만약 idx 위치에 수가 배치가 됐다면
        // 다음 위치로 넘긴다.
        if (output[idx] != -1)
            return findAnswer(idx + 1, pick, selected, output, x);

        // x개의 수를 idx 위치에 배치할 수 있는지 확인한다.
        for (int i = 0; i < x.length; i++) {
            // i번째 수가 사용되지 않았다면
            if (!selected[i]) {
                // idx 위치와 nextIdx에 수를 배치하게 된다.
                int nextIdx = idx + x[i] + 1;
                // 다음 위치가 범위를 벗어나지 않고, 아직 수가 배치되지 않았다면
                if (nextIdx < output.length && output[nextIdx] == -1) {
                    // 해당 수 배치후
                    output[idx] = output[nextIdx] = x[i];
                    // 수 사용 체크
                    selected[i] = true;
                    // 만약 그런 그러한 결과가 수열을 만드는 결과로 이어진다면 true 반환
                    if (findAnswer(idx + 1, pick + 1, selected, output, x))
                        return true;
                    // 그렇지 않다면 상태를 복구 시킨다.
                    output[idx] = output[nextIdx] = -1;
                    selected[i] = false;
                }
            }
        }
        // 현재 상태에서 남은 수 어떠한 것을 배치하더라도
        // 수열을 찾을 수 없다면 false를 반환한다.
        return false;
    }
}