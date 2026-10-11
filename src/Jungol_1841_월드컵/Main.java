/*
 Author : Ruel
 Problem : Jungol 1841번 월드컵
 Problem address : https://jungol.co.kr/problem/1841
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1841_월드컵;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 6개의 국가가 서로 다른 국가를 상대로 한번씩 총 다섯번의 경기를 치른다.
        // 경기 결과가 주어질 때, 가능한 결과표인지 여부를 판별하라
        // 총 4개의 테스트케이스가 주어진다.
        //
        // 백트래킹, 브루트포스 문제
        // 결과표를 보고서, 각각의 국가 별로 승 무 패를 해보아 결과표에 오류가 발생하지 않는지 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        // 결과표
        int[][] scores = new int[6][3];
        StringBuilder sb = new StringBuilder();
        // 4개의 테스트케이스
        for (int testCase = 0; testCase < 4; testCase++) {
            st = new StringTokenizer(br.readLine());
            // 결과표 입력
            for (int i = 0; i < scores.length; i++) {
                for (int j = 0; j < scores[i].length; j++)
                    scores[i][j] = Integer.parseInt(st.nextToken());
            }
            // 가능 여부 판별
            sb.append(possible(0, 1, scores) ? 1 : 0).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 전체 답 출력
        System.out.println(sb);
    }

    // a국가와 b국가의 차례이고 현재 결과표
    static boolean possible(int a, int b, int[][] scores) {
        // b가 모든 국가에 대해 마쳤고
        if (b == 6) {
            // a도 5번째 국가라면 더 이상 경기가 남아있지 않다.
            // 결과표를 보고 오류가 있는지 찾는다.
            if (a == 4) {
                for (int i = 0; i < scores.length; i++) {
                    for (int j = 0; j < scores[i].length; j++) {
                        // 0이 아닌 결과가 남았다면 오류. false 반환
                        if (scores[i][j] != 0)
                            return false;
                    }
                }
                // 정상적으로 모두 0이 들어갔다면 true 반환
                return true;
            } else  // 그 외의 경우 다음 순번의 a와 b로 넘긴다.
                return possible(a + 1, a + 2, scores);
        }

        // a가 이기고 b가 지는 경우
        if (scores[a][0] > 0 && scores[b][2] > 0) {
            scores[a][0]--;
            scores[b][2]--;
            if (possible(a, b + 1, scores))
                return true;
            scores[a][0]++;
            scores[b][2]++;
        }

        // 무승부인 경우
        if (scores[a][1] > 0 && scores[b][1] > 0) {
            scores[a][1]--;
            scores[b][1]--;
            if (possible(a, b + 1, scores))
                return true;
            scores[a][1]++;
            scores[b][1]++;
        }

        // a가 지고 b가 이기는 경우.
        if (scores[a][2] > 0 && scores[b][0] > 0) {
            scores[a][2]--;
            scores[b][0]--;
            if (possible(a, b + 1, scores))
                return true;
            scores[a][2]++;
            scores[b][0]++;
        }
        return false;
    }
}