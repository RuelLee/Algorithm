/*
 Author : Ruel
 Problem : Baekjoon 20055번 컨베이어 벨트 위의 로봇
 Problem address : https://www.acmicpc.net/problem/20055
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20055_컨베이어벨트위의로봇;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1 ~ 2n번까지 원형 모양의 컨베이어벨트가 있다.
        // 1번 위치에서는 로봇을 올리고, n번 위치에서는 로봇을 내린다.
        // 각 컨베이어 벨트 칸에는 내구도가 있다.
        // 컨베이어 벨트는 매 시간마다
        // 1. 벨트가 각 칸 위에 있는 로봇과 함께 한 칸 회전한다.
        // 2. 먼저 벨트에 올라간 로봇들 부터, 앞으로 한 칸 이동할 수 있다면 이동한다.(다음 칸에 로봇이 없고, 내구도가 1 이상일 경우)
        // 3. 올리는 칸에 내구도가 0보다 크다면 로봇을 올린다.
        // 4. 내구도가 0인 칸이 k개 이상이라면 과정을 종료한다.
        // 종료될 때의 시간을 구하라
        //
        // 구현, 시뮬레이션 문제
        // 문제에 적힌 내용에 따라 착실히 구현하면 된다.
        // 매 시간 회전을 하는 것은 올리는 칸과 내리는 칸이 변경되는 것일 뿐이다.
        // 따라서 시간마다 올리는 칸과 내리는 칸을 따로 계산하고 회전을 무시한다.
        // 로봇은 큐로 관리해주며, 컨베이어 벨트에 올라온 순서대로 살펴본다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 2*n개의 컨베이어 칸의 수
        int n = Integer.parseInt(st.nextToken());
        // 종료되는 시점은 내구도가 0인 칸이 k개 일 때
        int k = Integer.parseInt(st.nextToken());
        
        // 각 컨베이어 칸의 내구도
        int[] hps = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 로봇을 관리할 큐
        Queue<Integer> robots = new LinkedList<>();
        // 현재 위치에 로봇이 있는지 표시할 boolean 배열
        boolean[] full = new boolean[2 * n];
        
        // 내구도가 0인 칸의 수
        int blanks = 0;
        // 시간
        int time = 0;
        // 내구도가 0인 칸이 k개 미만일 때, 계속 반복
        while (blanks < k) {
            // 시간 증가
            time++;
            
            // 올리는 위치와 내리는 위치
            int upIdx = ((0 - time) % (2 * n) + 2 * n) % (2 * n);
            int downIdx = ((n - 1 - time) % (2 * n) + 2 * n) % (2 * n);
            // 만약 내리는 위치에 로봇이 있다면
            // full에서 false로 만들어준다.
            if (full[downIdx])
                full[downIdx] = false;

            Queue<Integer> temp = new LinkedList<>();
            // 로봇을 순차적으로 모두 살펴본다.
            while (!robots.isEmpty()) {
                int robotIdx = robots.poll();
                // 만약 현재 칸이 비어있다고 표시되어있다면(=내리는 칸이라서) 건너뛴다.
                if (!full[robotIdx])
                    continue;

                // 이동할 다음 위치
                int nextIdx = (robotIdx + 1) % (2 * n);
                // 다음 칸에 로봇이 없고 내구도가 1이상이라면
                if (!full[nextIdx] && hps[nextIdx] > 0) {
                    // 현재 위치의 로봇을 없다고 표시하고
                    full[robotIdx] = false;
                    // 다음 칸의 내구도를 감소시키며 0이 되는지 확인한다.
                    if (--hps[nextIdx] == 0)
                        blanks++;
                    // 다음 칸이 내리는 칸이 아닐 때만
                    // 다음 칸에 로봇이 있다고 표시하고 큐에 추가한다.
                    if (nextIdx != downIdx) {
                        full[nextIdx] = true;
                        temp.offer(nextIdx);
                    }
                } else      // 다음 칸으로 이동이 불가능한 경우. 그대로 다시 큐에 넣는다.
                    temp.offer(robotIdx);
            }
            // robots에 있는 모든 로봇들을 살펴보는 게 끝났다.
            // temp에 저장된 로봇들을 robots으로 옮겨 다음 시간에 살펴본다.
            robots = temp;
            
            // 올리는 칸의 내구도가 1이상이라면
            if (hps[upIdx] > 0) {
                // 로봇 유무 표시
                full[upIdx] = true;
                // 큐에 로봇 추가.
                robots.offer(upIdx);
                // 내구도가 0이 되는지 확인
                if (--hps[upIdx] == 0)
                    blanks++;
            }
        }

        // 반복문을 빠져나온 시간(=종료된 시간) 출력.
        System.out.println(time);
    }
}