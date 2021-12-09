/*
 Author : Ruel
 Problem : Baekjoon 2867번 수열의 값
 Problem address : https://www.acmicpc.net/problem/2867
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 수열의값;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    static long[] nums;

    public static void main(String[] args) throws IOException {
        // 수열의 값은 해당 수열의 최대값 - 최소값이라고 한다
        // 수열이 주어질 때, 모든 부분 수열에 대해 수열의 값의 합을 구하는 문제.
        // 수열의 값은 두 부분으로 나눌 수 있다.
        // 부분 수열의 최대값의 합과 부분 수열의 최소값들의 합. 이 둘을 뺀 값이 답이다
        // 결국 부분 수열마다의 최대값 최소값을 구해라라는 문제이다
        // a1, a2, a3, ... , an이라는 수열이 주어질 때, an으로 끝나는 부분수열을 모두 처리한다고 치자
        // 다시 말해 부분수열을 만들 때, an으로부터 한 숫자씩 앞 숫자를 포함하는 형태로 만든다고 생각하자.
        // 예를 들어 n이 1이라면 (a1) 하나만 해당하고
        // n = 2라면 (a2), (a1, a2)
        // n = 3이라면 (a3), (a2, a3), (a1, a2, a3)이 해당한다.
        // 만약 위 경우 an이 최대값이라면 해당하는 부분수열 모두 최대값이 an이 된다
        // 하지만 앞에 나온 다른 값이 최대값에 해당한다면 이를 모두 계산해야한다
        // 따라서 이는 스택으로 값을 내림차순으로 집어넣게 되면 해결할 수 있다. ( -> 뒤에서 부터 보면 오름차순으로 볼 수 있으므로 해당 부분수열의 최대값으로 볼 수 있다)
        // 4 1 2 3 1 이라는 수열이 주어지고 마지막 숫자의 차례라면, 스택에는 4, 3(실제로는 해당하는 숫자의 idx값. 0, 3)이 들어있다.
        // 이를 통해서, 부분 수열 (3, 1), (2, 3, 1), (1, 2, 3, 1)은 최대값이 3이고, (4, 1, 2, 3, 1)은 최대값이 4임을 알 수 있다.
        // 이는 1에서 얻는 부분 수열들의 최대값만 적는다면 4 3 3 3 1 로 볼 수 있다. 숫자가 하나 늘어갈 때마다 최대값을 더할 때 연산을 줄이기 위해 an까지의 부분수열들의 최대값의 합을 갖고 다니기로 하자
        // 4에서는 4가 되고,
        // 4 1 에서는 4, 1 -> 5 되고 (현재 스택의 상태 4 1)
        // 4 1 2 에서는 4, 2, 2 -> 9가 되고 (현재 스택의 상태 4 2 )
        // 4 1 2 3 에서는 4 3 3 3 -> 10, (현재 스택의 상태 4 3)
        // 4 1 2 3 1에서는 4 3 3 3 1 -> 13 (현재 스택의 상태 4 3 1)
        // 최소값에 대해서는 구한뒤 더하는 것이 아니라 빼주기만 하면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        nums = new long[n + 1];
        for (int i = 1; i < nums.length; i++)
            nums[i] = Integer.parseInt(br.readLine());

        long valueTotal = 0;        // 첫번째부터 i번째까지 각 자리가 포함된 부분수열을 구성할 때, 그 때의 최대값들의 합들을 표현한다.
        long answer = 0;            // 최종적으로 수열의 합이 저장될 곳.
        Stack<Integer> stack = new Stack<>();       // 최대값을 내림차순 모노톤 스택을 만든다.
        nums[0] = Integer.MAX_VALUE;        // MAX_VALUE를 첫자리에 값을 넣어두면 이보다 큰 값이 들어오는 경우가 없기 때문에 항상 0번 인덱스를 차지하고 있어준다. 따라서 idx 계산하기 편리.
        stack.push(0);
        for (int i = 1; i < nums.length; i++) {
            // 스택 최상단에 i번째 수보다 작은 값이 들어있다면
            // 스택 최상단의 idx값부터, 다음 최상단의 idx 값까지 최대값이 스택 최상단의 값이었다는 것을 의미한다
            // 하지만 이번 i번째 수가 이보다 크므로, 해당 구간의 최대값은 i로 바뀔 것이다.
            // 따라서 valueTotal에서 해당 구간에서의 최대값 합을 빼준다.
            // 이를 i보다 큰 값이 나타날 때까지 시행한다.(i번째 값이 가장 크다해도 0번 인덱스 MAX_VALUE 값을 만나 0번까지 제대로 잘 계산될 것이다)
            while (nums[stack.peek()] <= nums[i])
                valueTotal -= nums[stack.peek()] * (stack.pop() - stack.peek());
            // 그리고 현재 스택 최상단에 있는 값의 다음 위치부터, i 위치까지의 최대값은 nums[i]가 차지할 것이다.
            // 해당 구간과 nums[i]를 곱한 값을 valueTotal에 더해주자.
            valueTotal += nums[i] * (i - stack.peek());
            // i번째 수를 마지막으로 하는 부분수열의 합은 valueTotal과 같다. 이를 answer에 더해주자.
            answer += valueTotal;
            // 스택에 i값(nums[i]의 인덱스)을 넣어준다.
            stack.push(i);
        }

        valueTotal = 0;         // 이번에는 각 부분수열의 최소값의 합을 더할 것이다.
        nums[0] = Integer.MIN_VALUE;        // 0번 인덱스에 MIN_VALUE를 넣어주고, 스택에도 넣어주자.
        stack.push(0);
        // 이번에는 반대로 오름차순 모노톤 스택을 만든다(뒤에서부터 보면 내림차순이 되므로, 뒤에서부터 하나씩 숫자를 늘려가는 부분 수열을 구했을 때, 최소값이 갱신되는 것을 알 수 있다)
        for (int i = 1; i < nums.length; i++) {
            // 만약 nums[i]보다 같거나 큰 값이 스택에 최상단에 있다면
            // nums[i]가 최소값으로 갱신된 것이므로, 해당 구간에 대한 최소값들의 합을 valueTotal에서 빼주자.
            while (nums[stack.peek()] >= nums[i])
                valueTotal -= nums[stack.peek()] * (stack.pop() - stack.peek());
            // 현재 스택 최상단에 있는 값은 nums[i]보다 작은 값이므로, 그 이후로 생기는 부분수열들의 값은 최상단이 맡을 것이다
            // 따라서 스택 최상단 다음 인덱스부터, i 인덱스까지의 최소값은 nums[i]가 맡게 되고, 위 구간에 nums[i]를 곱한 값은 valueTotal에 더해주자.
            valueTotal += nums[i] * (i - stack.peek());
            // valueTotal에는 nums[i]를 마지막으로 하는 부분수열들의 최소값 합이 모여있다. answer에서 빼주자
            answer -= valueTotal;
            // 스택에 i값(nums[i]의 인덱스)을 넣어준다.
            stack.push(i);
        }
        // 결과적으로 계산된 answer가 최종 답.
        System.out.println(answer);
    }
}