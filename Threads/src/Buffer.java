import java.util.* ;

public class Buffer {
    private boolean isFull = false;
    private boolean isEmpty = true;
    Queue<Integer> sharedList ;


    public Buffer()
    {
        this.sharedList = new LinkedList<>();
    }

    public synchronized void addInteger(Integer i) throws Exception
    {
        if(isFull)
        {
            System.out.println("Producer is waiting !");
            wait();
        }
        sharedList.add(i);
        if(isEmpty)
        {
            isEmpty = false;
            notify();
        }
        if(sharedList.size() <= 10)
        {
            isFull = true;
        }
    }

    public synchronized Integer pollInteger() throws Exception
    {
        if(isEmpty)
        {
            System.out.println("Consumer is waiting !");
            wait();
        }
        Integer out = sharedList.poll();
        if(isFull)
        {
            isFull = false;
            notify();
        }
        if(sharedList.isEmpty())
        {
            isEmpty = true;
        }
        return out;
    }

}
