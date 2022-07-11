/*
 Author : Ruel
 Problem : Baekjoon 3078번 좋은 친구
 Problem address : https://www.acmicpc.net/problem/3078
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3078_좋은친구;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생의 이름이 성적순으로 주어진다
        // 학생들은 이름의 길이가 같고, 등수 차이가 k 이내인 학생과 좋은 친구가 된다고 한다.
        // 좋은 친구들은 모두 몇 쌍인지 구하여라.
        //
        // 이름을 순서대로 살펴보며, 이전 k 범위 안에 같은 길이의 이름이 몇개 있는지 세어보며 좋은 친구 쌍을 세어가자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 이름 자체가 중요하진 않다.
        // 이름의 길이로 저장해두자.
        int[] nameLengths = new int[n];
        for (int i = 0; i < nameLengths.length; i++)
            nameLengths[i] = br.readLine().length();

        // k 범위 내에 있는 이름의 길이에 따른 개수를 계산해서 저장한다.
        int[] namesInRange = new int[21];
        // 좋은 친구의 쌍.
        long couples = 0;

        // 모든 학생들에 대해
        for (int i = 0; i < nameLengths.length; i++) {
            // 이번 학생과 쌍을 이루는 k 범위 내의 같은 이름 길이의 학생들의 수를 좋은 친구로 더해준다.
            couples += namesInRange[nameLengths[i]];
            // 이번 학생 이름의 길이를 namesInRange에 넣어둔다.
            namesInRange[nameLengths[i]]++;
            // i + 1 학생으로 넘어가면, i - k번째 학생은 이제 범위에서 빠져아한다.
            // 해당 학생 이름의 길이에 해당하는 값을 하나 줄여준다.
            if (i >= k)
                namesInRange[nameLengths[i - k]]--;
        }

        // 총 좋은 친구 쌍을 출력한다.
        System.out.println(couples);
    }
}