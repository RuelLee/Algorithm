/*
 Author : Ruel
 Problem : Baekjoon 16434번 드래곤 앤 던전
 Problem address : https://www.acmicpc.net/problem/16434
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16434_드래곤앤던전;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static long atk;
    static int[][] rooms;

    public static void main(String[] args) throws IOException {
        // 플레이어겐 세 개의 능력치가 있ㅅ다.
        // 최대 체력을 나타내는 고정값 maxHP, 현재 체력을 나타내는 currentHP
        // 공격력을 나타내는 atk
        // 던전에는 n개의 방이 있으며, 마지막 방의 용을 처치하고자 한다.
        // 각 방에는 몬스터 혹은 포션이 있다.
        // 각 방에 대한 정보는
        // 1 a h 
        // 몬스터가 있는 방에 도달할 경우
        // 플레이어가 먼저 몬스터의 체력 h를 atk 만큼 감소 시킨다.
        // 몬스터가 살아있다면 몬스터 또한 플레이어의 체력을 a만큼 감소시킨다.
        // 위 과정을 용사가 몬스터가 쓰러질 때까지 반복하고 다음 방으로 나아간다.
        // 2 a h
        // 포션이 있는 방에 도달할 경우
        // 플레이어의 공격력을 a만큼 증가, 플레이어의 체력을 h만큼 회복시킨다.
        // 용사는 던전에 들어가기 전에 단련을 통해 maxHP를 증가시킬 수 있다고 한다.
        // 어느 정도까지 maxHP를 단련해야 마지막 용을 처치할 수 있을까?
        //
        // 이분 탐색 문제
        // 이분 탐색을 통해, 던전에 들어가기 전 최대 체력을 설정하고
        // 해당 체력으로 던전 클리어가 가능한지 살펴본다.
        // 이를 이분 탐색을 통해 범위를 줄여나가며 해당 값을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 주어지는 방의 개수와 플레이어의 공격력 
        int n = Integer.parseInt(st.nextToken());
        atk = Integer.parseInt(st.nextToken());
        
        // 방의 정보
        rooms = new int[n][];
        for (int i = 0; i < rooms.length; i++)
            rooms[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 이분 탐색을 통해 클리어가 가능한 최소 체력을 찾는다.
        long left = 0;
        long right = Long.MAX_VALUE;
        while (left < right) {
            long mid = (left + right) / 2;
            if (isPossible(mid))
                right = mid;
            else
                left = mid + 1;
        }
        
        // 최소 체력 출력
        System.out.println(left);
    }

    // 해당 체력으로 던전 클리어가 가능한지 계산한다.
    static boolean isPossible(long maxHp) {
        long currentAtk = atk;
        long currentHp = maxHp;
        // 각 방들을 거치며
        for (int[] room : rooms) {
            // 몬스터가 있는 방일 경우
            // 용사가 먼저 선제 공격을 하므로,
            // 몬스터의 체력 / 용사의 공격력의 올림 만큼을 때려야 몬스터를 쓰러뜨릴 수 있다.
            // 따라서 그보다 1회 적은 횟수만큼을 용사 또한 몬스터에게 맞아야한다.
            if (room[0] == 1)
                currentHp -= (Math.ceil((double) room[2] / currentAtk) - 1) * room[1];
            else {
                // 포션이 존재할 경우
                // 공격력 증가
                currentAtk += room[1];
                // 체력 회복의 경우에는 최대 체력을 넘을 수 없다.
                currentHp = Math.min(currentHp + room[2], maxHp);
            }
            
            // 만약 현재 체력이 0이하가 되었다면 클리어가 불가능한 경우
            // false 반환
            if (currentHp < 1)
                return false;
        }
        // 위 과정을 모두 통과했다면 클리어가 가능한 경우
        // true 반환
        return true;
    }
}