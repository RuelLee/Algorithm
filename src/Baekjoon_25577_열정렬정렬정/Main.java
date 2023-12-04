/*
 Author : Ruel
 Problem : Baekjoon 25577번 열 정렬정렬 정
 Problem address : https://www.acmicpc.net/problem/25577
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25577_열정렬정렬정;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기가 n인 수열이 주어지고
        // 두 수를 서로 교환하는 방법으로, 오름차순 정렬하고자한다.
        // 최소의 교환으로 정렬하고자 할 때 그 횟수는?
        //
        // 순열 사이클 분할 문제
        // 현재 상태와 정렬된 상태의 수의 순서를 통해 순열 사이클을 찾고
        // 그 때의 크기 -1 만큼이 해당 사이클을 정렬하는데 필요한 교환 횟수이다.
        // 이 교환 횟수의 합을 구하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 원래 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] sorted = nums.clone();
        // 정렬된 수열
        Arrays.sort(sorted);

        // 두 수열을 통해 순열 사이클을 찾는다.
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++)
            hashMap.put(nums[i], sorted[i]);

        // 찾아진 순열 사이클의 크기 -1을 모두 더한다.
        HashSet<Integer> hashSet = new HashSet<>();
        int sum = 0;
        for (int key : hashMap.keySet()) {
            if (hashSet.contains(key))
                continue;

            int count = 0;
            int value = key;
            while (!hashSet.contains(value)) {
                hashSet.add(value);
                value = hashMap.get(value);
                count++;
            }
            sum += (count - 1);
        }
        
        // 정렬하는데 필요한 교환의 최소 횟수
        System.out.println(sum);
    }
}