/*
 Author : Ruel
 Problem : Baekjoon 13397번 구간 나누기 2
 Problem address : https://www.acmicpc.net/problem/13397
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13397_구간나누기2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 이들을 m개 이하의 구간으로 나누며, 각 구간의 최대값과 최소값의 차이를 구간의 점수라고 부른다.
        // 구간 점수의 최대값을 최소화시키고자 할 때, 그 값은?
        //
        // 이분 탐색 문제
        // 이분 탐색을 통해, 구간 점수의 최대값을 정해놓고,
        // m개 이하의 구간으로 해당 구간 점수를 만족시킬 수 있는지 찾아나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 수의 개수와 구간 제한
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 주어지는 n개의 수
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 이분 탐색
        int start = 0;
        int end = 10000;
        while (start < end) {
            int mid = (start + end) / 2;
            if (splitPossible(nums, mid, m))
                end = mid;
            else
                start = mid + 1;
        }
        
        // 구간 점수의 최소값 출력
        System.out.println(start);
    }

    // 주어지는 수열과 구간 점수의 최대값으로 sections개 이하의 구간으로 나눌 수 있는지 확인한다.
    static boolean splitPossible(int[] nums, int maxValue, int sections) {
        // 구간의 개수
        int count = 1;
        // 해당 구간의 최대, 최소값
        int min = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 최대, 최소값 갱신
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
            
            // 구간 점수가 maxValue 제한보다 커졌다면
            if (max - min > maxValue) {
                // i - 1번 수까지가 하나의 구간.
                // 최대, 최소값을 i번째 수로 바꿔주고
                min = max = nums[i];
                // 구간을 하나 증가시키며, 해당 구간의 수가 sections개 보다 많아졌다면
                // false를 반환한다.
                if (++count > sections)
                    return false;
            }
        }
        // sections개 보다 많은 수의 구간으로 나뉘지 않았다면
        // true를 반환한다.
        return true;
    }
}