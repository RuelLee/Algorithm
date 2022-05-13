/*
 Author : Ruel
 Problem : Baekjoon 7570번 줄 세우기
 Problem address : https://www.acmicpc.net/problem/7570
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7570_줄세우기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 어린이들이 각각의 번호를 갖고 줄을 서 있다
        // 이 중 몇 명의 어린이를 뽑아, 줄의 맨 앞으로, 혹은 맨 뒤로 보내
        // 번호를 오름차순으로 만들고 싶다
        // 이동해야하는 어린이의 최소 수는 몇 명인가
        //
        // 구현을 하는데는 어렵지는 않지만, 어떻게 풀어야하는가에 대해서는 생각하기 어려웠다
        // 맨 앞 / 맨 뒤 규칙이 없다면 이는 최장 증가 수열을 찾는 문제로
        // 오름차순으로 증가하는 가장 큰 어린이들의 수를 찾고,
        // 이 어린이들 사이 혹은 앞 뒤로 오름차순이 되도록 다른 어린이들을 세워주면 된다.
        // 하지만 문제에서는 맨 앞 / 맨 뒤 규칙이 있으므로, 중간에 다른 어린이들을 삽입하는 것이 불가능하다.
        // 따라서 '연속하는' 최장 증가 수열을 찾아
        // 해당 어린이들을 제외하고 앞 or 뒤로 다른 어린이들을 배치하여 번호가 연속하도록 만들어준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] children = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 이전에 등장한 수들 + 현재 수까지 연속한 수의 길이를 표시하자
        int[] maxSequence = new int[n + 1];
        // 초기값을 1로 세팅.
        Arrays.fill(maxSequence, 1);
        // 앞에 나온 수인지, 아직 나오지 않은 수인지 확인하기 위한 방문 체크 배열.
        boolean[] visited = new boolean[n + 1];
        for (int child : children) {
            // children[i] 방문 체크.
            visited[child] = true;
            // 만약 children[i] - 1가 앞에서 등장을 했었다면
            // 이번 수가 children[i] 이므로 연속한 수열이 될 수 있다.
            // children[i] - 1까지의 연속한 수열에 children[i]가 덧붙는다.
            // maxSequence[children[i]]에 해당 길이를 기록.
            if (visited[child - 1])
                maxSequence[child] = maxSequence[child - 1] + 1;
        }
        // 등장한 연속한 최장 증가 수열의 길이들 중 가장 큰 값을 구하고
        // 전체 어린이들에서 해당 길이의 크기만큼을 제외하고는 어린이들을 이동시켜 오름차순으로 배열해야한다
        // 전체 이동 수는 n - 최대 연속 최장 증가 수열의 길이
        System.out.println(n - Arrays.stream(maxSequence).max().getAsInt());
    }
}