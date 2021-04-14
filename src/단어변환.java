import java.util.HashSet;
import java.util.Set;

public class 단어변환 {
    static int min;
    static boolean[] check;

    public static void main(String[] args) {
        String begin = "hit";
        String target = "cog";
        String[] words = {"hot", "dot", "dog", "lot", "log"};

        Set<String> wordSet = new HashSet<>();      // words를 검사해서 target이 있는지 확인.
        for (String s : words)
            wordSet.add(s);

        check = new boolean[words.length];
        min = Integer.MAX_VALUE;

        if (wordSet.contains(target)) {
            dfs(begin, target, words, 0);
            System.out.println(min);
        } else
            System.out.println(0);
    }

    static void dfs(String cur, String target, String[] words, int count) {     // 글자를 한글자씩 바꿔가며 DFS를 진행할 메소드
        if (cur.equals(target)) {       // cur와 target이 같아지면 그 때의 count값과 min값을 비교하여 더 작을 경우 갱신.
            min = Math.min(min, count);
            return;
        }

        for (int i = 0; i < words.length; i++) {    // check 배열을 통해 이전에 지나온 단어들은 거치지 않으며, 다음 단어가 현재와 한글자 차이라면 글자를 바꾸고 진행한다.
            if (!check[i] && isOneDiffWords(cur, words[i])) {
                check[i] = true;
                dfs(words[i], target, words, count + 1);
                check[i] = false;
            }
        }
    }

    static boolean isOneDiffWords(String a, String b) {     // 두 String 타입의 객체가 한글자만 다른지 체크해주는 메소드.
        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            if (count > 1)
                break;

            if (a.charAt(i) != b.charAt(i))
                count++;
        }

        if (count == 1)
            return true;
        return false;
    }
}