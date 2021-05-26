package 압축;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Solution {
    static HashMap<String, Integer> hashMap;

    public static void main(String[] args) {
        // 알파벳에 대한 데이터를 저장한 뒤,
        // 만약 다음 문자열이 데이터에 없을 경우, 해당 문자열을 데이터를 저장하고,
        // 현재 존재하는 문자열에 대한 idx 값으로 문자열을 압축한다.
        String msg = "TOBEORNOTTOBEORTOBEORNOT";

        setHashMap();
        List<Integer> list = new ArrayList<>();

        int lastIdx = 0;
        for (int i = 1; i <= msg.length(); i++) {   // lastIdx 부터 i 까지의 문자열을 뽑아낸다.
            if (!hashMap.containsKey(msg.substring(lastIdx, i))) {  // 해당 문자열의 정보가 없을 경우에는,
                list.add(hashMap.get(msg.substring(lastIdx, i - 1)));   // 해당 문자열보다 1글자 적은 정보는 있으므로, 해당 정보를 list 에 남기고,
                hashMap.put(msg.substring(lastIdx, i), hashMap.size() + 1);     // 해당 문자열을 HashMap 에 저장해준다.
                lastIdx = i - 1;    // 다음 문자열의 시작은 list 에 남겨진 문자열의 다음 글자부터 시작한다.
            }
        }
        // i 값이 정보가 없는 경우 기준이므로 문자열의 길이를 넘으면 그냥 종료된다. 하지만 해당 문자 혹은 문자열은 반드시 HashMap 안에 등록되어 있다.(최소 알파벳은 들어있으므로)
        // 따라서 마지막으로 남겨진 lastIdx로부터 마지막 문자까지의 문자열을 HashMap 에서 찾은 뒤, 해당 값을 list에 추가해주자.
        list.add(hashMap.get(msg.substring(lastIdx)));

        System.out.println(Arrays.toString(list.stream().mapToInt(i -> i).toArray()));
    }

    static void setHashMap() {      // 각 알파벳에 대한 정보를 HashMap 에 저장해주자.
        hashMap = new HashMap<>();
        for (int i = 0; i < 26; i++)
            hashMap.put(String.valueOf((char) ('A' + i)), i + 1);
    }
}