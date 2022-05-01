public class q27_RemoveElement {
//    public int removeElement(int[] nums, int val) {
//        int count = 0;
//        for (int i = 0; i < nums.length; i++) {
//            if (nums[i] == val) {
//                count += 1;
//            } else if (count != 0){
//                nums[i - count] = nums[i];
//            }
//        }
//        return nums.length - count;
//    }

    public int removeElement2(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i++] = nums[j];
            }
        }
        return i;
    }

    public int removeElement(int[] nums, int val) {
        int i = 0;
        int n = nums.length;
        while (i < n) {
            if (nums[i] == val) {
                nums[i] = nums[n - 1];
                n--;
            } else {
                i++;
            }
        }
        return n;
    }
}
