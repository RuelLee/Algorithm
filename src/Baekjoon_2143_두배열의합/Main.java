/*
 Author : Ruel
 Problem : Baekjoon 2143번 두 배열의 합
 Problem address : https://www.acmicpc.net/problem/2143
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2143_두배열의합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 배열 a와 b가 주어진다.
        // 부 배열은 한 배열의 연속된 원소로 이루어진 배열이고, 부 배열의 합은 각 원소의 합이다.
        // a의 부 배열과 b의 부 배열의 합이 t가 되는 경우의 수를 구하라
        //
        // 맵, 이분 탐색, 누적합 문제
        // a를 누적합을 통해 가능한 모든 부 배열의 합을 구하고, 그 개수를 세어, 해쉬 맵에 담는다.
        // b 또한 가능한 모든 부 배열의 합을 구하고, 개수를 세며, 검색을 쉽게 하기 위해 이는 트리 맵에 담는다.
        // 혹은 트리 맵이 아닌 이분 탐색을 통해 구할 수도 있다.
        // a의 가능한 부 배열 합을 모두 탐색하며, b의 부 배열 합들 중 더하여 t가 되는 경우가 있는지 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        // 배열 a
        int n = Integer.parseInt(br.readLine());
        int[] a = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < a.length; i++)
            a[i] = Integer.parseInt(st.nextToken());

        // 배열 b
        int m = Integer.parseInt(br.readLine());
        int[] b = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < b.length; i++)
            b[i] = Integer.parseInt(st.nextToken());

        // 해쉬 맵에 a의 부 배열 합들을 구하고 개수를 센다
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            int sum = a[i];
            inputValue(sum, hashMap);
            for (int j = i + 1; j < a.length; j++) {
                sum += a[j];
                inputValue(sum, hashMap);
            }
        }

        // 트리 맵에 b의 부 배열 합들을 구하고 개수를 센다.
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int i = 0; i < b.length; i++) {
            int sum = b[i];
            inputValue(sum, treeMap);
            for (int j = i + 1; j < b.length; j++) {
                sum += b[j];
                inputValue(sum, treeMap);
            }
        }

        long sum = 0;
        // 해쉬 맵의 모든 키 값을 돌며
        // 트리 맵의 키 값과의 합이 t인 경우가 있는지 계산한다.
        for (int key : hashMap.keySet()) {
            if (treeMap.higherKey(t - key - 1) != null &&
                    treeMap.higherKey(t - key - 1) == t - key)
                sum += (long) hashMap.get(key) * treeMap.get(t - key);
        }
        // 답 출력
        System.out.println(sum);
    }

    static void inputValue(int value, Map<Integer, Integer> map) {
        if (!map.containsKey(value))
            map.put(value, 1);
        else
            map.put(value, map.get(value) + 1);
    }
}