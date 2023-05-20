/*
 Author : Ruel
 Problem : Baekjoon 22981번 휴먼 파이프라인
 Problem address : https://www.acmicpc.net/problem/22981
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22981_휴먼파이프라인;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람과 k개의 상자가 주어진다
        // n명의 사람들은 각각의 작업 속도가 주어진다.
        // 이들을 두 팀으로 나누어 일을 하고자 한다.
        // 팀의 작업 속도는 (팀원 중 가장 느린 작업 속도 * 팀원) 으로 정해진다.
        // 모든 작업을 완료하는데 가장 적게 걸리는 시간은?
        //
        // 정렬 문제
        // 정렬을 한 후, 순차적으로 살펴보며
        // 0 ~ i 까지가 한 팀, (i + 1) ~ (n -1)까지가 한 팀으로 나뉘었을 때 두 팀의 작업 속도의 합을 구한다
        // 최대 합을 구한 뒤, k개의 상자를 옮기는데 드는 시간을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 사람과
        int n = Integer.parseInt(st.nextToken());
        // k개의 상자
        long k = Long.parseLong(st.nextToken());
        
        // 각 사람들의 작업 속도
        long[] abilities = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        Arrays.sort(abilities);
        // 두 팀의 작업 속도의 합
        long max = 0;
        // 0 ~ i까지가 한 팀, (i + 1) ~ (n -1)까지가 한 팀
        for (int i = 0; i < abilities.length - 1; i++)
            max = Math.max(max, abilities[0] * (i + 1) + abilities[i + 1] * (n - i - 1));

        // k개의 상자를 두 팀이 나를 때
        // 총 k / max 분이 소모되며, 딱 나누어 떨어진다면 그 값 그대로
        // 그렇지 않다면 +1 분
        long answer = k / max + (k % max == 0 ? 0 : 1);

        // 답 출력
        System.out.println(answer);
    }
}