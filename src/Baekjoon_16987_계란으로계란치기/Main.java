/*
 Author : Ruel
 Problem : Baekjoon 16987번 계란으로 계란치기
 Problem address : https://www.acmicpc.net/problem/16987
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16987_계란으로계란치기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][] eggs;

    public static void main(String[] args) throws IOException {
        // n개의 계란의 내구도와 무게가 주어진다.
        // 두 계란을 부딪쳤을 때, 각각의 계란은 상대편 계란의 무게만큼 내구도가 차감되고
        // 내구도가 0이하가 되면 깨진다.
        // 가장 왼쪽 계란부터 손에 들고, 깨지지 않은 다른 계란을 내려친다고 할 때
        // 깰 수 있는 가장 많은 계란의 수는?
        //
        // 브루트 포스 문제
        // n이 8로 크지 않게 주어지기 때문에 모든 경우의 수를 계산하는 것이 가능
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 계란
        int n = Integer.parseInt(br.readLine());
        
        // 계란의 내구도와 무게
        eggs = new int[n][];
        for (int i = 0; i < eggs.length; i++)
            eggs[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 최대 깰 수 있는 계란의 수 출력
        System.out.println(findAnswer(0, 0));
    }
    
    // 브루트 포스를 통해 답을 구한다.
    // 매개 변수로는 현재 손에 든 계란의 순서와 깨진 계란의 수
    static int findAnswer(int idx, int breaks) {
        // 모든 계란을 시도했다면, 현재 깨진 계란의 수 반환
        if (idx >= eggs.length)
            return breaks;
        // 만약 이미 깨진 계란이라면 오른쪽 계란으로 그대로 넘긴다.
        else if (eggs[idx][0] <= 0)
            return findAnswer(idx + 1, breaks);
        
        // 현재 상황에서 깰 수 있는 계란의 최대 개수
        int max = breaks;
        // idx와 부딪칠 계란을 살펴본다.
        for (int i = 0; i < eggs.length; i++) {
            // idx와 같은 계란이거나 이미 깨진 계란일 경우 건너뛴다.
            if (idx == i || eggs[i][0] <= 0)
                continue;
            
            // 각각의 계란은 상대편의 무게 만큼 내구도 차감
            eggs[idx][0] -= eggs[i][1];
            eggs[i][0] -= eggs[idx][1];

            // idx나 i 중 깨진 계란이 있는지 센다.
            int count = 0;
            if (eggs[idx][0] <= 0)
                count++;
            if (eggs[i][0] <= 0)
                count++;
            // idx와 i가 서로 부딪친 상태로 다음 계란으로 순서를 넘긴다.
            // 그 때의 최대 깰 수 있는 계란의 수가 반환되므로, max에 최대값이 갱신되는지 확인한다.
            max = Math.max(max, findAnswer(idx + 1, breaks + count));

            // idx, i가 부딪쳤을 때의 상황이 끝났으므로
            // 내구도를 복구한다.
            eggs[idx][0] += eggs[i][1];
            eggs[i][0] += eggs[idx][1];
        }
        // 찾아진 경우들 중 최대한 많은 계란을 깬 경우의 계란 수를 반환한다.
        return max;
    }
}