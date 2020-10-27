/**  
* <p>Title: EsFunction.java</p>  
* <p>Description: </p>  
* @author shinelon  
* @date 2020年10月27日  
*/ 
package com.shinelon.deathknight.config.es;

import java.io.IOException;

@FunctionalInterface
public interface EsFunction<T, U, R> {

  R apply(T t, U u) throws IOException;
}
