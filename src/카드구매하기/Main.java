/*
 Author : Ruel
 Problem : Baekjoon 16909번 카드 구매하기 3
 Problem address : https://www.acmicpc.net/problem/16909
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 카드구매하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수열의 값(https://www.acmicpc.net/problem/2867) 문제와 같은 문제.
        // n개의 카드가 주어지고, 연속된 카드를 구매하려 한다
        // 카드 팩의 가격은 팩에 포함된 카드들의 최대 능력치 - 최소 능력치 값이다.
        // 구매할 수 있는 모든 카드 팩의 가격 합을 구하자
        // 카드의 개수가 최대 100만개 주어지므로 선형적으로 풀 수 있는 방법을 찾아야한다
        // 첫 카드부터 마지막 카드까지 한번씩 들리면서, 해당 카드로 끝나는 모든 카드팩의 가격 합을 모두 찾는다
        // 카드 팩의 가격은 +최대 카드의 능력치 - 최소 카드의 능력치 이므로, 2개를 분리해서
        // 전체 카드팩에서 최대 카드의 능력치들의 합과 최소 카드의 능력치의 합을 따로 구해 빼주자
        // 선형적으로 구하기 위해 i번째 카드에서 해당 카드로 끝나는 모든 카드팩들을 구한다.
        // 3, 2, 1이라는 카드가 주어지고, 3번째 카드의 순서라고 하면 가능한 카드팩들은
        // (1), (2, 1), (3, 2, 1)이다.
        // 자신으로부터 앞의 카드를 하나씩 추가해나가는 방법으로 카드팩들이 구성되고, 그 때 추가되는 값이 최대값이라면 카드 팩의 최대값이 갱신된다
        // 따라서 스택에 첫 카드부터 감소 모노톤 스택으로 카드를 저장한다
        // 그러면 해당 카드부터 첫 카드까지로 천천히 살펴보면 오름차순으로 되기 때문에 다음 최대값이 바뀌는 위치를 알 수 있다.
        // 그리고 구간과 그 구간에 해당하는 최대값(혹은 최소값)을 알 수 있기 때문에, 구간에 따른 최대값을 구하고 이를 답에 반영하는 방법으로 구하자.
        // 3, 2, 1, 3을 손으로 푼다고 친다면
        // 1. 첫번째 카드에서 가능한 카드팩 -> (3) 최대값 3 / 최대값의 합 : 3 / 스택의 상태 : 3
        // 2. 두번째 카드에서 가능한 카드팩 -> (2) 최대값 2, (3, 2) 최대값 3 / 최대값의 합 : 5  / 스택의 상태 : 3, 2
        // 3. 세번째 카드에서 가능한 카드팩 -> (1) 최대값 1, (2, 1) 최대값 2, (3, 2, 1) 최대값 3 / 최대값의 합 6 / 스택의 상태 3 2 1
        // 4. 네번째 카드에서 가능한 카드팩 -> (3) 최대값 3, (1, 3) 최대값 3, (2, 1, 3) 최대값 3, (3, 2, 1, 3) 최대값 3 / 최대값의 합 12 / 스택의 상태 3
        // 네번째 카드에서 3보다 같거나 큰 값들인 3, 2, 1이 모두 비워지며, 최대값의 합 6에서 최대값과 구간의 곱인 3 * 1, 2 * 1, 1 * 1 값들의 합을 빼주고
        // 현재 최대값인 3을 넣어주고, 모든 구간에 대해 최대값이 3이므로 최대값의 합에 3 * 4를 넣어준다.
        // 하나씩 앞카드를 추가하는 형태로 모든 형태를 구하는 것이기 때문에, 스택에 들어있는 최대값이 다음 최대값이 나올 때까지의 최대값이 되며, 해당하는 카드팩들의 최대값이 된다.
        // 위와 같은 연산을 모든 카드에 대해, 그리고 최소값에 대해 시행한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] nums = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < nums.length; i++)
            nums[i] = Integer.parseInt(st.nextToken());

        long answer = 0;    // 모든 카드팩의 가격을 저장할 공간.
        long valueTotal = 0;        // 최대값 * 구간의 합을 저장할 공간.
        Stack<Integer> stack = new Stack<>();       // 최대값을 감소 모노톤 스택으로 저장할 공간.
        nums[0] = Integer.MAX_VALUE;            // 첫 값을 Integer.MAX_VALUE으로 설정해주면 이보다 큰 값이 들어오지 않으며, 인덱스 0을 차지해주므로 구간을 구하기 편하다.
        stack.push(0);      // 해당 값을 스택에 넣어주고,
        for (int i = 1; i < nums.length; i++) {
            while (nums[stack.peek()] <= nums[i])       // i번째 카드보다 작은 카드가 스택에 들어있다면 제거하면서, 최대값 * 구간의 합인 valueTotal에서 해당 값을 빼준다.
                valueTotal -= (long) nums[stack.peek()] * (stack.pop() - stack.peek());
            // 비어있는 공간은 nums[i]가 최대값이 될 것이다. 해당 구간 * nums[i]값을 valueTotal에 더해주자.
            valueTotal += (long) nums[i] * (i - stack.peek());
            // i번째 카드로 끝나는 카드팩에 대한 모든 가격의 합이 valueTotal에 계산됐다. answer에 더해주자.
            answer += valueTotal;
            // 그리고 현재 카드를 스택에 담자.
            stack.push(i);
        }

        // 최소값에 대해서도 같은 연산을 시행한다.
        valueTotal = 0;         // i번째 카드에서 모든 카드팩의 최소값을 저장할 것이다.
        stack = new Stack<>();
        nums[0] = Integer.MIN_VALUE;        // 최소값에 대해서는 증가 모노톤 스택으로 저장될 것이므로, 0번 인덱스에 Integer.MIN_VALUE로 초기화해주면 구간 구하기가 편하다.
        stack.push(0);
        for (int i = 1; i < nums.length; i++) {
            while (nums[stack.peek()] >= nums[i])       // 증가 모노톤 스택이기 떄문에 i번째 카드보다 큰 값들을 스택에서 제해준다.
                valueTotal -= (long) nums[stack.peek()] * (stack.pop() - stack.peek());
            valueTotal += (long) nums[i] * (i - stack.peek());
            answer -= valueTotal;       // 최소값은 빼준다.
            stack.push(i);
        }
        // 계산된 결과가 모든 카드팩 가격의 합!
        System.out.println(answer);
    }
}