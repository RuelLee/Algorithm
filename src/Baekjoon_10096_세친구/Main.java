/*
 Author : Ruel
 Problem : Baekjoon 10096번 세 친구
 Problem address : https://www.acmicpc.net/problem/10096
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10096_세친구;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 세 명의 친구가 주어진다.
        // 첫 친구는 문자열 S를 선택한다.
        // 두번째 친구는 S를 S 뒤에 붙인다.
        // 세번째 친구는 문자열 어딘가에 자신이 고른 문자 하나를 삽입한다.
        // 만들어진 문자열 u에서 문자열 S를 구하라
        //
        // 애드 혹 문제
        // 같은 문자열이 반복되고, 어딘가에 문자 하나가 끼어있는 형태이다.
        // 따라서 문자 하나가 앞 문자열에 있는가 뒷문자열에 있는가를 구분하여 두 번만 살펴보면 된다.
        // 먼저 문자가 뒤에 있다고 가정한다면
        // 앞에는 처음부터 s가 온전히 들어가있다.
        // 반대로 문자가 앞에 있다고 한다면, s의 길이 / 2 + 1 위치부터 뒤의 문자열 s가 온전히 들어가있다.
        // 위 점을 참고하여 최대 한 번 건너뛰기를 허용하며 앞 뒤 문자열이 같은지 판별한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 길이 n의 문자열 u
        int n = Integer.parseInt(br.readLine());
        String u = br.readLine();

        // 만약 n이 짝수라면 불가능한 경우.
        if (n % 2 == 0)
            System.out.println("NOT POSSIBLE");
        else {
            boolean[] possible = new boolean[2];
            Arrays.fill(possible, true);
            int bias = 0;
            // 앞 문자열에 문자 하나가 끼어있는 형태라면
            // u.length() / 2 + 1부터 문자열 s가 온전히 있다.
            // 양 쪽의 문자를 비교하며, 앞 부분에 한해 1번의 건너뛰기(삽입된 문자)를 허용한다.
            for (int i = 0; i < u.length() / 2 && bias <= 1; i++) {
                if (u.charAt(i + bias) != u.charAt(u.length() / 2 + 1 + i)) {
                    bias++;
                    i--;
                }
            }
            // 만약 bias가 0인 상태라면 삽입된 문자가 정중앙에 있는 경우.
            // 뒤에서도 세어지기 때문에, 해당 경우는 제외.
            // bias가 2이상이 됐다면 불가능한 경우이므로
            // 첫번째에 실패라고 체크해둠
            if (bias == 0 || bias > 1)
                possible[0] = false;

            bias = 0;
            // 이번엔 뒷 문자열에 문자가 하나 끼어있는 형태.
            for (int i = 0; i < u.length() / 2 && bias <= 1; i++) {
                if (u.charAt(i) != u.charAt(u.length() / 2 + bias + i)) {
                    bias++;
                    i--;
                }
            }
            if (bias > 1)
                possible[1] = false;

            // 둘 다 가능하다면, 여러 형태가 가능하므로 NOT UNIQUE 출력
            if (possible[0] && possible[1])
                System.out.println("NOT UNIQUE");
            // 둘 다 불가능하다면 NOT POSSIBLE 출력
            else if (!possible[0] && !possible[1])
                System.out.println("NOT POSSIBLE");
            else        // 한 경우만 가능하다면 해당 경우의 s를 출력
                System.out.println(possible[0] ? u.substring(u.length() / 2 + 1) : u.substring(0, u.length() / 2));
        }
    }
}