/*
 Author : Ruel
 Problem : Baekjoon 26217번 별꽃의 세레나데 (Easy)
 Problem address : https://www.acmicpc.net/problem/26217
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_26217_별꽃의세레나데_Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n종류의 씨앗들이 주어진다.
        // 해당 씨앗들은 모양이 같아 꽃이 피기 전까지는 어떤 꽃이 피어날지 모른다고 한다.
        // 모든 종류의 꽃을 하나 이상씩 얻기 위해서 심어야하는 씨앗의 기대값은 몇 개인가?
        //
        // 간단한 수학 문제
        // n 종류의 씨앗이 주어진다면
        // 첫번째 씨앗을 심으면 어떠한 꽃이든 하나의 꽃이 피어난다.
        // 두번째 씨앗을 심으면, 처음에 핀 꽃을 제외한 다른 꽃이 피어날 확률은 (n - 1) / n 이다
        // 따라서 두번째 심은 씨앗이 다른 꽃이기 위한 기댓값은 위 확률의 역수인 n / (n - 1)이다.
        // ...
        // i번째 씨앗을 심었을 때, 이전에 피지 않은 꽃일 확률은 (n - (i -1)) / n 이며
        // 기대값은 위 확률의 역수이다.
        // 처음부터, n번째 꽃까지의 기대값을 모두 더해 더해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n 종류
        int n = Integer.parseInt(br.readLine());
        
        // 답안은 double 타입
        double answer = 0;
        // i번째로 심은 씨앗이 이전에 핀 꽃이 아닐 확률은 (n - (i - 1)) / n
        // 이전에 피지 않은 꽃을 피우기 위한 심어야하는 씨앗의 기대값은 n / (n - (i - 1))
        // 따라서 위 기대값을 1 ~ n까지 모두 구해 더해준다.
        for (int i = 1; i <= n; i++)
            answer += n / (double) (n - (i - 1));

        // 모든 꽃을 최소 하나씩 피우기 위한 기대값 출력.
        System.out.println(answer);
    }
}