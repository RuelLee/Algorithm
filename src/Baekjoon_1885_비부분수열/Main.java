/*
 Author : Ruel
 Problem : Baekjoon 1885번 비부분수열
 Problem address : https://www.acmicpc.net/problem/1885
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1885_비부분수열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열에서 몇 개의 수를 순서대로 골라 만들 수 있는 수열을 부분수열이라고 한다.
        // {1, 5, 3, 2, 5, 1, 3, 4, 4, 2, 5, 1, 2, 3}이라고 할 때, 1, 5, 7, 10번째 수를 뽑아 {1, 5, 3, 2} 라는 부분 수열을 만들 수 있다.
        // 수열 s가 주어질 때, 1 ~ k까지의 원소들로 이루어져있을 때, 만들 수 없는 부분수열의 최소 길이를 구하라
        //
        // 그리디 문제
        // 만들 수 없는 부분 수열의 최소 길이이므로
        // 우리는 가장 등장하지 않는 수를 선택하여 짧은 길이로 만드는 것이 유리하다
        // 수열을 첫번째부터 살펴보며, 1 ~ k까지 모두 최소 한번씩 등장하는 것을 하나의 그룹으로 만들면 된다.
        // 그러한 그룹의 개수 + 1(아무 원소)를 하면 만들 수 없는 부분 수열의 최소 길이가 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 원소의 길이 n, 원소의 범위 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        // 수열
        int[] s = new int[n];
        for (int i = 0; i < s.length; i++)
            s[i] = Integer.parseInt(br.readLine());

        // 해쉬셋의 크기를 통해 모든 수가 최소 한 번씩 등장했는지 판별한다.
        HashSet<Integer> hashSet = new HashSet<>();
        // 그룹의 개수
        int length = 0;
        for (int num : s) {
            // 해쉬셋에 num 추가
            hashSet.add(num);
            
            // 해쉬셋의 크기가 k가 됐다면 모든 수가 한번식 등장.
            if (hashSet.size() == k) {
                // 그룹의 개수 증가
                length++;
                // 해쉬셋 초기화
                hashSet.clear();
            }
        }
        // 찾은 그룹의 개수 + 1 개가
        // s에서 만들 수 없는 부분 수열의 최소 길이
        System.out.println(length + 1);
    }
}