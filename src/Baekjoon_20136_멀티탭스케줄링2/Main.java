/*
 Author : Ruel
 Problem : Baekjoon 20136번 멀티탭 스케줄링 2
 Problem address : https://www.acmicpc.net/problem/20136
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20136_멀티탭스케줄링2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Active {
    int nextOrder;
    int num;

    public Active(int nextOrder, int num) {
        this.nextOrder = nextOrder;
        this.num = num;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n구의 멀티탭이 주어진다.
        // 전기 용품의 사용순서가 최대 k번 주어질 때
        // 플러그를 뽑는 횟수를 최소화하여 모든 전기용품을 사용하고자 한다.
        // 플러그를 뽑는 최소 횟수는?
        //
        // 그리디, 우선순위큐 문제
        // 전기 용품을 사용할 순서를 모두 알고 있으므로
        // 다음 이용 순서가 가장 늦은 전기용품을 우선적으로 제거하는 것이 유리하다.
        // 따라서 전기 용품들을 사용 순서에 따라 큐에 담고
        // 멀티탭에서는 최대 힙 우선순위큐를 통해, 다음 사용 순서가 가장 낮은 전기용품을 우선적으로 제거하도록 한다./
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n구의 멀티탭과 전기용품의 사용 횟수 k번
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 전기용품 사용 순서
        int[] order = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Queue<Integer>[] appliances = new Queue[k + 1];
        // 각 전기용품 별 사용 순서를 큐로 정리
        for (int i = 0; i < order.length; i++) {
            if (appliances[order[i]] == null)
                appliances[order[i]] = new LinkedList<>();
            appliances[order[i]].offer(i);
        }
        
        // 최대 힙 우선순위큐를 사용하되,
        // 더 이상 사용하지 않는 전기용품을 최우선적으로 제거
        // 그 외의 경우에는 다음 사용 순서가 늦은 순으로 제거
        PriorityQueue<Active> multitap = new PriorityQueue<>((o1, o2) -> {
            if (o1.nextOrder == -1)
                return -1;
            else if (o2.nextOrder == -1)
                return 1;
            else
                return Integer.compare(o2.nextOrder, o1.nextOrder);
        });

        // 플러그를 뽑는 횟수
        int answer = 0;
        // 현재 멀티탭에 꽂혀있는 전기용품의 수
        int plugCount = 0;
        // 꽂혀 있는 전기 용품
        boolean[] plug = new boolean[k + 1];
        for (int i = 0; i < order.length; i++) {
            // order[i]번 전기 용품을 사용한다.
            appliances[order[i]].poll();
            // 만약 이미 꽂혀있다면
            // 새로운 정보만 추가.
            // 어차피 기존 정보는 현재 추가하는 순서보다
            // 낮은 순서를 갖고 있을 것이므로, 사용되지 않고 우선순위큐에 계속 남을 것이다.
            if (plug[order[i]])
                multitap.offer(new Active(appliances[order[i]].isEmpty() ? -1 : appliances[order[i]].peek(), order[i]));
            else {      // 꽂혀있지 않다면
                // 멀티탭 상황을 보고, 모두 사용중이라면 그 중 하나를 제거해야한다.
                while (plugCount >= n) {
                    // 이미 제거된 전기 용품이라면 우선순위큐에서 제거만 한다.
                    if (!plug[multitap.peek().num])
                        multitap.poll();
                    else {      // 아직 제거되지 않은 전기 용품이라면
                        // boolean 배열에서 제거 표시
                        plug[multitap.poll().num] = false;
                        // 플러그 사용 개수 1 감소
                        plugCount--;
                        // 플러그 제거 횟수 1 증가
                        answer++;
                    }
                }
                // 그 후, 멀티 탭에 order[i]번 전기 용품을 꽂는다.
                multitap.offer(new Active(appliances[order[i]].isEmpty() ? -1 : appliances[order[i]].peek(), order[i]));
                // order[i]번 전기 용품 사용 표시
                plug[order[i]] = true;
                // 플러그 사용 개수 1 증가.
                plugCount++;
            }
        }
        // 구한 최소 플러그 제거 횟수 출력
        System.out.println(answer);
    }
}