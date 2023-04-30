/*
 Author : Ruel
 Problem : Baekjoon 1239번 차트
 Problem address : https://www.acmicpc.net/problem/1239
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1239_차트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 총 100마리의 개가 종류별로 주어진다
        // 이 개들로 원형 그래프를 그리는데, 원의 중심을 지나는 선의 개수를 최대로 하고 싶다.
        // 그 때의 원의 중심을 지나는 선의 개수는?
        //
        // 브루트 포스 알고리즘, 두 포인터
        // 주어지는 개의 종류를 순열을 통해 모든 경우의 수로 정렬하여 본다.
        // 그리고 특정 개의 값부터 이후의 값들까지의 합이 정확히 50이 되는 경우가 있는지 확인한다.
        // 만약된다면 원을 지나도록 이등분이 가능한 경우.
        // 그러한 선분의 개수의 최대값이 몇개인지 센다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 개의 종류의 수
        int n = Integer.parseInt(br.readLine());
        // 각 종류의 마릿수
        int[] dogs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 답안 출력
        System.out.println(findMaxLines(0, new int[n], new boolean[n], dogs));
    }

    // 먼저 순열을 통해 가능한 모든 경우의 수로 정렬한 후
    // 해당 순서대로 그래프를 채웠을 경우, 몇 개의 선분이 중앙을 가로지르는지 확인한다.
    static int findMaxLines(int idx, int[] order, boolean[] selected, int[] dogs) {
        // 정렬이 끝난 경우
        if (idx == dogs.length) {
            // 선분의 수
            int maxLines = 0;
            // i번째부터, j번째까지 개체 수 합
            int sum = 0;
            int j = 0;
            // i번째 종류부터
            for (int i = 0; i < order.length; i++) {
                // sum이 50이 넘지 않는 동안
                // j - 1번째까지의 개체 수 합을 더한다.
                while (sum < 50)
                    sum += dogs[order[j++ % order.length]];
                // 만약 개채 수 합이 정확히 50이 된다면 중앙을 가로지르는 경우.
                if (sum == 50)
                    maxLines++;
                // 다음인 i + 1번째에서는 i번째 종류의 개체수가 빠진다.
                sum -= dogs[order[i]];
            }

            // 합이 50인지 여부를 기준으로 계산했다는 뜻은
            // 선분이 반원, 반원으로 두 번 계산이 된다. 따라서 2로 나눈 값이
            // 중앙을 가르는 선분의 수
            return maxLines / 2;
        }

        // idx번째까지 지정한 순서에서 최대 중앙을 가르는 선분의 개수
        int maxLines = 0;
        // idx번째에 몇번째 순서의 개체수를 지정할지 정한다.
        for (int i = 0; i < selected.length; i++) {
            // i번째 개체수가 아직 사용되지 않았다면
            if (!selected[i]) {
                // 사용 체크
                selected[i] = true;
                // idx번째에 i번 개체수를 사용
                order[idx] = i;
                // 재귀를 통해 idx + 1 번째로 진행 후,
                // 중앙을 가는 선분의 개수가 최대값을 갱신하는지 확인.
                maxLines = Math.max(maxLines, findMaxLines(idx + 1, order, selected, dogs));
                // 사용 체크 복구
                selected[i] = false;
            }
        }
        // 최대값 반환.
        return maxLines;
    }
}