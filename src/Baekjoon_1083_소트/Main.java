/*
 Author : Ruel
 Problem : Baekjoon 1083번 소트
 Problem address : https://www.acmicpc.net/problem/1083
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1083_소트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어지고, 서로 교환 가능한 횟수 s가 주어진다.
        // s이내에 n을 사전순으로 가장 뒷서는 순서로 만들고자할 때, 그 때의 수열을 출력하라.
        //
        // 그리디, 소트 문제
        // 단순히 앞의 수가 뒤의 수보다 작을 때, 두 수를 교환해나가며 s를 감소시켰는데 그래선 안되는 문제였다.
        // 첫 수부터 순서대로 자신으로부터 s 범위 이내에 가장 큰 수를 찾고, 그 수를 자신의 자리까지 앞으로 올려보내야했다.
        // n은 50, s는 최대 100만까지 주어지나, s 값 자체가 작기 때문에 사실상 s는 모든 수가 오름차순으로 정렬이 되어있더라도
        // n(n+1)/2 즉, 49 * 50 / 2이 최대 일어날 수 있는 교환 횟수나 마찬가지다.
        // 따라서 일일이 직접하더라도 문제가 없다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 수의 개수
        int n = Integer.parseInt(br.readLine());
        // 초기 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 교환 가능 횟수.
        int s = Integer.parseInt(br.readLine());

        for (int i = 0; i < nums.length - 1; i++) {
            if (s == 0)
                break;

            // 교환 대상이 되는 앞의 수.
            int idx = i;
            // 최대값을 찾을 범위.
            int scope = Math.min(i + s + 1, nums.length);
            // 최대값을 찾는다.
            for (int j = idx; j < scope; j++) {
                if (nums[j] > nums[idx])
                    idx = j;
            }
            
            // nums[i] 보다 더 큰 값을 찾았다면
            if (i != idx) {
                // 해당 수를 i의 위치까지 올려보낸다.
                for (int j = idx; j > i; j--) {
                    int temp = nums[j - 1];
                    nums[j - 1] = nums[j];
                    nums[j] = temp;
                }
                // 이 때 idx와 i의 차이만큼의 교환 횟수가 소모된다.
                s -= (idx - i);
            }
        }

        // 최종 수열을 출력한다.
        StringBuilder sb = new StringBuilder();
        for (int num : nums)
            sb.append(num).append(" ");
        System.out.println(sb);
    }
}