/*
 Author : Ruel
 Problem : Baekjoon 31264번 사격
 Problem address : https://www.acmicpc.net/problem/31264
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31264_사격;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 표적과 각 점수가 주어진다.
        // 표적을 맞추기 위해서는 해당 점수 이상의 실력이 필요하며, 해당 표적을 맞춘 뒤, 실력은 점수만큼 상승한다고 한다.
        // 사격은 최대 m번 할 수 있으며, 획득한 점수가 a점 이상일 경우, 진급할 수 있다고 한다.
        // 처음 실력이 얼마여야 진급이 가능한가?
        //
        // 이분 탐색
        // 처음 시작 실력에 따라 얻을 수 있는 최대 점수가 달라진다.
        // 시작 점수에 따른 최대 점수를 구하는 메소드를 만들고
        // 이분 탐색을 통해, 진급할 수 있는 최소 실력의 값을 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 표적, 사격 횟수 m, 진급 점수 a
        int n = Integer.parseInt(st.nextToken());
        long m = Long.parseLong(st.nextToken());
        long a = Long.parseLong(st.nextToken());
        
        // 표적의 점수
        int[] scores = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < scores.length; i++)
            scores[i] = Integer.parseInt(st.nextToken());
        Arrays.sort(scores);
        
        // 이분 탐색
        long start = scores[0];
        long end = scores[n - 1];
        while (start < end) {
            long mid = (start + end) / 2;
            if (maxScore(mid, m, scores) >= a)
                end = mid;
            else
                start = mid + 1;
        }
        System.out.println(start);
    }
    
    // 최초 실력이 skill이고, 사격 기회가 shoot번, 표적들이 scores일 때
    // 얻을 수 있는 최대 점수를 반환
    static long maxScore(long skill, long shoot, int[] scores) {
        // 점수 합
        long sum = 0;
        // 노릴 수 있는 최대 점수 표적의 idx
        int idx = 0;
        // 사격 기회가 남은 동안
        while (shoot > 0) {
            // 맞출 수 있는 최대 점수 표적을 찾는다.
            while (idx + 1 < scores.length && scores[idx + 1] <= skill)
                idx++;

            // 마지막 표적이라면 남은 기회를 해당 표적에 쏜다.
            if (idx == scores.length - 1) {
                sum += shoot * scores[idx];
                shoot = 0;
            } else {
                // 그 외의 경우
                // 남은 사격 기회와 다음 표적을 쏘기 위해 해당 표적을 쏴야하는 횟수를 비교한다
                long cycle = Math.min(shoot, (scores[idx + 1] - skill + scores[idx] - 1) / scores[idx]);
                // 해당 횟수만큼 현재 표적 사격
                sum += cycle * scores[idx];
                skill += cycle * scores[idx];
                shoot -= cycle;
            }
        }
        // 얻은 최대 점수 반환
        return sum;
    }
}