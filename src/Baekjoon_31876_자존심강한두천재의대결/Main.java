/*
 Author : Ruel
 Problem : Baekjoon 31876번 자존심 강한 두 천재의 대결
 Problem address : https://www.acmicpc.net/problem/31876
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31876_자존심강한두천재의대결;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // u진법의 수 a, v진법의 수 b가 주어진다.
        // 2 <= u, v <= 10
        // 0 <= a <= u^3_000_000
        // 0 <= b <= v^3_000_000
        // 그리고 a와 b의 길이는 같다.
        // a가 크다면 ras, b가 크다면 auq, 같다면 rasauq을 출력한다
        //
        // 애드 혹 문제
        // 문제에서 주어지다시피 자릿수가 300이나 되는 엄청 큰 수이다.
        // int는 2의 31자리, long은 2의 63자리 수를 다룰 수 있으므로 직접 비교하기엔 터무니없이 크다는 걸 알 수 있다.
        // 보면 자릿수가 10자리 이하이므로
        // 일정 자릿수가 되면 자릿수만으로도 값의 대소가 결정되는 시기가 온다.
        // 그러한 차이가 가장 작은 두 수를 꼽자면 10과 9이다.
        // 10진법과 9진법이 같은 자릿수라는 조건 하에
        // 10진법의 가장 작은 값이 9진법의 가장 큰 값보다 더 커지는 시점을 찾는다.
        // 그 때의 자릿수는 22였다.
        // 자릿수가 22보다 같거나 큰 두 수를 비교한다면
        // u와 v의 비교만으로도 값 비교가 끝난다.
        // u와 v가 같다면 큰 자리부터 값을 비교하면 된다.
        // 자릿수가 22보다 작다면 직접 비교할 수 있는 정도다.
        // 다만 역시 값이 크긴하므로 BigInteger로 값 비교를 해준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // a의 진법 u, b의 진법 v
        int u = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());
        String a = br.readLine();
        String b = br.readLine();

        int answer = 0;
        // 자릿수가 22자리 이상인 경우
        if (a.length() >= 22) {
            // u와 v가 같다면 직접 큰 자릿수부터 수 비교
            if (u == v) {
                for (int i = 0; i < a.length(); i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        if (a.charAt(i) < b.charAt(i))
                            answer = -1;
                        else
                            answer = 1;
                        break;
                    }
                }
            } else if (u < v)   // v가 크다면 b가 큼
                answer = -1;
            else        // 반대인 경우는 a가 큼
                answer = 1;
        } else {    
            // 22자리 미만이라면
            // BigInteger로 직접 값 비교
            BigInteger numA = new BigInteger(a, u);
            BigInteger numB = new BigInteger(b, v);
            answer = numA.compareTo(numB);
        }
        // 답 출력
        System.out.println(answer < 0 ? "auq" : answer == 0 ? "rasauq" : "ras");
    }
}