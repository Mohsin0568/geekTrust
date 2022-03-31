/**
 * 
 */
package com.ledger.main;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author mohsin
 *
 */

@Getter
@Builder
@ToString
public class Payment {
	
	private Long lumsumAmount;
	private int emiNumber;	
	
}
