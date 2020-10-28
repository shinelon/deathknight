package com.shinelon.deathknight.config.es;

import java.io.IOException;

/*
 * <p>Title: EsFunction.java</p>
 * <p>Description: </p>
 *
 * @author
 * @date 2020年10月27日
 */

@FunctionalInterface
public interface EsFunction<T, U, R> {

  R apply(T t, U u) throws IOException;
}
