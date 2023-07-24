/*
 Author : Ruel
 Problem : Baekjoon 1394번 암호
 Problem address : https://www.acmicpc.net/problem/1394
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1394_암호;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static final int LIMIT = 900528;

    public static void main(String[] args) throws IOException {
        // 암호에 쓰이는 문자들이 순서에 따라 주어진다.
        // 암호가 주어졌을 때, 해당 암호를 찾기 위해 사전순으로 모두 대입해본다했을 때
        // 몇 번째만에 해당 암호를 찾을 수 있는가?
        //
        // 조합 문제
        // 문자의 순서가 주어지므로, 해당 문자들의 순서를 기억해둔다.
        // 암호가 주어지므로, 각 자리별로 한 글자가 넘어가기 위해서 필요한 경우의 수를 미리 계산해둔다.
        // 그 후, 각 자리 암호 문자의 순서와 해당하는 경우의 수를 대입해가며
        // 사전순으로 찾았을 때, 몇 번째만에 찾을 수 있는지 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 암호에 쓰이는 문자들
        char[] chars = br.readLine().toCharArray();
        String password = br.readLine();

        // 문자들을 해쉬맵을 통해 순서를 바로 찾을 수 있도록 매칭시켜둔다.
        HashMap<Character, Integer> order = new HashMap<>();
        for (int i = 0; i < chars.length; i++)
            order.put(chars[i], i + 1);
        
        // 각 자리 별로 다음 문자로 넘어가기 위핸 경우의 수를 계산해둔다.
        // 가장 오른쪽 자리는 1이며, 한자리씩 오른쪽으로 넘어갈 때마다
        // 문자의 개수만큼 곱해진다.
        int[] pows = new int[password.length()];
        pows[password.length() - 1] = 1;
        for (int i = password.length() - 2; i >= 0; i--) {
            pows[i] = pows[i + 1] * chars.length;
            pows[i] %= LIMIT;
        }
        
        // 총 순서
        int sum = 0;
        for (int i = 0; i < password.length(); i++) {
            // i번째 글자에 password.charAt(i)가 오기 위해서는
            // pow[i] * password.charAt(i)의 순서 만큼의 횟수가 필요하다
            // 해당 횟수를 더해주고 LIMIT을 넘어갈 수 있으므로 모듈러 연산을 취해준다.
            sum += order.get(password.charAt(i)) * pows[i];
            sum %= LIMIT;
        }

        // 답안을 출력한다.
        System.out.println(sum);
    }
}