/*
 Author : Ruel
 Problem : Baekjoon 2632번 피자판매
 Problem address : https://www.acmicpc.net/problem/2632
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 피자판매;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 두 개의 피자와 각 피자 조각의 크기가 주어진다. 그리고 각 피자는 연속된 피자 조각들만 같이 팔 수 있다.
        // 고객이 원하는 피자 조각의 크기가 주어질 때, 판매할 수 있는 가짓수를 구하여라.
        // 각 피자에 대해서 가능한 피자 조각들의 크기를 구한다 -> 누적합을 통해 가능한 피자 크기들을 모두 센다
        // 그리고 각 피자에서 만들 수 있는 가짓수를 곱해 전체 가짓수를 구한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int pizza = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int aPizzaPieces = Integer.parseInt(st.nextToken());
        int bPizzaPieces = Integer.parseInt(st.nextToken());

        int[] aPizzaSum = new int[aPizzaPieces];        // 각 피자조각들의 연속된 누적합
        int[] bPizzaSum = new int[bPizzaPieces];
        aPizzaSum[0] = Integer.parseInt(br.readLine());     // 첫번째 피자 조각은 바로 입력받는다.
        for (int i = 1; i < aPizzaPieces; i++)
            aPizzaSum[i] = Integer.parseInt(br.readLine()) + aPizzaSum[i - 1];      // 누적합이기 때문에, 두번째 조각들은 이전 조각들의 크기들을 더해준다.

        // 다음 피자도 마찬가지로 누적합을 구해준다.
        bPizzaSum[0] = Integer.parseInt(br.readLine());
        for (int i = 1; i < bPizzaPieces; i++)
            bPizzaSum[i] = Integer.parseInt(br.readLine()) + bPizzaSum[i - 1];

        // 각 피자에서 가능한 조각들의 크기의 가짓수를 센다.
        int maxPizzaSize = aPizzaSum[aPizzaPieces - 1] + bPizzaSum[bPizzaPieces - 1] + 1;
        int[] aPizza = new int[maxPizzaSize];
        int[] bPizza = new int[maxPizzaSize];

        for (int i = 0; i < aPizzaPieces; i++) {        // i +1번 조각부터,
            for (int j = i + 1; j < aPizzaPieces + i; j++) {        // j번 조각까지의 크기를 구한다.
                // 원형이기 때문에, j값이 i값보다 작은 경우도 발생한다.
                // 따라서 j가 피자 조각의 개수를 넘어갈 경우, 전체 피자의 크기 + 넘치는 만큼을 더해준다.
                int end = j < aPizzaPieces ? aPizzaSum[j] : (aPizzaSum[aPizzaPieces - 1] + aPizzaSum[j % aPizzaPieces]);
                // i + 1 ~ j까지 조각의 크기를 센다.
                aPizza[end - aPizzaSum[i]]++;
            }
        }
        // 다른 피자도 마찬가지로 세어준다.
        for (int i = 0; i < bPizzaPieces; i++) {
            for (int j = i + 1; j < bPizzaPieces + i; j++) {
                int end = j < bPizzaPieces ? bPizzaSum[j] : (bPizzaSum[bPizzaPieces - 1] + bPizzaSum[j % bPizzaPieces]);
                bPizza[end - bPizzaSum[i]]++;
            }
        }

        // 전체 한 판의 크기로 파는 경우는 한가지.
        aPizza[aPizzaSum[aPizzaPieces - 1]] = 1;
        bPizza[bPizzaSum[bPizzaPieces - 1]] = 1;

        // 가능한 가짓수를 센다. 고객이 원하는 피자를 각 피자에서 한번에 만들 수 있는 경우를 각각 더해준다.
        long numOfCase = aPizza[pizza] + bPizza[pizza];

        // 첫번째 피자에서 i크기를 만드는 가짓수 * 두번째 피자에서 나머지 크기를 만드는 가짓수를 구해 모두 더해준다.
        for (int i = 1; i < pizza; i++)
            numOfCase += (long) aPizza[i] * bPizza[pizza - i];
        System.out.println(numOfCase);
    }
}