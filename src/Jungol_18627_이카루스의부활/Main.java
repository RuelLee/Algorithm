/*
 Author : Ruel
 Problem : Jungol 18627번 이카루스의 부활
 Problem address : https://jungol.co.kr/problem/18627
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_18627_이카루스의부활;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws IOException {
        // 단어 w가 주어진다.
        // 첫 글자에서 시작하여, 좌, 우 혹은 가장 가까운 같은 문자로 이동을 할 수 있다고 한다.
        // 마지막 문자까지 이동하는데 걸리는 단계의 수는?
        //
        // BFS 문제
        // 자신과 가장 가까운 같은 문자의 위치를 미리 구해두고
        // BFS를 통해 첫 문자부터 마지막 문자까지 이동한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 주어지는 단어 w
        String w = br.readLine();
        // 알파벳 -> 수로 변환 과정을 매번 거치기 귀찮으므로, 미리 처리
        int[] nums = new int[w.length()];
        for (int i = 0; i < w.length(); i++)
            nums[i] = w.charAt(i) - 'a';

        // 마지막에 등장한 같은 문자의 위치
        int[] lastAppeared = new int[26];
        Arrays.fill(lastAppeared, -1);
        // 왼쪽 -> 오른쪽으로 살펴가며 자신보다 왼쪽에 있는 가장 가까운 같은 문자의 위치를 계산한다.
        int[] toLeft = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            toLeft[i] = lastAppeared[nums[i]] == -1 ? i : lastAppeared[nums[i]];
            lastAppeared[nums[i]] = i;
        }
        // 마찬가지로 오른쪽에서부터도 한다.
        int[] toRight = new int[nums.length];
        Arrays.fill(lastAppeared, -1);
        for (int i = nums.length - 1; i >= 0; i--) {
            toRight[i] = lastAppeared[nums[i]] == -1 ? i : lastAppeared[nums[i]];
            lastAppeared[nums[i]] = i;
        }

        // BFS
        // 현재 위치에 도달하는 최소 움직임
        int[] minMoves = new int[nums.length];
        Arrays.fill(minMoves, Integer.MAX_VALUE);
        // 시작 위치
        minMoves[0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        // 방문 체크
        boolean[] enqueued = new boolean[nums.length];
        enqueued[0] = true;
        while (!queue.isEmpty()) {
            // 현재 위치
            int cur = queue.poll();
            // 좌로 이동
            if (cur - 1 >= 0 && !enqueued[cur - 1] && minMoves[cur - 1] > minMoves[cur] + 1) {
                minMoves[cur - 1] = minMoves[cur] + 1;
                queue.offer(cur - 1);
                enqueued[cur - 1] = true;
            }

            // 우로 이동
            if (cur + 1 < nums.length && !enqueued[cur + 1] && minMoves[cur + 1] > minMoves[cur] + 1) {
                minMoves[cur + 1] = minMoves[cur] + 1;
                queue.offer(cur + 1);
                enqueued[cur + 1] = true;
            }

            // 왼쪽의 가장 가까운 같은 문자로 이동
            if (!enqueued[toLeft[cur]] && minMoves[toLeft[cur]] > minMoves[cur] + 1) {
                minMoves[toLeft[cur]] = minMoves[cur] + 1;
                queue.offer(toLeft[cur]);
                enqueued[toLeft[cur]] = true;
            }

            // 오른쪽의 가장 가까운 문자로 이동
            if (!enqueued[toRight[cur]] && minMoves[toRight[cur]] > minMoves[cur] + 1) {
                minMoves[toRight[cur]] = minMoves[cur] + 1;
                queue.offer(toRight[cur]);
                enqueued[toRight[cur]] = true;
            }
        }
        // 답 출력
        System.out.println(minMoves[nums.length - 1]);
    }
}