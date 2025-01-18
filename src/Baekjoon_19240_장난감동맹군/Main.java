/*
 Author : Ruel
 Problem : Baekjoon 19240번 장난감 동맹군
 Problem address : https://www.acmicpc.net/problem/19240
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19240_장난감동맹군;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 장난감을 두 팀으로 나누고자 한다.
        // 몇몇의 장난감들은 서로 사이가 좋지 않다.
        // 서로 사이가 좋지 못한 m개의 장난감이 쌍이 주어질 때
        // 모든 장난감들을 두 팀으로 나눌 수 있는지 확인하라
        //
        // BFS 문제
        // 아직 팀이 할당되지 않은 장난감들에 대해
        // 첫 장난감을 한 팀으로 편성하고, 그와 사이 좋지 못한 장난감들은 모두 상대 진영에 포함시킨다.
        // 이런 방법을 BFS를 통해 재귀적으로 반복하고, 서로 사이 좋지 못한 장난감들이 한 팀이 되는 순간
        // 불가능한 경우가 된다.
        // 모든 장난감들을 할당할 수 있다면 가능, 그렇지 못하다면 불가능으로 판별한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트케이스
        int t = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 1 ~ n까지의 장난감
            int n = Integer.parseInt(st.nextToken());
            // m개의 서로 사이 좋지 못한 장난감 쌍
            int m = Integer.parseInt(st.nextToken());
            List<List<Integer>> opponents = new ArrayList<>();
            for (int i = 0; i < n + 1; i++)
                opponents.add(new ArrayList<>());
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                opponents.get(a).add(b);
                opponents.get(b).add(a);
            }
            
            // 팀 정보
            int[] teams = new int[n + 1];
            // 초기화
            Arrays.fill(teams, -1);
            // 모든 팀으로 이분 가능한지 여부
            boolean possible = true;
            for (int i = 1; i < teams.length; i++) {
                // 아직 팀이 할당되지 않았다면
                if (teams[i] == -1) {
                    // 1번 팀으로 할당.
                    teams[i] = 1;
                    // 그리고 자신과 사이 못한 장난감들을 BFS를 통해 탐색
                    // 두 팀으로 나눌 수 없는 경우가 생긴다면
                    // possible을 false로 표시후, 반복문 종료
                    if (!canAllocateTeam(i, teams, opponents)) {
                        possible = false;
                        break;
                    }
                }
            }
            // 결과 기록
            sb.append(possible ? "YES" : "NO").append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // BFS를 통해 연관된 장난감들을 서로 모순되지 않게 두 팀으로 배치할 수 있는지 계산한다.
    static boolean canAllocateTeam(int idx, int[] teams, List<List<Integer>> opponents) {
        // idx의 적대적 장난감들
        for (int opponent : opponents.get(idx)) {
            // 만약 idx와 opponent가 같은 팀으로 할당되었다면
            // 불가능한 경우이므로 false 반환
            if (teams[opponent] == teams[idx])
                return false;
            else if (teams[opponent] == -1) {       // 아직 할당되지 않았다면, idx의 상대팀에 할당한다.
                teams[opponent] = (teams[idx] + 1) % 2;
                // 그리고 BFS
                // 만약 돌아오는 결과가 false라면 바로 false 반환
                if (!canAllocateTeam(opponent, teams, opponents))
                    return false;
            }
        }
        // idx의 적대적 관계들을 모두 상대팀에 할당하는 것이 가능했다면
        // true 반환.
        return true;
    }
}