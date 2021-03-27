package volatiledemo;

import java.util.concurrent.TimeUnit;

public class VolatileDemo {//VolatileDemo.class->jvm字节码


    public static void main(String[] args){
        //1.保证可见行
        visibleByVolatile();
        //2.不保证原子性（会出现丢失写值的情况）
        unActomicByVolatile();

    }

    public static void unActomicByVolatile() {
        Mythread01 mythread01 = new Mythread01();
        //20个线程
        for (int i = 1; i <=20; i++) {
            new Thread(()->{
                       for(int j = 1; j <=1000; j++){
                           mythread01.addplus();//不保证
                           mythread01.addAtomic();//保证
                       }
                    },String.valueOf(i)).start();
        }

        //需要等到以上20个线程都执行完成之后，使用主线程获取最终的结果是什么？
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"finally number = ="+mythread01.number);//结果并不是理想中的20000
    }

    //volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
    public static void visibleByVolatile() {
        Mythead t1 = new Mythead();//共享资源

        //t1线程
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t1.addTO60();
            System.out.println(Thread.currentThread().getName()+"执行后的number=="+t1.number);
        },"t1").start();

        while (t1.number==0) {
        }
        //主线程
        System.out.println(Thread.currentThread().getName()+"执行后的number=="+t1.number);
    }

}

class Mythead{
    //1.1假如 int number = 0; number变量之前根本没有添加volatile关键字修饰,结果：主线程接受不到t1线程修改之后的值，则就会在while处循环
    int  number = 0;
    //1.2number变量之前添加volatile关键字修饰，结果：主线程在while处不循环，顺利接受到t1线程修改之后的值
   // volatile int  number = 0;
    public void addTO60(){
        this.number = 60;
        }
        }

 class Mythread01{
    //1.1验证不保证原子性
     //原子性：不可分割，完整性，也就是说，某个线程正在做某个具体业务时，中间不可以被加塞或者被分割，需要整体完整。要么同时成功，要么同时失败
    volatile int  number = 0;

    public void addplus(){
        this.number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }
 }
