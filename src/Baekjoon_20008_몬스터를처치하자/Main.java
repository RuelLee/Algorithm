/*
 Author : Ruel
 Problem : Baekjoon 20008번 몬스터를 처치하자!
 Problem address : https://www.acmicpc.net/problem/20008
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20008_몬스터를처치하자;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 스킬을 사용하여 hp 체력을 가진 몬스터를 쓰러뜨리고자 한다.
        // 스킬은 각각 데미지와 쿨타임을 갖고 있다.
        // 최소 시간에 몬스터를 쓰리고자할 때 그 시간은?
        //
        // 브루트포스, 백트래킹 문제
        // 스킬 사용이 가능할 때마다 스킬을 써, 최대한 적은 시간에 많은 데미지를 주는 방법을
        // 브루트포스를 통해 모두 계산한다.
        // 가능한 스킬이 여러개일 경우, 각각에 대해 스킬을 사용한 경우 모두 계산한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 스킬의 개수 n, 몬스터의 체력 hp
        int n = Integer.parseInt(st.nextToken());
        int hp = Integer.parseInt(st.nextToken());
        
        // 스킬의 쿨타임과 데미지
        int[][] skills = new int[n][];
        for (int i = 0; i < skills.length; i++)
            skills[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 백트래킹과 브루트포스를 통해 구한 최소 시간 출력
        System.out.println(findAnswer(skills, hp, 0, new int[n]));
    }
    
    // 백트래킹
    static int findAnswer(int[][] skills, int remainHp, int time, int[] availableTimes) {
        // 몬스터의 체력이 0 이하가 된다면 해당 시간을 반환한다.
        if (remainHp <= 0)
            return time;

        // 현재 가능한 분기를 모두 계산한다.
        int minTime = Integer.MAX_VALUE;
        // 스킬들 중 가장 빠르게 시전할 수 있는 스킬의 시간
        int minSkillTime = Arrays.stream(availableTimes).min().getAsInt();
        // 만약 현재 시전할 수 있는 스킬이 없다면
        // 가장 빠르게 시전할 수 있는 스킬의 시간으로 건너뛴다.
        if (minSkillTime > time)
            minTime = Math.min(minTime, findAnswer(skills, remainHp, minSkillTime, availableTimes));
        else {
            // 시전할 수 있는 스킬이 있는 경우
            // 가능한 스킬들을 모두 사용해본다.
            for (int i = 0; i < skills.length; i++) {
                if (availableTimes[i] <= time) {
                    // i번 스킬이 사용가능하다면
                    // 현재 i번 스킬의 사용 가능 시간을 저장해두고
                    int temp = availableTimes[i];
                    // 다음 사용 가능한 시간을 기록
                    availableTimes[i] = time + skills[i][0];
                    // 그리고 1초 스킬이 적중했을 때로 시간을 넘긴다.
                    // 그리고 해당 분기로 진행했을 때, 몬스터를 쓰러뜨리는 최소 시간을 반환받아
                    // minTime에 최소 시간을 계산한다.
                    minTime = Math.min(minTime,
                            findAnswer(skills, remainHp - skills[i][1], time + 1, availableTimes));
                    // i번 스킬을 사용했을 때의 분기를 모두 계산했으므로
                    // i번 스킬의 사용 가능 시간을 다시 되돌리고
                    // 다음 스킬들에 대한 분기들을 계산한다.
                    availableTimes[i] = temp;
                }
            }
        }
        // 가능한 최소 시간 반환.
        return minTime;
    }
}