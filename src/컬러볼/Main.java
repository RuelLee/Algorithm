/*
 Author : Ruel
 Problem : Baekjoon 10800번 컬러볼
 Problem address : https://www.acmicpc.net/problem/10800
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 컬러볼;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Ball {
    int n;
    int color;
    int size;

    public Ball(int n, int color, int size) {
        this.n = n;
        this.color = color;
        this.size = size;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 처음엔 펜윅트리로 구간별 누적합을 하면 되지 않을까라고 생각을 했지만
        // 주어지는 색상이 최대 20만, 그리고 크기가 2000까지 주어지므로 너무 큰 배열이 되서 사용하면 안되겠다는 생각이 들었다
        // 그렇다면 크기가 작은 순서대로 정렬을 한 후,
        // sum[0]에는 전체 공들의 합, sum[n]에는 n색상에 해당하는 공의 크기의 합을 더해가면서
        // sum[0] - sum[n]을 하면 해당 공보다 작은 다른 색상의 공들 크기의 합이 나오겠다고 생각을 했다
        // 하지만 역시 같은 무게가 있다면 이를 어떻게 처리할까에 대한 고민을 했다
        // 역시 색상의 범위가 너무 커서, 차라리 공들을 임시로 저장했다가, 이전보다 크기가 커질 경우에만 임시 공간에 저장된 공들을
        // 반영해주고 계산하기로 했다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st;
        PriorityQueue<Ball> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.size, o2.size));     // 공들을 우선순위큐에 담아 크기에 대해 오름차순으로 뽑을 것이다.
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            priorityQueue.offer(new Ball(i, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
        Queue<Ball> temp = new LinkedList<>();
        int[] sum = new int[n + 1];     // sum[0]는 전체 공들의 크기의 합, sum[n]은 n번 색에 대한 공들의 크기의 합
        int[] answer = new int[n];          // 그리고 i번째 공에 대한 답
        Ball pre = new Ball(0, 0, 0);           // 이전 공에 대한 값 초기화
        while (!priorityQueue.isEmpty()) {
            Ball current = priorityQueue.poll();        // 공을 하나 꺼내
            if (pre.size < current.size) {      // 이전보다 크기가 커졌을 때만
                while (!temp.isEmpty()) {       // temp에 담겨있는 공들을 sum 배열에 반영해준다.
                    sum[0] += temp.peek().size;
                    sum[temp.peek().color] += temp.poll().size;
                }
            }
            // 답은 전체 공들의 크기의 합 - 같은 색상 공들의 크기의 합이고
            answer[current.n] = sum[0] - sum[current.color];
            // 이전 공에는 current로 바꿔주고
            pre = current;
            // 현재 공 또한 temp 공간에 담아 다음 이후에 나올 공들과 크기 비교 후 sum에 반영해주자.
            temp.offer(current);
        }

        StringBuilder sb = new StringBuilder();
        for (int i : answer)        // 순서대로 answer 배열에 답이 담겨있으므로 출력만 해주면 끝.
            sb.append(i).append("\n");
        System.out.println(sb);
    }
}