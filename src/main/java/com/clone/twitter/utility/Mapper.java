package com.clone.twitter.utility;

public interface Mapper<S, T> {

	T transform(S source);

	S transformBack(T source);
}
