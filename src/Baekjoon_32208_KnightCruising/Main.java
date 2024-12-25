/*
 Author : Ruel
 Problem : Baekjoon 32208번 Knight Cruising
 Problem address : https://www.acmicpc.net/problem/32208
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32208_KnightCruising;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 3차원 공간의 원점(0, 0, 0)에 나이트가 놓여있다.
        // 나이트는 이동을 할 수 있는데, 한 번에, (x, y, z) 좌표중 하나는 +-1, +-2, +-3 으로 이동할 수 있다고 한다.
        // n개의 좌표가 주어질 때, 해당 좌표로 나이트가 이동할 수 있는지 출력하라
        //
        // 애드혹
        // 좌표의 크기가 최대 10억으로 주어지므로, BFS를 이용하려면 시간적, 공간적으로 너무 커진다.
        // 따라서 좌표가 주어지면 일정 계산을 통해 간단하게 판별하는 방법이 존재한다.
        // 먼저 좌표를 이리저리 움직여, 처음 위치에서 가장 작게 움직일 수 있는 방법을 생각해보자.
        // 가장 작게 x, y, z좌표중 하나만 +-2가 되거나, 혹은 2개의 좌표에 대해 +-1을 할 수 있다.
        // 다시 말해, 한 좌표에 대해 2칸을 움직이거나, 2좌표에 대해 1칸씩 두 칸을 움직이는 것이 가능하다.
        // 따라서, 주어지는 좌표의 합이 짝수이라면 해당 좌표로 이동할 수 있다는 것을 알 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 좌표
        int n = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 해당 좌표
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            
            // 세 좌표의 합이 짝수라면 이동할 수 있고
            // 그렇지 않다면 이동이 불가능하다.
            sb.append(((x % 2) + (y + 2) + (z % 2)) % 2 == 0 ? "YES" : "NO").append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}