/*
 Author : Ruel
 Problem : Baekjoon 14337번 Sums of Sums (Large)
 Problem address : https://www.acmicpc.net/problem/14337
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package SumsOfSums_Large;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static long[] psum;
    static long[] qsum;

    public static void main(String[] args) throws IOException {
        // 정말 정말 어려웠던 문제
        // 이분 탐색 + 누적 합 + 두 포인터 등이 복합적으로 쓰였고 개념 자체를 떠올리기도 어려웠다.
        // 참고한 사이트
        // https://www.quora.com/How-can-problem-D-Sums-of-sums-from-Round-E-of-the-Google-APAC-Test-2016-be-solved-for-the-large-dataset
        // https://blog.naver.com/sorisem4106/222441549279
        // https://www.udebug.com/GCJ/337
        //
        // n개의 수로 이루어진 수열이 주어진다
        // 연속된 원소로 이루어진 부분 수열을 추출해 원소들의 합을 구한다. 그 후 이들을 오름차순 나열하여 새로운 수열을 만든다
        // 그리고 q개의 쿼리가 주어지며, 새로운 수열에서 l부터 r까지 수들의 합을 구하는 문제
        // 가령 원래 수열이 5 4 3 2 1로 주어진다면
        // 새로운 수열은 5, 4, 3, 2, 1, (5+4), (4+3), (3+2), (2+1), (5+4+3), (4+3+2), .... , (5+4+3+2+1)로 만들어지며 해당 수들을 오름차순 정렬해야한다
        // 문제는 주어지는 n의 크기가 최대 20만까지 주어지므로 새로운 수열을 일일이 만들어 저장하기도 힘들뿐더라 시간도 오래 걸린다
        // 원래 수열을 arr이라 할 때
        // psum이라는 누적합을 구하자. psum[i] = arr[1] + arr[2] + ... + arr[i]
        // arr[i] = psum[i] - psum[i-1]로 구할 수도 있고, 이를 통해 새로 생기는 수열의 원소들은 원래 수열의 구간합이므로 이들을 구하기도 쉽다
        // 그리고 qsum이라는 새로운 누적합도 구하도록 하자 qsum[i] = n * arr[1] + (n - 1) * arr[2] + ... + (n - i + 1) * arr[i]
        // qsum을 구해두면 arr[l] 부터 arr[r]까지의 범위로 생성되는 새로운 수열에서의 원소들 합인 (psum[l] - psum[l-1]) + (psum[l+1] - psum[l-1] + ... + (psum[r] - psum[l-1])을
        // qsum[r] - qsum[l-1] - (n - r) * (psum[r] - psum[l-1])로 쉽게 구할 수 있다.
        // qsum[r] - qsum[l-1] = n * arr[1] + ... + (n - r + 1) * arr[i] - (n * arr[1] + ... + (n - l + 2) * arr[l - 1])
        //                     = (n - l + 1) * arr[l] + ... + (n - r + 1) * arr[r]
        // psum[r] - psum[l-1] = arr[l] + ... + arr[r]
        // qsum[r] - qsum[l-1] - (n - r) * (psum[r] - psum[l-1]) = ( n - l + 1 - n + r) * arr[l] + ... + (n - r + 1 - n + r ) * arr[r]
        //                                                       = ( r - l + 1) * arr[l] + ... + 1 * arr[r]
        // 이차원으로 나타내면
        // arr[l] + arr[l+1] + ... + arr[r-1] + arr[r] = psum[r] - psum[l-1]
        // arr[l] + arr[l+1] + ... + arr[r-1]          = psum[r-1] - psum[l-1]
        //  ...
        // arr[l] + arr[l+1]                           = psum[l+1] - psum[l-1]
        // arr[l]                                      = psum[l] - psum[l-1]
        //  ↑ arr[l]이 r - l + 1개
        //            ↑ arr[l+1]이 r - 1개 ...
        //                              ↑arr[r-1]이 2개
        //                                        ↑arr[r]이 1개
        // 이렇게 한 번에 합을 구하기 위해 psum과 qsum을 사용한다.
        // 새로 생긴 수열에서 x번째 수를 정확히 알고 싶다고 하자
        // 우리는 일일이 생성되는 수열을 직접 만들지는 않았으므로, 이진 탐색으로 한 해당 수를 추측하여 그 수보다 작은 수가 몇개인지를 세어 나가며
        // x번째 수가 무엇인지 알아맞추자.
        // 그 후 우리가 원하는 답은 새로운 수열에서 r번째까지의 수로 이루어지는 부분 수열의 합 - (l-1)번째까지의 수로 이루어지는 부분 수열의 합이므로 이를 구해주면 된다.
        // 한국어로 된 자료는 별로 없었고, quora에서 설명을 보더라도 이해하기도 참 어려웠다.
        // 조만간 다시 한번 풀어봐야할 것 같다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int t = 0; t < testCase; t++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());

            psum = new long[n + 1];     // psum[r] - psum[l-1]과 같은 연산을 쉽게 하기 위해 첫자리에 0을 넣어둔다 생각하자.
            qsum = new long[n + 1];
            for (int i = 1; i < psum.length; i++) {
                psum[i] = Integer.parseInt(st.nextToken());     // psum에 일단 해당 수를 저장.
                qsum[i] = psum[i] * (n - i + 1) + qsum[i - 1];      // qsum에는 해당 수 * (n - i + 1) + qsum[i-1]로 저장해주자.
                psum[i] += psum[i - 1];     // 그 후에 psum[i]에 psum[i-1]을 더해 누적합으로 바꿔주자.
            }

            sb.append("Case #").append(t + 1).append(":").append("\n");
            for (int i = 0; i < q; i++) {
                st = new StringTokenizer(br.readLine());
                long from = Long.parseLong(st.nextToken());     // 새로운 수열에서 from부터
                long to = Long.parseLong(st.nextToken());       // to까지의 합은

                // 1 ~ to까지의 합에서 1 ~ from까지의 합을 뺀 것과 같다.
                sb.append((getSumFromOne(to) - getSumFromOne(from - 1))).append("\n");
            }
        }
        System.out.println(sb);
    }

    static long getSumFromOne(long to) {        // 새로운 수열에서 첫번째 수부터, to번째 수까지의 합.
        long sum = 0;
        long number = findXthNumberInSubarray(to);      // to번째 수는 number이다.
        int right = 1;
        // 투 포인터를 활용해서 찾은 값이 number보다 작은지 ( 1 ~ to 사이에 포함되는지) 확인한다.
        for (int left = 1; left < psum.length; left++) {
            // number에 해당하는 수가 여러개로 중복되어있을 수 있다.
            // 따라서 number보다 작은 수만 세어나가며, 총 개수에서 센 것들을 빼, 최종 답안에 남은 숫자에 해당하는 개수만큼 number를 곱해 더해주자.
            // left는 1부터, right는 나아가며 arr[left] + ... + arr[right] = psum[right] - psum[left]가 number보다 '작은지' 확인한다.
            while (right < psum.length && psum[right] - psum[left - 1] < number)
                right++;
            // 이번에 세어진 것을 to개수에서 빼준다.
            to -= right - left;
            // right가 멈춘 시점은 number보다 같거나 큰 시점일 것이다. 하나 빼주자
            right--;
            // 처음에 설명했던 대로 부분 수열의 합인 (arr[l]) + (arr[l] + arr[l+1]) + ... + (arr[l] + .. + arr[r])을 구해서 더해주자
            sum += qsum[right] - qsum[left - 1] - (long) (psum.length - 1 - right) * (psum[right] - psum[left - 1]);
        }
        // 그리고 아직 세어지지 않은 to개는 number와 같은 수를 갖는 수들이다
        // to * number를 더해 리턴해주자.
        return sum + number * to;
    }

    static long findXthNumberInSubarray(long x) {
        // 이분 탐색으로 x번째 수가 무엇인지 찾아낼 것이다.
        // start는 0 혹은 psum[1]이어도 상관없다.
        long start = 0;
        // 새로 생기는 수열의 수 중 가장 큰 수는 arr[1] + .. + arr[n]인데, 이는 psum[n+1]과 같다.
        long end = psum[psum.length - 1];
        while (start < end) {
            // mid값으로 추측.
            long mid = (start + end) / 2;
            // mid보다 작은 값들의 수를 센다.
            long count = 0;

            // 투 포인터를 활용하여
            int right = 1;
            for (int left = 1; left < psum.length; left++) {
                // 여기서 psum[right] - psum[left-1]을 새로운 수열의 수와 같다.
                // 새로운 수열의수가 mid와 같거나 작다면 right를 하나씩 증가시켜주자.
                while (right < psum.length && psum[right] - psum[left - 1] <= mid)
                    right++;
                // 세어진 개수는 right - left개
                // 이를 psum의 길이만큼 반복한다.
                count += right - left;
            }
            // 만약 count 값이 x보다 작다면 우리가 원하는 x번째 수는 mid보다 크다.
            // start 에 mid + 1 값을 넣어주자
            if (count < x)
                start = mid + 1;
            // 그렇지 않다면 x번째 수는 mid보다 같거나 작다. end를 mid 값으로 넣어주자.
            else
                end = mid;
        }
        // 최종적으로 start 값이 우리가 원하는 새로운 수열에서 x번째 수이다.
        return start;
    }
}