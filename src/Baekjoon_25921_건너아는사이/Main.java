/*
 Author : Ruel
 Problem : Baekjoon 25921번 건너 아는 사이
 Problem address : https://www.acmicpc.net/problem/25921
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25921_건너아는사이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람들이 1 ~ n까지의 번호가 붙어있다.
        // 친구의 친구 사이는 친구이다.
        // 두 사람이 친구가 되는 방법은 두 사람이 함께 식사를 하는 것이며 음식의 가격은
        // 두 사람의 번호가 서로소일 때는 두 번호 중 큰 값
        // 두 사람의 번호가 서로소가 아닐 때는 두 번호의 최대공약수이다.
        // 모든 사람들을 친구 사이로 만들고자할 때, 최소 식사 비용은?
        //
        // 에라토스테네스의 체 문제
        // 서로소일 때는 방법이 없지만, 되도록 작은 번호의 사람과 식사를 하는 것이 유리하다
        // 따라서 작은 수부터 자신의 배수들과는 자신이 식사를 하는 것이 좋다.
        // 이 방법이 에라토스테네스의 체를 사용하는 방법과 같다
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명의 사람
        int n = Integer.parseInt(br.readLine());
        
        // 이미 친구 관계가 된 사람
        boolean[] selected = new boolean[n + 1];
        // 음식 비용
        long sum = 0;
        for (int i = 2; i < selected.length; i++) {
            // i가 친구관계가 되어있다면 건너뛴다.
            if (selected[i])
                continue;
            
            // 그렇지 않다면 친구 표시
            selected[i] = true;
            // 서로소이므로 음식 비용으로 i만큼을 내야한다
            sum += i;
            // 아직 친구관계가 아닌 i의 배수들과 식사를 한다.
            for (int j = 2; i * j < selected.length; j++) {
                if (!selected[i * j]) {
                    selected[i * j] = true;
                    sum += i;
                }
            }
        }
        // 총 음식 비용 출력
        System.out.println(sum);
    }
}