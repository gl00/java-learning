package cn.itcast.multithread;

import java.util.concurrent.*;

/**
 * Created by Admin on 9/1/2016.
 */
public class CallableAndFutureTest {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        Future<String> future = threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello";
            }
        });

        try {
            String result = future.get();
            System.out.printf("Result:%s%n", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return finalI;
                }
            });
        }

        for (int i = 0; i < 10; i++) {
            try {
                // 按照最先完成的顺序返回
                Integer integer = completionService.take().get();
                System.out.printf("结果：%d%n", integer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
