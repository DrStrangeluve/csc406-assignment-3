public class MainKnappSection1 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: <fullFilePath>");
            System.exit(1);
        }
        else {
            new KruskalsMSTKnappSection1(args[0]);
        }
    }
}

