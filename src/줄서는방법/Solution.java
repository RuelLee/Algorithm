package 줄서는방법;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        // 첫번째 숫자가 아직 나오지 않은 순서들 중 몇번째로 작은 값인지 계산하기 위해서는
        // (k - 1)의 값이 (n - 1)!으로 나눈 몫이 몇인지 구하면 된다.
        // 그 몫이 0일 때는 가장 작은 값인 1이 먼저 나올 것이고, 몫이 1이라면, 그 다음 작은 값인 2가 나오는 식.
        // 따라서 factorial 값을 모두 구한 뒤, (k - 1)을 (n - 1)!로 나눈 몫을 저장한다.
        // 나머지는 다시 (n - 2)!로 나눈 몫을 구한다. 이를 1까지 반복.
        // 이 몫 번째 작은 숫자를 순서대로 뽑아내면, n 개의 숫자를 사전순으로 정렬했을 때 k번째 방법이 된다.

        int n = 3;
        int k = 5;

        boolean[] check = new boolean[n];
        long[][] factorial = new long[20][2];
        factorial[1][0] = 1;
        k--;

        for (int i = 2; i < factorial.length; i++)  // DP로 factorial을 구하자
            factorial[i][0] = factorial[i - 1][0] * i;

        for (int i = n; i > 0; i--) {       // (k - 1) 값을 i factorial 값으로 나눈 값을 저장해두고, k는 그 나머지로 갱신.
            if ((k / factorial[i][0]) > 0) {
                factorial[i][1] = k / factorial[i][0];
                k %= factorial[i][0];
            }
        }

        List<Integer> list = new ArrayList<>();

        for (int i = n - 1; i >= 0; i--) {      // 사용되지 않은 숫자들 중 count 번째의 숫자를 꺼내고, 꺼냈다고 체크해주자.
            int count = (int) factorial[i][1];
            for (int j = 0; j < n; j++) {
                if (!check[j]) {
                    if (count > 0)
                        count--;
                    else if (count == 0) {
                        check[j] = true;
                        list.add(j + 1);
                        break;
                    }
                }
            }
        }

        int[] answer = new int[n];
        for (int i = 0; i < n; i++)
            answer[i] = list.get(i);
        System.out.println(Arrays.toString(answer));
    }
}