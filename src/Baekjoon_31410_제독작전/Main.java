/*
 Author : Ruel
 Problem : Baekjoon 31410번 제독 작전
 Problem address : https://www.acmicpc.net/problem/31410
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_31410_제독작전;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 수직선 위의 n개의 위치에 오염 물질이 있다.
        // i번째 오염물질의 위치는 xi이며, 오염도는 pi이다.
        // 오염물질을 정화하기 위해서는 pi만큼의 제독제를 뿌려야한다.
        // 제독 장비에 금이 가, 제독 장비를 사용할 때마다, 1분마다 흘러내리는 제독제의 양이 1씩 증가한다.
        // 제독병은 1분에 1만큼의 거리를 이동할 수 있고, 시작 위치는 임의의 위치에서 시작할 수 있다.
        // 제독병은 오염 물질을 정화한 후, 다음 가까운 오염 물질의 방향으로 이동한다. 두 방향 모두 거리가 같다면 임의의로 정할 수 있다.
        // 오염 물질의 분석을 위해, 아무 곳이나 한 곳을 남겨두고 n-1개의 오염물질을 정화한다.
        // 충전해야하는데 제독제의 최소 양은?
        //
        // 그리디, 누적합 문제
        // 생각해보면, 갔던 방향을 되돌아가는 것보다는, 한 방향으로 진행하면서 정화하는 것이 유리하다는 것을 알 수 있다.
        // 왼쪽에서 시작하는 경우와 오른쪽에서 시작하는 두 경우를 생각해볼 수 있다.
        // 왼쪽에서 시작하는 경우에서 모든 물질을 정화한다면
        // 1번째 오염 물질을 정화
        // 2번째 오염 물질을 정화하러 가는 동안 거리 * 1 만큼의 제독제가 흘러내림
        // 2번째 오염 물질 정화
        // 3번째 오염 물질을 정화하러 가는 동안 거리 * 2 만큼의 제독제가 흘러내림
        // ..
        // 이 반복되어 n번째 오염물질까지 가게 된다.
        // 따라서 위 상황에서 계산되는 제독제의 양을 먼저 계산해둔다.
        // 그 후, i번째 오염 물질을 분석을 위해 남겨둔다고 한다면
        // i ~ n번째 물질까지의 낭비되는 제독제가 모두 거리 * 1만큼 줄어드는 것과 같다.
        // 이 때 n번째 물질을 남겨둔다면 n번째 오염 물질까지는 가지 않아도 되므로 따로 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 오염 물질
        int n = Integer.parseInt(br.readLine());
        int[][] pollutions = new int[n][2];
        for (int i = 0; i < pollutions.length; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < pollutions[i].length; j++)
                pollutions[i][j] = Integer.parseInt(st.nextToken());
        }
        // 오름차순으로 정렬
        // 왼 -> 오
        Arrays.sort(pollutions, Comparator.comparingInt(o -> o[0]));
        long answer = findAnswer(pollutions);
        // 내림차순으로 정렬
        // 오 -> 왼
        Arrays.sort(pollutions, (o1, o2) -> Integer.compare(o2[0], o1[0]));
        answer = Math.min(answer, findAnswer(pollutions));
        // 답 출력
        System.out.println(answer);
    }

    static long findAnswer(int[][] pollutions) {
        // 넘어온 오염 물질에 대한 정보를 바탕으로
        // 오염 물질 간의 거리를 누적합으로 계산
        long[] distancePsums = new long[pollutions.length];
        // 총 오염 물질의 양 계산
        long pollutionsTotal = pollutions[0][1];
        for (int i = 1; i < distancePsums.length; i++) {
            distancePsums[i] = distancePsums[i - 1] + Math.abs(pollutions[i][0] - pollutions[i - 1][0]);
            pollutionsTotal += pollutions[i][1];
        }

        // 모든 오염 물질을 정화할 때, 흘러내리는 제독제의 양 계산
        long decontamination = 0;
        // n-1번째 오염 물질까지만 계산해 두고
        for (int i = 1; i < pollutions.length - 1; i++)
            decontamination += (long) Math.abs(pollutions[i][0] - pollutions[i - 1][0]) * i;
        // 현재 n - 1까지의 값만 계산된 decontamination은 
        // 마지막 n번째 오염 물질을 분석을 위해 남겨둔 경우에서의 흘러내리는 제독제의 양과 같다.
        // 따라서 현재 decontamination 값과 전체 오염 물질의 양 - n번째 오염 물질의 양을 더해
        // n번째 오염 물질을 남겨둔 경우를 미리 계산해서 answer에 반영
        long answer = decontamination + pollutionsTotal - pollutions[pollutions.length - 1][1];
        // 모든 오염 물질을 정화할 때, n번째 오염 물질을 정화하러 가는 동안 흘러내리는 제독제의 양 누적.
        decontamination += (long) Math.abs(pollutions[pollutions.length - 1][0] - pollutions[pollutions.length - 2][0]) * (pollutions.length - 1);
        
        // i번째 오염 물질을 남겨둔다고 한다면
        // i ~ n번째 물질까지의 분 당 흘러내리는 제독제의 양이 1씩 줄어든다.
        // 따라서 decontamination에서 해당하는 거리 만큼의 양을 뺀 후,
        // 전체 오염 물질의 양에서 해당 오염 물질의 양을 뺀 값을 더한 것이, 필요한 제독제의 양이다.
        for (int i = 0; i < pollutions.length - 1; i++)
            answer = Math.min(answer, decontamination - (distancePsums[distancePsums.length - 1] - distancePsums[i]) + pollutionsTotal - pollutions[i][1]);
        // 답 반환
        return answer;
    }
}