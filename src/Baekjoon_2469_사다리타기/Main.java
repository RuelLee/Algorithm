/*
 Author : Ruel
 Problem : Baekjoon 2469번 사다리 타기
 Problem address : https://www.acmicpc.net/problem/2469
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2469_사다리타기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // k명이 가로줄이 n개인 사다리 게임에 참여한다.
        // 그 중 하나의 가로줄은 감춰져있다.
        // 도착 순서를 안다고 할 때
        // 가로줄을 도착 순서에 맞도록 찾아내라.
        // 막대가 있는 경우는 -, 없는 경우는 *으로 표현된다
        //
        // 구현 문제
        // 위에서부터 감춰진 가로줄까지의 결과와
        // 아래서부터 감춰진 가로줄까지의 결과를 비교하여
        // 해당 위치에 막대를 두어 양쪽의 결과를 뒤바꿔야하는지를 판별하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // k명의 참가자와 n개의 가로줄
        int k = Integer.parseInt(br.readLine());
        int n = Integer.parseInt(br.readLine());
        
        // 시작 상태
        int[] start = new int[k];
        for (int i = 1; i < start.length; i++)
            start[i] = i;

        // 도착 상태
        char[] order = br.readLine().toCharArray();
        int[] end = new int[k];
        for (int i = 0; i < end.length; i++)
            end[i] = order[i] - 'A';
        
        // 사다리
        char[][] ladder = new char[n][];
        for (int i = 0; i < n; i++)
            ladder[i] = br.readLine().toCharArray();
        
        // 위에서부터 감춰질 줄까지 사다리타기의 결과
        for (int i = 0; i < n && ladder[i][0] != '?'; i++) {
            for (int j = 0; j < ladder[i].length; ) {
                if (ladder[i][j] == '*')
                    j++;
                else {
                    int temp = start[j];
                    start[j] = start[j + 1];
                    start[j + 1] = temp;
                    j += 2;
                }
            }
        }

        // 최종 결과값으로부터 감춰진 가로줄까지 역산
        for (int i = ladder.length - 1; i >= 0 && ladder[i][0] != '?'; i--) {
            for (int j = 0; j < ladder[i].length; ) {
                if (ladder[i][j] == '*')
                    j++;
                else {
                    int temp = end[j];
                    end[j] = end[j + 1];
                    end[j + 1] = temp;
                    j += 2;
                }
            }
        }

        // 감춰진 가로줄에서 만나는 두 값을 바탕으로
        // 감춰진 가로줄을 계산.
        boolean possible = true;
        StringBuilder sb = new StringBuilder();
        // 양쪽의 결과를 바꿀 경우
        // 다음 세로줄에서는 반드시 막대가 나와서는 안된다.
        // 이를 위해 막대가 한번 나올 경우, 다음엔 비어있도록 체크한다.
        boolean[] checked = new boolean[k];
        for (int i = 0; i < start.length - 1; i++) {
            // 체크되어있는 경우, 비워야한다.
            if (checked[i])
                sb.append('*');
            // 양쪽의 결과가 같다면 비우고
            else if (start[i] == end[i])
                sb.append('*');
            // 양쪽의 결과가 서로 뒤바뀌어있다면
            // 막대를 놓아 값을 서로 바꾼다.
            else if (start[i] == end[i + 1] && start[i + 1] == end[i]) {
                // 막대를 놓았기 때문에, 다음엔 반드시 비어있어야한다.
                checked[i + 1] = true;
                sb.append('-');
            } else {
                // 위 경우에 모두 해당하지 않을 경우
                // 불가능한 경우.
                possible = false;
                sb.setLength(0);
                break;
            }
        }

        // 불가능한 경우 k-1개의 x를 채운다.
        if (!possible) {
            for (int i = 0; i < k - 1; i++)
                sb.append('x');
        }
        // 답 출력
        System.out.println(sb);
    }
}