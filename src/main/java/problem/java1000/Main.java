package problem.java1000;

public class Main {
    public static void main(String[] args) throws Exception {
        byte[] bs = new byte[3];
        int a = System.in.read(bs);
        if (a > 0) {
            System.out.println((bs[0] - 48) + (bs[2] - 48));
        }
    }
}
