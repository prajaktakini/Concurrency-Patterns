package com.example.concurrency.multithreading.patterns.token.bucket.daemon.thread;

public class TokenBucketFilterFactory {

    /**
     * we don't want consumers to be able to instantiate the token bucket filter objects without interacting with the factory.
     * For this reason, we'll make the class MultithreadedTokenBucketFilter private and nest it within the factory class.
     */
    // Force users to interact with the factory only through the static methods
   private TokenBucketFilterFactory() {

   }

   public static TokenBucketFilter makeTokenBucketFilter(int maxTokens) {
       MultithreadedTokenBucketFilter tokenBucketFilter = new MultithreadedTokenBucketFilter(maxTokens);
       tokenBucketFilter.initialiseBackgroundThread();
       return tokenBucketFilter;
   }
}
