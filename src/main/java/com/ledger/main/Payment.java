/**
 * 
 */
package com.ledger.main;

import lombok.Builder;
import lombok.Getter;

/**
 * @author mohsin
 *
 */

@Getter
@Builder
public class Payment {
	
	private Long lumsumAmount;
	private int emiNumber;	
	
}
