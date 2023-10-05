/*
 Author : Ruel
 Problem : Baekjoon 9489번 사촌
 Problem address : https://www.acmicpc.net/problem/9489
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9489_사촌;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열이 주어진다.
        // 첫 정수는 트리의 노드
        // 다음에 등장하는 연속한 수의 집합은 루트의 자식 노드들.
        // 그 다음부터 모든 연속된 수의 집합은 아직 자식이 없는 노드의 자식이 된다.
        // 집합은 수가 연속하지 않은 곳에서 구분된다.
        // 사촌은 두 노드의 부모는 다르지만 두 부모가 형제일 때를 칭한다.
        // 수열의 특정 번호 k가 주어질 때 사촌의 수를 구하라
        //
        // 트리 문제
        // 순차적으로 노드의 부모 노드들을 설정해주면서
        // 원하는 k의 부모 노드는 다르면서, 부모의 부모 노드가 같은 노의 개수를 세주면 된다.
        // 개수는 1000개 이하인 반면, 번호는 100만번까지 주어지므로, 메모리 최적화를 위해서는 좌표 압축 필요
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // n개의 노드
            int n = Integer.parseInt(st.nextToken());
            // k의 사촌 노드의 개수를 찾는다.
            int k = Integer.parseInt(st.nextToken());

            if (n == 0 && k == 0)
                break;

            st = new StringTokenizer(br.readLine());
            int root = Integer.parseInt(st.nextToken());
            // 각 노드의 부모 노드
            int[] parents = new int[n + 1];
            int num = 0;
            parents[num] = -1;
            // 아직 자식 노드가 할당되지 않은 가장 이른 번호의 노드들을
            // 큐를 통해 순서대로 뽑는다.
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(-1);
            queue.offer(0);
            int pre = root;
            int changedK = 0;
            for (int i = 1; i < n; i++) {
                // 이번 노드
                int current = Integer.parseInt(st.nextToken());
                // 좌표 압축을 통해 메모리 사용을 줄인다.
                num++;
                // 이번이 k에 해당하는 수라면
                // 해당하는 압축된 번호도 따로 저장해둔다.
                if (current == k)
                    changedK = num;

                // 이전 번호와 연속된다면 같은 부모 노드를 공유하고
                // 연속하지 않다면 다음 순서의 노드를 부모 노드로 설정해야한다.
                if (pre + 1 != current)
                    queue.poll();
                
                // 부모 노드 기록
                parents[num] = queue.peek();
                // 큐에 num을 추가하여 후에 자식 노드들을 설정할 부모 노드로 등록해준다.
                queue.offer(num);
                // 이전 노드에 current 기록
                pre = current;
            }

            int count = 0;
            // k가 루트 노드인 경우는 제외.
            if (changedK != 0) {
                // 사촌 노드의 개수를 센다.
                for (int i = 1; i < parents.length; i++) {
                    if (parents[i] != parents[changedK] && parents[parents[i]] == parents[parents[changedK]])
                        count++;
                }
            }
            // 사촌 노드의 개수 기록
            sb.append(count).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}