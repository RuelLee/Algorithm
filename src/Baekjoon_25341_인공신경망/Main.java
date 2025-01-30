/*
 Author : Ruel
 Problem : Baekjoon 25341번 인공 신경망
 Problem address : https://www.acmicpc.net/problem/25341
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25341_인공신경망;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 인공 신경은 c개의 입력 데이터에 따른 w1, w2 ... wc, c개의 가중치를 갖고 있고, 편향값 b를 갖고 있다.        
        // x1, ... , xc의 입력값이 들어오면 x1 * w1 + ... + xc * wc + b를 계산하여 출력한다.
        // 인공 신경망이 주어지는데, 이는 입력층, 은닉층, 출력층으로 구성되어있다.
        // 입력층은 n개의 입력이 주어지고
        // 은닉층은 m개의 인공 신경, 출력층은 1개의 인공 신경으로 구성되어있다.
        // 입력 데이터는 은닉층과 출력층을 거쳐 하나의 값으로 바뀐다.
        // q개의 입력이 주어질 때, 각각의 출력값을 출력하라
        //
        // 사칙연산, 계산 문제
        // 인데 일일이 모두 계산하다가는 시간 초과가 난다.
        // 따라서 각각의 인공신경이 최종값에 반영되는 값들을 미리 계산해두어야한다.
        // 먼저 은닉층과 출력층을 살펴보면
        // 은닉층에서 생성된 결과가 출력층의 입력이 되고 이를 바탕으로 결과값이 나온다.
        // 출력층에 있는 인공신경이 갖고 있는 각 입력데이터에 따른 가중치를
        // 아예 은닉층 자체에 곱해서, 출력층을 거치지 않더라도 은닉층에서 결과값을 계산할 수 있도록 한다.
        // 마찬가지로 은닉층에 있는 인공신경들의 가중치를 고려하여
        // 입력되는 데이터에 따른 가중치를 곱해 입력 데이터의 가중치들을 누적시킨다.
        // 그러면 매번 곱셈을 모두 하는 것이 아니라, 미리 계산된 입력 데이터에 따른 누적 가중치의 계산만으로
        // 최종 결과값을 도출할 수 있다.
        // 출력층의 편향값을 그대로, 은닉층의 편향값은 출력층의 가중치 만큼 가중된 값을 계산해서 따로 더해준다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 입력, m개의 은닉층 인공 신경의 개수, q개의 입력들
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 은닉층
        int[][] concealment = new int[m][];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken());
            concealment[i] = new int[2 * c + 1];
            for (int j = 0; j < concealment[i].length; j++)
                concealment[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 출력층
        st = new StringTokenizer(br.readLine());
        int[] output = new int[m + 1];
        for (int i = 0; i < output.length - 1; i++) {
            output[i] = Integer.parseInt(st.nextToken());
            // 출력층의 가중치들을 아예 은닉층에 곱해준다.
            for (int j = concealment[i].length / 2; j < concealment[i].length; j++)
                concealment[i][j] *= output[i];
        }
        // 출력층의 편향값
        output[m] = Integer.parseInt(st.nextToken());

        long[] multiple = new long[n + 1];
        // 은닉층에는 출력층의 가중치까지 고려된 값이 계산되어있다.
        // 이를 바탕으로 은닉층과 출력층의 가중치를 모두 고려된 값을 계산한다.
        // 상수 출력층의 상수는 그대로
        int constant = output[output.length - 1];
        for (int i = 0; i < concealment.length; i++) {
            int length = concealment[i].length / 2;
            // 은닉층과 출력층의 가중치를 반영한
            // 각 입력들의 가중치를 계산한다.
            for (int j = 0; j < length; j++)
                multiple[concealment[i][j]] += concealment[i][j + length];
            // 해당 은닉층 인공 신경의 편향값을 상수에 누적.
            constant += concealment[i][length * 2];
        }

        // 이제 multiple 값만 보면
        // 입력 -> 은닉 -> 출력으로 갈 필요 없이
        // 입력 * multiple 값만으로 결과값을 계산할 수 있다.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            long answer = 0;
            int[] input = new int[n + 1];
            // 각 입력에 가중치들을 곱한 후
            for (int j = 1; j < input.length; j++) {
                input[j] = Integer.parseInt(st.nextToken());
                answer += input[j] * multiple[j];
            }
            // 상수 값을 더해준다.
            answer += constant;
            // 답 기록
            sb.append(answer).append("\n");
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}