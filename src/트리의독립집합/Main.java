/*
 Author : Ruel
 Problem : Baekjoon 2213번 트리의 독립집합
 Problem address : https://www.acmicpc.net/problem/2213
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 트리의독립집합;

import java.util.*;

public class Main {
    static Stack<Integer>[][] dp;
    static int[] weight;
    static List<Integer>[] child;

    public static void main(String[] args) {
        // 재귀적으로 푸는 방법을 이해하는데 시간이 좀 걸렸다.
        // 트리 형태이므로 아무 노드에서 시작하더라도 상관이 없다
        // 따라서 처음으로 입력 받는 노드를 루트로 생각하고 전개하였다
        // dp[node][0] = node가 포함되지 않았을 때 최댓값과 그 때의 노드들
        // dp[node][1] = node가 포함됐을 때 최댓값과 그 때의 노드들
        // 이를 첫번째로 입력받은 노드로부터 시작하여 재귀적으로 말단 노드까지 계산할 것이다
        // 따라서 dp[first][0]과 dp[first][1]이 채워지는 건 가장 마지막이 되고, dp[말단][0], dp[말단][1]이 가장 먼저 채워질 것이다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        dp = new Stack[n][2];
        weight = new int[n];

        for (int i = 0; i < weight.length; i++)
            weight[i] = sc.nextInt();

        child = new List[n];
        for (int i = 0; i < child.length; i++)
            child[i] = new ArrayList<>();

        // 트리 구성하기.
        // 이전에 언급이 된 적 있는 노드만 부모노드로서 자식노드를 받을 수 있다
        // 이전에 언급이 된 적 없는 노드는 자식노드로만 가능
        boolean[] check = new boolean[n];
        int first = sc.nextInt() - 1;
        int second = sc.nextInt() - 1;
        child[first].add(second);
        check[first] = check[second] = true;

        for (int i = 1; i < n - 1; i++) {
            int a = sc.nextInt() - 1;
            int b = sc.nextInt() - 1;

            if (check[a]) {
                child[a].add(b);
                check[b] = true;
            } else {
                child[b].add(a);
                check[a] = true;
            }
        }

        // first 노드를 포함했을 때와 포함하지 않았을 때의 Stack 두개를 구한다.
        Stack<Integer> include = dfs(first, true);
        Stack<Integer> exclude = dfs(first, false);

        StringBuilder sb = new StringBuilder();
        if (include.peek() > exclude.peek())    // 포함했을 때 가중치의 합이 크다면 include Stack으로
            makePrintForm(include, sb);
        else        // 그렇지 않다면 excludeStack으로 결과를 저장한다.
            makePrintForm(exclude, sb);
        System.out.println(sb);
    }

    static void makePrintForm(Stack<Integer> answer, StringBuilder sb) {
        sb.append(answer.pop()).append("\n");       // 가장 첫번째 값은 가중치의 합
        PriorityQueue<Integer> temp = new PriorityQueue<>();        // 나머지는 노드들이므로 우선순위큐에 넣어 오름차순으로 정렬하여 StringBuilder 에 기록해주자.
        temp.addAll(answer);
        while (!temp.isEmpty())
            sb.append(temp.poll() + 1).append(" ");
    }

    static Stack<Integer> dfs(int current, boolean include) {
        if (include) {      // current 노드를 포함했을 때의 최댓값
            if (dp[current][1] != null)     // 이전에 계산한 적이 있다면, 해당 결과값을 반환
                return dp[current][1];

            int includeSum = weight[current];       // 현재 노드의 가중치부터 시작하여
            Stack<Integer> answer = new Stack<>();
            for (int i : child[current]) {      // 자식 노드는 포함하지 않았을 때의 최댓값들 더해준다.
                Stack<Integer> temp = (Stack<Integer>) dfs(i, false).clone();
                includeSum += temp.pop();       // 첫번째인 가중치의 합은 includeSum에 더해주고,
                answer.addAll(temp);    // 나머지는 결과로 return 해줄 answer Stack에 담아주자
            }
            answer.push(current);       // 마지막으로 current 값을 추가시켜주고
            answer.push(includeSum);    // 가중치의 합을 가장 마지막에 추가하여
            return dp[current][1] = answer;     // dp[current][1]에 저장하고 리턴해주자.
        } else {
            if (dp[current][0] != null)     // 이전에 계산한 적이 있다면 값만 리턴
                return dp[current][0];

            int excludeSum = 0;
            Stack<Integer> answer = new Stack<>();
            for (int i : child[current]) {      // current 노드는 미포함이므로, 자식 노드들은 포함되어도, 포함되지 않아도 된다.
                Stack<Integer> includeStack = (Stack<Integer>) dfs(i, true).clone();        // 자식 노드를 포함했을 때 최댓값
                Stack<Integer> excludeStack = (Stack<Integer>) dfs(i, false).clone();       // 자식 노드를 포함하지 않았을 때 최댓값


                if (includeStack.peek() > excludeStack.peek()) {    // 큰 쪽을 골라
                    excludeSum += includeStack.pop();       // 가중치의 합을 excludeSum에 더해주고
                    answer.addAll(includeStack);        // 노드들은 answer Stack에 담아주자
                } else {
                    excludeSum += excludeStack.pop();
                    answer.addAll(excludeStack);
                }
            }
            answer.push(excludeSum);        // 마지막으로 가중치의 합을 answer Stack에 담아주고
            return dp[current][0] = answer;     // 값을 dp[current][0]에 기록하고 리턴.
        }
    }
}