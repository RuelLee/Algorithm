/*
 Author : Ruel
 Problem : Baekjoon 12869번 뮤탈리스크
 Problem address : https://www.acmicpc.net/problem/12869
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12869_뮤탈리스크;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 뮤탈리스크 1개와 scv n(최대 3)개 남아있다.
        // 뮤탈리스크 한 번에 3개의 scv를 공격할 수 있다.
        // 각 공격으로 9, 3, 1의 데미지를 줄 수 있으며 한 번의 공격에 같은 scv를 여러번 공격할 수는 없다.
        // scv를 모두 파괴하기 위해서는 모두 몇 번의 공격을 해야하는가?
        //
        // 메모이제이션 문제
        // 각 scv의 hp를 DP로 세우고 bottom up 방식의 dp로 문제를 해결하자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // scv의 수
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // scv의 체력
        int[] scvs = new int[3];
        for (int i = 0; i < n; i++)
            scvs[i] = Integer.parseInt(st.nextToken());

        // 체력을 기반으로 DP 생성 후, 초기값 세팅
        int[][][] dp = new int[scvs[0] + 1][scvs[1] + 1][scvs[2] + 1];
        for (int[][] dd : dp) {
            for (int[] d : dd)
                Arrays.fill(d, Integer.MAX_VALUE);
        }
        // 정답 출력
        System.out.println(findAnswer(0, scvs, dp));
    }

    // 재귀 함수와 메모이제이션 활용
    static int findAnswer(int turn, int[] scvs, int[][][] dp) {
        if (Arrays.stream(scvs).sum() == 0)     // scv들이 모두 파괴되었다면, 현재 턴 반환.
            return turn;
        else if (dp[scvs[0]][scvs[1]][scvs[2]] == Integer.MAX_VALUE) {      // 메모이제이션 값이 없다면
            int[] tempScvs = scvs.clone();      // 현재 scv들의 값 복사
            // 첫번째 scv로 i번째 scv 선택
            for (int i = 0; i < scvs.length; i++) {
                if (scvs[i] == 0)       // 선택한 scv의 피가 0일 경우 건너뜀.
                    continue;

                // i번째 scv의 원래 hp 저장
                int scv1Temp = scvs[i];
                // 첫 공격으로 9의 데미지.
                scvs[i] = Math.max(scvs[i] - 9, 0);

                // 남은 두 scv의 hp가 0이라면 현재 상태에서 다음 턴으로 넘긴다.
                if (Arrays.stream(scvs).sum() == scvs[i])
                    dp[tempScvs[0]][tempScvs[1]][tempScvs[2]] = Math.min(dp[tempScvs[0]][tempScvs[1]][tempScvs[2]],
                            findAnswer(turn + 1, scvs, dp));

                // 두번째 scv로 j번 scv를 선택
                for (int j = 0; j < scvs.length; j++) {
                    if (j == i || scvs[j] == 0)         // i번이 다시 선택되거나 hp가 0인 경우 건너뜀.
                        continue;

                    // j번째 scv hp 저장.
                    int scv2Temp = scvs[j];
                    // 두번째 공격으로 3의 데미지
                    scvs[j] = Math.max(scvs[j] - 3, 0);

                    // 남은 scv의 체력이 0인 경우, 다음 턴으로 진행
                    if (Arrays.stream(scvs).sum() == scvs[i] + scvs[j])
                        dp[tempScvs[0]][tempScvs[1]][tempScvs[2]] = Math.min(dp[tempScvs[0]][tempScvs[1]][tempScvs[2]],
                                findAnswer(turn + 1, scvs, dp));

                    // 3번째 scv로 k번째 scv 선택
                    for (int k = 0; k < scvs.length; k++) {
                        // 첫번째, 두번째 공격 받은 scv와 겹치거나 hp가 0인 경우 건너뜀.
                        if (k == i || k == j || scvs[k] == 0)
                            continue;

                        // k번째 scv의 원래 체력 저장.
                        int scv3Temp = scvs[k];
                        // 세번째 공격으로 1의 데미지
                        scvs[k] = Math.max(scvs[k] - 1, 0);
                        // 다음 턴 진행
                        dp[tempScvs[0]][tempScvs[1]][tempScvs[2]] = Math.min(dp[tempScvs[0]][tempScvs[1]][tempScvs[2]],
                                findAnswer(turn + 1, scvs, dp));
                        // k scv hp 복구
                        scvs[k] = scv3Temp;
                    }
                    // j scv hp 복구
                    scvs[j] = scv2Temp;
                }
                // i scv hp 복구
                scvs[i] = scv1Temp;
            }
        }

        // 위의 else if를 통해 DP 값이 계산되거나
        // 이미 계산된 결과이므로 DP에 기록된 값 반환.
        return dp[scvs[0]][scvs[1]][scvs[2]];
    }
}