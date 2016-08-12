// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils.async;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class AsyncExecutor implements Disposable {
    private final ExecutorService executor;

    public AsyncExecutor(int maxConcurrent) {
        super();
        this.executor = Executors.newFixedThreadPool(maxConcurrent, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread v0 = new Thread(r, "AsynchExecutor-Thread");
                v0.setDaemon(true);
                return v0;
            }
        });
    }

    public void dispose() {  // has try-catch handlers
        this.executor.shutdown();
        try {
            this.executor.awaitTermination(9223372036854775807L, TimeUnit.SECONDS);
            return;
        }
        catch(InterruptedException v0) {
            throw new GdxRuntimeException("Couldn\'t shutdown loading thread", ((Throwable)v0));
        }
    }

    public AsyncResult submit(AsyncTask arg4) {
        if(this.executor.isShutdown()) {
            throw new GdxRuntimeException("Cannot run tasks on an executor that has been shutdown (disposed)");
        }

        return new AsyncResult(this.executor.submit(new Callable() {
            public Object call() throws Exception {
                return this.val$task.call();
            }
        }));
    }
}

