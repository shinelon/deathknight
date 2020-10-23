package com.shinelon.deathknight.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/***
 * 
 * @author syq
 *
 */
public class EsDateUtil {
	
	
	public static LocalDateTime getLocalDateTime(String esDateStr) {
		ZonedDateTime parse = ZonedDateTime.parse(esDateStr);
		return LocalDateTime.ofInstant(parse.toInstant(), ZoneId.systemDefault());
	}
	
	public static void main(String[] args) {
		System.out.println(getLocalDateTime("2020-10-21T09:08:35.532Z")); 
		System.out.println(ZoneId.getAvailableZoneIds() ); 
    }
}
