/*
 Author : Ruel
 Problem : Baekjoon 2195번 문자열 복사
 Problem address : https://www.acmicpc.net/problem/2195
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2195_문자열복사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // s문자열과 p문자열이 주어진다
        // s문자열의 연속된 일부를 복사하여 p문자열을 만들려고 한다
        // 최소 복사 횟수를 구하여라
        //
        // s문자열을 문자들에 대해 각 지점들을 따로 저장해준뒤,
        // p문자열의 문자를 하나씩 살펴보며, 해당 문자로 시작하는 s문자열의 시작점을 모두 살펴보며
        // 복사할 수 있는 가장 긴 문자열의 길이를 계산해, 최소 복사 횟수를 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        String p = br.readLine();

        // s문자열의 각 문자에 대해 해쉬맵과 리스트를 통해 위치를 저장해준다.
        HashMap<Character, List<Integer>> hashMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (!hashMap.containsKey(s.charAt(i)))
                hashMap.put(s.charAt(i), new ArrayList<>());
            hashMap.get(s.charAt(i)).add(i);
        }

        // 살펴보면 p문자열의 위치
        int pIdx = 0;
        // 전체 복사 명령 사용 횟수.
        int cycle = 0;
        while (pIdx < p.length()) {
            // 복사로 만들어낼 수 없는 경우는 주어지지 않는다고 했으므로 최소 한 글자는 일치한다.
            int maxLength = 1;

            // pIdx에 해당하는 문자의 s에서의 위치들을 모두 살펴본다.
            for (int sIdx : hashMap.get(p.charAt(pIdx))) {
                // 복사로 가져올 수 있는 최대 길이를 계산한다
                // sIdx + i가 s의 길이를 넘어서는 안되며,
                // pIdx + i 또한 p의 길이를 넘어서는 안된다.
                for (int i = 1; i + sIdx < s.length() && i + pIdx < p.length(); i++) {
                    // 일치하지 않는 문자가 나타난다면 뒤는 더 이상 살펴보지 않는다.
                    if (p.charAt(pIdx + i) != s.charAt(sIdx + i))
                        break;
                    // 일치한다면, 해당 길이를 maxLength에 최대값인지 확인하고 갱신해준다.
                    else
                        maxLength = Math.max(maxLength, i + 1);
                }
            }
            // 최종적으로 구해진 복사로 사용할 수 있는 maxLength 이용하여 pIdx를 해당 길이만큼 건너뛴다.
            pIdx += maxLength;
            // 복사 사용 횟수 증가.
            cycle++;
        }
        // 최종 복사 사용 횟수 출력.
        System.out.println(cycle);
    }
}