/*
 Author : Ruel
 Problem : Baekjoon 22358번 스키장
 Problem address : https://www.acmicpc.net/problem/22358
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22358_스키장;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Course {
    int end;
    int time;

    public Course(int end, int time) {
        this.end = end;
        this.time = time;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 스키장에 n개 지점이 있으며, 높은 지점부터 1 ~ n까지 번호가 매겨져 있다.
        // 스키장에는 m개의 코스가 있으며, a 지점에서 b 지점 방향으로 이어지며, c 만큼의 시간이 소요된다.
        // 또한 코스마다 반대 방향으로 리프트가 있다.
        // 리프트는 최대 k번 탑승할 수 있다.
        // s 지점에 모여있는 친구들은, 스키를 모두 타고 난 후, t 지점에서 모이기로 하였다.
        // 스키 코스와 리프트만 이용하여 s 지점에서 t 지점으로 가되, 스키 타는 시간을 최대화하고자 할 때, 그 시간은?
        //
        // DP 문제
        // dp[지점][리프트이용횟수] = 시간
        // dp를 채워나간다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 지점, m개의 스키 코스, 리프트 탑승 제한 k회, 시작 지점 s, 종료 지점 t
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 코스와 리프트
        List<List<Course>> courses = new ArrayList<>();
        List<List<Course>> lifts = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            courses.add(new ArrayList<>());
            lifts.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            
            // a -> b 지점으로 가는 스키 코스
            courses.get(a).add(new Course(b, c));
            // b -> a 로 가는 리프트
            lifts.get(b).add(new Course(a, 0));
        }
        
        // dp[지점][리프트이용횟수] = 스키를 탄 시간
        long[][] dp = new long[n + 1][k + 1];
        for (long[] d : dp)
            Arrays.fill(d, Long.MIN_VALUE);
        // 시작 지점에선 0
        dp[s][0] = 0;
        for (int j = 0; j < k + 1; j++) {
            for (int i = 0; i < dp.length; i++) {
                // 값이 초기값이라면 가능하지 않은 경우
                // 건너 뜀.
                if (dp[i][j] == Long.MIN_VALUE)
                    continue;

                // 해당 지점에서 가능한 코스들을 살펴본다.
                for (Course c : courses.get(i)) {
                    if (dp[c.end][j] < dp[i][j] + c.time)
                        dp[c.end][j] = dp[i][j] + c.time;
                }
                
                // 리프트 탑승 횟수가 남았다면
                if (j < k) {
                    // 탑승 가능한 리프트들을 살펴본다
                    for (Course c : lifts.get(i)) {
                        if (dp[c.end][j + 1] < dp[i][j])
                            dp[c.end][j + 1] = dp[i][j];
                    }
                }
            }
        }
        
        // t 지점에서 리프트 탑승 횟수에 따른 가능한 모든 스키를 탄 시간을 비교하여
        // 최대 시간을 구한다
        long max = Long.MIN_VALUE;
        for (int i = 0; i < dp[t].length; i++)
            max = Math.max(max, dp[t][i]);

        // 초기값 그대로라면 불가능한 경우이므로 -1 출력
        // 그 외의 경우엔 구한 최대값 출력
        System.out.println(max == Long.MIN_VALUE ? -1 : max);
    }
}