package 가장긴팰린드롬;

public class Solution {
    public static void main(String[] args) {
        String s = "abcdcba";

        int max = 1;    // 한 글자만 주어졌을 경우 이는 1글자의 팰린드롬
        for (int i = 1; i < (s.length() - 1) * 2; i++) {
            // 글자가 주어졌을 때, 한 글자를 기준으로 좌우가 대칭일 수 있지만(홀수인 경우), 문자 사이의 공백을 기준으로 대칭일 수도 있다.(짝수인 경우)
            // 따라서 이를 한번에 처리하기 위해서 글자 사이마다의 공간을 처리하기 위해 i 값의 범위를 (s.length() - 1) * 2로 주었다.
            // 가령 세 글자의 "AAA"의 경우, "A□A□A"라는 가상의 형식으로 표현한다. 이 때 대칭포인트는 첫번째와 마지막 A는 포함할 필요가 없다.
            // 따라서 1번부터 탐색하여, 3( <(3 - 1) * 2))까지만 생각하면 된다.

            int count = 0;
            int start = i - Math.min(i, (s.length() - 1) * 2 - i);
            // 각 i의 대칭점에서 시작할 지점은 0부터 i까지의 거리와, i부터 (s.length() - 1) * 2까지의 거리 중 짧은 길이를 택해, i에서 그 길이를 뺀 만큼의 위치에서부터 시작한다.

            while (start < i) {     // 대칭점을 넘지 않은 곳까지만 체크
                if (s.charAt(start / 2) == s.charAt((2 * i - start) / 2))   // 검사할 두 문자는, start와 i로부터 (i - start)만큼 오른쪽으로 떨어진 (2 * i - start) 지점이다. 이는 가상의 위치이므로 실제 위치로 바꾸기 위해 2를 나눠준다.
                    count++;    // 만약 두 문자가 같다면 count 개수를 하나 늘려준다.
                else
                    count = 0;  // 같지 않다면 0으로 초기화시켜준다.
                start += 2; // 한번 사이클을 돌 때마다 공백은 건너뛰고 문자열만 검사해야하므로 2씩 증가시킨다.
            }

            if (i % 2 == 1)     // 만약 이번 사이클의 문자열이 짝수개라면(i가 홀수라서 공백을 기준으로 검사한 경우) 이는 2 * count 길이의 팰린드롬이다.
                max = Math.max(max, count * 2);
            else        // 이번 사이클의 문자열이 홀수개라면(i가 짝수라서 한 문자를 기준으로 검사한 경우) 대칭점 문자까지 포함해야하므로 2 * count + 1 길이의 팰린드롬이다.
                max = Math.max(max, count * 2 + 1);
        }
        System.out.println(max);
    }
}