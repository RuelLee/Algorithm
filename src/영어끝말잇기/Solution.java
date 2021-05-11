package 영어끝말잇기;

import java.util.Arrays;
import java.util.HashSet;

public class Solution {
    public static void main(String[] args) {
        // n명의 인원이 끝말잇기를 진행할 때, 어떤 사람이 몇 번의 말을 했을 때 게임이 끝나는지에 대한 문제이다.
        // 중복 단어 여부는 HashSet으로 처리하자.

        int n = 2;
        String[] words = {"hello", "one", "even", "never", "now", "world", "draw"};

        HashSet<String> answered = new HashSet<>();

        int i = 0;
        char lastChar = words[0].charAt(0);     // 첫단어는 성립해야하므로, 첫단어의 머릿글자를 저장해두고, 다음부터는 해당 단어의 마지막 글자를 저장하자.
        boolean ended = false;
        for (; i < words.length; i++) {
            if (!answered.contains(words[i]) && lastChar == words[i].charAt(0)) {   // 끝말잇기가 성립된다면, 해당 단어를 HashSet에 추가, words[i]의 마지막 글자를 lastChar에 저장해두자.
                answered.add(words[i]);
                lastChar = words[i].charAt(words[i].length() - 1);
            } else {    // HashSet에 있는 단어거나, words[i]의 머릿글자가 lastChar와 다르다면 게임이 끝난다.
                ended = true;
                break;
            }
        }


        // 만약 중간에 끝났다면, i번째 단어에서 끝났다.
        // 0부터 i가 시작했음에 유의하고, n명의 사람이 있으므로, i%n을 하면 몇번째 사람인지 알 수 있다.
        // 여기서는 1부터 시작이므로 값에 +1을 해주자.
        // 이 사람이 몇 번째 반복에서 게임이 끝났는지는 i값을 n으로 나눠주자.
        // 이 사람은 무조건 이번 사이클에서 말을 했으므로, +1을 해주자.

        if (ended)
            System.out.println(Arrays.toString(new int[]{(i % n) + 1, (i / n) + 1}));
        else
            System.out.println(Arrays.toString(new int[]{0, 0}));
    }
}