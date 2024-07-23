/*
 Author : Ruel
 Problem : Baekjoon 17449번 순위 계산
 Problem address : https://www.acmicpc.net/problem/17449
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17449_순위계산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 주인공이 채점받을 당시의 등수가 주어진다.
        // 그리고 이후 n명이 채점을 받는데, 해당 사람들이 채점받은 당시의 등수가 주어진다.
        // 주인공이 가능한 가장 높은 순위와 가장 낮은 순위를 출력하라
        //
        // 그리디, 애드혹 문제
        // 가장 낮은 순위는 간단하게 자신보다 같거나 높은 순위가 등장할 때마다 하나씩 순위를 뒤로 밀면 된다.
        // 높은 순위일 경우, 동점자 처리를 해야한다.
        // 자신과 같은 순위일 경우, 동점으로 같은 순위에 위치할 가능성이 생기기 때문.
        // 뿐만 아니라 m등일 때, 동점자가 2명이라 계산했다면, 이후 등장하는 결과에서 m+1 등이 등장할 수 없다.
        // 동점자가 존재해서 다음 순위는 m+2 등이기 때문.
        // 따라서 동점자의 인원을 계산하며, 등장해서는 안되는 순위 범위까지 고려해야한다.
        // 그러한 순위가 등장한다면 해당 순위의 앞까지 한명씩 배치하고, 해당 순위에 동점자들이 몰려있는 형태로 생각하는 것이
        // 주인공이 가능한 최대 순위가 될 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int r = Integer.parseInt(br.readLine());
        int n = Integer.parseInt(br.readLine());
        
        // 최고 순위
        int max = r;
        int sameCounter = 0;
        // 최저 순위
        int min = r;
        // 주인공 이후, 참가자들의 순위
        int[] participant = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        for (int p : participant) {
            // 만약 max보다 작다면 무조건 순위 하락
            if (p < max)
                max++;
            else if (p == max) {        // 만약 같다면 동점자의 가능성이 존재.
                sameCounter++;
            } else if (p <= max + sameCounter) {
                // max + sameCounter보다 같거나 작은 순위(p)가 등장했다면
                // p보다 높은 순위에 대해 한명씩 배치 후
                // 주인공을 포함한 나머지 인원이 p 등에 몰려있다고 계산.
                sameCounter -= (p - max - 1);
                max = p;
            }

            // 최저 순위는 같거나 작은 순위가 등장할 때마다 하나씩 순위를 낮춘다.
            if (p <= min)
                min++;
        }
        // 계산 값 출력
        System.out.println(max + " " + min);
    }
}