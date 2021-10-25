/*
 Author : Ruel
 Problem : Baekjoon 17281번 ⚾
 Problem address : https://www.acmicpc.net/problem/17281
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 야구;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[][] players;
    static Queue<Integer> queue;

    public static void main(String[] args) throws IOException {
        // 순열을 활용한 브루트포스 문제
        // 1번 선수를 제외한 선수들을 모든 가짓수에 대해 계산하고 그 때 얻을 수 있는 최대 점수를 출력한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        players = new int[9][n];

        for (int i = 0; i < n; i++) {       // 각 선수가 이닝마다 얻을 수 있는 점수를 기록한다.
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++)
                players[j][i] = Integer.parseInt(st.nextToken());
        }

        int[] order = new int[9];       // 순서를 저장할 공간
        order[3] = 0;           // 4번 타자는 1번 선수로 정해져있다.
        int maxScore = getMaxScore(order, 1, 0);        // order를 넘겨주고, 1번선수는 이미 정해졌으니, 1<<0 값을 먼저 기록해준다.
        System.out.println(maxScore);
    }

    static int getMaxScore(int[] order, int bitmask, int selected) {
        if (selected >= 9) {        // 9명의 순서가 모두 골라졌다면
            int outCount = 0;           // 아웃 기록을 센다
            int orderCount = 0;         // 타자들의 순번
            int sum = 0;        // 점수
            queue = new LinkedList<>();
            while (outCount < players[0].length * 3) {      // 아웃이 이닝 수 * 3보다 적을 때까지 게임을 계속한다.
                int result = players[order[orderCount++ % 9]][outCount / 3];        // 그 때 해당 선수의 점수는, order[orderCount % 9]번째 선수의 outCount / 3 이닝의 점수이다.
                if (result == 0) {      // 그 점수가 0이라면
                    outCount++;         // 아웃카운터를 증가시키고
                    if (outCount % 3 == 0)      // 아웃카운터가 3의 배수가 됐다면(== 이닝이 끝났다면)
                        queue.clear();      // 큐를 비워준다.
                } else if (result < 4) {        // 안타를 쳤다면
                    int runner = queue.size();      // 주자들의 명 수를 세고,
                    for (int i = 0; i < runner; i++) {
                        if (queue.peek() + result > 3) {        // 각 주자들에게 안타만큼의 점수를 더해, 득점을 했다면
                            sum++;          // 점수를 하나 올리고
                            queue.poll();       // 해당 주자를 큐에서 제거한다.
                        } else          // 아직 베이스를 밟고 있다면
                            queue.offer(queue.poll() + result);     // 해당 베이스로 이동시켜준다.
                    }
                    queue.offer(result);        // 그리고 이번 타자를 출루시킨다.
                } else {        // 홈런이라면
                    sum += queue.size() + 1;        // 주자들 + 타자만큼의 점수를 증가시키고
                    queue.clear();      // 큐를 비워준다.
                }
            }
            // 총 득점을 반환한다.
            return sum;
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 9; i++) {       // 타자들의 순서를 정한다.
            if ((bitmask & (1 << i)) == 0) {        // 아직 순서가 정해지지 않은 타자라면
                order[selected] = i;        // 이번 순서에 i번째 타자를 할당하고,
                // 다음 순서의 타자를 정한다
                // 4번 타자는 정해져있으므로, 건너뛰어야한다
                // 만약 이번에 3번 타자를 정했다면, 5번타자로 건너뛰고, 아니라면 하나의 순서만 늘려준다.
                max = Math.max(max, getMaxScore(order, bitmask | (1 << i), selected == 2 ? 4 : selected + 1));
            }
        }
        return max;
    }
}