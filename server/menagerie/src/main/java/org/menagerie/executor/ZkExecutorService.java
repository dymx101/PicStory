/*
 * Copyright 2010 Scott Fines
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.menagerie.executor;

import org.menagerie.Beta;
import org.menagerie.Serializer;
import org.menagerie.ZkCommandExecutor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Scott Fines
 *         Date: 12/16/11
 *         Time: 9:57 AM
 */
@Beta
public class ZkExecutorService<V> implements ExecutorService{
    private final ZkCommandExecutor commandExecutor;
    private final Serializer<Callable<V>> callableSerializer;

    private ZkExecutorService(ZkCommandExecutor commandExecutor, Serializer<Callable<V>> callableSerializer) {
        this.commandExecutor = commandExecutor;
        this.callableSerializer = callableSerializer;
    }

    @Override
    public void shutdown() {
        //TODO -sf- delete all watcher

    }

    @Override
    public List<Runnable> shutdownNow() {
        //TODO -sf- delete all watchers
        return null;
    }

    @Override
    public boolean isShutdown() {
        //TODO -sf- determine if shutdown
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return null;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return null;
    }

    @Override
    public Future<?> submit(Runnable task) {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void execute(Runnable command) {

    }
}
