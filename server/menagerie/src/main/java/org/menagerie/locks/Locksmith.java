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
package org.menagerie.locks;

import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.menagerie.Beta;
import org.menagerie.ZkCommandExecutor;
import org.menagerie.ZkSessionManager;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Static factories for creating and using ZooKeeper-based Locks.
 *
 * <p>These methods wrap public constructors in specific classes, but
 * using those constructors is <em>not</em> recommended.
 */
@Beta
public class Locksmith {
    /**
     * Creates a new reentrant lock instance.
     *
     * <p>The returned lock will satisfy the {@link java.util.concurrent.locks.Lock} specification,
     * including all optional methods.
     *
     * @param sessionManager a ZooKeeper Session manager
     * @param lockPath the lock path
     * @return a ZooKeeper lock
     */
    public static final Lock reentrantLock(ZkSessionManager sessionManager, String lockPath) {
        return new ReentrantZkLock2(lockPath, sessionManager, ZooDefs.Ids.OPEN_ACL_UNSAFE);
    }

    /**
     * Creates a new reentrant lock instance.
     *
     * <p>The returned lock will satisfy the {@link java.util.concurrent.locks.Lock} specification,
     * including all optional methods.
     *
     * @param exec a ZkCommandExecutor
     * @param lockPath the lock path
     * @return a ZooKeeper lock
     */
    public static final Lock reentrantLock( String lockPath, ZkCommandExecutor exec,List<ACL> privileges){
        return new ReentrantZkLock2(lockPath, exec, privileges);
    }

    /**
     * Creates a new reentrant lock instance.
     *
     * <p>The returned lock will satisfy the {@link java.util.concurrent.locks.Lock} specification,
     * including all optional methods.
     *
     * @param sessionManager the session manager of interest
     * @param lockPath the lock path
     * @param privileges the privileges for the lock node
     * @return a ZooKeeper lock
     */
    public static final Lock reentrantLock(ZkSessionManager sessionManager, String lockPath, List<ACL> privileges){
        return new ReentrantZkLock2(lockPath, sessionManager, privileges);
    }


    public static final ReadWriteLock readWriteLock(ZkSessionManager sessionManager,String lockPath,List<ACL> privileges){
        return new ReentrantZkReadWriteLock2(lockPath,sessionManager,privileges);
    }

    public static final ReadWriteLock readWriteLock(ZkSessionManager sessionManager,String lockPath){
        return new ReentrantZkReadWriteLock2(lockPath,sessionManager);
    }

    public static final ReadWriteLock readWriteLock(ZkCommandExecutor commandExecutor,String lockPath,List<ACL> privileges){
        return new ReentrantZkReadWriteLock2(lockPath,commandExecutor,privileges);
    }




}
