/*
 Author : Ruel
 Problem : Baekjoon 25200번 곰곰이와 자판기
 Problem address : https://www.acmicpc.net/problem/25200
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25200_곰곰이와자판기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 음료수 자판기에는 랜덤 버튼이 있는데 이 버튼을 누르면
        // 이세계에서 출발한 음료수가 m번 차원 이동 후, 상품 출구로 떨어진다.
        // i번째 차원 이동 중에, 음료수 ui는 vi로 바뀐다.
        // 1 ~ n 까지의 모든 음료수가 m번 차원 이동을 거치면 어떤 음료수가 되는지 출력하라
        //
        // 애드혹 문제
        // 먼저 정순으로 차원이동을 시킨다면
        // 차원 이동마다 변경되는 음료수들을 모두 변경되는 음료수로 바꿔줘야한다.
        // 이전에 1번 음료가 4번 음료로 바뀌었고, 이번에 4번 음료가 5번 음료로 바뀐다면
        // 1, 4번 음료에 대해 모두 5번 음료로 바꿔줘야한다.
        // 하지만 역순으로 생각한다면
        // 그 때 그 때 차원 이동으로 바뀌는 음료만 생각하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 음료 종류, m번의 차원 이동
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // i번째 차원 이동 시, 바뀌는 음료
        int[][] moves = new int[m][2];
        for (int i = 0; i < moves.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < moves[i].length; j++)
                moves[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 답
        // 처음에는 자기 자신의 음료를 가르키고 있다.
        int[] answer = new int[n + 1];
        for (int i = 1; i < answer.length; i++)
            answer[i] = i;
        
        // 역순으로 살펴본다.
        // moves[i][0]번 음료가 moves[i][1]번 음료로 바뀐다.
        // 현재 moves[i][0]번 위치에 있는 음료를 moves[i][1]번 위치에 있는 음료로 바꾼다.
        for (int i = moves.length - 1; i >= 0; i--)
            answer[moves[i][0]] = answer[moves[i][1]];
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        sb.append(answer[1]);
        for (int i = 2; i < answer.length; i++)
            sb.append(" ").append(answer[i]);
        // 답 출력
        System.out.println(sb);
    }
}