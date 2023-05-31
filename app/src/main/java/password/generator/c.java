public class App
    {
        public static void main(String[] args)
    {
       try {
            for (int i = 0; i < 15; i++) {
                Thread.sleep(1000);
                System.out.println(i);
            }
        }
        catch (Exception e) {

            // catching the exception
            System.out.println(e);
        }
    }
    }