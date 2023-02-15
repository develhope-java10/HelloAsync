import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();

        for(int t = 0; t < 100; ++t) {
            System.out.println(" --- ");
            List<Future<Integer>> futures = new ArrayList<>();
            futures.add(pool.submit(() -> 5 + 2));
            futures.add(pool.submit(() -> 5 * 2));
            futures.add(pool.submit(() -> 5 - 2));

            while(futures.size() > 0) {
                var iterator = futures.listIterator();
                while(iterator.hasNext()) {
                    var future = iterator.next();
                    if(future.isDone()) {
                        System.out.println("Got result: " + future.get());
                        iterator.remove();
                    }
                }
            }

            Thread.sleep(100);
        }

        pool.shutdown();
    }
}
