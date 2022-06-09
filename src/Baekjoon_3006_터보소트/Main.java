/*
 Author : Ruel
 Problem : Baekjoon 3006번 터보소트
 Problem address : https://www.acmicpc.net/problem/3006
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3006_터보소트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // n개의 숫자가 주어진다
        // 이를 '터보 소트'를 사용하여 정렬하고 한다
        // 1. 홀수번째에는 정렬되지 않은 가장 작은 수를 찾는다.
        // 2. 그 수를 앞의 수와 교체해나가며 맨 앞으로 이동시켜 정렬한다.
        // 3. 짝수번째에는 정렬되지 않은 가장 큰 수를 찾는다.
        // 4. 그 수를 뒤의 수와 교체해나가며 맨 뒤로 이동시켜 정렬한다.
        // 이러한 작업을 반복하여 모든 수를 정렬하고자할 때, 각 단계별 수의 이동 횟수를 출력하라
        //
        // 세그먼트 트리 문제
        // n개의 수가 있다고 보자.
        // 먼저 숫자 1은 1의 위치로부터 첫번째 위치까지 숫자들과 모두 자리 교체를 하며 맨 앞으로 간다.
        //  - 위 단계를 세그먼트 트리를 이용하여 현재 위치부터 맨 앞까지의 수들의 개수를 센다.
        //  - 그 후 세그먼트 트리에서 1은 제외한다.
        // 그 후 숫자 n은 n의 위치로부터 마지막 위치까지 숫자들과 모두 자리를 교체하며 맨 뒤로 간다.
        //  - 역시 위 단계도 세그먼트 트리를 이용하여 n의 위치부터 맨 뒤의 자리까지 수들의 개수를 센다.
        //  - 그 후 세그먼트 트리에서 n은 제외한다.
        // 위와 같은 연산을 반복하여 모든 수에 대해 시행한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] indexes = new int[n + 1];
        // 각 수의 위치를 정한다.
        // 가령 2 1 3일 경우
        // 2의 위치는 첫번째, 1의 위치는 두번째, 3의 위치는 세번째라는 점에 주목하며 해당 위치를 저장한다.
        for (int i = 0; i < n; i++)
            indexes[Integer.parseInt(br.readLine())] = i + 1;

        // 합을 구할 때 연산상 이득이 있는 펜윅 트리를 사용한다.
        fenwickTree = new int[n + 1];
        // 처음에는 모든 수가 있으므로 각 자리에 모두 1을 넣어준다.
        for (int i = 1; i < fenwickTree.length; i++)
            input(i, 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            int target;
            int shift;
            if (i % 2 == 1) {
                // 홀수일 때, 가장 작은 수를 골라 맨 앞으로 보낸다.
                // 이 때의 수는 target.
                target = (i + 1) / 2;
                // target의 바로 앞 위치로부터 맨 앞의 위치까지 아직 정렬되지 않은 수들의 개수를 센다.
                // 여기서 이동하는 위치는 target번째가 아닌 1로 보내는 것이 중요한데
                // 정렬된 수는 별도의 정렬된 공간에 위치한다고 생각하면 편하다.
                shift = getSumFromAToB(1, indexes[target] - 1);
            } else {
                // 짝수일 때 가장 큰 수를 골라 맨 뒤로 보낸다.
                target = n - ((i - 1) / 2);
                // target의 바로 뒤 위치로부터 맨 뒤까지 아직 정렬되지 않은 수의 개수를 센다.
                // 마찬가지로 target 위치로 보내는 것이 아닌 n번째 위치로 보내게 된다.
                shift = getSumFromAToB(indexes[target] + 1, n);
            }
            // 결국 세어진 수의 개수 shift가 교체 횟수가 되며, 이를 출력한다.
            sb.append(shift).append("\n");
            // 그리고 정렬된 수는 펜윅트리에서 제외한다.
            input(indexes[target], -1);
        }
        System.out.print(sb);
    }

    // a부터 b까지 수의 개수를 센다.
    static int getSumFromAToB(int a, int b) {
        return getSumFromZeroToN(b) - getSumFromZeroToN(a - 1);
    }

    // 펜윅 트리 loc 위치에 value 값을 더한다.
    static void input(int loc, int value) {
        while (loc < fenwickTree.length) {
            fenwickTree[loc] += value;
            loc += (loc & -loc);
        }
    }

    // n 이하 위치에 수들의 개수를 센다.
    static int getSumFromZeroToN(int n) {
        int sum = 0;
        while (n > 0) {
            sum += fenwickTree[n];
            n -= (n & -n);
        }
        return sum;
    }
}