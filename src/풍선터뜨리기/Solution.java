package 풍선터뜨리기;

public class Solution {
    public static void main(String[] args) {
        int[] a = {-16, 27, 65, -2, 58, -92, -71, -68, -61, -33};

        int leftMin = a[0];
        int rightMin = a[a.length - 1];

        int i = 1;
        int j = a.length - 2;

        boolean rightOrder = true;
        int count = 0;
        while (!(i > j)) {  // i값과 j값이 서로 교차할 때까지.
            if (rightOrder) {
                if (a[i] < leftMin) {   // i값은 오른쪽으로 나아가며 최저값(leftMin)이 갱신될 때마다 count 값 증가.
                    leftMin = a[i];
                    count++;
                    i++;
                } else {
                    if (a[i] > rightMin) {
                        i++;
                    } else {
                        rightOrder = false;
                    }
                }
            } else {
                if (a[j] < rightMin) {  // j값은 왼쪽으로 나아가며 최저값(rightMin)이 갱신될 때마다 count 값 증가.
                    rightMin = a[j];
                    count++;
                    j--;
                } else {
                    if (a[j] > leftMin) {
                        j--;
                    } else {
                        rightOrder = true;
                    }
                }
            }
        }
        count = count + 2;  // 최양단 값은 항상 남길 수 있으므로 제하고 셈하였으로 마지막에 2개 추가.
        System.out.println(count);
    }
}