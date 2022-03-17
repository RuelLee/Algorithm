/*
 Author : Ruel
 Problem : Baekjoon 12728번 n제곱 계산
 Problem address : https://www.acmicpc.net/problem/12728
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12728번_n제곱계산;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    // C1, C0에 해당하는 값.
    static final int[][] end = {{6}, {2}};
    // 분할 정복을 할 때, 해쉬맵을 통해 메모이제이션을 하도록하자.
    static HashMap<Integer, int[][]> hashMap;
    // 제곱해야하는 행렬.
    static final int[][] factor = {{6, -4}, {1, 0}};

    public static void main(String[] args) throws IOException {
        // 이전에 풀었던 Numbers(https://www.acmicpc.net/problem/12925)와 동일한 문제
        // 지만 역시 어려웠던 문제답게 이번에도 어렵다....^^
        // 문제는 (3 + root5) n제곱에서 천이하 정수부 3자리를 구하는 것이다.
        // 여기서 n이 최대 20억까지 매우 큰 값이 주어지므로 직접적으로 계산하는 것은 불가능하다.
        // 또한 그냥 분할 정복을 하려해도 double에서 값의 한계 때문에 소수부 이하가 커져서 정수가 되는 경우가 발생하므로 이 역시도 안된다.
        // 수학적 지식이 들어간 풀이를 해야한다.
        // 우리가 원하는 건 (3 + root5)의 제곱인데, (3 - root5)의 제곱도 같이 생각해보자
        // 3 = a, root5 = b라 했을 때,
        // (a + b)^n = a^n + (nC1) * a^(n-1) * b + ... + (nCn-1) * a * b^(n-1) + b^n이 되고
        // (a - b)^n = a^n - (nC1) * a^(n-1) * b + ... +- (nCn-1) * a + b^(n-1) -+ b^n이 된다. 여기서 n이 홀수냐 짝수냐에 따라서 부호가 결정된다.
        // (a + b)^n + (a - b)^n을 해보면, 2 * (a^n + (nC2) * a^(n-1) * b^2 + ... )가 된다.
        // 여기서 볼 점은 위 값이 정수가 된다는 점이다.
        // 소수를 갖는 b가 짝수번 들어갔을 때는 제곱이 되어 사라지고, 홀수번 들어갔을 때는 합하면서 상쇄되어 사라지기 때문이다.
        // 그리고 이제 (3 - root5)값을 다시 보자. root5는 2보다 크고, 3보다 작은 값이다.
        // 따라서 0 < 3 - root5 < 1 관계가 성립하고, 이 수는 아무리 제곱하더라도 1보다 작고 0보다 큰 수이다
        // (3 + root5)^ n < (3 + root5)^n + (3 - root5)^n < (3 + root5)^n + 1의 관계이며
        // 두 제곱의 합에서 우리는 1만 빼준다면 정수부를 얻는데는 아무 무리가 없다.
        //
        // 이제 제곱을 구해야하는데, n의 범위가 매우 크다.
        // 따라서 분할 정복을 통해 제곱의 연산을 줄여보자
        //  3 + root5 = A, 3 - root5 = B라 했을 때
        // A, B는 (x - A) * (x - B) = 0의 두 근이다
        // x^2 -(A + B)x + AB = 0이며 x^2 - 6x + 4 = 0으로 나타낼 수 있다.
        // 양변에 x^(n-2)을 곱해주면 x^n - 6x^(n-1) + 4x^(n-2) = 0, x^n = 6x^(n-1) - 4x^(n-2)이다
        // 이에 각각 A와 B를 대입해서 더해준다면 A^n + B^n = 6 * (A^(n-1) + B^(n-1)) -4 * (A^(n-2) + B^(n-2)) 으로 쓸 수 있다.
        // A^n + B^n = Cn이라 정의한다면,
        // Cn = 6 * C(n-1) - 4 * C(n-2)로 쓸 수 있다.
        // 이는 행렬의 곱으로 표현할 수 있으며
        // ┌   Cn   ┐   ┌ 6, -4 ┐   ┌ C(n-1) ┐ = ┌ 6, -4 ┐^(n - 1)     ┌ C1 ┐
        // └ C(n-1) ┘ = └ 1   0 ┘ * └ C(n-2) ┘   └ 1   0 ┘         *   └ C0 ┘
        // C1은 3 + root5 + 3 - root5 이므로 6
        // C0는 (3 + root5)^0 + (3 - root5)^0 = 2
        // ┌ 6, -4 ┐^(n - 1)
        // └ 1   0 ┘          이 값을 분할정복으로 구한다면 A^n + B^n을 구할 수 있다.
        // 그 후 1을 빼주고, 백이하의 세자리 정수부를 취한다면 답을 구할 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        hashMap = new HashMap<>();
        hashMap.put(1, factor);

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = Integer.parseInt(br.readLine());

            // factor의 n-1 제곱과 end를 곱해주자.
            int[][] answer = getMulti(getPow(n - 1), end);
            // answer[0][0]에서 1 뺀값이 우리가 원하는 답이지만, int 값의 범위 때문에
            // 제곱 계산을 하며 천 이상의 값은 모듈러 연산으로 천미만의 값으로 만들어버렸다
            // 따라서 음의 값이 존재할 수 있기 때문에 +1000을 해서 천이상의 값으로 만든 뒤 1을 빼고, 다시 %1000을 하여 값을 보정해주자.
            sb.append("Case #").append(t + 1).append(": ").append(makeAnswerForm((answer[0][0] + 1000 - 1) % 1000)).append("\n");
        }
        System.out.println(sb);
    }

    static String makeAnswerForm(int n) {       // 0이 포함된 정답 형태로 수를 바꿔준다.
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i > 0; i--) {
            if (n < Math.pow(10, i))        // 수가 100, 10이 넘는지 확인하고 아니라면 0을 앞에 추가한다.
                sb.append(0);
        }
        sb.append(n);
        return sb.toString();
    }

    static int[][] getMulti(int[][] a, int[][] b) {     // 두 행렬의 곱을 구한다.
        int[][] answer = new int[a.length][b[0].length];
        for (int row = 0; row < answer.length; row++) {
            for (int col = 0; col < answer[row].length; col++) {
                for (int i = 0; i < a[0].length; i++) {
                    answer[row][col] += a[row][i] * b[i][col];
                    answer[row][col] %= 1000;       // 모듈러 연산으로 백이하의 자리값들만 남겨둔다.
                }
            }
        }
        return answer;
    }

    static int[][] getPow(int n) {  // factor의 n제곱을 구한다.
        if (hashMap.containsKey(n))     // 값이 존재한다면 바로 참고하고
            return hashMap.get(n);

        if (n % 2 == 0)      // 그렇지 않고, n이 짝수라면, factor^(n/2) * factor(n/2)으로 값을 저장하고,
            hashMap.put(n, getMulti(getPow(n / 2), getPow(n / 2)));
        else        // 홀수라면 factor^(n/2) * factor^(n/2) * factor 형태로 값을 구해 저장한다
            hashMap.put(n, getMulti(getMulti(getPow(n / 2), getPow(n / 2)), factor));
        // 저장한 값을 참고.
        return hashMap.get(n);
    }
}