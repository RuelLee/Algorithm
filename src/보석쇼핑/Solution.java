package 보석쇼핑;

import java.util.Arrays;
import java.util.HashMap;

public class Solution {
    public static void main(String[] args) {
        // 모든 보석을 한 종류씩 선택할 수 있는 최소 길이의 구간을 구하라.
        // 각 보석마다 가장 최근의 위치를 저장하자.
        // 새로운 보석이 추가된다 -> 모든 보석을 선택할 수 있는 첫 구간.
        // 값이 갱신된다 -> 구간의 길이가 줄어들었는지 확인하자.
        String[] gems = {"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"};

        HashMap<String, Integer> hashMap = new HashMap<>();
        int[] gemLoc = new int[100000];     // 보석의 가장 최근 위치를 저장할 배열

        int minLength = Integer.MAX_VALUE;      // 구간의 길이
        int[] answer = new int[2];          // 구간의 시작과 끝
        for (int i = 0; i < gems.length; i++) {
            if (!hashMap.containsKey(gems[i])) {    // 새로운 보석이라면, minLength 와 answer 값을 현재 시점의 값으로 바꿔주자.
                hashMap.put(gems[i], hashMap.size());
                gemLoc[hashMap.get(gems[i])] = i + 1;
                answer = getRange(hashMap.size(), gemLoc);
                minLength = answer[1] - answer[0] + 1;
            } else {        // 이미 있는 값이라면, 구간의 길이가 줄어들었는지 확인하고, 그랬다면 minLength 와 answer 값을 갱신해주자.
                gemLoc[hashMap.get(gems[i])] = i + 1;
                int[] temp = getRange(hashMap.size(), gemLoc);
                if (minLength > temp[1] - temp[0] + 1) {
                    answer = temp;
                    minLength = answer[1] - answer[0] + 1;
                }
            }
        }
        System.out.println(Arrays.toString(answer));
    }

    static int[] getRange(int n, int[] gemLoc) {    //HashMap 의 size 와 gemLoc 배열을 주면, 현재 보석들의 구간 길이를 계산해준다.
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            min = Math.min(min, gemLoc[i]);
            max = Math.max(max, gemLoc[i]);
        }
        return new int[]{min, max};
    }
}