/*
 Author : Ruel
 Problem : Baekjoon 1305번 광고
 Problem address : https://www.acmicpc.net/problem/1305
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 광고;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // KMP 문제는 코딩은 정말 간단하게 나오지만 그 내용을 생각하는데 시간이 좀 걸리는 것 같다
        // l길이의 전광판이 주어지며, 광고주들은 내용을 가득 채워보여주기 위해 l보다 작은 길이의 문그를
        // 이어붙여 l길이만큼 전광판에 보여준다
        // 만약 aaab라는 문구를 길이 5인 전광판에 보여준다면
        // aaaba -> aabaa -> abaaa -> baaab -> ....
        // 전광판에 보여주는 문구가 주어졌을 때, 원래 광고문구의 최소길이를 알아내라는 문제
        // aaaba -> aaab + a(aab)
        // aabaa -> aab + aa(b)
        // abaaa -> abaa + a(baa)
        // baaab -> baaa  + b(aaa)
        // 전광판 문구에 따라 원래 광고 문구의 길이가 달라질 수 있다. 그냥 주어지는 전광판 문구의 경우만 생각하면 된다
        // 전광판 문구는 계속 반복이 되어 뒤에 이어붙여지는 형태가 되므로
        // 앞에서 등장했던 문구가 뒤에 붙어 반복이 된다
        // 따라서 KMP를 활용하여, 계산을 해나가, 마지막 글자에 주어지는 pi배열의 값이 앞 부분의 문자열과 중복되는 길이다.
        // 따라서 중복되는 부분인 pi배열의 값을 전광판 길이에서 빼준다면 답이 된다.
        Scanner sc = new Scanner(System.in);

        int l = sc.nextInt();
        sc.nextLine();
        char[] input = sc.nextLine().toCharArray();
        int[] pi = new int[l];
        int j = 0;
        for (int i = 1; i < pi.length; i++) {
            while (j > 0 && input[i] != input[j])
                j = pi[j - 1];
            if (input[i] == input[j])
                pi[i] = ++j;
        }
        System.out.println((l - pi[pi.length - 1]));
    }
}