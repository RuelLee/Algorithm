/*
 Author : Ruel
 Problem : Baekjoon 27085번 최대 점수
 Problem address : https://www.acmicpc.net/problem/27085
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27085_최대점수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 일렬로 이어진 n개의 방으로 이루어진 던전이 주어진다.
        // 주인공은 s번째 방에서 시작하여, 좌우의 하나의 방으로 이동하거나 던젼에서 탈출할 수 있다.
        // s방을 제외한 모든 방에는 몬스터가 살고 있으며, 해당 몬스터를 잡을 때마다 할당된 점수만큼을 얻는다.
        // 현재 점수가 0 미만이 될 경우 게임이 오버된다.
        // 최대한 많은 점수를 얻고자할 때, 그 점수는?
        //
        // 그리디, 두 포인터 문제
        // 한 방향으로 이동을 한다면, 그 방향에서 얻는 점수가 양수가 될 때까진 전진해야한다.
        // 얻는 점수가 양수가 되는 때의 방 번호, 중간에 갖게되는 최저 음수 점수, 총 얻을 수 있는 점수로 구분한다.
        // 현재 점수가 음수가 되면 게임 오버가 되기 때문에, 이를 고려하여 이동할 수 있는지를 판별하여 점수를 얻는 과정을 반복한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 방, 시작 위치 s
        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken()) - 1;
        
        st = new StringTokenizer(br.readLine());
        // 각 방에서의 몬스터를 잡을 때 얻는 점수
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = Integer.parseInt(st.nextToken());
        
        // 현재 이동한 왼쪽 끝 방과 오른쪽 끝 방의 번호
        long[] idxes = new long[]{s, s};
        // 다음 양수의 점수를 얻기 위해 이동해야하는 번호, 도중에 얻게 되는 최저 음수 점수, 얻게되는 총 점수 
        long[] leftTarget = new long[]{s, 0, 1};
        long[] rightTarget = new long[]{s, 0, 1};
        // 현재 점수
        long sum = 0;
        // 양 쪽 방향 아무 곳이나 이동할 수 있을 때까지
        while ((leftTarget[0] >= 0 && sum + leftTarget[1] >= 0 && leftTarget[2] > 0)
                || (rightTarget[0] < a.length && sum + rightTarget[1] >= 0) && rightTarget[2] > 0) {
            // left 방향 타겟이 현재 위치라면 다음 타겟을 찾는다.
            if (leftTarget[0] == idxes[0]) {
                leftTarget[1] = leftTarget[2] = 0;
                while (leftTarget[0] - 1 >= 0 && leftTarget[2] <= 0)
                    leftTarget[1] = Math.min(leftTarget[1], leftTarget[2] += a[(int) (--leftTarget[0])]);
            }
            // 최저 음수 점수가 현재 점수보다 크기가 작아 이동할 수 있고, 총 얻게 되는 점수가 양수인 경우
            // 해당 위치까지 이동
            if (leftTarget[0] >= 0 && sum + leftTarget[1] >= 0 && leftTarget[2] > 0) {
                idxes[0] = leftTarget[0];
                sum += leftTarget[2];
            }
            
            // right 방향 타겟이 현재 위치라면 다음 타겟을 찾는다.
            if (rightTarget[0] == idxes[1]) {
                rightTarget[1] = rightTarget[2] = 0;
                while (rightTarget[0] + 1 < a.length && rightTarget[2] <= 0)
                    rightTarget[1] = Math.min(rightTarget[1], rightTarget[2] += a[(int) (++rightTarget[0])]);
            }
            if (rightTarget[0] < a.length && sum + rightTarget[1] >= 0 && rightTarget[2] > 0) {
                idxes[1] = rightTarget[0];
                sum += rightTarget[2];
            }
        }
        // 총 점수 출력
        System.out.println(sum);
    }
}