/*
 Author : Ruel
 Problem : Baekjoon 9011번 순서
 Problem address : https://www.acmicpc.net/problem/9011
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9011_순서;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수로 이루어진 수열 S가 주어진다.
        // 각 수는 모두 다르다.
        // 이 수열을 통해 자신보다 이전에 등장한 작은 수를 통해 새로운 수열 R을 만들 수 있다.
        // 예를 들어, S가 6 4 3 5 1 2 7 8 9 10 이라면
        // 0 0 0 2 0 1 6 7 8 9로 새로운 수열 R을 만들 수 있다.
        // R이 주어질 때 대응하는 S를 찾아라
        // 불가능하다면 IMPOSSIBLE을 출력한다.
        //
        // 구현 문제
        // 별 다를 것은 없고, 모든 수에 대해 고려하는 맨 뒤에서부터 차근차근
        // 수를 지정해나가면 된다.
        // n이 10이라면 10번째 수는 남은 9개의 수에 대해 고려한 수가 위치한다.
        // 따라서 10번째 수는 확정적으로 구할 수 있고,
        // 9번째 수에 대해서는 자신보다 작은 수와 10번째 수를 제외한 수들 중에 고려하므로 또 한가지를 구할 수 있다.
        // 이런 식으로 뒤에서부터 구해나가면 되는 문제

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 수열의 길이
            int n = Integer.parseInt(br.readLine());
            // 주어지는 R 수열
            int[] r = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            // 가능 여부
            boolean possible = true;
            // S 수열
            int[] nums = new int[n];
            // 사용한 수
            boolean[] selected = new boolean[n + 1];
            // 뒤에서부터 찾아나간다.
            for (int i = nums.length - 1; i >= 0; i--) {
                // 앞에 나오는 수들 중에서 r[i] + 1번째 큰 수를 찾는다.
                int smaller = 0;
                for (int j = 1; j < selected.length; j++) {
                    // 이미 뒤에 사용된 수라면 고려 자체를 하지 않는다.
                    if (selected[j])
                        continue;
                    else if (smaller == r[i]) {
                        // 해당하는 수를 찾은 경우
                        // i번째 순서에 j를 적고
                        nums[i] = j;
                        // 사용 표시
                        selected[j] = true;
                        // i번째 수 찾기를 중단.
                        break;
                    } else      // 사용되지 않은 수라면 r[i] + 1번째 수를 찾을 때까지 smaller 증가.
                        smaller++;
                }

                // 만약 r[i] + 1번째 수를 찾지 못해
                // nums[i]에 초기값이 들어있다면 불가능한 경우.
                if (nums[i] == 0) {
                    // 불가능 표시 후
                    possible = false;
                    // 종료
                    break;
                }
            }
            // 불가능한 경우
            if (!possible)
                sb.append("IMPOSSIBLE");
            else {      // 가능한 경우라면 수열을 기록
                for (int num : nums)
                    sb.append(num).append(" ");
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}