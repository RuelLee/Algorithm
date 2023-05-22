/*
 Author : Ruel
 Problem : Baekjoon 1599번 민식어
 Problem address : https://www.acmicpc.net/problem/1599
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1599_민식어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    static String[] alphabets = {"a", "b", "k", "d", "e", "g", "h", "i", "l", "m", "n", "ng", "o", "p", "r", "s", "t", "u", "w", "y"};

    public static void main(String[] args) throws IOException {
        // 20개의 알파벳에 대한 새로운 순서가 주어진다.
        // 그 중 'ng'라는 두 글자로 이루어진 알파벳도 주어진다.
        // ng는 무조건 이 알파벳으로 생각한다. (n + g로 보지 않는다.)
        // 그리고 n개의 단어가 주어질 때, 해당 단어들을 사전순으로 출력하라
        //
        // 정렬과 관련된 문제
        // java에는 sort를 메소드가 구현되어있고
        // 이 때 매개변수로 Comparator를 통해 어떤 순서로 정렬할 것인지에 대한 정보를 주면
        // 해당 정보로 정렬을 해준다.
        // 따라서 이 문제는 Comparator 를 만들 수 있는가에 대한 문제이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 먼저 알파벳의 순서를 해쉬맵에 저장해둔다.
        HashMap<String, Integer> alphabetsOrder = new HashMap<>();
        for (int i = 0; i < alphabets.length; i++)
            alphabetsOrder.put(alphabets[i], i);

        // n개의 단어
        int n = Integer.parseInt(br.readLine());
        String[] words = new String[n];
        for (int i = 0; i < words.length; i++)
            words[i] = br.readLine();

        // 단어들을 정렬을 하되,
        Arrays.sort(words, (o1, o2) -> {
            // 두 단어의 길이가 다를 수 있으므로 각각의 idx를 설정해준다.
            int idx1 = 0;
            int idx2 = 0;

            //
            int value1, value2;
            while (idx1 < o1.length() && idx2 < o2.length()) {
                // 만약 o1 단어의 idx번째 글자가 n이고, idx+1번째가 g인 경우
                // ng에 해당하는 값을 읽음
                if (o1.charAt(idx1) == 'n' && idx1 + 1 < o1.length() && o1.charAt(idx1 + 1) == 'g') {
                    value1 = alphabetsOrder.get("ng");
                    idx1 += 2;
                } else      // 그렇지 않다면 그냥 하나의 글자에 대한 값을 읽음.
                    value1 = alphabetsOrder.get(String.valueOf(o1.charAt(idx1++)));

                // o2단어도 마찬가지.
                if (o2.charAt(idx2) == 'n' && idx2 + 1 < o2.length() && o2.charAt(idx2 + 1) == 'g') {
                    value2 = alphabetsOrder.get("ng");
                    idx2 += 2;
                } else
                    value2 = alphabetsOrder.get(String.valueOf(o2.charAt(idx2++)));

                // 두 값이 서로 다르다면 두 단어의 순서가 결정된다.
                // 값을 바탕으로 비교값 리턴
                if (value1 != value2)
                    return Integer.compare(value1, value2);
            }

            // while문이 모두 끝날 때까지 결정되지 않은 경우
            // 두 단어가 동일하거나, 짧은 단어가 긴 단어의 접두사인 경우
            // idx1이 단어 끝까지 보지 않았다면, o1이 긴 단어인 경우
            // 긴 단어가 뒤로 가야한다.
            if (idx1 < o1.length())
                return 0;
            // 반대인 경우
            else
                return -1;
        });

        // 정렬이 끝난 단어들을 순서대로 개행하여 출력한다.
        StringBuilder sb = new StringBuilder();
        for (String word : words)
            sb.append(word).append("\n");
        // 답안 출력
        System.out.print(sb);
    }
}