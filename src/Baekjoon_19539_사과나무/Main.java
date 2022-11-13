/*
 Author : Ruel
 Problem : Baekjoon 19539번 사과나무
 Problem address : https://www.acmicpc.net/problem/19539
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19539_사과나무;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 2개의 물뿌리개가 주어진다
        // 물뿌리개는 각각 다른 나무에 뿌려야하고, 각각 하루에 2, 1씩 나무를 성장시킬 수 있다고 한다
        // 원하는 나무들의 높이가 주어질 때, 정확히 해당 높이들로 나무들을 성장시킬 수 있는지 출력하라
        //
        // 수학 문제
        // 코드량은 간단하지만 푸는 방법을 떠올리는 것은 그렇지 않았다.
        // 나무의 개수가 최대 10만까지 주어지므로 당연히 브루트포스를 사용하면 안된다.
        // 하루에 총 성장시킬 수 있는 높이는 3이다.
        // 따라서 주어지는 전체 나무 높이의 합 또한 3의 배수여야한다.
        // 전체 나무 높이의 합이 sum이라면 총 sum / 3의 시일이 소요가 된다.
        // 이 뜻은 각각 물뿌리개를 sum / 3씩 사용할 수 있다는 뜻이다.
        // 이 때 2씩 성장시키는 물뿌리개는 1씩 성장시키는 물뿌리개 2번으로 대체할 수 있으므로
        // 2씩 성장시키는 물뿌리개의 사용 가능 횟수가 sum / 3번을 같거나 넘어야한다.(넘는 부분에 대해서는 1씩 성장시키는 물뿌리개로 대체)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 전체 나무 높이의 합
        int sum = 0;
        // 2씩 성장시키는 물뿌리개의 최대 사용 가능 횟수.
        int max2 = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            // 원하는 나무의 높이
            int tree = Integer.parseInt(st.nextToken());
            // 전체 합에 더함.
            sum += tree;
            // 해당 나무의 길이를 만드는데 쓸 수 있는 2 물뿌리개의 최대 횟수.
            max2 += (tree / 2);
        }

        // 전체 높이의 합이 3의 배수이고,
        // 2 물뿌리개의 사용횟수가 전체 소요일보다 같거나 크다면
        // 원하는 높이들로 나무들을 성장시키는 게 가능한 경우.
        if (sum % 3 == 0 && max2 >= sum / 3)
            System.out.println("YES");
        // 그렇지 않다면 불가능한 경우.
        else
            System.out.println("NO");
    }
}