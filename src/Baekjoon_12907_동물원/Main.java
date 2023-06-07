/*
 Author : Ruel
 Problem : Baekjoon 12907번 동물원
 Problem address : https://www.acmicpc.net/problem/12907
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12907_동물원;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 고양이와 토끼, 두 종류의 동물이 n마리 있다.
        // 각 동물은 자신의 종에서 자신보다 키가 큰 개체 수를 말한다.
        // 가능한 경우의 수는?
        //
        // 조합 문제
        // 만약
        // 0 1 2가 주어진다면
        // 한 종류의 동물이 세마리 존재하는 것이며, 고양이와 토끼 모두 가능하므로
        // 2가지가 된다.
        // 자신보다 키가 큰 동물의 수를 말하는 것이기 때문에
        // 가장 작은 동물이 외친 수부터 0까지 모든 자연수가 나와야한다.
        // 이게 고양이와 토끼 각각에 대해서 가능해야한다.
        // 따라서 외친 수의 개수를 배열에 저장하고
        // 큰 수부터 순차적으로 살펴나가며,
        // 외친 수의 개수가 적어지거나 2를 넘어간다면 불가능한 경우
        // 개수가 0에서 처음 1이나 2가 되었다면 고양이와 토끼에 대해 각각 가능하므로 2가지
        // 만약 1이 계속된다면 한 종에 대해서만 적용이되는 것이므로 경우의 수는 2 그대로
        // 만약 2가 되거나 연속된다면 서로 다른 종에 대해서 적용이 가능하므로 *2를 하여 경우의 수를 센다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        // 수를 외친 개수
        int[] nums = new int[41];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            nums[Integer.parseInt(st.nextToken())]++;

        // 답안
        // 40을 외친 동물이 존재한다면 2로 시작
        long answer = (nums[40] > 0 ? 2 : 0);
        // 39부터 순차적으로 살펴본다.
        for (int i = nums.length - 2; i >= 0; i--) {
            // 만약 외친 수가 더 적이지거나
            // 2를 초과한다면 불가능한 경우이므로
            // 반복문을 종료하고 0을 출력한다.
            if (nums[i] < nums[i + 1] || nums[i] > 2) {
                answer = 0;
                break;
            } else if (answer == 0 && nums[i] > 0)  // 만약 0 이후에 1 혹은 2가 처음 등장했다면 현재 경우의 수는 2
                answer = 2;
            else if (nums[i] == 2) {
                // 만약 1 -> 2 혹은 2 -> 2로 2가 나왔다면 두 동물에 대해서 
                // 각각 토끼 고양이 혹은 고양이 토끼로 경우의 수는 2배가 된다
                answer *= 2;
            }
        }
        
        // 계산된 경우의 수 출력
        System.out.println(answer);
    }
}