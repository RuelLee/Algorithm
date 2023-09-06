/*
 Author : Ruel
 Problem : Baekjoon 27313번 효율적인 애니메이션 감상
 Problem address : https://www.acmicpc.net/problem/27313
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27313_효율적인애니메이션감상;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 애니메이션과 시간 m이 주어진다.
        // 동시에 k개의 애니메이션을 묶어서 볼 수 있으며,
        // 한 묶음의 애니메이션을 보는 도중에는 새로운 애니메이션 보기를 시작할 수 없고
        // 묶음의 애니메이션이 끝나는 시간은 가장 긴 애니메이션이 끝나는 시간이다.
        // 감상할 수 있는 최대 애니메이션의 수는?
        //
        // 그리디 문제
        // 한번에 k개의 애니메이션을 볼 수 있으므로 감상 시간에 따른 오름차순으로 정렬한 뒤
        // 시간이 짧은 애니메이션을 우선적으로 보는 것이 유리하다.
        // 한 번에 k개의 애니메이션을 동시에 볼 수 있다하였다.
        // 대부분의 경우에는 최대한 볼 수 있는 k개의 애니메이션을 동시에 감상하는 것이 유리하지만
        // 짜투리 시간이 남아, 이 시간 동안 애니메이션을 볼 수 있다면 이를 활용하는 것도 생각해야한다.
        // n = 5, m = 6, k = 3이 주어졌는데
        // 애니메이션이 2 2 4 4 4 와 같이 주어진다면
        // 처음 3개를 묶어서봐버리면 시간 4가 소모되어, 더 이상 감상이 불가능하다
        // 하지만 (2, 2), (4, 4, 4)를 묶어서 본다면 5개를 볼 수 있다.
        // 첫 묶음을 k개 이하의 몇 개로 묶어 감상하느냐가 중요하다.
        // 어차피 이후로는 k개씩 묶어서 한번에 감상하는 것이 무조건 유리하므로.        
        // 그런데 k개 이하에 대해서는 묶어서 감상이 가능하므로, 
        // 해당 애니메이션의 감상시간 동안 자신보다 앞 번호의 애니메이션들은 모두 동시에 시청하여 같이 감상을 마치는 것이 가능하다.
        // 따라서 k번 애니메이션에 대해서는 자신의 감상 시간,
        // 이후 애니메이션에 대해서는 자기보다 k만큼 이른 애니메이션의 종료 시간 + 자신의 감상 시간이 m을 넘는지 체크해주면서 개수를 세어준다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // n개의 애니메이션, 주어진 시간 m, 동시에 감상 가능한 개수 k
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 우선순위큐를 통해 오름차순 정렬
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            priorityQueue.offer(Integer.parseInt(st.nextToken()));

        // 현재 감상 중인 k개의 애니메이션에 대해 관리한다.
        Queue<Integer> queue = new LinkedList<>();
        int count = 0;
        while (!priorityQueue.isEmpty()) {
            // 현재 애니메이션
            int current = priorityQueue.poll();
            // 감상 중인 애니메이션의 수가 k개 이하라면 0
            // 그렇지 않고 이미 k개의 애니메이션을 감상 중이라면
            // 가장 빨리 끝나는 애니메이션 + current가 현재 애니메이션의 감상 완료 시간이 된다.
            // 이 시간이 m보다 크다면 이후의 애니메이션들 또한 감상할 수 없다.
            if ((queue.size() < k ? 0 : queue.peek()) + current > m)
                break;

            // 현재 감상 중인 애니메이션의 수가 k 미만이라면
            // current 애니메이션을 동시에 감상한다.
            if (queue.size() < k)
                queue.offer(current);
            else
                queue.offer(queue.poll() + current);
            // 그렇지 않고 k개라면
            // 가장 빨리 완료되는 애니메이션 + current가
            // 현재 애니메이션에 대한 감상을 가장 빨리 마치는 시간이다.

            // 감상한 애니메이션 개수 추가.
            count++;
        }

        // 전체 감상한 애니메이션의 수 출력
        System.out.println(count);
    }
}