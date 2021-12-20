/*
 Author : Ruel
 Problem : Baekjoon 12925번 Numbers
 Problem address : https://www.acmicpc.net/problem/12925
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Numbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    static HashMap<Integer, int[][]> memo;
    static final int[][] end = {{6}, {2}};

    public static void main(String[] args) throws IOException {
        // 머리 아픈 수학 문제^^..
        // n이 주어질 때 (3 + root5)의 n 제곱값에서 정수부 마지막 3자리를 구하는 문제.
        // 주어진 값이 1보다 큰 값이므로, n값이 매우 큰 값이 들어오면, double로 계산할 경우 오차가 매우 커져 오답이 된다
        // 따라서 정수 형태로 계산해야하는 방법을 궁리해야한다
        // 이 때 (3 - Math.sqrt(5))와 같이 고려하면,
        // (3 + root5) = a, (3 - root5) = b라 하면, a^n + b^b을 합치면 정수가 된다
        // a^n 형태는 3^n + 3^(n-1) * root5 + 3^(n-2) * (root5)^2 + .... + (root5)^n 형태를 띄게 될 것이고
        // b^n 형태는 3^n - 3^(n-1) * root5 + 3^(n-2) * (root5)^2 + .... +- (root5)^n (n이 짝수냐 홀수냐에 따라 +-) 형태가 된다.
        // 그럼 여기서 무리수가 되는 부분은 root5가 홀수로 들어간 부분인데, 이들은 서로 상쇄되어 사라지고 무리수부분은 모두 사라진다.
        // 그런데 0 < b < 1이므로, 0<b^n< 1 또한 만족하게 되는데 우리가 원하는 부부은 정수부이므로, a^n + b^n - 1 값을 하면 원하는 정수부의 값을 구할 수 있다.
        // a와 b는 (x-a)(x-b) = 0의 두 해이다.
        // 따라서 x^2 -6x + 4 = 0이고, 이는 x^n - 6x^(n-1) + 4x^(n-2) = 0이라 볼 수 있다. 각 각의 해를 집어넣고 두 식을 더해주면
        // a^n + b^n - 6 * (a^(n-1) + b^(n-1)) + 4 * ( a^(n-2) + b^(n-2)) = 0이 되고,
        // a^n + b^n = c^n이라 한다면, c^n - 6c^(n-1) + 4c^(n-2) = 0을 만족한다
        // 수가 작은 경우라면 DP를 활용해서 선형적으로 계산이 가능하나, n이 최대 20억이므로 행렬의 곱셈을 통해 분할정복으로 바꿔보자.
        // ( c^(n+1) )     (6 -4)(  c^n )    (6 -4)^n ( c^1 )
        // ( c^n     )  =  (1  0)(c^(n-1) =  (1  0)   ( c^0 ) 으로 나타낼 수 있다.
        // c^1 = a + b = 6이고, c^0 = 1 + 1 = 2이므로 위의 행렬의 제곱을 구하면 c^n의 값을 구할 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        memo = new HashMap<>();
        memo.put(1, new int[][]{{6, -4}, {1, 0}});
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = Integer.parseInt(br.readLine());
            int[][] matrix = matrixMultiple(getPow(n), end);        // 최종적으로 구해지는 행렬의 n제곱에 (c^1, c^0 값을 곱해주자)
            sb.append("Case #").append(t + 1).append(": ");
            // 원하는 답이 음수가 1000이하의 값만 구했기 때문에 음수가 나올 수 있다
            // 우리가 원하는 값은 c^n - 1 값의 정수부이기 때문에 음수가 나올 것을 고려하여
            // (matrix[1][0] - 1 + 1000) % 1000에 백의자리, 십의 자리를 살펴보고 비어있다면 0을 채운 값을 출력하자.
            sb.append(makeAnswerForm((matrix[1][0] + 999) % 1000)).append("\n");
        }
        System.out.println(sb);
    }

    static String makeAnswerForm(int n) {       // 답에 백의 자리, 혹은 십의 자리의 값이 없다면 0을 채워줘야한다.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2 - Math.log10(n); i++)
            sb.append(0);
        sb.append(n);
        return sb.toString();
    }

    static int[][] getPow(int n) {      // 행렬의 제곱을 구한다.
        if (!memo.containsKey(n)) {     // 메모이제이션되어있지 않다면
            if (n % 2 == 0)         // 짝수라면
                memo.put(n, matrixMultiple(getPow(n / 2), getPow(n / 2)));      //  반씩 나눠서 계산
            else        // 홀수라면, 반씩 나눠서 계산 후에, 다시 행렬을 한번 곱해준다.
                memo.put(n, matrixMultiple(matrixMultiple(getPow(n / 2), getPow(n / 2)), getPow(1)));
        }
        return memo.get(n);
    }

    static int[][] matrixMultiple(int[][] a, int[][] b) {       // 행렬을 곱을 하는 메소드
        int[][] answer = new int[a.length][b[0].length];
        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < answer[i].length; j++) {
                for (int k = 0; k < a.length; k++) {
                    answer[i][j] += a[i][k] * b[k][j];
                    answer[i][j] %= 1000;       // 우리가 필요한 부분은 정수부 중 마지막 세자리이므로 만이상은 모듈러 연산으로 없애주자.
                }
            }
        }
        return answer;
    }
}