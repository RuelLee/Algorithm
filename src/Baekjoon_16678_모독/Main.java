/*
 Author : Ruel
 Problem : Baekjoon 16678번 모독
 Problem address : https://www.acmicpc.net/problem/16678
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16678_모독;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 왕과 n명의 의원들이 주어진다
        // 국회의원들은 각자 명예 점수를 갖고 있으며, 그 값이 0이 될 경우 의원직을 박탈 당한다.
        // 왕은 모든 국회의원들의 의원직을 박탈하려한다.
        // 프로젝트 Defile을 아래와 같이 작동한다.
        // 1. 모든 국회의원을 모독해서 각각의 명예 점수를 1씩 감소시킨다.
        // 1로 인해 1명이라도 국회의원에서 박탈당한 사람이 발생했다면 국민들의 분노를 이용해 1로 돌아간다.
        // 1에 의해 국회의원에서 박탈당한 사람이 없다면 프로젝트를 종료한다.
        // 해커를 통해 국회의원의 명예 점수를 1 차감할 수 있다고 한다.
        // 모든 의원들의 자격을 박탈하는데 필요한 최소 해커의 수는?
        //
        // 정렬 문제
        // 의원직을 박탈하는 국회의원이 생긴다면, Defile 프로젝트를 연속적으로 진행할 수 있다.
        // 따라서 모든 국회의원들의 명예 점수를 1부터 중복이 가능한 연속적인 수열로 만들어야한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 국회의원들의 명예 점수.
        int[] congressman = new int[n];
        for (int i = 0; i < congressman.length; i++)
            congressman[i] = Integer.parseInt(br.readLine());
        // 정렬.
        Arrays.sort(congressman);
        
        // 총 필요한 해커의 수
        // 국회의원의 수 최대 10만, 명예 점수 최대 10만이므로 Integer 범위를 벗어날 수 있다.
        long hackerSum = 0;
        // 명예 점수들이 1부터 연속한 수열로 나타나는지 확인한다.
        int targetHonor = 1;
        for (int i = 0; i < congressman.length; i++) {
            // 만약 이번 국회의원의 명예 점수가 targetHonor보다 높은 점수를 갖고 있다면
            // 해당 targetHonor로 낮춰줄 만큼의 해커를 고용한다.
            // 이번 targetHonor가 채워졌으므로, targetHonor의 값을 하나 증가시킨다.
            if (congressman[i] > targetHonor)
                hackerSum += congressman[i] - targetHonor++;
            // 만약 이번 명예 점수가 targetHonor와 일치할 경우
            // 해커를 고용하지 않아도 된다.
            // 다음 targetHonor는 targetHonor + 1 값이 된다.
            else if (congressman[i] == targetHonor)
                targetHonor++;
        }
        // 최종적으로 더한 hacker의 수를 출력한다.
        System.out.println(hackerSum);
    }
}