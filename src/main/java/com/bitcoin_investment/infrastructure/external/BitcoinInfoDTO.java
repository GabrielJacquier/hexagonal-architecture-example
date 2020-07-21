package com.bitcoin_investment.infrastructure.external;

import java.util.Date;

public class BitcoinInfoDTO {
	private Payload ticker;

	public Payload getTicker() {
		return ticker;
	}

	public void setTicker(Payload ticker) {
		this.ticker = ticker;
	}

	public class Payload {
		private Double buy;
		private Double sell;
		private Double high;
		private Double low;
		private Double vol;
		private Double last;
		private Double open;
		private Date date;

		public Double getBuy() {
			return buy;
		}

		public void setBuy(Double buy) {
			this.buy = buy;
		}

		public Double getSell() {
			return sell;
		}

		public void setSell(Double sell) {
			this.sell = sell;
		}

		public Double getHigh() {
			return high;
		}

		public void setHigh(Double high) {
			this.high = high;
		}

		public Double getLow() {
			return low;
		}

		public void setLow(Double low) {
			this.low = low;
		}

		public Double getVol() {
			return vol;
		}

		public void setVol(Double vol) {
			this.vol = vol;
		}

		public Double getLast() {
			return last;
		}

		public void setLast(Double last) {
			this.last = last;
		}

		public Double getOpen() {
			return open;
		}

		public void setOpen(Double open) {
			this.open = open;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
	}



}
