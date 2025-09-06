/*
 Author : Ruel
 Problem : Baekjoon 33666번 생일 멘션이 너무 많아
 Problem address : https://www.acmicpc.net/problem/33666
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_33666_생일멘션이너무많아;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람들이 루미를 멘션을 했다.
        // 루미는 멘션에 답장을 하기 위해 m개의 메시지를 준비했다.
        // 멘션을 2번 이상한 사람들의 평균을 내어, 평균보다 많다면, 첫번째 멘션에만 1번 메시지를 답장하고
        // 평균 이하인 사람에게는 1 ~ m까지의 메시지를 답장하고, 다 쓴 경우 다시 1번 메시지로 돌아와 보낸다.
        // 사람들은 자신이 받은 메시지 중 동일한 메시지가 있다면 매크로 답장인지 의심한다.
        // 매크로 의심을 받을 경우 -1
        // 그 외의 경우, 각 메시지를 답장한 횟수를 출력하라
        //
        // 누적 합, 차분 배열 트릭, imos
        // imos? 차분 배열 트릭? 둘이 뭐가 다른지 잘 모르겠지만.
        // 누적합을 통해 연속한 값의 변화를 빠르게 바꿀 수 있는 방법.
        // 예를 들어 1 ~ 4 구간에 전부 값을 1 추가하고 싶다면
        // 각각에 +1을 할 수도 있지만.
        // 1에는 +1, 5에는 -1을 하여 누적합을 통해 계산할 수도 있다.
        // 1 ~ 4, 2 ~ 3, 1 ~ 5 과 같이 중복된 구간이 발생하더라도 한번의 누적합으로 계산을 마칠 수 있는 것이 장점
        // 문제는 위의 조건에 따라 평균을 구하고
        // 평균에 따라 각 메시지를 imos에 따라 표시해주고, 나중에 한번에 스위핑으로 누적합을 구해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 사람, m개의 메시지
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 멘션 횟수
        int[] mentions = new int[n];
        // 평균
        long average = 0;
        // 2회 이상 멘션한 사람의 수
        int count = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < mentions.length; i++) {
            // 멘션 수가 2회 이상인 경우만 평균에 반영
            if ((mentions[i] = Integer.parseInt(st.nextToken())) > 1) {
                average += mentions[i];
                count++;
            }
        }

        // count가 0인 경우, 나눌 수 없다.
        if (count > 0)
            average /= count;

        boolean possible = true;
        // 각 메시지의 발송 횟수
        int[] messages = new int[m + 2];
        for (int i = 0; i < mentions.length; i++) {
            // 평균 초과일 경우.
            // 1번 메시지만 한번 보냄
            if (mentions[i] > average) {
                messages[1]++;
                messages[2]--;
            } else if (mentions[i] > m) {
                // 평균 이하인데, 멘션 수가 m 초과라면
                // m개의 메시지를 보내고 다시 1번 메시지로 돌아가기 때문에
                // 매크로 의심을 받는다.
                possible = false;
                break;
            } else {
                // 그 외의 경우
                // 1번 메시지부터 mentions[i]번 메시지까지를 한번씩 발송한다.
                messages[1]++;
                messages[mentions[i] + 1]--;
            }
        }
        
        // 불가능한 경우
        if (!possible)
            System.out.println(-1);
        else {
            // 가능한 경우
            // 각 메시지의 발송 횟수 기록
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < messages.length - 1; i++)
                sb.append(messages[i] += messages[i - 1]).append(" ");
            sb.deleteCharAt(sb.length() - 1);
            // 답 출력
            System.out.println(sb);
        }
    }
}