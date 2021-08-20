/*
 Author : Ruel
 Problem : Baekjoon 1039번 교환
 Problem address : https://www.acmicpc.net/problem/1039
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 교환;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // SSAFY 초기에 동일한 문제를 그리디하게 푼 적이 있었다
        // 가능한 모든 조건에 대한 검사를 하고 회수를 차감시켜가며 직접 따져가며 교환했었는데
        // 다시 시도하려하니 조건도 잘 생각이 안나고, 여러 위험성이 있는 것 같다
        // BFS로 전수검사를 통해 해결하는 방법도 알아보자!
        // i번째 자리와 i+1 ~ 끝자리 까지의 숫자를 바꿔가며 queue 에 집어 넣는다
        // 단 동일한 교환에서 같은 숫자를 넣을 필요는 없다. HashSet 을 사용해서 중복처리를 해주자
        // k번 시도하고 나서, 숫자들을 우선순위큐에 담아 최대숫자만 가져오자!
        // 만약 담긴 숫자가 없다면 k번 숫자를 바꾸는 것이 불가능한 경우! ex) 10 1 -> 1과 0을 바꿔야하지만 0은 1번째 자리에 올 수 없다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        Queue<String> queue = new LinkedList<>();
        queue.add(Integer.toString(n));
        while (k > 0) {
            Queue<String> temp = new LinkedList<>();    // queue 에 담긴 것을 한번 모두 뽑아내는 것이 한 사이클! 그 동안의 숫자를 담아둘 임시공간
            HashSet<String> hashSet = new HashSet<>();      // 사이클 동안 같은 숫자가 나오는지 체크할 HashSet

            while (!queue.isEmpty()) {
                String current = queue.poll();
                for (int i = 1; i < current.length(); i++) {        // 첫번째 숫자와 i번째 숫자와 자리 교환을 할 것이다.
                    if (current.charAt(i) != '0') {     // i번째 숫자가 0이 아니고
                        String changed = current.charAt(i) + current.substring(1, i) + current.charAt(0) + current.substring(i + 1);
                        if (!hashSet.contains(changed)) {       // HashSet 에 중복되지 않은 숫자일 때만
                            temp.add(changed);      // temp 에 넣고
                            hashSet.add(changed);   // HashSet 에 체크해준다
                        }
                    }
                }

                for (int i = 1; i < current.length() - 1; i++) {        // i번째 숫자와
                    for (int j = i + 1; j < current.length(); j++) {        // j번째 숫자를 교환할 것이다.
                        String changed = current.substring(0, i) + current.charAt(j) + current.substring(i + 1, j) + current.charAt(i) + current.substring(j + 1);
                        if (!hashSet.contains(changed)) {       // 마찬가지로 HashSet 에 없을 때만
                            temp.add(changed);
                            hashSet.add(changed);
                        }
                    }
                }
            }
            queue.addAll(temp); // 한 사이클이 끝났으므로 temp에 내용을 queue에 모두 넣어준다.
            k--;
        }
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1));
        for (String s : queue)      // 우선순위 큐에 queue 에 남아있는 숫자들을 모두 담고
            priorityQueue.add(Integer.parseInt(s));
        System.out.println(priorityQueue.isEmpty() ? -1 : priorityQueue.poll());    // 비어있다면 -1, 그렇지 않다면 가장 큰 수를 출력한다.
    }
}