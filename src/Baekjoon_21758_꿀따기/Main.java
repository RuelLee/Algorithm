/*
 Author : Ruel
 Problem : Baekjoon 21758번 꿀 따기
 Problem address : https://www.acmicpc.net/problem/21758
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21758_꿀따기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 꿀을 채취할 수 있는 지점들이 주어진다.
        // 이 지점들 중 두 곳에는 꿀벌을 두고, 한 곳에는 꿀통을 둔다.
        // 각 꿀벌들은 자기 지점으로부터 꿀통까지 꿀들을 채취하여 이동한다.
        // 꿀벌이 놓인 위치에서는 어느 꿀벌도 꿀을 채취할 수 없다고 한다.
        // 꿀통에 최대한 많은 꿀을 채취하고자 할 때, 꿀의 양은?
        //
        // 누적합 문제
        // 2마리의 꿀벌과 하나의 꿀통이므로 세가지의 경우가 가능하다.
        // 1. 꿀벌 -> 꿀벌 -> 꿀통
        // 위 경우에는 첫번째 꿀벌은 0번 위치에서 시작하는 것이 유리.
        // 꿀통은 마지막 위치에하는 것이 유리.
        // 두번째 꿀벌의 위치를 채취할 수 있는 꿀의 양을 비교하며 찾는다.
        // 2. 꿀벌 -> 꿀통 -> 꿀벌
        // 각 꿀벌들은 서로 끝지점에 위치하는 것이 유리.
        // 꿀통의 위치를 채취할 수 있는 꿀의 양을 비교하며 찾는다.
        // 3. 꿀통 -> 꿀벌 -> 꿀벌
        // 꿀통은 첫번째 자리에 위치하는 것이 유리.
        // 두번째 꿀벌은 오른쪽 끝에 위치하는 것이 유리.
        // 첫번째 꿀벌의 위치를 채취할 수 있는 꿀의 양을 비교하며 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] honeycombs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 누적합
        int[] psum = new int[honeycombs.length];
        psum[0] = honeycombs[0];
        for (int i = 1; i < psum.length; i++)
            psum[i] = psum[i - 1] + honeycombs[i];

        int max = 0;
        // 꿀벌 -> 꿀벌 -> 벌통
        // psum[n - 1] - psum[0] -> 첫번째 꿀벌이 채취하는 꿀의 양
        // psum[n - 1] - psum[i] -> 두번째 꿀벌이 채취하는 꿀의 양
        // -honeycombs[i] -> 두번째 꿀벌이 위치함으로써 첫번째 꿀벌이 꿀을 채취하지 못하는 양.
        for (int i = 1; i < psum.length; i++)
            max = Math.max(max, (psum[n - 1] - psum[0]) + (psum[n - 1] - psum[i]) - honeycombs[i]);
        // 꿀벌 -> 벌통 -> 꿀벌
        // psum[i] - psum[0] -> 첫번째 꿀벌이 채취하는 꿀의 양 
        // psum[n - 2] - psum[i - 1] -> 두번째 꿀벌이 채취하는 꿀의 양
        for (int i = 1; i < psum.length; i++)
            max = Math.max(max, (psum[i] - psum[0]) + (psum[n - 2] - psum[i - 1]));
        // 벌통 -> 꿀벌 -> 꿀벌
        // psum[i] - honeycombs[i] -> 첫번째 꿀벌이 채취하는 꿀의 양
        // psum[n - 1] - honeycombs[n - 1] -> 두번째 꿀벌이 채취하는 꿀의 양
        // -honeycombs[i] -> 첫번째 꿀벌이 위치함으써 두번째 꿀벌이 채취하지 못하는 꿀의 양
        for (int i = 0; i < psum.length - 1; i++)
            max = Math.max(max, (psum[n - 1] - honeycombs[n - 1]) + (psum[i] - honeycombs[i] * 2));

        // 채취할 수 있는 최대 꿀의 양 출력.
        System.out.println(max);
    }
}