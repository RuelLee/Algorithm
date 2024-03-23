/*
 Author : Ruel
 Problem : Baekjoon 9241번 바이러스 복제
 Problem address : https://www.acmicpc.net/problem/9241
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9241_바이러스복제;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // 바이러스에 감염되기 전과 후의 DNA 배열이 주어진다.
        // 바이러스는 연속된 일부분을 다른 DNA로 교체한다.
        // 바이러스에 해당하는 최소 DNA의 길이를 구하라
        //
        // 그리디 문제
        // 앞과 뒤로부터 두 DNA 배열에서 일치하는 부분의 길이를 센다.
        // 그 후, 총 일치하는 부분의 길이의 합이 두 DNA들 중 짧은 것보다 크다면(모두 일치한다면 중복되어 클 수 있다.)
        // 1. 감염 후가 더 긴 경우, 해당하는 부분만큼만 추가된 것이고
        // 2. 감염 전이 더 길다면 해당 부분만큼만 삭제된 것이다.
        // 1의 경우 해당 부분의 길이, 2의 경우 0이 답이다.
        // 일치하는 부분의 길이의 합이 두 DNA들 중 짧은 것보다 작다면
        // 감염 전 일치하지 않는 부분이 감염 후 일치 하지 않는 부분으로 교체된 것이다.
        // 이 때 필요한 길이는 감염 후 길이에서 일치하지 않는 부분의 길이.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 감염 전 before, 감염 후 after
        String before = br.readLine();
        String after = br.readLine();

        // 앞 뒤로부터 일치하는 부분의 길이를 센다.
        int[] sameCounts = new int[2];
        while (sameCounts[0] < Math.min(before.length(), after.length())) {
            if (before.charAt(sameCounts[0]) != after.charAt(sameCounts[0]))
                break;
            sameCounts[0]++;
        }
        while (sameCounts[1] < Math.min(before.length(), after.length())) {
            if (before.charAt(before.length() - sameCounts[1] - 1) != after.charAt(after.length() - sameCounts[1] - 1))
                break;
            sameCounts[1]++;
        }
        
        // 일치하는 부분의 길이가 두 DNA들의 작은 길이보다 같거나 크다면
        // 1. after가 before보다 크다면 after - before 만큼이 추가된 것 -> after - before의 길이
        // 2. before가 after보다 크다면 before에서 해당 길이만큼만 삭제된 것 -> 0
        if (sameCounts[0] + sameCounts[1] >= Math.min(before.length(), after.length()))
            System.out.println(after.length() >= before.length() ? after.length() - before.length() : 0);
        else        // 그 외의 경우는 before의 일부분이 after의 일부분으로 교체 된 것. -> after의 길이 - 일치하는 부분의 길이 합
            System.out.println(after.length() - (sameCounts[0] + sameCounts[1]));
    }
}