/*
 Author : Ruel
 Problem : Baekjoon 19847번 여우 신탁
 Problem address : https://www.acmicpc.net/problem/19847
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19847_여우신탁;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // 여우들은 여우신에게 빌면 0 이상 x미만의 수를 균일한 수로 내려준다.
        // 귀찮아진 여우신은, 두번째 여우부터는 새로 내려줄 수를 이전에 내려줬던 수에
        // 부탁받은 값을 나눈 나머지로 내려주기로 했다.
        // 마지막 여우가 받는 값의 기대값을 출력하라
        //
        // DP 문제
        // n이 30만, x가 최대 1만으로 주어져서 일일이 계산하면 안될 것 같지만
        // 나머지라는 조건 때문에 연산이 줄어들게 된다.
        // 예를 들어 5의 나머지는 0 ~ 4까지의 수로 구성되는데, 다음으로 모듈러 연산을 취할 수가
        // 5이상의 수가 된다면 그 값은 변하지 않으므로 연산을 하지 않고 건너뛰어도 된다.
        // 따라서 현재까지 등장한 부탁받은 수들의 최소값을 가져가며, 해당 값보다 더 작은 경우에만
        // 실제로 연산을 하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n마리의 여우
        int n = Integer.parseInt(br.readLine());
        // 각 여우가 부탁하는 값
        int[] foxes = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 값의 분포
        // 첫 여우가 부탁한 값의 범위 내에서는 모두 같은 확률을 갖는다.
        double[] dp = new double[foxes[0]];
        Arrays.fill(dp, (double) 1 / foxes[0]);
        
        // 부탁받는 값들 중 최소 값
        int minFox = foxes[0];
        // 여우들을 모두 살펴본다.
        for (int i = 1; i < foxes.length; i++) {
            // minFox보다 같거나 큰 값이 들어온다면 연산하지 않아도 된다.
            if (foxes[i] >= minFox)
                continue;
            
            // foxes[i]부터 minFox보다 작은 값에 한해서
            // foxes[i]로 나눈 나머지의 값에
            // 해당 확률을 더해준다.
            for (int j = foxes[i]; j < minFox; j++)
                dp[j % foxes[i]] += dp[j];
            // minFox 값 갱신
            minFox = foxes[i];
        }
        
        // 수들에 대해 기대값을 계산
        double answer = 0;
        for (int i = 0; i < minFox; i++)
            answer += (i * dp[i]);

        // 답안 출력
        System.out.println(answer);
    }
}