/*
 Author : Ruel
 Problem : Baekjoon 17089번 세 친구
 Problem address : https://www.acmicpc.net/problem/17089
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17089_세친구;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> friends;

    public static void main(String[] args) throws IOException {
        // n명의 사람과 m개의 교우 관계가 주어진다.
        // 여기서 서로 친구인 세 사람을 골라,
        // 친구의 합이 최소가 되게끔하고자 한다.
        // 친구의 합을 구할 때, 세 사람은 서로를 빼고 계산한다.
        // 예를 들어 a의 친구를 계산할 땐, b, c를 제외한다.
        //
        // 브루트 포스 문제
        // 서로 친구인 세 사람을 구하고
        // 그 때의 친구합을 브루트포스, 백트래킹을 활용하여 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 교우 관계
        friends = new ArrayList<>();
        for (int i = 0; i < n; i++)
            friends.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            friends.get(a).add(b);
            friends.get(b).add(a);
        }

        // 친구 합의 최소값
        int answer = Integer.MAX_VALUE;
        // 첫번째 사람으로 i를 고르는 경우의 최소 친구 합
        for (int i = 0; i < n - 2; i++)
            answer = Math.min(answer, findAnswer(1, new int[]{i, 0, 0}));
        
        // answer가 초기값이라면 세 사람을 고르는 것이 불가능한 경우
        // -1 출력
        // 그 외의 경우 answer 출력
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
    
    // 세 사람을 정해, 최소 친구 합을 구한다.
    static int findAnswer(int idx, int[] pick) {
        // 3명을 모두 고른 경우,
        // 친구 합을 구해 반환.
        if (idx == 3) {
            int sum = 0;
            for (int i = 0; i < pick.length; i++)
                sum += friends.get(pick[i]).size() - 2;
            return sum;
        }
        
        // 아직 세 명을 모두 고르지 않은 경우
        int min = Integer.MAX_VALUE;
        // 이전에 고른 사람보다 +1번째 사람부터 살펴보며
        for (int i = pick[idx - 1] + 1; i < friends.size(); i++) {
            boolean possible = true;
            // i가 이미 고른 사람들과 친구인지 살펴본다.
            for (int j = 0; j < idx; j++) {
                if (!friends.get(i).contains(pick[j])) {
                    possible = false;
                    break;
                }
            }

            // 이미 고른 사람들과 친구라면 idx번째에 i를 고르고
            // 그 때의 최소 친구합을 반환 받아
            // min 값에 최소값으로 갱신되는지 확인한다.
            if (possible) {
                pick[idx] = i;
                min = Math.min(min, findAnswer(idx + 1, pick));
            }
        }
        // 그렇게 구한 최소 친구 합을 반환한다.
        return min;
    }
}