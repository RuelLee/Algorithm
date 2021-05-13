package N개의최소공배수;

public class Solution {
    public static void main(String[] args) {
        // 유클리드 호제법을 사용하여, answer와 arr[i] 값에 최대공약수를 구해,
        // arr[i] / 최대공약수 한 값을 answer 곱해주자.
        // 최대공약수를 제외한 나머지 값만 곱해지므로, answer가 결국 최소공배수가 된다.

        int[] arr = {2, 6, 8, 14};

        int answer = arr[0];

        for (int i = 1; i < arr.length; i++) {
            int gcd = uclid(answer, arr[i]);
            answer *= arr[i] / gcd;
        }
        System.out.println(answer);
    }

    static int uclid(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a, b);

        if (max % min == 0)
            return min;
        return uclid(max % min, min);
    }
}