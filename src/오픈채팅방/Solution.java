package 오픈채팅방;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        // uid에 따른 닉네임이 새로 생기거나 변경되는 때는 Enter 또는 Change
        // 두 경우를 잡아 HashMap에 uid, 닉네임으로 값을 저장.
        // 출력메시지가 표시되는 경우는 입장과 퇴장
        // Enter 또는 Leave일 때, 해당 uid로 HashMap에서 닉네임을 가져와서 출력해주자.

        String[] record = {"Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234", "Enter uid1234 Prodo", "Change uid4567 Ryan"};

        HashMap<String, String> hashMap = new HashMap<>();
        for (String re : record) {
            String[] split = re.split(" ");

            if (split[0].equals("Enter") || split[0].equals("Change")) {
                hashMap.put(split[1], split[2]);
            }
        }
        List<String> list = new ArrayList<>();

        for (String re : record) {
            String[] split = re.split(" ");

            if (split[0].equals("Enter")) {
                list.add(hashMap.get(split[1]) + "님이 들어왔습니다.");
            } else if (split[0].equals("Leave")) {
                list.add(hashMap.get(split[1]) + "님이 나갔습니다.");
            }
        }

        System.out.println(Arrays.toString(list.toArray(new String[0])));
    }
}