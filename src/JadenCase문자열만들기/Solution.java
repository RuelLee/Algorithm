package JadenCase문자열만들기;

public class Solution {
    public static void main(String[] args) {
        // 각 단어의 첫글자가 알파벳일 경우, 대문자로 바꿔주어야한다.
        String s = "3people unFollowed me";

        StringBuilder sb = new StringBuilder();

        boolean isFirst = true;     // 단어의 첫번째인지를 표시할 boolean
        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);

            if (current == ' ') {   // 공백문자가 나타면 그 다음엔 단어의 첫문자가 온다.
                isFirst = true;
                sb.append(' ');
                continue;
            }

            if (isFirst) {      // 첫문자이고, 알파벳이면서 소문자라면
                if (checkAlphabet(current) && current >= 'a')
                    sb.append((char) (current - 'a' + 'A'));    // 대문자로 변경
                else
                    sb.append(current);     // 알파벳이 아니거나, 이미 대문자라면 그대로 StringBuilder 에 append
                isFirst = false;
            } else {
                if (checkAlphabet(current) && current <= 'Z') {     // 첫문자가 아닌데 대문자가 나타났다면
                    sb.append((char) (current - 'A' + 'a'));        // 소문자로 변경 후 append
                } else
                    sb.append(current);     // 그 외에는 그냥 append
            }
        }
        System.out.println(sb.toString());
    }

    static boolean checkAlphabet(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
}