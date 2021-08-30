/*
 Author : Ruel
 Problem : Baekjoon 11280번 2-SAT - 3
 Problem address : https://www.acmicpc.net/problem/11280
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Q2_SAT_3;

import java.util.*;

public class Main {
    static List<HashSet<Integer>> relationship;
    static List<HashSet<Integer>> groupMember;
    static int[] ancestorNum;
    static boolean[] grouped;
    static int ancestorCounter = 1;
    static Stack<Integer> stack;

    public static void main(String[] args) {
        // 강한 연결 요소의 응용문제
        // 각 절 마다 두 개의 조건 중 하나는 T여야 전체 식이 T가 성립한다
        // 따라서 반대로 x1, x2 조건 중 ~x1이라면 반드시 x2여야하고, ~x2라면 반드시 x1이여야함을 이용하자
        // 위 사실을 이용하여 각 조건 별로 상관관계를 만들어 연결해주자
        // 하나가 성립한다면 다른 하나도 성립해야만 하는 사이클이 생긴다 -> 강한 연결 요소(SCC)
        // 그런데 강한 연결 요소에서 서로 상반되는 조건이 같이 있다면 이는 해당 식이 성립할 수 없음을 나타낸다.
        // 조건으로 n개의 입력이 주어지므로 입력 a가 T라면 a, F라면 a + n 으로 별개로 생각하였다.
        // 차후 만들어지는 SCC 에서 a가 존재한다면, a + n이 존재해선 안되고, a + n이 존재한다면 a가 존재해선 안된다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        init(n);

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            // abs(a)가 T일 때 -> abs(a), abs(a)가 F일 때 -> abs(a + n)
            // a < 0 이라면, a가 T일 때, b 여야만 한다.
            // a > 0 이라면, a가 F일 때, b 여야만 한다.
            relationship.get(a < 0 ? -a : a + n).add(b > 0 ? b : -b + n);
            // 역으로 b인 경우도 추가시켜주자.
            relationship.get(b < 0 ? -b : b + n).add(a > 0 ? a : -a + n);
        }
        for (int i = 1; i < grouped.length; i++) {
            if (!grouped[i])    // SCC 로 안 묶인 노드들 모두 묶어주기.
                tarjan(i);
        }
        System.out.println(possible(n) ? 1 : 0);
    }

    static boolean possible(int n) {
        for (HashSet<Integer> group : groupMember) {    // group 에서
            for (int member : group) {      // member 와
                if (group.contains(member > n ? member - n : member + n))       // member 의 반대되는 조건이 같이 있을 경우, 식 성립 불가.
                    return false;
            }
        }
        return true;
    }

    static int tarjan(int num) {         // tarjan 알고리즘으로 SCC 구하기.
        int minAncestor = ancestorNum[num] = ancestorCounter++;
        stack.push(num);

        for (int next : relationship.get(num)) {
            if (grouped[next])
                continue;

            else if (ancestorNum[next] == 0)
                minAncestor = Math.min(minAncestor, tarjan(next));
            else
                minAncestor = Math.min(minAncestor, ancestorNum[next]);
        }

        if (minAncestor == ancestorNum[num]) {
            HashSet<Integer> hashSet = new HashSet<>();
            while (!stack.isEmpty()) {
                grouped[stack.peek()] = true;
                hashSet.add(stack.peek());
                if (stack.pop() == num)
                    break;
            }
            groupMember.add(hashSet);
        }
        return minAncestor;
    }

    static void init(int n) {
        relationship = new ArrayList<>();
        groupMember = new ArrayList<>();

        for (int i = 0; i < 2 * n + 1; i++)
            relationship.add(new HashSet<>());

        ancestorNum = new int[2 * n + 1];
        grouped = new boolean[2 * n + 1];

        stack = new Stack<>();
    }
}