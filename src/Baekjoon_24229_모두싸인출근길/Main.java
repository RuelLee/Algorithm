/*
 Author : Ruel
 Problem : Baekjoon 24229번 모두싸인 출근길
 Problem address : https://www.acmicpc.net/problem/24229
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24229_모두싸인출근길;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 판자가 (l, r) 위치에 놓여있다.
        // 판자 위에서는 자유롭게 건너갈 수 있으며, 판자에서 최대 걸은 거리만큼 점프하여 다음 판자로 이동할 수 있다.
        // 예를 들어 9에서 착지해서 12에서 점프를 한다면 최대 3만큼 점프를 할 수 있다.
        // 이동할 수 있는 최대 거리는 얼마인가?
        //
        // 스위핑 문제
        // 도달할 수 있는 위치, 그 때 점프할 수 있는 최대 거리
        // 그리고 현재 점프로 이동가능한 최대 거리를 따로 변수로 가져가며 스위핑해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 판자
        int n = Integer.parseInt(br.readLine());
        int[][] boards = new int[n][];
        for (int i = 0; i < n; i++)
            boards[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 판자의 시작 위치를 기준으로 정렬한다.
        Arrays.sort(boards, Comparator.comparingInt(o -> o[0]));
        
        // 현재 걸어서 이동가능한 최대 위치
        int current = 0;
        // 그 때의 최대 점프 거리
        int maxJump = 0;
        // 이전 기록들까지 합쳐 점프로 이동 가능한 최대 위치.
        int accessibleDistance = 0;
        for (int[] board : boards) {
            // 만약 현재 판자의 최대 위치가 current보다 작거나 같다면 고려하지 않는다.
            // 뒤에 나왔다는 것은 판자의 시작 위치가 이전보다 더 큰 값이라 점프 거리가 줄어들기 때문.
            if (board[1] <= current)
                continue;

            // 판자가 연이어져있는 경우.
            if (board[0] <= current) {
                // 최대 점프 길이 계산
                maxJump += board[1] - current;
                // 걸어 이동 가능한 거리 계산
                current = board[1];
                // 점프로 이동 가능한 최대 위치 계산
                accessibleDistance = Math.max(accessibleDistance, current + maxJump);
            } else if (board[0] <= accessibleDistance) {
                // 만약 i번째 판자가 걸어서 이동할 수는 없지만 점프로 이동가능한 경우.
                // 현재 판자로 이동 가능한 최대 위치.
                current = board[1];
                // 그 때의 점프 길이
                maxJump = board[1] - board[0];
                // 점프로 이동 가능한 최대 위치
                accessibleDistance = Math.max(accessibleDistance, current + maxJump);
            }
        }
        // 걸어서 이동 가능한 최대 위치 출력.
        System.out.println(current);
    }
}