/*
 Author : Ruel
 Problem : Baekjoon 1086번 박성원
 Problem address : https://www.acmicpc.net/problem/1086
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 박성원;

import java.util.Scanner;

public class Main {
    static String[] nums;
    static int[] mod10K;
    static int[] modNumK;
    static long[][] dp;

    public static void main(String[] args) {
        // 개념이 사아아아아앙당히 어려웠던 문제
        // 숫자들을 순열로 이어붙였을 때, k로 나눈 나머지의 값이 0인 값들의 개수를 구하라는 문제이다
        // 각 숫자들은 최대 50자리이며, 숫자는 최대 15개가 주어진다
        // 숫자 자리에서부터, int나 long으로는 생각도 못할 큰 크기이다.
        // String으로 받아, 이를 차근 차근 k에 관한 나머지 연산을 돌려야한다
        // 예를 들어 23이라는 숫자에 관해 3으로 mod 연산을 한다고 하자. 23은 2 * 10 + 3으로 나타낼 수 있다
        // mod 연산은 곱셈과 덧셈에 관해 닫혀있으므로
        // ((2 % 3) * (10 % 3) + (3 % 3)) % 3)의 연산 결과와도 같다
        // 이를 이용해서, 먼저 최대 50자리의 숫자이므로, 0 ~ 10^49 까지 mod k 값을 저장해두자
        // 그리고 이를 이용하여 입력받은 각 숫자를
        // 한자리씩 나머지 연산하여 그 값을 저장해주자
        // 예를 들어 56이라는 숫자가 들어왔다면
        // 먼저 6을 % k 하여 mod 값을 가져오고, 그 후에 (((5 % k) * (10 % k) % k) + mod) % k 연산을
        // 순차적으로 하여 전체 자리수에 대해 모두 시행해 줄 것이다.
        // 그리고 나서 dp를 활용한다
        // dp dp의 행과 열이 의미하는 값은
        // 행 -> 각 숫자가 사용되었는지를 표시할 bitmask.
        // 열 -> 그 때의 나머지 값
        // 배열의 값 -> 그 때의 가지수
        // 만약 dp[1][0] 이라면, 첫번째 숫자가 사용되었으며, 나머지가 0인 경우의 가지수이고,
        // dp[3][1] 이라면 첫번째 숫자와 두번째 숫자가 사용되었으며 나머지가 1인 경우의 가지수이다.
        // dp[0][0] 값은 공집합이며 나머지가 0일 때의 값이긴하지만, DP를 통해 연산을 해야하므로 1로 값을 설정해준다.
        // 그리고 나서 각 행마다, 입력된 숫자가 포함되었는지 여부를 확인하고, 포함되지 않았다면, 해당 숫자를 그 뒤에 붙였을 때의 나머지 값을 구한다
        // 그 후, bitmask와 입력 된 숫자를 or 연산한 행, 나머지에 해당하는 열에, 현재 dp의 값을 저장해주면 된다(현재 가지수 그대로, 해당 나머지의 경우의 수로 바뀔 것이므로)
        // 결국 나머지가 0이 되는 경우는, 모든 숫자가 선택되어있는, dp[(1 << n) - 1][0]의 값이 되게된다.
        // 그 후, 유클리드 호제법을 이용하여, 기약 분수 형태로 바꿔주면 정답!
        Scanner sc = new Scanner(System.in);
        init(sc);

        long numsOfCase = dp[dp.length - 1][0];
        long numsOfWholeCase = getFactorial(nums.length);
        long gcd = calcGCD(numsOfCase, numsOfWholeCase);
        StringBuilder sb = new StringBuilder();
        if (numsOfCase == 0)
            sb.append("0/1");
        else
            sb.append(numsOfCase / gcd).append("/").append(numsOfWholeCase / gcd);
        System.out.println(sb);
    }

    static long getFactorial(int n) {   // 전체 가지수를 구해줄 factorial
        long answer = 1;
        for (int i = 2; i < n + 1; i++)
            answer *= i;
        return answer;
    }

    static long calcGCD(long a, long b) {       // 분자와 분모의 최대공약수를 구해줄 메소드
        long min = Math.min(a, b);
        long max = Math.max(a, b);
        long temp;
        while (min != 0) {
            temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }

    static void init(Scanner sc) {      // 초기 값 세팅!
        int n = sc.nextInt();
        nums = new String[n];
        mod10K = new int[50 * n];
        modNumK = new int[n];

        sc.nextLine();
        for (int i = 0; i < n; i++)
            nums[i] = sc.nextLine();        // 각 숫자를 입력받는다.

        int k = sc.nextInt();
        fillMod10K(k);      // 10의 배수들을 mod k 연산하여 그 결과값을 저장해주자.
        fillModNumK(k);     // 입력된 숫자들을 mod k 연산하여 그 결과값을 저장해주자.
        dp = new long[1 << n][k];       // dp의 행 크기는 각 숫자를 모두 표현할 수 있는 2^n, 열 크기는 k의 나머지는 0 ~ k-1 까지 k개
        fillDp(k);
    }

    static void fillDp(int k) {
        dp[0][0] = 1;           // 초기 값 1 세팅

        for (int bitMask = 0; bitMask < dp.length; bitMask++) {     // 공집합부터 시작하여, 모든 숫자가 선택될 때까지
            for (int selectedNum = 0; selectedNum < nums.length; selectedNum++) {       // 하나씩 숫자를 선택해가며
                if ((bitMask & (1 << selectedNum)) == 0) {      // 만약 비트마스킹에, 현재 선택한 숫자가 포함되어있지 않다면
                    for (int mod = 0; mod < dp[bitMask].length; mod++) {        // 각 저장된 나머지 값을 모두 훑으며
                        int modJoined = ((mod * mod10K[nums[selectedNum].length()]) % k + modNumK[selectedNum]) % k;        // 뒤에 선택된 숫자를 붙였을 때의 나머지 값을 계산한다.
                        // 그리고 선택된 숫자가 포함된 행
                        // 그리고 계산된 나머지의 열에
                        // 현재 가지수를 그대로 더해준다.
                        dp[bitMask | (1 << selectedNum)][modJoined] += dp[bitMask][mod];
                    }
                }
            }
        }
    }

    static void fillMod10K(int k) {
        mod10K[0] = 1;
        mod10K[1] = 10 % k;
        for (int i = 2; i < mod10K.length; i++)     // 10^n을 k로 나눈 나머지 값을 저장해줄 것이다!
            mod10K[i] = (mod10K[i - 1] * mod10K[1]) % k;
    }

    static void fillModNumK(int k) {        // 각 숫자를 한자리씩 mod 연산하여 전체 값에 대한 나머지를 저장해줄 메소드.
        for (int i = 0; i < nums.length; i++) {
            int mod = 0;
            for (int j = nums[i].length() - 1; j >= 0; j--)
                mod = (mod + (((nums[i].charAt(j) - '0') % k) * mod10K[nums[i].length() - j - 1]) % k) % k;     // 뒤에서부터 한자리씩 계산하여 mod을 차근차근 더해주고
            modNumK[i] = mod;       // 최종적인 결과를 저장해준다.
        }
    }
}