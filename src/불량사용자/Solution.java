package 불량사용자;

import java.util.HashSet;

public class Solution {
    static boolean[] check;
    static HashSet<Integer> set;

    public static void main(String[] args) {
        // banned_id와 user_id를 대조해, 제재되었는지 여부를 확인하여야한다.
        // banned_id와 user_id가 모두 매칭되었다면, 매칭된 유저 목록을 bitmasking을 통해 HashSet에 저장해준다.
        // bitmasking을 통해 제재된 유저목록을 하나의 숫자로 표현할 수 있고, HashSet을 통해 중복된 경우를 제할 수 있다.

        String[] user_id = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
        String[] banned_id = {"*rodo", "*rodo", "******"};

        check = new boolean[user_id.length];
        set = new HashSet<>();

        dfs(user_id, banned_id, 0, 0);
        System.out.println(set.size());

    }

    static void dfs(String[] user_id, String[] banned_id, int level, int bitmask) {     // banned_id 하나당 하나의 user_id가 매치되어야하므로, banned_id는 level로 진행해가며 하나씩 늘려가자.
        if (level == banned_id.length) {    // 모두 매칭되었다면, bitmask 값을 HashSet에 저장.
            set.add(bitmask);
            return;
        }

        for (int i = 0; i < user_id.length; i++) {  // 해당 user_id가 제재되었는지 여부와, banned_id[level]과 대조 결과에 따라 다음 재귀를 부른다.
            if (!check[i] && isBanned(user_id[i], banned_id[level])) {
                check[i] = true;
                dfs(user_id, banned_id, level + 1, bitmask | (int) Math.pow(2, i));
                check[i] = false;
            }
        }
    }

    static boolean isBanned(String user_id, String banned_id) {     // user_id와 banned_id를 대조한다.
        if (user_id.length() != banned_id.length())
            return false;

        for (int i = 0; i < user_id.length(); i++) {
            if (banned_id.charAt(i) != '*' && user_id.charAt(i) != banned_id.charAt(i))
                return false;
        }
        return true;
    }
}