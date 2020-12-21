package com.shuailee;

import com.shuailee.spi.IMyCache;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ServiceLoader<IMyCache> myCaches = ServiceLoader.load(IMyCache.class);
        Iterator<IMyCache> iterator = myCaches.iterator();
        while (iterator.hasNext()) {
            IMyCache cache = iterator.next();
            System.out.println(cache.get("key"));
        }
    }




}
