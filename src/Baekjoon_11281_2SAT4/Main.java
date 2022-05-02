/*
 Author : Ruel
 Problem : Baekjoon 11281번 2-SAT - 4
 Problem address : https://www.acmicpc.net/problem/11281
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_11281_2SAT4;

import java.util.*;

public class Main {
    static List<HashSet<Integer>> relationship;
    static List<HashSet<Integer>> groupMember;
    static int[] ancestorNum;
    static int[] group;
    static int ancestorCounter = 1;
    static int groupCounter = 1;
    static Stack<Integer> stack;

    public static void main(String[] args) {
        // 이전에 풀었던 2-SAT - 3이 해당 식이 성립되는 경우가 있는지의 유무만 따졌던 것에 비해
        // 이번 경우는 성립되는 경우 중 하나를 출력을 해야한다!
        // 이 때 성립되는 경우를 찾는 방법이 매애애애우 아름답고 멋있다.
        // 먼저 명제에 대한 기본적인 성질을 살펴보자
        // p -> q 이다라는 명제가 있을 때
        // p가 true 라면 q도 반드시 true 여야한다
        // 하지만 p가 false 라면? q는 true 이던지, false 이던지 상관이 없다.
        // 이 부분과, SCC 를 구하면서, 그룹을 배정할 때 쓰이는 방법과 연관지어 각 변수의 값을 지정한다
        // SCC 를 구하면서 그룹을 할당할 때, DFS 를 활용하기 때문에, 가장 끝부분에서부터 낮은 그룹 번호가 할당되며 점차 그 값이 상승한다
        // 따라서 p 라는 조건과, ~p라는 조건 중 p라는 조건보다 ~p라는 조건의 값에 할당된 그룹의 번호가 작을 경우
        // p -> ....... -> ~p 라는 형태를 띄게 될 가능성이 있다! (무조건 그러한 것은 아니다)
        // 여기에 명제의 기본적인 조건이 들어가게되는데, p가 true 라면 위의 명제는 성립하지 않는다
        // 하지만 p가 false 라면? ~p 는 true 이던, false 이던 상관없이 성립할 수 있다.
        // 각 변수들을 순차적으로 접근하면서, 각 변수가 true 일 때의 그룹 번호와, false 일 때의 그룹 번호를 따져 먼저 오는 조건을 false로 만들어주면 된다!

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        init(n);
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            relationship.get(toFalse(a)).add(toTrue(b));
            relationship.get(toFalse(b)).add(toTrue(a));
        }

        for (int i = 1; i < group.length; i++) {
            if (group[i] == 0)
                tarjan(i);
        }

        boolean possible = true;
        for (HashSet<Integer> group : groupMember) {
            for (int member : group) {
                if (group.contains(member > n ? member - n : member + n)) {
                    possible = false;
                    break;
                }
            }
        }

        if (!possible)
            System.out.println(0);
        else {
            StringBuilder sb = new StringBuilder();
            sb.append(1).append("\n");
            // 코드로는 너무 간단하지만, 이 부분이 각 변수들을 출력하는 부분.
            // i일 때(i가 true 일 때)와 i + n일 때 (i가 false 일 때)의 그룹 번호를 비교한다
            // 그 중 큰 쪽이 앞쪽에 나오는 조건이 될 가능성이 있다
            // 앞에 나오는 조건을 부정하는 값을 i의 값으로 정해준다!
            // i 그룹의 값이 더 크다면, i를 부정하는 false 인 0값
            // i + n의 그룹의 값이 더 크다면, i를 부정했던 , i+n을 다시 부정하는 true인 1 값.
            for (int i = 1; i < n + 1; i++)
                sb.append(group[i] > group[i + n] ? 0 : 1).append(" ");
            System.out.println(sb);
        }
    }

    static int tarjan(int n) {
        int minAncestor = ancestorNum[n] = ancestorCounter++;
        stack.push(n);

        for (int next : relationship.get(n)) {
            if (group[next] != 0)
                continue;

            else if (ancestorNum[next] == 0)
                minAncestor = Math.min(minAncestor, tarjan(next));
            else
                minAncestor = Math.min(minAncestor, ancestorNum[next]);
        }

        if (minAncestor == ancestorNum[n]) {
            HashSet<Integer> hashSet = new HashSet<>();
            while (!stack.isEmpty()) {
                group[stack.peek()] = groupCounter;
                hashSet.add(stack.peek());
                if (stack.pop() == n) {
                    ++groupCounter;
                    break;
                }
            }
            groupMember.add(hashSet);
        }
        return minAncestor;
    }

    static int toFalse(int n) {
        return n > 0 ? n + (ancestorNum.length - 1) / 2 : -n;
    }

    static int toTrue(int n) {
        return n > 0 ? n : -n + (ancestorNum.length - 1) / 2;
    }

    static void init(int n) {
        relationship = new ArrayList<>();
        groupMember = new ArrayList<>();
        groupMember.add(new HashSet<>());

        for (int i = 0; i < 2 * n + 1; i++)
            relationship.add(new HashSet<>());
        ancestorNum = new int[2 * n + 1];
        group = new int[2 * n + 1];
        stack = new Stack<>();
    }
}