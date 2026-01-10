/*
 Author : Ruel
 Problem : Baekjoon 2608번 로마 숫자
 Problem address : https://www.acmicpc.net/problem/2608
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2608_로마숫자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static String[] rome = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
    static int[] arabia = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    static HashMap<String, Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // 로마 수가 주어진다.
        // 로마 수는 I V X L C D M이 있고, 각각 1 5 10 50 100 500 1000을 나타낸다.
        // 같은 수는 연속해서 세번까지 쓸 수 있다.
        // 작은 수가 큰 수 왼쪽에 붙는 경우가 있는데 IV IX XL XC CD CM이 있다.
        // 각각 오른쪽 수에서 왼쪽 수를 뺀 수를 의미한다.
        // 두 개의 로마 수가 주어질 때, 두 수의 합을 아라비아 수와 로마 수로 표현하라
        //
        // 구현 문제
        // 가능한 모든 로마 수를 만들어 아라비아 수와 매칭해둔 뒤
        // 로마 수에서 아라비아 수로 만들 때는, 두글자로 먼저 매칭되는 수가 있는지 없다면 한 글자 순으로 순차적으로 더해나간다.
        // 아라비아 수에서 로마 수로 만들 때는, 현재 뺄 수 있는 가장 큰 로마수를 빼가며 로마수를 연이어 붙여나가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 로마 수에 아라비아 수를 맵을 통해 매칭
        for (int i = 0; i < rome.length; i++)
            map.put(rome[i], arabia[i]);

        // 입력으로 들어온 두 수를 아라비아 수로 변환
        int num1 = toArabia(br.readLine());
        int num2 = toArabia(br.readLine());

        // 두 수의 합
        int sum = num1 + num2;
        // 합을 아라비아 수와 로마 수로 출력
        System.out.println(sum + "\n" + toRome(sum));
    }

    // 아라비아 수를 로마 수로
    static String toRome(int n) {
        StringBuilder sb = new StringBuilder();
        // 가능한 가장 큰 로마 수를 빼가며 로마수를 연이어 붙여준다.
        for (int i = arabia.length - 1; i >= 0; i--) {
            while (n >= arabia[i]) {
                sb.append(rome[i]);
                n -= arabia[i];
            }
        }
        return sb.toString();
    }

    // 로마 수를 아라비아 수로
    static int toArabia(String s) {
        int num = 0;
        // 앞에서부터
        for (int i = 0; i < s.length(); ) {
            // 가능하다면 두 글자 수가 매칭되는 것이 있나 살펴보고
            if (i + 1 < s.length() && map.containsKey(s.substring(i, i + 2))) {
                num += map.get(s.substring(i, i + 2));
                i += 2;
            } else {    // 없다면 한 글자로 매칭
                num += map.get(s.substring(i, i + 1));
                i++;
            }
        }
        // 합을 반환
        return num;
    }
}