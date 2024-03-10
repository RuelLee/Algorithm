/*
 Author : Ruel
 Problem : Baekjoon 1208번 부분수열의 합 2
 Problem address : https://www.acmicpc.net/problem/1208
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1208_부분수열의합2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열의 크기로 40이하의 n이 주어진다.
        // 부분 수열의 합이 s인 경우의 수를 구하라
        //
        // 중간에서 만나기
        // n이 최대 40으로 주어지므로 가능한 경우의 수는 2^40까지 나올 수도 있다.
        // 따라서 두 개의 그룹으로 나눠 각각 부분 수열의 합을 구하고
        // 두 그룹의 합이 s인 경우의 수를 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 수열의 크기 n, 부분수열의 합 s
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        
        // 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        HashMap<Integer, Integer> left = new HashMap<>();
        HashMap<Integer, Integer> right = new HashMap<>();
        // 왼쪽 그룹의 부분 수열의 합
        findGroupSum(0, n / 2, 0, nums, true, left);
        // 오른쪽 그룹
        findGroupSum(n / 2, n, 0, nums, true, right);
        
        // 각 그룹에서만 s가 나오는 경우
        long count = (left.containsKey(s) ? left.get(s) : 0) + (right.containsKey(s) ? right.get(s) : 0);
        // 두 그룹의 합이 s가 되는 경우
        for (int l : left.keySet()) {
            if (right.containsKey(s - l))
                count += (long) left.get(l) * right.get(s - l);
        }
        // 경우의 수
        System.out.println(count);
    }

    // idx ~ end 전까지의 부분수열의 합들을 구한다.
    static void findGroupSum(int idx, int end, int sum, int[] nums, boolean nothing, HashMap<Integer, Integer> hashMap) {
        // 해당하는 범위의 모든 경우의 수를 계산한 경우.
        if (idx == end) {
            // 아무 수도 선택하지 않은 경우는 제외
            if (!nothing) {
                // 해쉬맵에 없는 경우에는 키를 추가.
                if (!hashMap.containsKey(sum))
                    hashMap.put(sum, 0);
                // 값을 하나 추가.
                hashMap.put(sum, hashMap.get(sum) + 1);
            }
            return;
        }

        // idx 수를 더하지 않는 경우
        findGroupSum(idx + 1, end, sum, nums, nothing, hashMap);
        // idx 수를 더하는 경우
        findGroupSum(idx + 1, end, sum + nums[idx], nums, false, hashMap);
    }
}