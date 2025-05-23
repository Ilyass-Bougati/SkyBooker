import java.util.*;


public class Main{
    public static void main(String[] args)
    {
        Buffer buffer = new Buffer();

        Thread Productor = new Thread(()->{
            for(int i = 0; i < 100 ; i++)
            {
                try {
                    Thread.sleep((new Random()).nextInt(1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try{
                    buffer.addInteger(i);
                }catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        });

        Thread Consumer = new Thread(()->{
            for(int i = 0; i < 100 ; i++)
            {
                try {
                    Thread.sleep((new Random()).nextInt(1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try{
                    System.out.println(buffer.pollInteger());
                }catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        });

        Productor.start();
        Consumer.start();
    }
}