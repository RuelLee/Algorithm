/*
 Author : Ruel
 Problem : Baekjoon 1501번 영어 읽기
 Problem address : https://www.acmicpc.net/problem/1501
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1501_영어읽기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 한 단어의 첫 글자와 마지막 글자가 일치하고, 포함된 알파벳의 개수가 같다면
        // 서로 다른 단어라도 같은 단어로 읽힐 수 있다.
        // abcde는 abcde, abdce, acbde, acdbe, adbce, adcbe로 해석될 수 있다.
        // n개의 단어가 주어지고
        // m개의 문장이 주어질 때
        // 각 문장이 해석될 수 있는 가짓수를 출력하라
        //
        // 정렬, 해쉬 관련 문제
        // 해쉬맵을 통해 문자의 앞뒤 문자, 그리고 포함된 알파벳의 개수를 해쉬로 표현해서 계산한다.
        // 앞뒤의 문자만 일치하면, 사이의 문자들의 순서는 상관이 없기 때문에
        // 단어의 알파벳들을 정렬하여 해쉬코드값으로 만들어 계산하자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 단어
        int n = Integer.parseInt(br.readLine());

        // 먼저 앞뒤 글자를 key로 사용하고, 그 다음엔 정렬된 단어의 hash값을 key로 사용한다.
        HashMap<Integer, HashMap<Integer, Integer>> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char[] input = br.readLine().toCharArray();
            // 앞 뒤 글자를 key로
            int idx = input[0] * 57 + input[input.length - 1];
            Arrays.sort(input);
            // 정렬된 단어의 해쉬값
            int hash = Arrays.hashCode(input);

            if (!hashMap.containsKey(idx))
                hashMap.put(idx, new HashMap<>());
            if (!hashMap.get(idx).containsKey(hash))
                hashMap.get(idx).put(hash, 1);
            else
                hashMap.get(idx).put(hash, hashMap.get(idx).get(hash) + 1);
        }
        
        StringBuilder sb = new StringBuilder();
        // m개의 문장
        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            // 포함된 단어가 여러개일 수 있다.
            StringTokenizer st = new StringTokenizer(br.readLine());
            int count = 1;
            // 모든 단어들에 대해
            while (st.hasMoreTokens()) {
                char[] input = st.nextToken().toCharArray();
                // 앞 뒤 글자로 key를 뽑아내고
                int idx = input[0] * 57 + input[input.length - 1];
                Arrays.sort(input);
                // 정렬된 단어로 hash값을 계산하여
                int hash = Arrays.hashCode(input);
                // 해당하는 값이 없다면 0
                if (!hashMap.containsKey(idx) ||
                        !hashMap.get(idx).containsKey(hash)) {
                    count *= 0;
                    break;
                } else      // 존재한다면 그 개수만큼 곱해준다.
                    count *= hashMap.get(idx).get(hash);
            }
            // 답 기록
            sb.append(count).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}