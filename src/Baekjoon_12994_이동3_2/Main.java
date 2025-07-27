/*
 Author : Ruel
 Problem : Baekjoon 12994번 이동3-2
 Problem address : https://www.acmicpc.net/problem/12994
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12994_이동3_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 좌표 평면이 주어지며, (0, 0)에서 시작한다.
        // 각 단계마다 3^단계 만큼 이동하며, 단계는 0부터 시작한다.
        // 이동은 +x, -x, +y, -y 네 방향으로 움직일 수 있다.
        // 좌표가 주어질 때, 해당 좌표로 이동할 수 있는가를 계산하라
        //
        // 수학 문제
        // 음이든 양이든 (x, y)의 이동량이 중요하므로 두 값 모두 절대값 처리한다.
        // 그 후, 각각을 3진수로 바꿔준다.
        // 각 단계마다 한 방향으로 한 번만 움직일 수 있고, 단계를 건너뛸 수 없으므로
        // 기본적으로는 x와 y에서 1이 나온다면 통과이다.
        // 하지만 음의 방향으로 이동하는 경우, 다음 단계에서 +1 * 3과 이번 단계에서의 -1 이 합쳐져 2가 나올 수도 있다.
        // 따라서 2가 나오는 경우, -1로 바꿔주고, 다음 자리의 값을 1 올려주는 방식을 택한다.
        // 물론 위 과정이 연쇄적으로도 나타날 수도 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int x = Math.abs(Integer.parseInt(st.nextToken()));
        int y = Math.abs(Integer.parseInt(st.nextToken()));

        // 데크를 통해 x와 y 둘 다 3진수 처리
        Deque<Integer> xDeque = new LinkedList<>();
        while (x > 0) {
            xDeque.offerLast(x % 3);
            x /= 3;
        }
        Deque<Integer> yDeque = new LinkedList<>();
        while (y > 0) {
            yDeque.offerLast(y % 3);
            y /= 3;
        }

        System.out.println(Arrays.toString(xDeque.toArray()));
        System.out.println(Arrays.toString(yDeque.toArray()));
        boolean possible = true;
        // x와 y에 둘 중 하나라도 아직 값이 남은 경우
        while (!xDeque.isEmpty() || !yDeque.isEmpty()) {
            int curX = 0;
            int curY = 0;
            if (!xDeque.isEmpty())
                curX = xDeque.pollFirst();
            if (!yDeque.isEmpty())
                curY = yDeque.pollFirst();

            System.out.println(curX + " " + curY);
            // 3인 된 경우, 자릿수를 올려줌
            if (curX == 3) {
                xDeque.offerFirst((xDeque.isEmpty() ? 0 : xDeque.pollFirst()) + 1);
                curX = 0;
            }
            if (curY == 3) {
                yDeque.offerFirst((yDeque.isEmpty() ? 0 : yDeque.pollFirst()) + 1);
                curY = 0;
            }
            
            // 두 값 모두 0이 아니거나 0이라면 해당 단계에서 양쪽 모두 이동했거나 하지 않은 경우
            // 조건에 위반되므로 불가능한 경우
            if ((Math.abs(curX) > 0 && Math.abs(curY) > 0) ||
                    (Math.abs(curX) + Math.abs(curY) == 0)) {
                possible = false;
                break;
            }
            
            // 둘 중 하나가 1인 경우는 그냥 건너뜀
            if (curX == 1 || curY == 1)
                continue;
            // 둘 중 하나가 2인 경우는 
            // 해당 자리에선 -1, 윗 자리에서 +1 이동한 경우
            else if (curX == 2) {
                int next = 0;
                if (!xDeque.isEmpty())
                    next = xDeque.pollFirst();
                xDeque.offerFirst(next + 1);
            } else {
                int next = 0;
                if (!yDeque.isEmpty())
                    next = yDeque.pollFirst();
                yDeque.offerFirst(next + 1);
            }
        }
        
        // 가능 여부 출력
        System.out.println(possible ? 1 : 0);
    }
}