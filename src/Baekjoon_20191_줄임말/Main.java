/*
 Author : Ruel
 Problem : Baekjoon 20191번 줄임말
 Problem address : https://www.acmicpc.net/problem/20191
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20191_줄임말;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // 문자열 A가 문자열 B의 줄임말이라는 것은 B의 순서를 바꾸지 않고 0 또는 그 이상 개수의 문자를 지워 A를 만들 수 있다는 뜻.
        // 예를 들어, ac, ab, aa, aabc는 aabc의 줄임말이다.
        // 문자열 T를 자연수 n번 반복해서 이어쓴 문자열을 T n이라고 하자.
        // 문자열 S가 T^n의 줄임말이 되는 최소 n을 구하라.
        //
        // 이분 탐색 문제
        // S가 최대 100만, T가 10만으로 주어지므로, O(S * T)로는 해결이 불가능하다.
        // 따라서 T를 알파벳별로 idx를 저장한다.
        // 그 후, S를 하나씩 살펴보며, 해당하는 T의 idx를 이분탐색을 통해 찾아나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 문자열 S, T
        String s = br.readLine();
        String t = br.readLine();

        // T를 알파벳에 따라 인덱스를 저장한다.
        List<List<Integer>> alphabetIndexes = new ArrayList<>();
        for (int i = 0; i < 26; i++)
            alphabetIndexes.add(new ArrayList<>());
        for (int i = 0; i < t.length(); i++)
            alphabetIndexes.get(t.charAt(i) - 'a').add(i);
        
        // 불가능한지 여부
        boolean possible = true;
        // T의 이어쓴 횟수
        int n = 1;
        // 현재 T에서 idx의 위치
        int tIdx = 0;
        for (int i = 0; i < s.length(); i++) {
            // S의 i번째 알파벳에 해당하는
            // T의 idx들
            List<Integer> indexes = alphabetIndexes.get(s.charAt(i) - 'a');
            // 만약 존재하지 않는다면 불가능한 경우.
            if (indexes.isEmpty()) {
                possible = false;
                break;
            }

            // 이분 탐색을 통해 위치를 찾는다.
            int left = 0;
            int right = indexes.size();
            while (left < right) {
                int mid = (left + right) / 2;
                // 현재 tIdx보다 작은 위치라면 left를 mid + 1 로 올린 범위에서 찾는다.
                if (indexes.get(mid) < tIdx)
                    left = mid + 1;
                // 같거나 크다면 right를 낮춘 범위에서 찾는다.
                else
                    right = mid;
            }

            // 만약 left가 오른쪽 끝 범위를 넘어섰다면
            // tIdx 뒤로 가능한 알파벳이 없는 것.
            // n을 하나 증가시켜주고, tIdx를 0으로 바꿔 다시 i번째 알파벳에 대한 탐색을 한다.
            if (left == indexes.size()) {
                n++;
                tIdx = 0;
                i--;
            } else      // 아니라면 해당 위치를 찾았으므로, tIdx를 해당하는 위치 +1 위치로 옮겨준다.
                tIdx = indexes.get(left) + 1;
        }

        // possible이 false라면 불가능한 경우이므로 -1
        // 그 외의 경우에는 n을 출력한다.
        System.out.println(possible ? n : -1);
    }
}