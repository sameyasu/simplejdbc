package entity;

import simplejdbc.annotation.Column;
import simplejdbc.annotation.IncrementValue;
import simplejdbc.annotation.PrimaryKey;
import simplejdbc.annotation.Table;

import java.util.Date;

/**
 * GOODS_MSTテーブルのエンティティクラス
 * @author simpleJdbc (AutoGen)
 */
@Table(name="GOODS_MST")
public class GoodsMstDualPrimaryKey {

	@PrimaryKey
	@IncrementValue(sequence="SEQ_GOODS_ID")
	@Column(name="GOODS_ID")
	public Integer goodsId;

	@Column(name="PRICE")
	public Double price;

	@PrimaryKey
	@Column(name="GOODS_NAME")
	public String goodsName;

	@Column(name="REGIST_DATE")
	public Date registDate;

	@Column(name="UPDATE_DATE")
	public Date updateDate;

}
