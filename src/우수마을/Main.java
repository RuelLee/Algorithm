/*
 Author : Ruel
 Problem : Baekjoon 1949번 우수 마을
 Problem address : https://www.acmicpc.net/problem/1949
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 우수마을;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class PopCompare {
    int include;
    int exclude;

    public PopCompare(int include, int exclude) {
        this.include = include;
        this.exclude = exclude;
    }
}

public class Main {
    static int[] village;
    static List<Integer>[] connected;
    static int[][] maxPopulation;
    static boolean[] check;

    public static void main(String[] args) {
        // 트리 구조에 DP를 활용해야한다.
        // 먼저 가짓수를 생각해보자.
        // 1. 해당 마을이 우수마을이라면 자식 마을은 전부 우수마을이면 안된다.
        // 자신이 우수 마을이 아니라면, 자신의 부모 마을의 우수마을인지 아닌지 여부에 따라
        // 2. 부모는 우수마을, 자신은 우수마을 x -> 자식은 우수마을이던 우수마을이 아니던 상관이 없다 -> 자식의 자식 마을이 우수 마을일 수 있으므로.
        // 3. 부모도 우수마을 X, 자신도 우수 마을 X -> 자식 중 하나는 우수 마을이어야한다.
        // 위와 같은 3가지 경우를 고려하여 풀면 끝!
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        village = new int[n];
        for (int i = 0; i < village.length; i++)
            village[i] = sc.nextInt();

        connected = new List[n];
        for (int i = 0; i < connected.length; i++)
            connected[i] = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;

            connected[a].add(b);
            connected[b].add(a);
        }
        maxPopulation = new int[n][3];
        check = new boolean[n];
        int answer = Math.max(calcMaxPop(0, true, false), calcMaxPop(0, false, false));     // root마을을 포함/미포함 경우 중 큰 값을 가져온다.
        System.out.println(answer);
    }

    static int calcMaxPop(int n, boolean nInclude, boolean parentInclude) {
        check[n] = true;

        int maxPop = 0;
        if (nInclude) {     // 자신이 우수 마을인 경우.
            if (maxPopulation[n][1] != 0)   // 값이 저장되어있다면 바로 리턴
                return maxPopulation[n][1];

            maxPop = village[n];
            for (int i : connected[n]) {
                if (!check[i])      // 이미 거쳐온 부모 노드가 나오면 안된다
                    maxPop += calcMaxPop(i, false, true);       // 자식 마을은 우수마을이면 안된다
            }
            maxPopulation[n][1] = maxPop;
        } else {
            if (parentInclude) {    // 자신은 X, 부모 마을은 O
                if (maxPopulation[n][2] != 0)
                    return maxPopulation[n][2];

                for (int i : connected[n]) {
                    if (!check[i])      // 자식 마을이 우수 마을이든 아니든 상관없다. 큰 값을 가져오자.
                        maxPop += Math.max(calcMaxPop(i, true, false), calcMaxPop(i, false, false));
                }
                maxPopulation[n][2] = maxPop;
            } else {        // 자신 X, 부모 마을도 X
                if (maxPopulation[n][0] != 0)
                    return maxPopulation[n][0];

                boolean childGoodVillage = false;   // 자식 마을 중 한 마을이라도 우수 마을이 되는게 더 큰 값인지 확인.
                // 아닐 경우를 위한 우선순위큐
                // 자식 마을 모두가 우수 마을이 아닐 경우가 인원의 합이 더 크다면, 강제적으로 한 마을을 우수마을로 선정해야한다.
                // 그렇다면 우수마을이 됐을 때 - 우수 마을이 아닐 때의 차가 가장 적은 녀석으로 골라야한다!
                PriorityQueue<PopCompare> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.include - o2.exclude, o1.include - o1.exclude));
                for (int i : connected[n]) {
                    if (!check[i]) {
                        int include = calcMaxPop(i, true, false);
                        int exclude = calcMaxPop(i, false, false);

                        if (include > exclude)      // 자식 마을 중 우수 마을이 있다면 true
                            childGoodVillage = true;

                        priorityQueue.add(new PopCompare(include, exclude));
                    }
                }
                if (!childGoodVillage && !priorityQueue.isEmpty())  // 자식 중 우수마을이 없다면
                    maxPop += priorityQueue.poll().include;         // 우선순위큐에 담긴 첫번째 마을을 무조건 우수마을로 선정해주자.

                while (!priorityQueue.isEmpty())        // 그 후( 혹은 자식 중 우수마을이 알아서 있다면) 둘 중 큰 값을 더해주자.
                    maxPop += Math.max(priorityQueue.peek().include, priorityQueue.poll().exclude);
                maxPopulation[n][0] = maxPop;
            }
        }
        check[n] = false;
        return maxPop;
    }
}