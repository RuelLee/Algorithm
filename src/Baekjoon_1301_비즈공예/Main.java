/*
 Author : Ruel
 Problem : Baekjoon 1301번 비즈 공예
 Problem address : https://www.acmicpc.net/problem/1301
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1301_비즈공예;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class State {
    int previous;
    int idx;

    public State(int previous, int idx) {
        this.previous = previous;
        this.idx = idx;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n(3 <= n <= 5) 종류의 구슬을 갖고 있다.
        // 각 구슬들은 최대 10개이며, 구슬 개수의 총합은 35를 넘지 않는다.
        // 이 구슬들을 서로 꿰어 목걸이를 만들고자한다.
        // 임의의 연속된 3개의 구슬의 색을 모두 다르게 하고자 한다.
        // 목걸이는 원형이 아니라 직선일 때, 목걸이를 만들 수 있는 경우의 수는?
        //
        // DP문제
        // n이 정해진 수가 아니라 몇차원 DP를 만들어야할지 고민이 됐다.
        // dp는 이전 두 구슬의 상태와 남은 구슬의 개수를 나타내야한다.
        // 다른 풀이를 살펴보니 7차원 DP도 있었으나 코드도 복잡해질 우려가 있었다.
        // 따라서 상태와 남은 구슬을 각각 하나의 수로 처리하기로 했다.
        // 먼저 상태는 이전 두 개의 구슬이 무슨 구슬인지 나타내면 되므로
        // 색을 1 ~ 5까지의 수로 나타내고, 직전 구슬이 a, 하나 떨어진 구슬이 b라 할 때
        // a * 6 + b로 나타내었다.
        // 구슬의 개수는 각 구슬이 최대 10개로 주어지므로
        // 각 구슬의 개수를 a, b, c, d, e라고 할 때
        // a는 a * 10^4, b는 b * 10^3, ... , e는 e * 10^0 으로 나타내었다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 갖고 있는 구슬의 종류
        int n = Integer.parseInt(br.readLine());
        // 각 구슬의 수
        int[] beads = new int[5];
        for (int i = 0; i < n; i++)
            beads[i] = Integer.parseInt(br.readLine());
        
        // 생성되는 DP의 크기를 작게하기 위해 정렬하였다.
        Arrays.sort(beads);
        int size = 0;
        for (int i = 0; i < beads.length; i++) {
            size *= 11;
            size += beads[i];
        }
        // dp[이전 두 구슬의 종류][현재 남은 구슬]
        // dp[state][idx]
        long[][] dp = new long[5 * 6 + 6][size + 1];
        dp[0][beadsToIdx(beads)] = 1;
        
        // 너비우선탐색
        Queue<State> queue = new LinkedList<>();
        // 처음 목걸이를 만들 때의 상태
        queue.offer(new State(0, beadsToIdx(beads)));
        while (!queue.isEmpty()) {
            State current = queue.poll();

            // idx를 배열로 변환한다.
            int[] currentBeads = idxToBeads(current.idx);
            for (int i = 0; i < currentBeads.length; i++) {
                // (i + 1)번째 구슬이 남아있고
                // 이전 두 구슬에 (i + 1) 구슬이 사용되지 않았다면
                if (currentBeads[i] > 0 && (current.previous % 6 != (i + 1) && (current.previous / 6 != (i + 1)))) {
                    // 6으로 나눔으로써, 두번째 구슬의 정보는 사라지고
                    // 첫번째 구슬의 정보가 두번째 구슬의 정보 위치로 간다.
                    // 그리고 (i + 1)번째 구슬을 첫번째 구슬의 정보로 남긴다.
                    int newPrevious = current.previous / 6 + (i + 1) * 6;
                    // (i + 1)번째 구슬을 하나 사용
                    currentBeads[i]--;
                    // 그 상태를 idx로 변환
                    int newIdx = beadsToIdx(currentBeads);
                    currentBeads[i]++;

                    // dp에 기록
                    dp[newPrevious][newIdx] += dp[current.previous][current.idx];
                    // 동일한 상태가 여러번 큐에 담겨 중복으로 계산되면 안된다.
                    // 따라서 아직 어떠한 구슬이라도 남아있고, 처음 계산된 경우에만 큐에 추가한다.
                    if (dp[newPrevious][newIdx] == dp[current.previous][current.idx] && newIdx > 0)
                        queue.offer(new State(newPrevious, newIdx));
                }
            }
        }

        long sum = 0;
        // 이전 두 구슬의 상태가 어떠하든, 모든 구슬을 사용한 경우의 수를 합친다.
        for (int i = 0; i < dp.length; i++)
            sum += dp[i][0];
        // 답안 출력
        System.out.println(sum);
    }
    
    // 배열을 idx로
    static int beadsToIdx(int[] beads) {
        int idx = 0;
        for (int i = 0; i < beads.length; i++) {
            idx *= 11;
            idx += beads[i];
        }
        return idx;
    }
    
    // idx를 배열로
    static int[] idxToBeads(int idx) {
        int[] beads = new int[5];
        for (int i = beads.length - 1; i >= 0; i--) {
            beads[i] = idx % 11;
            idx /= 11;
        }
        return beads;
    }
}