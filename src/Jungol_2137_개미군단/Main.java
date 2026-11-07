/*
 Author : Ruel
 Problem : Jungol 2137번 개미 군단
 Problem address : https://jungol.co.kr/problem/2137
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_2137_개미군단;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 길이 l의 수평 막대 위에 n 마리의 개미 군단이 있다.
        // 각 개미는 초속 1cm로 움직이며, 두 개미가 부딪칠 경우, 서로 진행 방향을 바꾸며
        // 막대의 끝에 도달할 경우 떨어진다.
        // 방향에 대한 정보는 주어지지 않는다.
        // 모든 개미가 떨어지는 최소 시간과 최대 시간은?
        //
        // 애드혹 문제
        // 방향이 주어지지 않으므로 임의로 설정이 가능하다.
        // 두 개미에 대해서만 생각해보면,
        // 두 개미가 부딪칠 경우 서로 방향을 바꿔 계속 진행한다라는 점이
        // = 두 개미가 서로를 통과해서 지나간다 와 개미만 바뀔 뿐, 떨어지는 시각은 동일하다.
        // 따라서 각 개미에 대해 막대의 두 끝에 이르는 최소 시간, 최대 시간을 비교하가며 답을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 길이 l의 막대, n마리의 개미
        int l = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // 각 개미의 위치
        int[] ants = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            ants[i] = Integer.parseInt(st.nextToken());

        // 최소, 최대 시간
        int[] answer = new int[2];
        for (int i = 0; i < ants.length; i++) {
            // 각 개미가 떨어지는 최소 시각을 구하고, 그 중 최댓값(=마지막 개미가 떨어지는 시간)을 구한다.
            answer[0] = Math.max(answer[0], Math.min(ants[i], l - ants[i]));
            // 각 개미가 떨어지는 최대 시각을 구하고, 그 중 최댓값을 구한다.
            answer[1] = Math.max(answer[1], Math.max(ants[i], l - ants[i]));
        }
        // 답 출력
        System.out.println(answer[0] + " " + answer[1]);
    }
}