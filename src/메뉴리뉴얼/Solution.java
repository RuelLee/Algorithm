package 메뉴리뉴얼;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Solution {
    static HashMap<String, Integer> hashMap;
    static int[] maximumOrdered;

    public static void main(String[] args) {
        // 두 명 이상이 주문한 메뉴들 중 그 가짓수가 course 만큼 있는 것들을 골라라.
        // orders에 나오는 주문들을 메뉴가 2개 이상은 조합을 모두 구해서, HashMap에 조합 이름으로 된 값을 넣고, value값을 Integer로 줘서 값을 늘려나가자.

        String[] orders = {"XYZ", "XWY", "WXA"};
        int[] course = {2, 3, 4};
        hashMap = new HashMap<>();

        maximumOrdered = new int[21];
        for (String order : orders) {
            for (int i = 2; i < order.length() + 1; i++)
                combination(order, 0, i, 0, "");
        }

        List<String> list = new ArrayList<>();
        for (String s : hashMap.keySet()) {
            if (maximumOrdered[s.length()] == hashMap.get(s) && maximumOrdered[s.length()] >= 2 && isElement(course, s.length()))   // 조합 메뉴가 2개 이상이고, 주문 횟수가 해당 메뉴 가짓수에 대한 최대 주문 횟수와 같고, course에 주어진 메뉴 가짓수일 때
                list.add(s);    // 리스트에 담는다.
        }
        list.sort(String::compareTo);   // 모두 담아지면 알파벳순으로 정렬.
        System.out.println(Arrays.toString(list.toArray(new String[0])));
    }

    static void combination(String s, int selected, int toChoose, int cycle, String result) {   // 주문에 대한 메뉴들의 조합을 뽑는 함수
        if (selected == toChoose) {     // 골라졌다면
            char[] chars = new char[result.length()];
            for (int i = 0; i < result.length(); i++)
                chars[i] = result.charAt(i);
            Arrays.sort(chars);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length(); i++)
                sb.append(chars[i]);
            result = sb.toString();     // 메뉴들을 알파벳순으로 정렬해준 다음

            if (hashMap.containsKey(result))    // 이미 있는 메뉴조합이라면 그 값을 1증가
                hashMap.put(result, hashMap.get(result) + 1);
            else
                hashMap.put(result, 1);     // 아니라면 새로운 값을 추가.
            if (hashMap.get(result) >= maximumOrdered[result.length()])     // 그리고 해당 메뉴 가짓수에 대해 최대주문수를 계속 갱신해주자.
                maximumOrdered[result.length()] = hashMap.get(result);
            return;
        }

        if (cycle >= s.length())
            return;

        combination(s, selected + 1, toChoose, cycle + 1, result + s.charAt(cycle));
        combination(s, selected, toChoose, cycle + 1, result);
    }

    static boolean isElement(int[] course, int a) {
        for (int i : course) {
            if (a == i)
                return true;
        }
        return false;
    }
}