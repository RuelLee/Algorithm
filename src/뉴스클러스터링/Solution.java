package 뉴스클러스터링;

import java.util.HashMap;

public class Solution {
    public static void main(String[] args) {
        // 두 문자열을 두글자씩 끊어, 두 글자가 모두 알파벳일 경우, 그것들을 원소로 갖는 각각의 집합을 만든다
        // 이 때 집합은 중복을 허용한다.
        // 두 집합의 합집합의 원소의 개수와 교집합의 원소의 개수를 구하라.

        String str1 = "handshake";
        String str2 = "shake hands";

        HashMap<String, Integer> hashMapOne = new HashMap<>();      // HashMap을 통해 동일 원소가 존재한다면 Integer 값을 하나씩 늘려주자.
        for (int i = 0; i < str1.length() - 1; i++) {
            String sub = str1.substring(i, i + 2).toLowerCase();
            if (isAlphabet(str1.charAt(i)) && isAlphabet(str1.charAt(i + 1))) {
                if (hashMapOne.containsKey(sub))
                    hashMapOne.put(sub, hashMapOne.get(sub) + 1);
                else
                    hashMapOne.put(sub, 1);
            }
        }

        HashMap<String, Integer> hashMapTwo = new HashMap<>();
        for (int i = 0; i < str2.length() - 1; i++) {
            String sub = str2.substring(i, i + 2).toLowerCase();
            if (isAlphabet(str2.charAt(i)) && isAlphabet(str2.charAt(i + 1))) {
                if (hashMapTwo.containsKey(sub)) {
                    hashMapTwo.put(sub, hashMapTwo.get(sub) + 1);
                } else
                    hashMapTwo.put(sub, 1);
            }
        }
        int total = 0;
        int cnt = 0;
        for (String s : hashMapOne.keySet()) {      //HashMapOne에서 총 원소의 개수를 구하며, HashmMapTwo에 중복원소가 존재하는지 검사하자.
            total += hashMapOne.get(s);
            if (hashMapTwo.containsKey(s))
                cnt += hashMapOne.get(s) > hashMapTwo.get(s) ? hashMapTwo.get(s) : hashMapOne.get(s);   // 존재한다면 그 중 작은 값을 cnt에 더해주자.
        }
        for (String s : hashMapTwo.keySet())
            total += hashMapTwo.get(s);
        total -= cnt;       // cnt는 교집합 원소의 개수이므로, 합집합 원소의 개수는 총 원소의 개수 - 교집합 원소의 개수

        System.out.println(total == 0 ? 65536 : (int) (cnt / (double) total * 65536));
    }

    static boolean isAlphabet(char c) {
        if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
            return true;
        return false;
    }
}